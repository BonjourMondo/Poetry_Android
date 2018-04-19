package com.example.sf.DrawMap.DrawLine;

import android.graphics.Color;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.ArcOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.NavigateArrow;
import com.amap.api.maps.model.NavigateArrowOptions;
import com.example.sf.amap3d.R;

/**
 * Created by sf on 17-12-6.
 * 在两个点之间画一条直线
 */

public class DrawLine {
    AMap aMap;
    public DrawLine(AMap aMap){
            this.aMap=aMap;
    }
    /**
     * 在给出的两点之间画一条直线*/
    public void drawDirectLine(LatLng point1,LatLng point2){
        ArcOptions arc = new ArcOptions().point(point1,
                point1,
                point2).strokeColor(R.color.addToPlayListDialogHeader);
        aMap.addArc(arc);
    }
    public void drawDirectLine(LatLng point1,LatLng point2,int color){
        ArcOptions arc = new ArcOptions().point(point1,
                point1,
                point2).strokeColor(R.color.actionBtnPrs);
        aMap.addArc(arc);
    }
    public NavigateArrow drawLineWithArrow(float width,LatLng...Points){
       return aMap.addNavigateArrow(new NavigateArrowOptions().add(Points).width(width).zIndex(100));
    }
}
