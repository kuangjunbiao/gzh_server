package com.gaoan.forever.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

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

import com.google.common.collect.Maps;

/**
 * Created by DELL on 2017/9/16.
 */
public class ExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    private static Map<Long,String> reqExMap = Maps.newConcurrentMap();
    
    private static final String reqEx = "^[0-9|a-z|A-Z]{33,34}$";
    static{
    }

    public static Map<String, Object> readXml(MultipartFile file,Long coinId) {
    	String reqex = reqExMap.containsKey(coinId)?reqExMap.get(coinId):reqEx;
    	Pattern pattern = Pattern.compile(reqex);
        Map<String, Object> map = new HashMap<String, Object>();
        Set<String> adds = new HashSet<String>();
        boolean isE2007 = false;    //判断是否是excel2007格式
        int num = 0;//有效行
        int total = 0;//获取的总行数
        int error = 0;//错误行或者无效行
        Integer currRowNum = null;
        if (file.getOriginalFilename().endsWith("xlsx"))
            isE2007 = true;
        try {
            //建立输入流
            Workbook wb = null;
            FormulaEvaluator formulaEvaluator = null;
            //根据文件格式(2003或者2007)来初始化
            if (isE2007) {
                wb = new XSSFWorkbook(file.getInputStream());
                formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) wb);
            } else {
                wb = new HSSFWorkbook(file.getInputStream());
                formulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);
            }
            Sheet sheet = wb.getSheetAt(0);        //获得第一个表单
            Iterator<Row> rows = sheet.rowIterator();    //获得第一个表单的迭代器
            while (rows.hasNext()) {
            	total++;
                Row row = rows.next();    //获得行数据   //获得行号从0开始
                currRowNum = row.getRowNum();
                if (row.getRowNum() == 0) {
                    continue;
                }
                Cell cell = row.getCell(0);
                String cellValue = null;
                if (cell != null) {
                    //根据cell中的类型来输出数据
                    switch (cell.getCellTypeEnum()) {
                        case _NONE:
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                //  如果是date类型则 ，获取该cell的date值
                                cellValue = DateUtil.getJavaDate(cell.getNumericCellValue()).toString();
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
                }
                if (cellValue != null && pattern.matcher(cellValue.trim()).matches()) {
                	num++;
                    adds.add(cellValue.trim());
                } else {
                	error++;
                    logger.info("cellValue:[" + cellValue + "] length: " + (cellValue!=null?cellValue.length():0));
                }
            }
        } catch (Exception ex) {
            logger.error("excel parse error,错误行："+(currRowNum!=null?(currRowNum+1):"")+"--"+ex.getMessage(), ex);
        }
        map.put("total", total);
        map.put("errorCount", error);
        map.put("num", num);
        map.put("adds", adds);
        return map;
    }
}
