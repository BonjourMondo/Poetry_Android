package com.example.LJJ.MyUser;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.LJJ.UI.LoadingFrame;
import com.example.sf.CONSTANTS_SF;
import com.example.sf.Server.Server;
import com.example.sf.amap3d.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Isuk on 2018/3/15.
 */

public class InfoEdit extends AppCompatActivity {
    private List<View> viewlist=new ArrayList<View>();
    private Button back2;
    private Button save;
    private User user;
    private ListView lv;
    private Dialog dialog;
    private EditText account;
    private EditText password;
    private EditText nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.personinfo);//设置对应的XML布局文件
        user =getIntent().getExtras().getParcelable("user");


        //toolbar=(Toolbar)findViewById(R.id.toolbar);
        //toolbar.setTitle("title");
        initviews();
        registerListener();
    }

    public void registerListener(){
        back2=(Button)findViewById(R.id.Back2);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save=(Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            dialog=LoadingFrame.createLoadingDialog(InfoEdit.this,"保存信息中");
            Handler handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    LoadingFrame.closeDialog(dialog);
                    System.err.println(msg.obj.toString());
                    if(true){
                        user.setAccount(account.getText().toString());
                        user.setPassword(password.getText().toString());
                        user.setNickname(nickname.getText().toString());
                    }
                }


            };
            JSONObject json=new JSONObject();
            try {
                json.put("username", account.getText().toString());
                json.put("password", password.getText().toString());
                json.put("nickname", nickname.getText().toString());
            }catch(JSONException e){
                e.printStackTrace();
            }
            Server server=new Server(handler,CONSTANTS_SF.URL_ROOT+"editinfo");
            server.post(json.toString());
        }} );
    }

    public void initviews(){
        lv=(ListView)findViewById(R.id.infolist);
        listAdapter la=new listAdapter(InfoEdit.this,user);
        lv.setAdapter(la);

        account=(EditText) (la.getView(0,null,lv).findViewById(R.id.item_value));
        password=(EditText) (la.getView(2,null,lv).findViewById(R.id.item_value));
        nickname=(EditText) (la.getView(1,null,lv).findViewById(R.id.item_value));
    }

    public void initinfo(){

    }




    @Override
    protected void onResume() {
        super.onResume();
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

