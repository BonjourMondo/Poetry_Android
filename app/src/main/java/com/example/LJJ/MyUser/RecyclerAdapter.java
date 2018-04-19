package com.example.LJJ.MyUser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sf.amap3d.R;
import com.mysql.fabric.Server;
import com.example.leesanghyuk.LayoutDemo.*;
import  com.example.leesanghyuk.POJO.*;

import java.util.List;
import java.util.logging.Handler;

import static org.litepal.LitePalApplication.getContext;

/**
 * Created by Isuk on 2018/3/13.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<MyHolder>{
    private final List<PoetInfo> list;
    private Context context;
    private int classify;
    public RecyclerAdapter(List<PoetInfo> flist,int cid,Context context){
        list = flist;
        this.context=context;
        this.classify=cid;

    }
    //OnCreateViewHolder用来给rv创建缓存的

    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.firend,parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }
    //给缓存控件设置数据
    public void onBindViewHolder(MyHolder holder, final int position) {
        final String item = list.get(position).getTitle();
        if (classify==R.string.friends) {
            User u=new User();
            holder.textView.setText(u.getNickname());
            holder.icon.setImageResource(R.mipmap.ic_launcher_round);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), PersonCenter.class);
                    User u = null;
                    Bundle b = new Bundle();
                    b.putParcelable("user", u);
                    i.putExtras(b);
                    context.startActivity(i);
                }

            });

        }else if(classify==R.string.collected){
            holder.textView.setText(item);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), TtsDemo.class);
//                    Bundle b = new Bundle();
//                    b.putString(TtsDemo.POETRIES_ID_EXTRA, list.get(position).getId());
String temp_s=list.get(position).getId();
                    int temp=Integer.valueOf(temp_s.trim());

                    i.putExtra(TtsDemo.POETRIES_ID_EXTRA,temp);
//                    i.putExtras(b);

                            context.startActivity(i);
                }

            });
        }
    }
    //获取记录数

    public int getItemCount() {
        return list.size();
    }
}
