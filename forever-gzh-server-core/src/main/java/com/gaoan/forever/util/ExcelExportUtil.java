package com.gaoan.forever.util;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaoan.forever.base.AppException;
import com.gaoan.forever.base.IBaseService;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.constant.ForeverConstant;
import com.gaoan.forever.log.spring.ExportColComment;
import com.gaoan.forever.utils.date.DateUtil;
import com.github.pagehelper.PageInfo;

public class ExcelExportUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelExportUtil.class);

    private static final String CHATSET_UTF8 = "UTF-8";

    private static final int MAX_ROW = 60000;

    @SuppressWarnings("resource")
    public static <V, E> void export(HttpServletResponse response, List<String> includeCols,
	    IBaseService<V> baseService, E entity, String fileName) {
	int pageNum = 1;
	int pageSize = 1000;

	PageInfo<V> page = null;
	List<V> list = null;
	OutputStream output = null;
	int sheetRow = 1; // 当前sheet页行数
	try {
	    response.setContentType("application/msexcel");
	    // response.setContentType("application/force-download");
	    response.setCharacterEncoding(CHATSET_UTF8);
	    response.setHeader("Content-Disposition", "attachment; filename="
		    + URLEncoder.encode(fileName + DateUtil.dateFormatY1(new Date()) + ".xls", CHATSET_UTF8));
	    output = response.getOutputStream();
	    HSSFWorkbook wb = new HSSFWorkbook();
	    // SXSSFWorkbook wb = new SXSSFWorkbook();
	    // XSSFWorkbook wb = new XSSFWorkbook();

	    HSSFSheet sheet = wb.createSheet(fileName);
	    // XSSFSheet sheet = wb.createSheet(fileName + pageNum);
	    do {
		page = baseService.queryExportData(entity, pageNum, pageSize);
		list = page.getList();
		logger.info("批量导出excel文件处理中... 此页{}条, 当前第{}页, 共{}页, 每页{}条.", list == null ? "0" : list.size(), pageNum,
			page.getPages(), pageSize);
		sheetRow = batchExportData(sheet, list, includeCols, pageNum, sheetRow);
		pageNum++;
		// if (sheetRow > MAX_ROW) {
		// sheet = wb.createSheet(fileName + pageNum);
		// sheetRow = 1;
		// }
	    } while (page != null && !page.isIsLastPage());

	    // response.reset();
	    logger.error("批量导出excel:{}", fileName);

	    // 创建可写入的Excel工作薄，且内容将写入到输出流，并通过输出流输出给客户端浏览
	    wb.write(output);
	    output.flush();
	    output.close();
	} catch (Exception e) {
	    logger.error("导出Excel 异常！", e);
	}
    }

    /**
     * 批量导出数据
     * 
     * @param out
     *            输出流
     * @param list
     *            导出的数据
     * @param includeCols
     *            导出的属性名
     * @param pageNum
     *            页码
     * @param flag
     *            单元格内容后缀标识 true为支持完全展示 false为不支持完全展示
     * @throws Exception
     */
    private static <T> int batchExportData(HSSFSheet sheet, List<T> list, List<String> includeCols, int pageNum,
	    int sheetRow) throws Exception {
	if (CollectionUtils.isEmpty(includeCols)) {
	    logger.error("导出的列属性集合是空.");
	    throw new AppException(MessageInfoConstant.COMMON_ERROR_ILLEGAL_ARGUMENT_EXCEPTION);
	}

	if (CollectionUtils.isEmpty(list)) {
	    logger.info("导出的数据是空.");
	    return sheetRow;
	}

	T headeObj = list.get(0);
	Map<String, Field> map = new HashMap<String, Field>();
	Class<? extends Object> clazz = headeObj.getClass();
	List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
	List<Field> parentFields = Arrays.asList(clazz.getSuperclass().getDeclaredFields());
	transMap(map, fields);
	transMap(map, parentFields);

	Field field;

	// 创建列头
	if (pageNum == 1 && headeObj != null) {
	    HSSFRow header = sheet.createRow(0);
	    // XSSFRow header = sheet.createRow(0);
	    ExportColComment ecc;
	    for (int i = 0; i < includeCols.size(); i++) {
		if (map.containsKey(includeCols.get(i))) {
		    field = map.get(includeCols.get(i));
		    ecc = field.getDeclaredAnnotation(ExportColComment.class);
		    header.createCell(i).setCellValue(ecc == null ? "" : ecc.value());
		} else {
		    logger.info(clazz.getName() + " property [" + includeCols.get(i) + "] is not found.");
		}
	    }
	}

	Object value = null;
	String str = null;
	BigDecimal bd;

	int rownum = sheetRow;
	// 输出数据
	for (T obj : list) {
	    if (obj == null) {
		continue;
	    }
	    HSSFRow row = sheet.createRow(rownum);
	    // XSSFRow row = sheet.createRow(rownum);
	    rownum++;
	    for (int i = 0; i < includeCols.size(); i++) {
		if (map.containsKey(includeCols.get(i))) {
		    field = map.get(includeCols.get(i));
		    value = field.get(obj);
		    if (field.getType() == Date.class) {
			// 日期格式处理
			str = (value == null) ? getValue(clazz, obj, field.getName())
				: DateUtil.dateFormat1((Date) value);
		    } else if (field.getType() == BigDecimal.class) {
			// BigDecimal格式处理
			bd = (BigDecimal) value;
			str = (bd == null) ? getValue(clazz, obj, field.getName()) : bd.toPlainString();
		    } else {
			// 加上\t,防止显示科学计数
			str = (value == null) ? getValue(clazz, obj, field.getName()) : (value.toString());
		    }
		    row.createCell(i).setCellValue(str);
		}
	    }
	}

	return rownum;
    }

    /**
     * 将类属性List转换成Map
     * 
     * @param map
     * @param fields
     *            类属性List
     * @return
     */
    private static void transMap(Map<String, Field> map, List<Field> fields) {
	if (map == null) {
	    map = new HashMap<String, Field>();
	}
	if (CollectionUtils.isEmpty(fields)) {
	    return;
	}
	for (Field field : fields) {
	    field.setAccessible(true);
	    map.put(field.getName(), field);
	}
    }

    /**
     * 获取BigDecimal值
     * 
     * @param clazz
     * @param obj
     * @param filedName
     * @return
     */
    private static <T> String getValue(Class<?> clazz, T obj, String filedName) {
	Object value = getFieldValue(clazz, obj, filedName);
	String str;
	if (value == null) {
	    str = "";
	} else {
	    if (value instanceof BigDecimal) {
		str = ((BigDecimal) value)
			.setScale(ForeverConstant.VIRTUAL_CURRENCY_DECIMALCOUNT, BigDecimal.ROUND_UP).toString();
	    } else {
		str = (String) value;
	    }
	}
	return str;
    }

    /**
     * 通过get方法获得属性值
     * 
     * @param clazz
     * @param obj
     * @param filedName
     * @return
     */
    private static <T> Object getFieldValue(Class<?> clazz, T obj, String filedName) {
	if (clazz == null || obj == null || StringUtils.isEmpty(filedName)) {
	    return null;
	}
	Object value = null;
	try {
	    Method method = clazz.getMethod("get" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1));
	    if (method != null) {
		value = method.invoke(obj, new Object[] {});
	    }
	} catch (Exception e) {
	    logger.error("获取get{}方法出错", filedName);
	}
	return value;
    }
}
