package com.gaoan.forever.util;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.gaoan.forever.constant.ForeverConstant;
import com.gaoan.forever.utils.file.FastDFSFileUtil;
import com.github.tobato.fastdfs.domain.StorePath;

/**
 * 入金批量导入 Created by NO.9527 on 2017年11月16日
 */
public class ExcelImputCoinUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelImputCoinUtil.class);

    private static final int batchCountMax = 1000;

    public static Map<String, Object> importCoin() {
	MultipartFile file = null;
	Map<String, Object> map = new HashMap<String, Object>();
	boolean isE2007 = false; // 判断是否是excel2007格式
	int total = 0;// 获取的总行数
	int error = 0;// 错误行或者无效行
	int successCount = 0;
	Integer currRowNum = null;

	List<String> flowIdlist = new ArrayList<String>();
	// 不是excel格式，报错
	if (!file.getOriginalFilename().endsWith("xlsx") && !file.getOriginalFilename().endsWith("xls")
		&& !file.getOriginalFilename().endsWith("csv")) {
	    logger.info("文件格式不对.");
	    map = null;
	    return map;
	}

	if (file.getOriginalFilename().endsWith("xlsx") || file.getOriginalFilename().endsWith("csv")) {
	    isE2007 = true;
	}
	try {
	    // 建立输入流
	    Workbook wb = null;
	    FormulaEvaluator formulaEvaluator = null;
	    // 根据文件格式(2003或者2007)来初始化
	    if (isE2007) {
		wb = new XSSFWorkbook(file.getInputStream());
		formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) wb);
	    } else {
		wb = new HSSFWorkbook(file.getInputStream());
		formulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);
	    }

	    Date cdate = new Date();
	    String bn = "";

	    // Map<String,List<BigDecimal>> coinmap = new
	    // HashMap<String,List<BigDecimal>>();
	    Map<String, List<Map<String, Object>>> coinmap = new HashMap<String, List<Map<String, Object>>>();
	    Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
	    Iterator<Row> rows = sheet.rowIterator(); // 获得第一个表单的迭代器
	    int lastrowNum = sheet.getLastRowNum();
	    int batchCount = 0;
	    while (rows.hasNext()) {
		Row row = rows.next(); // 获得行数据 //获得行号从0开始
		currRowNum = row.getRowNum();
		if (null == row.getCell(7)) {
		    row.createCell(7);
		}
		if (row.getRowNum() == 0) {
		    row.getCell(7).setCellValue("状态");
		    continue;
		}
		total++;
		// 必要的列
		String isconfirm = parseCellVelue(row.getCell(0), formulaEvaluator);
		if (StringUtils.isBlank(isconfirm)) {
		    error++;
		    row.getCell(7).setCellValue("已确认为空");
		    continue;
		}
		// 判断是否确认未确认不入库
		if (!"true".equalsIgnoreCase(isconfirm)) {
		    error++;
		    row.getCell(7).setCellValue("未确认");
		    continue;
		}
		String address = parseCellVelue(row.getCell(4), formulaEvaluator);
		if (StringUtils.isBlank(address)) {
		    error++;
		    row.getCell(7).setCellValue("地址为空");
		    continue;
		}
		address = address.trim();

		String amount = parseCellVelue(row.getCell(5), formulaEvaluator);
		if (StringUtils.isBlank(amount) || !NumberUtils.isNumber(amount)) {
		    error++;
		    row.getCell(7).setCellValue("金额为空或不是数字");
		    continue;
		}

		amount = new BigDecimal(amount).toPlainString();
		// 非必要的列
		String date = parseCellVelue(row.getCell(1), formulaEvaluator);
		String type = parseCellVelue(row.getCell(2), formulaEvaluator);
		String leble = parseCellVelue(row.getCell(3), formulaEvaluator);
		String id = parseCellVelue(row.getCell(6), formulaEvaluator);
		logger.info("address:{}---amount:{}----isconfirm:{}---date:{}---type:{}--leble:{}---id:", address,
			amount, isconfirm, date, type, leble, id);

		if (StringUtils.isBlank(id)) {
		    row.getCell(7).setCellValue("id为空");
		    continue;
		}

		flowIdlist.add(id.trim());
		row.getCell(7).setCellValue("未找到入金地址");
		// 入金金额
		BigDecimal coinAmout = new BigDecimal(amount)
			.setScale(ForeverConstant.VIRTUAL_CURRENCY_DECIMALCOUNT, BigDecimal.ROUND_UP);
		if (coinmap.containsKey(address)) {
		    Map<String, Object> mdata = new HashMap<String, Object>();
		    mdata.put("coinAmout", coinAmout);
		    mdata.put("id", id.trim());
		    mdata.put("row", row);
		    coinmap.get(address).add(mdata);
		} else {
		    List<Map<String, Object>> clist = new ArrayList<Map<String, Object>>();
		    Map<String, Object> mdata = new HashMap<String, Object>();
		    mdata.put("coinAmout", coinAmout);
		    mdata.put("id", id.trim());
		    mdata.put("row", row);
		    clist.add(mdata);
		    coinmap.put(address.trim(), clist);
		}

		batchCount++;
	    }

	    File excelFile = new File(file.getOriginalFilename());
	    FileOutputStream out = new FileOutputStream(excelFile);
	    wb.write(out);
	    StorePath resultPath = FastDFSFileUtil.uploadFile(excelFile);
	    StorePath storePath = FastDFSFileUtil.uploadFile(file);
	    logger.info("file:{}", FastDFSFileUtil.getResAccessUrl(resultPath));
	    map.put("total", total);
	    map.put("batchNumber", bn);
	    map.put("errorCount", error);
	    map.put("successCount", successCount);
	    map.put("resultPath", resultPath.getFullPath());
	    map.put("filePath", storePath.getFullPath());
	    map.put("resultFile", FastDFSFileUtil.getResAccessUrl(resultPath));
	} catch (Exception ex) {
	    map = null;
	    logger.error(
		    "excel parse error,错误行：" + (currRowNum != null ? (currRowNum + 1) : "") + "--" + ex.getMessage(),
		    ex);
	}
	return map;
    }

    private static String parseCellVelue(Cell cell, FormulaEvaluator formulaEvaluator) {
	String cellValue = null;
	if (cell != null) {
	    try {
		// 根据cell中的类型来输出数据
		switch (cell.getCellTypeEnum()) {
		case _NONE:
		    break;
		case NUMERIC:
		    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
			// 如果是date类型则 ，获取该cell的date值
			cellValue = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue())
				.toString();
		    } else {
			// 纯数字
			cellValue = String.valueOf(cell.getNumericCellValue());
		    }
		    break;
		case STRING:
		    cellValue = cell.getStringCellValue();
		    break;
		case FORMULA:
		    cellValue = String.valueOf(formulaEvaluator.evaluate(cell).getNumberValue());
		    break;
		case BLANK:
		    break;
		case BOOLEAN:
		    cellValue = String.valueOf(cell.getBooleanCellValue());
		    break;
		case ERROR:
		    cellValue = String.valueOf(cell.getErrorCellValue());
		    break;
		default:
		    break;
		}
	    } catch (Exception e) {
		logger.error("获得某一列内容异常！", e);
	    }
	}
	return cellValue;
    }
}
