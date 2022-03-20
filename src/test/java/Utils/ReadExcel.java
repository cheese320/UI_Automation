package Utils;



import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class ReadExcel {
    private final String SrcFilePath = System.getProperty("user.dir")+"//src//test//java//testData//testData.xlsx";
    private final String DestFilePath = System.getProperty("user.dir")+"//src//test//java//testResult//testResult.xlsx";
    private static XSSFWorkbook workBook;
    private static XSSFSheet sheet;
    private static XSSFCell cell;


    public ReadExcel(){
        File file = new File(SrcFilePath);
        try{
            var inputStream = new FileInputStream(file);
            workBook = new XSSFWorkbook(inputStream);
            sheet = workBook.getSheetAt(0);
        } catch (Exception e){
            throw new RuntimeException("Please check your file name and path. Expected: " + SrcFilePath);
        }
    }

    public ReadExcel(String sheetName){
        File file = new File(SrcFilePath);
        try{
            var inputStream = new FileInputStream(file);
            workBook = new XSSFWorkbook(inputStream);
            sheet = workBook.getSheet(sheetName);
        } catch (Exception e){
            throw new RuntimeException("Please check your file name and path. Expected: " + SrcFilePath);
        }
    }


    //get row count
    private int getRowCnt(){
        return sheet.getLastRowNum()-sheet.getFirstRowNum();
    }

    //get column name
    private String getColName(int cellNum){
        return sheet.getRow(0)
                .getCell(cellNum)
                .getStringCellValue();
    }


    /**
     * expose to tester
     */
    //get cell value by rowNum, cellNum
    public String getCellValue(int rowNum, int cellNum){
        cell = sheet.getRow(rowNum).getCell(cellNum);
        return cell.getStringCellValue();
    }

    //get cell value by column name and row number
    public String getCellValueByColName(int rowNum, String colName){
        int cnt = sheet.getRow(0).getLastCellNum();
        int cellNum=-1;
        for(int i=0; i<cnt; i++){
            String cellValue = sheet.getRow(0)
                    .getCell(i)
                    .getStringCellValue();
            if(cellValue.equalsIgnoreCase(colName)){
                cellNum=i;
                break;
            }
        }
        if(cellNum==-1){
            throw new RuntimeException("colName " + colName + " Not found.");
        }
        return sheet.getRow(rowNum).getCell(cellNum).getStringCellValue();
    }

    //get cell data in given column
    public List<String> getCellValueList(String colName){
        int rowCnt = getRowCnt();

        List<String> list = new ArrayList<String>();
        for(int i=0; i<rowCnt; i++){
            list.add(getCellValueByColName(i,colName));
        }
        return list;
    }





    //TODO: up to business requirement, may need to add more methods here to update excel.
    //set cell value
    public void setCellValue(int rowNum, int cellNum, String cellValue){
        sheet.getRow(rowNum).createCell(cellNum).setCellValue(cellValue);

        try{
            var outputStream = new FileOutputStream(DestFilePath);
            workBook.write(outputStream);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
