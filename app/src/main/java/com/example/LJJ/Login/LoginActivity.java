package com.example.LJJ.Login;

/**
 * Created by Isuk on 2018/3/12.
 */

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.LJJ.MyUser.User;
import com.example.LJJ.UI.LoadingFrame;
import com.example.sf.amap3d.R;
import com.example.sf.Server.Server;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.sql.Connection;
import java.util.ArrayList;

import static com.example.sf.CONSTANTS_SF.URL_ROOT;


public class LoginActivity extends AppCompatActivity {

    private String ERR_MESSAGE="errout";
    private LinearLayout frame;
    private EditText account;
    private EditText password;
    private ImageButton cana;
    private ImageButton canp;
    private Button newaccount;
    private Button userlogin;
    private Button passengerlogin;
    private User user;
    private Connection connect;
    private Handler handler;
    private boolean isvalid;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        frame=(LinearLayout) findViewById(R.id.inputframe);
        account=(EditText)findViewById(R.id.account);
        password=(EditText)findViewById(R.id.password);
        cana=(ImageButton)findViewById(R.id.cancela);
        canp=(ImageButton)findViewById(R.id.cancelp);
        newaccount=(Button)findViewById(R.id.button);
        newaccount.setTextColor(getResources().getColor(R.color.deepskyblue));
        userlogin=(Button)findViewById(R.id.userlogin);
        userlogin.setAlpha(0.7f);
        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isvalid=false;
                dialog=LoadingFrame.createLoadingDialog(LoginActivity.this,"连接服务器中...");
                valid(account.getText().toString(),password.getText().toString());

            }
        });
        passengerlogin =(Button)findViewById(R.id.passengerlogin);
        passengerlogin.setAlpha(0.7f);
        passengerlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i=new Intent("com.example.sf.amap3d.MainActivity.ACTION_START");
                i.addCategory("android.intent.category.DEFAULT");
                Bundle b=new Bundle();
                b.putParcelable("user",user);
                i.putExtras(b);
                startActivity(i);
            }
        });
        newaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent("com.example.LJJ.Login.SigninActivity.ACTION_START");
                i.addCategory("android.intent.category.DEFAULT");
                startActivity(i);
            }
        });
        cana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(ERR_MESSAGE,"canb");
                account.setText("");
            }
        });



        canp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(ERR_MESSAGE,"cana");
                password.setText("");
            }
        });
    }


    //错误消息对话框

    private void valid(final String username, final String password) {

            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    LoadingFrame.closeDialog(dialog);
                    String result = msg.obj.toString();
                    System.err.println(result);
                    try {
                        JSONObject json = new JSONObject(new JSONTokener(result));
                        if(result.equals("{}")){
                            showDialog();
                        }
                        else if(username.equals(json.getJSONObject("0").getString("username")) && password.equals(json.getJSONObject("0").getString("password"))) {
                            user = new User();
                            user.setPassword(json.getJSONObject("0").getString("password"));
                            user.setAccount(json.getJSONObject("0").getString("username"));
                            user.setId(json.getJSONObject("0").getString("id"));
                            Intent i=new Intent("com.example.sf.amap3d.MainActivity.ACTION_START");
                            i.addCategory("android.intent.category.DEFAULT");
                            Bundle b=new Bundle();
                            b.putParcelable("user",user);
                            i.putExtras(b);
                            startActivity(i);
                            finish();
                            return;
                        }else {
                            showDialog();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            String url=URL_ROOT+"validation";
        Server server=new Server(handler,URL_ROOT+"validation");
        server.post(username);

    }

    public void showDialog(){
        AlertDialog ad=new AlertDialog.Builder(LoginActivity.this).setCancelable(true).setMessage("用户名或密码不正确").setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    protected void onStart(){
        super.onStart();
        ObjectAnimator oa=ObjectAnimator.ofFloat(frame,"translationY",0f,-300f);
        oa.setDuration(1000);
        oa.start();
    }
}
