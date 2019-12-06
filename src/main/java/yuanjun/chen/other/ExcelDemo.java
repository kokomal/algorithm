/**
 * @Title: ExcelDemo.java
 * @Package: yuanjun.chen.other
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2019年12月6日 上午11:32:56
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.other;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @ClassName: ExcelDemo
 * @author: 陈元俊
 * @date: 2019年12月6日 上午11:32:56
 */
public class ExcelDemo {
    private static final Logger logger = LogManager.getLogger(ExcelDemo.class);
            
    public static void main(String[] args) {
        // 定义表头
        String[] title = {"序号", "姓名", "年龄"};
        // 创建excel工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建工作表sheet
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, "haha");
        // 创建第一行
        HSSFRow row = sheet.createRow(0);
        // 插入第一行数据的表头
        for (int i = 0; i < title.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }
        // 写入数据
        for (int i = 1; i <= 10; i++) {
            HSSFRow nrow = sheet.createRow(i);
            HSSFCell ncell = nrow.createCell(0);
            ncell.setCellValue("" + i);
            ncell = nrow.createCell(1);
            ncell.setCellValue("user" + i);
            ncell = nrow.createCell(2);
            ncell.setCellValue("24");
            ncell = nrow.createCell(3);
            ncell.setCellValue(124.00);
        }
        genExcelXls("e://poi.xls", workbook);
    }
    
    public static void genExcelXls(String filePath, HSSFWorkbook workbook) {
     // 创建excel文件
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            // 将excel写入
            FileOutputStream stream = FileUtils.openOutputStream(file);
            workbook.write(stream);
            stream.close();
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
