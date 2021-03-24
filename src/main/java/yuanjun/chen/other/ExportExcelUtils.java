package yuanjun.chen.other;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class ExportExcelUtils {

    /**
     * @Title: exportExcel
     * @Description: 导出Excel的方法
     * @author: evan @ 2014-01-09
     * @param workbook
     * @param sheetNum (sheet的位置，0表示第一个表格中的第一个sheet)
     * @param sheetTitle （sheet的名称）
     * @param headers （表格的标题）
     * @param result （表格的数据）
     * @param out （输出流）
     * @throws Exception
     */
    public void exportExcel(HSSFWorkbook workbook, int sheetNum, String sheetTitle, String[] headers,
            List<List<String>> result, OutputStream out) throws Exception {
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, sheetTitle);
        // 设置表格默认列宽度为20个字节
        sheet.setDefaultColumnWidth((short) 20);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 指定当单元格内容显示不下时自动换行
        style.setWrapText(true);
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell((short) i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text.toString());
        }
        // 遍历集合数据，产生数据行
        if (result != null) {
            int index = 1;
            for (List<String> m : result) {
                row = sheet.createRow(index);
                int cellIndex = 0;
                for (String str : m) {
                    HSSFCell cell = row.createCell((short) cellIndex);
                    cell.setCellValue(str);
                    cellIndex++;
                }
                index++;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try {
            OutputStream out = new FileOutputStream("e://testPoi.xls");
            List<List<String>> data = new ArrayList<List<String>>();
            for (int i = 1; i < 5; i++) {
                List rowData = new ArrayList<>();
                rowData.add(String.valueOf(i));
                rowData.add("东霖柏鸿");
                data.add(rowData);
            }
            String[] headers = {"ID", "用户名"};
            ExportExcelUtils eeu = new ExportExcelUtils();
            HSSFWorkbook workbook = new HSSFWorkbook();
            eeu.exportExcel(workbook, 0, "上海", headers, data, out);
            eeu.exportExcel(workbook, 1, "深圳", headers, data, out);
            eeu.exportExcel(workbook, 2, "广州", headers, data, out);
            // 原理就是将所有的数据一起写入，然后再关闭输入流。
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
