package com.example.LJJ.MyUser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sf.amap3d.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Isuk on 2018/3/14.
 */

public class listAdapter extends BaseAdapter {
    private User user;
    private Context context;
    private LayoutInflater inflater;
    private List<View> textlist;
    public listAdapter(Context context,User u){
        user=u;
        this.context=context;
        inflater=LayoutInflater.from(context);
        textlist=new ArrayList<View>();
        initlist();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return textlist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return textlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        // Log.e("errlog", position%res.size()+" ");
        return textlist.get(position%textlist.size());
    }

    public void initlist(){
            LinearLayout account=(LinearLayout) inflater.inflate(R.layout.simple_item,null);
            TextView acc_d=(TextView) account.findViewById(R.id.item_key);
            acc_d.setText("账号");
            EditText acc_c=(EditText)account.findViewById(R.id.item_value);
            acc_c.setEnabled(false);
            acc_c.setText(user.getAccount());
            textlist.add(account);
        LinearLayout nickname=(LinearLayout) inflater.inflate(R.layout.simple_item,null);
        TextView nick_d=(TextView) nickname.findViewById(R.id.item_key);
        nick_d.setText("昵称");
        EditText nick_c=(EditText)nickname.findViewById(R.id.item_value);
        nick_c.setText(user.getNickname());
        textlist.add(nickname);
        LinearLayout password=(LinearLayout) inflater.inflate(R.layout.simple_item,null);
        TextView pass_d=(TextView) password.findViewById(R.id.item_key);
        pass_d.setText("密码");
        EditText pass_c=(EditText)password.findViewById(R.id.item_value);
        pass_c.setTransformationMethod(PasswordTransformationMethod.getInstance());
        pass_c.setText(user.getPassword());
        textlist.add(password);
    }

}