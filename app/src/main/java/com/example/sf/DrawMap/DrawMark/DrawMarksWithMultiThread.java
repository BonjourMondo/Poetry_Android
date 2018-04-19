package com.example.sf.DrawMap.DrawMark;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;

import java.util.List;

/**
 * Created by sf on 17-12-8.
 * 在地图上画出多个点，多线程操作
 */

public class DrawMarksWithMultiThread extends MarkStyle {
        private AMap aMap;
        private List<LatLng> marks;
//        一个线程中标记的数目，

    public void setMarkNum(int markNum) {
        this.markNum = markNum;
    }

    private int markNum=200;
    public DrawMarksWithMultiThread(AMap aMap, List<LatLng> marks) {
        this.aMap = aMap;
        this.marks = marks;
    }
    public void createThread(){
        int size=marks.size();
        int i=0;
        for(;i+markNum<size;i+=markNum){
            new Thread(new DrawMarksThread(aMap,marks.subList(i,i+markNum))).start();
        }
        if(i==0){
            new Thread(new DrawMarksThread(aMap, marks.subList(i, size))).start();
        }
        else {
            new Thread(new DrawMarksThread(aMap, marks.subList(i - markNum, size))).start();
        }
    }
}
class DrawMarksThread implements Runnable{
    private AMap aMap;
    private List<LatLng> marks;

    public DrawMarksThread(AMap aMap, List<LatLng> marks) {
        this.aMap = aMap;
        this.marks = marks;
    }

    @Override
    public void run() {
        DrawMarks drawMarks=new DrawMarks(aMap,marks);
        drawMarks.DrawPoints();
    }
}
