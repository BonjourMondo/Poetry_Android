package com.example.sf.DataBase;
import android.util.Log;
import com.example.sf.Exceloper.ExcelOper;
import org.litepal.crud.DataSupport;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import jxl.Sheet;
/**
 * Created by sf on 17-12-7.
 * 在此类中完成数据库的初始化操作
 */
public class DataInit {
    public static final String TAG="DataInit";
    public void initDate(InputStream inputStream) throws IOException {
        ExcelOper excelOper=new ExcelOper(inputStream,0, Poet.FirstLine);
        Map listNum=excelOper.getListNum();
        Sheet sheet=excelOper.getSheet();
//        保存诗人信息
        Poet sushi=new Poet();
        int sizeOfPF= Poet.FirstLine.size();
        for(int i = 0; i<sizeOfPF; i++){
            String columnName= Poet.FirstLine.get(i);
            if(listNum.containsKey(columnName)){
                int columnNum= (int) listNum.get(Poet.FirstLine.get(i));
                String sheetContent=sheet.getCell(columnNum,1).getContents();
                Poet.add(sushi,columnName,sheetContent);
            }
            else{
                Log.e("*********", "initDate: 表格中没有列："+ Poet.FirstLine.get(i));
            }
        }
        excelOper.setSheetNum(0, Poetry.FirstLine);
        listNum=excelOper.getListNum();
        int Rows=sheet.getRows();
        int sizeOfPtyFir= Poetry.FirstLine.size();
//        保存诗的信息
        for(int j=1;j<Rows;j++){
            if(sheet.getCell(0, j).getContents().trim().equals("")){
                break;
            }
            Poetry poetry=new Poetry();
            for(int i=0;i<sizeOfPtyFir;i++) {
                String columnName = Poetry.FirstLine.get(i);
                if (listNum.containsKey(columnName)) {
                    int columnNum = (int) listNum.get(columnName);
                    String sheetContent = sheet.getCell(columnNum, j).getContents().trim();
                    if (!sheetContent.equals("")) {
                        Poetry.add(poetry, columnName, sheetContent);
                    }
                }
            }
            poetry.setPoet_id(sushi.getId());
            sushi.getPoetries().add(poetry);
            poetry.saveThrows();
        }
//        sushi.saveThrows();
        if(sushi.save()){
            Log.i(TAG, "initDate: 数据库保存成功");
        }
        else{
            Log.e(TAG, "initDate: 数据库保存失败");
        }
        List<Poetry> poetries1=DataSupport.findAll(Poetry.class);
        List<Poet> poet=DataSupport.findAll(Poet.class);
        int size=poetries1.size();
        for(int i=0;i<size;i++){
            Log.i(TAG, "initDate: "+poetries1.get(i).getTitle());
        }
        inputStream.close();
    }
}
