package com.royal.admin.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.royal.admin.core.common.exception.BizExceptionEnum;
import com.royal.admin.modular.api.entity.TopicModel;
import com.royal.admin.modular.api.json.TopicJson;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;


/**
 * 从EXCEL导入到数据库 创建人：FH 创建时间：2014年12月23日
 */
public class ObjectExcelRead {

    /**
     * @param filepath //文件路径
     * @param filename //文件名
     * @param startrow //开始行号
     * @param startcol //开始列号
     * @param sheetnum //sheet
     * @return list
     */
    public static List<Map<String, Object>> readExcel(String filepath, String filename,
                                                      int startrow, int startcol, int sheetnum) {
        List<Map<String, Object>> varList = new ArrayList<Map<String, Object>>();

        try {
            File target = new File(filepath, filename);
            FileInputStream fi = new FileInputStream(target);
            varList = getList(fi, startrow, startcol, sheetnum, varList);
        } catch (Exception e) {
            System.out.println(e);
        }
        return varList;
    }

    /**
     * @param file//文件名
     * @param startrow  //开始行号
     * @param startcol  //开始列号
     * @param sheetnum  //sheet
     * @return list
     */
    public static List<Map<String, Object>> readExcel(MultipartFile file,
                                                      int startrow, int startcol, int sheetnum) {
        List<Map<String, Object>> varList = new ArrayList<Map<String, Object>>();

        try {
            FileInputStream fi = (FileInputStream) file.getInputStream();
            varList = getList(fi, startrow, startcol, sheetnum, varList);
        } catch (Exception e) {
            System.out.println(e);
        }
        return varList;
    }

    private static List<Map<String, Object>> getList(FileInputStream fi, int startrow, int startcol, int sheetnum, List<Map<String, Object>> varList) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook(fi);
        XSSFSheet sheet = wb.getSheetAt(sheetnum); // sheet 从0开始
        int rowNum = sheet.getLastRowNum() + 1; // 取得最后一行的行号

        for (int i = startrow; i < rowNum; i++) { // 行循环开始

            Map<String, Object> varpd = new HashMap<>();
            XSSFRow row = sheet.getRow(i); // 行
            int cellNum = row.getLastCellNum(); // 每行的最后一个单元格位置

            for (int j = startcol; j < cellNum; j++) { // 列循环开始

                XSSFCell cell = row.getCell(Short.parseShort(j + ""));
                String cellValue = null;
                if (null != cell) {
                    switch (cell.getCellType()) { // 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
                        case 0:
                            cellValue = String.valueOf((long) cell
                                    .getNumericCellValue());
                            break;
                        case 1:
                            cellValue = cell.getStringCellValue();
                            break;
                        case 2:
                            cellValue = cell.getNumericCellValue() + "";
                            // cellValue =
                            // String.valueOf(cell.getDateCellValue());
                            break;
                        case 3:
                            cellValue = "";
                            break;
                        case 4:
                            cellValue = String.valueOf(cell
                                    .getBooleanCellValue());
                            break;
                        case 5:
                            cellValue = String
                                    .valueOf(cell.getErrorCellValue());
                            break;
                    }
                } else {
                    cellValue = "";
                }

                varpd.put("var" + j, cellValue);

            }
            varList.add(varpd);
        }
        return varList;
    }

    /**
     * 批量导入题库
     *
     * @param file
     * @return
     */
    public static List<TopicModel> batchTopicJson(MultipartFile file) {
        List<Map<String, Object>> obj = ObjectExcelRead.readExcel(file, 2, 0,
                0);
        List<TopicModel> classList = new ArrayList<TopicModel>();
        for (Map<String, Object> object : obj) {
            boolean b = false;
            if (ToolUtil.isOneEmpty(object.get("var0"), object.get("var1"), object.get("var2"))) {
                continue;
            }
            String[] optionsArr = String.valueOf(object.get("var1")).split(";");
            for (String options : optionsArr) {
                System.out.println("options" + options + "-------------" + object.get("var2"));
                if (options.equals(String.valueOf(object.get("var2")))) {
                    b = true;
                    break;
                }
            }
            if (b) {
                TopicModel topicJson = new TopicModel();
                topicJson.setTitle(String.valueOf(object.get("var0")));
                topicJson.setOptions(String.valueOf(object.get("var1")));
                topicJson.setAnswer(String.valueOf(object.get("var2")));
                classList.add(topicJson);
            }
        }
        return classList;
    }
}
