package com.example.sf.DrawMap.DrawMark;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;

import java.util.List;

/**
 * Created by sf on 17-12-8.
 * 在地图上画出多个点
 * 单线程
 */

public class DrawMarks extends MarkStyle {
//    要画点的地图界面
    private AMap aMap;
//    要画的点的集合
    private List<LatLng> points;

    public DrawMarks(AMap aMap, List<LatLng> points) {
        this.aMap = aMap;
        this.points = points;
    }
    public void DrawPoints(){
        DrawMark drawMark=new DrawMark(aMap);
        int size=points.size();
        for(int i=0;i<size;i++){
            drawMark.drawMark(points.get(i));
        }
    }
}
