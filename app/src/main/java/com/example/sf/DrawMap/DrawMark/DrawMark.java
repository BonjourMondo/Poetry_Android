package com.example.sf.DrawMap.DrawMark;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.example.sf.DataBase.Poetry;

/**
 * Created by sf on 17-12-7.
 */

public class DrawMark extends MarkStyle {
    private AMap aMap;
    public DrawMark(AMap aMap){
        this.aMap=aMap;
    }
    public Marker drawMark(double lattitude, double longtitude){
        LatLng latlng = new LatLng(lattitude, longtitude);
        MarkerOptions markerOptions=new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).position(latlng);
        Marker marker=aMap.addMarker(markerOptions);
return marker;
   //     aMap.setPointToCenter(latlng.latitude,latlng.longitude);
    }
    public Marker drawMark(LatLng latLng){
        return this.drawMark(latLng.latitude,latLng.longitude);
    }
  /*  public Marker drawMark(LatLng latLng, String title,String ){
        LatLng latlng = new LatLng(lattitude, longtitude);
        MarkerOptions markerOptions=new MarkerOptions().title(poetry.getCity_current()+" "+poetry.getCounty_current()).snippet().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).position(latlng);
        Marker marker=aMap.addMarker(markerOptions);
        ma
        return marker;
    }*/
}
