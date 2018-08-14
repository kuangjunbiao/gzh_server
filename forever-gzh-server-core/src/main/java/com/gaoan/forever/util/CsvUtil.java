package com.gaoan.forever.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.gaoan.forever.base.AppException;
import com.gaoan.forever.base.IBaseService;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.constant.ForeverConstant;
import com.gaoan.forever.log.spring.ExportColComment;
import com.gaoan.forever.utils.date.DateUtil;
import com.gaoan.forever.utils.file.FastDFSFileUtil;
import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.domain.StorePath;

/**
 * Created by kuangjunbiao on 2017/11/18.
 */
@Component
public class CsvUtil {

	private static final Logger logger = LoggerFactory.getLogger(CsvUtil.class);

	private static final String SEPARATOR = ",";

	private static final String TAB = "\t";

	private static final String CHATSET_UTF8 = "UTF-8";

	private static final String CHATSET_GBK = "gbk";

	public static String tempFilePath;

	@Value("${turnOutExport.tempFilePath:/data/trade/trade-backstage/csv_tmp}")
	public void setTempFilePath(String tempFilePath) {
		CsvUtil.tempFilePath = tempFilePath;
	}

	/**
	 * 导出csv文件
	 * 
	 * @param response
	 * @param includeCols
	 *            导出列属性(按顺序)
	 * @param baseService
	 *            业务逻辑处理接口
	 * @param entity
	 *            查询实体类
	 * @param fileName
	 *            导出文件名称
	 * @param flag
	 *            单元格内容后缀标识 true为支持完全展示 false为不支持完全展示,且保存导出文件到服务器
	 * @return 服务器保存导出文件的路径
	 */
	public static <V, E> Map<String, String> exportCsvFile(HttpServletRequest request, HttpServletResponse response,
			List<String> includeCols, IBaseService<V> baseService, E entity, String fileName, boolean flag) {
		logger.info("导出CSV文件开始...");

		ServletOutputStream out = null;
		FileOutputStream fileOut = null;
		int pageNum = 1;
		int pageSize = 1000;

		PageInfo<V> page = null;
		List<V> list = null;

		File file = null;
		StorePath storePath = null;
		String csvFileName = fileName + DateUtil.format(new Date(), "yyyy年MM月dd日HH时mm分ss秒") + ".csv";

		try {
			String userAgent = request.getHeader("USER-AGENT");
			logger.info("USER-AGENT:{}", userAgent);
			String finalFileName;

			if (StringUtils.contains(userAgent, "Firefox")) {// 火狐浏览器
				finalFileName = new String(csvFileName.getBytes(), "ISO8859-1");
			} else {
				finalFileName = URLEncoder.encode(csvFileName, CHATSET_UTF8);// 其他浏览器
			}

			// response.reset();
			response.setContentType("application/csv; charset=" + CHATSET_GBK);
			response.setCharacterEncoding(CHATSET_GBK);
			response.setHeader("Content-Disposition", "attachment; filename=" + finalFileName);
			out = response.getOutputStream();

			// 重新生成一份文件保存到服务器
			if (!flag) {
				file = new File(tempFilePath + "/" + csvFileName);
				file.getParentFile().mkdirs();
				if (file.exists()) {
					file.delete();
				}
				file.createNewFile();
				fileOut = new FileOutputStream(file);
			}

			// 批量处理导出的数据
			do {
				page = baseService.queryExportData(entity, pageNum, pageSize);
				list = page.getList();
				logger.info("批量导出CSV文件处理中... 此页{}条, 当前第{}页, 共{}页, 每页{}条.", list == null ? "0" : list.size(), pageNum,
						page.getPages(), pageSize);
				batchExportData(out, fileOut, list, includeCols, entity, pageNum, flag);
				pageNum++;
			} while (page != null && !page.isIsLastPage());

			// 上传临时文件
			if (!flag) {
				storePath = FastDFSFileUtil.uploadFile(file);
			}

		} catch (Exception e) {
			logger.error("导出CSV文件出错.", e);
			if (e instanceof AppException) {
				throw (AppException) e;
			} else {
				throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
			}
		} finally {
			try {
				if (fileOut != null) {
					fileOut.close();
				}
				if (file != null && file.exists()) {
					file.delete();
				}
			} catch (IOException e) {
				logger.error("csv备份文件IO异常.", e);
			}
			logger.info("导出CSV文件结束...");
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("fileName", csvFileName);
		map.put("filePath", storePath != null ? FastDFSFileUtil.getResAccessUrl(storePath) : "");
		return map;
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
	private static <T, E> void batchExportData(OutputStream out, FileOutputStream fileOut, List<T> list,
			List<String> includeCols, E entity, int pageNum, boolean flag) throws Exception {
		if (CollectionUtils.isEmpty(includeCols)) {
			logger.error("导出的列属性集合是空.");
			throw new AppException(MessageInfoConstant.COMMON_ERROR_ILLEGAL_ARGUMENT_EXCEPTION);
		}

		if (CollectionUtils.isEmpty(list)) {
			logger.info("导出的数据是空.");
			out.write("".getBytes(CHATSET_GBK));
			return;
		}

		T titleObj = list.get(0);
		Map<String, Field> map = new HashMap<String, Field>();

		Class<? extends Object> clazz = titleObj.getClass();
		List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
		List<Field> parentFields = Arrays.asList(clazz.getSuperclass().getDeclaredFields());
		transMap(map, fields);
		transMap(map, parentFields);

		Field field;
		String str = null;

		// 输出列名,第一页才需要
		if (pageNum == 1 && titleObj != null) {
			ExportColComment ecc;
			String colName;
			for (int i = 0; i < includeCols.size(); i++) {
				colName = includeCols.get(i);
				if (map.containsKey(colName)) {
					// 特殊处理,主币地址列名是动态生成
					if ("mainAddr".equals(colName)) {
						str = getMainAddrColName(entity, titleObj);
					} else {
						field = map.get(colName);
						ecc = field.getDeclaredAnnotation(ExportColComment.class);
						str = (ecc == null) ? "" : ecc.value();
					}
					printContent(out, fileOut, str);
					if (i != includeCols.size() - 1) {
						str = SEPARATOR;
						printContent(out, fileOut, str);
					}
				} else {
					logger.info(clazz.getName() + " property [" + colName + "] is not found.");
				}
			}
			// 输出列头后换行
			str = "\r\n";
			printContent(out, fileOut, str);
		}

		String suffix = flag ? TAB : "";

		Object value = null;
		BigDecimal bd;
		// 输出数据
		for (T obj : list) {
			if (obj == null) {
				continue;
			}
			for (int i = 0; i < includeCols.size(); i++) {
				if (map.containsKey(includeCols.get(i))) {
					field = map.get(includeCols.get(i));
					value = field.get(obj);
					if (field.getType() == Date.class) {
						// 日期格式处理
						str = (value == null) ? getValue(clazz, obj, field.getName()) + suffix
								: DateUtil.dateFormat1((Date) value) + suffix;
					} else if (field.getType() == BigDecimal.class) {
						// BigDecimal格式处理
						bd = (BigDecimal) value;
						str = (bd == null) ? getValue(clazz, obj, field.getName()) + suffix
								: bd.setScale(ForeverConstant.VIRTUAL_CURRENCY_DECIMALCOUNT, BigDecimal.ROUND_UP)
										.toString() + suffix;
					} else {
						// 加上\t,防止显示科学计数
						str = (value == null) ? getValue(clazz, obj, field.getName()) + suffix
								: (StringUtils.trim(value.toString()) + suffix);
					}
					// 替换换行符，并转成字节
					str = str.replaceAll("\r\n", "").replaceAll("\n", "");
					printContent(out, fileOut, str);
					if (i != includeCols.size() - 1) {
						str = SEPARATOR;
						printContent(out, fileOut, str);
					}
				}
			}
			// 输出一行数据后换行
			str = "\r\n";
			printContent(out, fileOut, str);
		}
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
				str = ((BigDecimal) value).setScale(ForeverConstant.VIRTUAL_CURRENCY_DECIMALCOUNT, BigDecimal.ROUND_UP)
						.toString();
			} else {
				str = (String) value;
			}
		}
		return StringUtils.trim(str);
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

	/**
	 * 动态获取主币地址的列名
	 * 
	 * @param entity
	 * @param obj
	 * @return
	 */
	private static <E, T> String getMainAddrColName(E entity, T obj) {
		String colName = "主币地址";
		if (entity == null || obj == null) {
			return colName;
		}
		Class<?> entityClazz = entity.getClass();
		Class<?> objClazz = obj.getClass();
		try {
			Field coinTypeField = entityClazz.getDeclaredField("coinType");
			coinTypeField.setAccessible(true);
			Integer coinType = (Integer) coinTypeField.get(entity);
			// 当页面筛选币种条件的时候,主币地址列动态显示
			if (coinType != null) {
				Field parentCoinNameField = objClazz.getDeclaredField("parentCoinName");
				parentCoinNameField.setAccessible(true);
				String parentCoinName = (String) parentCoinNameField.get(obj);
				if (StringUtils.isNotEmpty(parentCoinName)) {
					colName = parentCoinName.toUpperCase() + "地址";
				} else {
					Field coinNameField = objClazz.getDeclaredField("coinName");
					coinNameField.setAccessible(true);
					if ("LCC".equals(coinNameField.get(obj).toString().toUpperCase())) {
						colName = "LCC地址";
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取主币地址列名出错.", e);
			colName = "主币地址";
		}
		return colName;
	}

	private static void printContent(OutputStream out, OutputStream writer, String str) throws IOException {
		if (out != null) {
			out.write(str.getBytes(CHATSET_GBK));
		}
		if (writer != null) {
			writer.write(str.getBytes(CHATSET_GBK));
		}
	}

	/**
	 * 导入出金处理结果文件
	 * 
	 * @param file
	 * @param tbWithdrawalRecordService
	 * @return
	 */
	public static Map<String, Object> importByTurnOut(MultipartFile file) {
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		int successCount = 0;
		int failCount = 0;
		try {
			InputStreamReader isr = new InputStreamReader(file.getInputStream(), CHATSET_UTF8);
			BufferedReader br = new BufferedReader(isr);
			String str = null;
			String[] arrs = null;
			str = br.readLine();// 读取列头
			if (StringUtils.isNotEmpty(str) && str.split(SEPARATOR).length != 7) {
				logger.error("{} 参数格式不对.", str);
				throw new AppException(MessageInfoConstant.PARAM_FORMAT_EXIST);
			}
			while ((str = br.readLine()) != null) {
				total++;
				arrs = str.split(SEPARATOR);
				try {
					// tbWithdrawalRecordService.processImportResult(arrs);
					successCount++;
				} catch (Exception e) {
					failCount++;
					logger.error("出金订单号:{}处理错误,{}", arrs[3] != null ? arrs[3] : "", e.getMessage());
				}
			}
		} catch (Exception e) {
			logger.error("出金处理失败.", e);
		}

		map.put("total", String.valueOf(total));
		map.put("successCount", String.valueOf(successCount));
		map.put("failCount", String.valueOf(failCount));
		return map;
	}

	/**
	 * 校验导出数据是否为空
	 * 
	 * @param baseService
	 * @param entity
	 */
	public static <V, E> void validateData(IBaseService<V> baseService, E entity) {
		PageInfo<V> page = baseService.queryExportData(entity, 1, 100);
		if (page != null && page.getTotal() == 0) {
			logger.error("导出的数据为空.");
			throw new AppException(MessageInfoConstant.EXPORT_DATA_IS_EMPTY);
		}
	}
}
