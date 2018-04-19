package com.example.LJJ.MyUser;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sf.amap3d.R;

/**
 * Created by Isuk on 2018/3/13.
 */

public class MyHolder extends RecyclerView.ViewHolder{
    public ImageView icon;
    public TextView textView;
    //实现的方法
    public MyHolder(View itemView) {
        super(itemView);
        icon= (ImageView) itemView.findViewById(R.id.friend_icon);
        textView= (TextView) itemView.findViewById(R.id.friend_name);
    }
}
