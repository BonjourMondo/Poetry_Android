package com.example.LJJ.Login;

/**
 * Created by Isuk on 2018/3/12.
 */

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.LJJ.MyUser.User;
import com.example.LJJ.UI.LoadingFrame;
import com.example.sf.CONSTANTS_SF;
import com.example.sf.Server.Server;
import com.example.sf.amap3d.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Handler;


public class SigninActivity extends AppCompatActivity {
    private String password;
    private String account;
    private EditText acc;
    private EditText pass;
    private EditText cpass;
    private Button signin;
    private ImageButton cana;
    private ImageButton canp;
    private ImageButton cancp;
    private ImageButton backpage;
    private LinearLayout mainframe;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sigin);
        initViews();
        register();
    }

    public void initViews(){
        acc = (EditText) findViewById(R.id.saccount);
        pass = (EditText) findViewById(R.id.spassword);
        cpass=(EditText)findViewById(R.id.confirmp);
        signin = (Button) findViewById(R.id.signin);
        cana = (ImageButton) findViewById(R.id.cancelsa);
        canp = (ImageButton) findViewById(R.id.cancelsp);
        cancp = (ImageButton) findViewById(R.id.cancelcp);
        backpage=(ImageButton)findViewById(R.id.backpage);
        mainframe=(LinearLayout)findViewById(R.id.mainframe);
        pass.getText().clear();
        acc.getText().clear();
        cpass.getText().clear();
    }

    public void register() {
        cana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acc.setText("");
            }
        });

        canp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setText("");
            }
        });

        cancp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cpass.setText("");
            }
        });

        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SigninActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account=acc.getText().toString();
                password=pass.getText().toString();
                if(!password.equals(cpass.getText().toString())||password.isEmpty()){//判断密码是否正确且不为空
                    passwordMessage();
                }else{
                dialog=LoadingFrame.createLoadingDialog(SigninActivity.this,"请稍候...");
                android.os.Handler handler=new android.os.Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        LoadingFrame.closeDialog(dialog);
                        System.err.println(msg.obj.toString());
                        if(true){
                            finish();
                        }
                    }


                };
                    Server server = new Server(handler, CONSTANTS_SF.URL_ROOT+"insertinfo");
                    JSONObject json = new JSONObject();
                    try {
                        json.put("username", account);
                        json.put("password", password);
                        json.put("neckname",RandomEXP.nextchar());
                    } catch (JSONException e){
                 e.printStackTrace();
                }
                  server.post(json.toString());

                }
            }
        });
    }

    public void passwordMessage(){
        AlertDialog ad=new AlertDialog.Builder(SigninActivity.this).setCancelable(true).setMessage("两次输入的密码不一致").setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public void accountMessage(){
        AlertDialog ad=new AlertDialog.Builder(SigninActivity.this).setCancelable(true).setMessage("该用户名已存在").setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }


    @Override
    public void onStart(){
        super.onStart();
        ObjectAnimator oa=ObjectAnimator.ofFloat(mainframe,"translationY",0f,-300f);
        oa.setDuration(1000);
        oa.start();
    }
}
