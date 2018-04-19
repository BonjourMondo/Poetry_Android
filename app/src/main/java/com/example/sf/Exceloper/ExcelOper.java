package com.example.sf.Exceloper;

import android.app.Activity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Created by sf on 17-12-7.
 * 对excel表格进行操作
 * 读取操作
 * 对表格的要求
 * 第一行为各个列的属性，属性名见com/example/sf/DataBase/ContextInFirstLine.java
 * 第一列中用来判断当前行是否有效，不为空则有效,主要是防止excel自动填充n多空行，导致读入大量的无效信息
 */

public class ExcelOper {
    private static final String TAG="com.exampl";
    private Workbook workbook;
    private Sheet sheet;
    private String path;
    private int sheetNum;
    private ArrayList<String> firstline;
    private Map listNum=new HashMap();
    /**
     * 初始化excel操作对象
     * @param inputStream 要操作的文件的数据流
     * @param sheetNum 要操作的表格
     * @param firstline 需要操作的excel文件中列的列名*/
    public ExcelOper(InputStream inputStream, int sheetNum, ArrayList<String> firstline){
        this.sheetNum=sheetNum;
        this.firstline=firstline;

        try {

            workbook=Workbook.getWorkbook(inputStream);
            sheet=workbook.getSheet(sheetNum);
            initMap();
        } catch (IOException e) {
            Log.e(TAG, "File open failed!" );
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

    }
/*×
* 初始化列名与其在表中列号之间的对应map*/
    private void initMap(){
        for(int i=0;i<sheet.getColumns();i++){
            String s=sheet.getCell(i,0).getContents();
            if(firstline.contains(s.trim())){
                listNum.put(s,i);
            }
        }
    }
/**
 * 更改要查询的表
 * @param sheetNum 要查询的新表的表号
 * @param firstline 要查询的新表中的列名*/
    public void setSheetNum(int sheetNum,ArrayList<String> firstline) {
        this.sheetNum = sheetNum;
        this.sheet=workbook.getSheet(sheetNum);
        this.firstline=firstline;
        initMap();
    }

    public Map getListNum() {
        return listNum;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setFirstline(ArrayList<String> firstline) {
        this.firstline = firstline;
    }

}
