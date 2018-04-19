package com.example.leesanghyuk.BackTools;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leesanghyuk.POJO.CommentInfo;
import com.example.sf.amap3d.R;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by LeesangHyuk on 2018/3/20.
 */

public class CommentInfoAdapter extends BaseAdapter {
    private List<CommentInfo> mData;//定义数据。
    private LayoutInflater mInflater;//定义Inflater,加载我们自定义的布局。
    /*
    定义构造器，在Activity创建对象Adapter的时候将数据data和Inflater传入自定义的Adapter中进行处理。
    */
    public CommentInfoAdapter(LayoutInflater inflater,List<CommentInfo> data){
        mInflater = inflater;
        mData = data;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.comment_list_item,parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name= (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.time= (TextView) convertView.findViewById(R.id.user_time);
            viewHolder.comment= (TextView) convertView.findViewById(R.id.user_comment);
            convertView.setTag(viewHolder);
        }
        else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
        CommentInfo commentInfo=mData.get(position);

        viewHolder.name.setText(String.valueOf(commentInfo.getUser_id()));
        viewHolder.time.setText(sdf.format(commentInfo.getDate()));
        viewHolder.comment.setText(commentInfo.getComment());
        return convertView;
    }
    private class ViewHolder{
        TextView name;
        TextView time;
        TextView comment;
    }
}
