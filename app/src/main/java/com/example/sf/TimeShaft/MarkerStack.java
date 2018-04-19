package com.example.sf.TimeShaft;

import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.NavigateArrow;
import com.example.sf.DataBase.Poet;

import java.util.Map;
import java.util.Stack;

/**
 * Created by sf on 17-12-11.
 * 在时间轴的绘制过程中绘制的每个点的信息进行将会被压入到这个栈中
 */

public class MarkerStack extends Stack {

    private Stack<Marker> markerStack;
    private Stack<NavigateArrow> arrowLineStack;
    private Poet poet;
    Map<Marker,MarkerPoetryInfo> marker_MarkerPoetryInfo_Map;

    public void push(Marker marker,NavigateArrow arrowLine){

    }

}
