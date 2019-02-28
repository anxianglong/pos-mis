package com.util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * excel读写工具类 */
public class POIUtil {
    private static Logger logger  = Logger.getLogger(POIUtil.class);
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";

    /**
     * 读入excel文件，解析后返回
     * @param file
     * @throws IOException
     */
    public static List<String[]> readExcel(File file) throws IOException{
        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();

        if(workbook != null){
            int docno = FileUtils.getDocno();
            if(docno == 0){
                FileUtils.createHideFile(0);
            }
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行
                for(int rowNum = firstRowNum+13;rowNum < lastRowNum;rowNum++){
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if(row == null){
                        continue;
                    }
                    //获得当前行的开始列
//                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
//                    int lastCellNum = row.getPhysicalNumberOfCells();
//                    String[] cells = new String[row.getPhysicalNumberOfCells()];
                    String[] cells = new String[13];
                    //循环当前行 TODO
//                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
//                        Cell cell = row.getCell(cellNum);
//                        cells[cellNum] = getCellValue(cell);
//                    }

                    //销售店铺号
                    cells[0] = "B0255N02";
                    //收银机号码
                    cells[1] = "01";
                    //销售日期
                    cells[2] = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                    //销售时间
//                    cells[3] = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmm"));
                    cells[3] = "0000";
                    //单据号
                    cells[4] = "S" + FileUtils.addZeroForNum(String.valueOf(docno), 9);
                    //销售货号
                    cells[5] = "B0255N02CY0001";
                    //VIP编号
                    cells[6] = "";
                    //付款方式（现金）
                    Cell cell6 = row.getCell(6);
                    cells[7] = getCellValue(cell6).replace("-","0");
                    //付款方式（内卡）
                    cells[8] = "0";
                    //付款方式（外卡）
                    cells[9] = "0";
                    //付款方式（其他）
                    Cell cell7 = row.getCell(7);
                    String otherPay7 = getCellValue(cell7);
                    Cell cell8 = row.getCell(8);
                    String otherPay8 = getCellValue(cell8);
                    Cell cell9 = row.getCell(9);
                    String otherPay9 = getCellValue(cell9);
                    Cell cell10 = row.getCell(10);
                    String otherPay10 = getCellValue(cell10);
                    if(otherPay7!=null&&!otherPay7.equals("-")){
                        cells[10] = otherPay7;
                    }else if(otherPay8!=null&&!otherPay8.equals("-")){
                        cells[10] = otherPay8;
                    }else if(otherPay9!=null&&!otherPay9.equals("-")){
                        cells[10] = otherPay9;
                    }else if(otherPay10!=null&&!otherPay10.equals("-")){
                        cells[10] = otherPay10;
                    }else{
                        Cell cell11 = row.getCell(11);
                        cells[10] = getCellValue(cell11).replace("-","0");
                    }
                    //整单折扣金额
                    cells[11] = "0";
                    //总金额
                    Cell cell5 = row.getCell(5);
                    cells[12] = getCellValue(cell5);

                    list.add(cells);
                    docno+=1;
                }
            }
            workbook.close();

            File file1 = new File("D:\\");
            File[] tempFile = file1.listFiles();
            for(int i = 0; i < tempFile.length; i++){
                if(tempFile[i].getName().startsWith(".anxianglong")){
                    tempFile[i].delete();
                    FileUtils.createHideFile(docno);
                    break;
                }
            }
        }


        return list;
    }
    public static void checkFile(File file) throws IOException{
        //判断文件是否存在
        if(null == file){
            logger.error("文件不存在！");
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getName();
        //判断文件是否是excel文件
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            logger.error(fileName + "不是excel文件");
            throw new IOException(fileName + "不是excel文件");
        }
    }
    public static Workbook getWorkBook(File file) {
        //获得文件名
        String fileName = file.getName();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        InputStream is = null;
        try {
            //获取excel文件的io流
            is = new FileInputStream(file);
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith(xls)){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith(xlsx)){
                //2007
                workbook = new XSSFWorkbook(is);
            }
            is.close();
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return workbook;
    }
    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
}
