package com.example.sf.amap3d;

import android.Manifest;
//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.ArcOptions;

import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.TileOverlayOptions;
import com.example.LJJ.Login.LoginActivity;
import com.example.LJJ.MyUser.PersonCenter;
import com.example.LJJ.MyUser.User;
import com.example.leesanghyuk.BackTools.PopMenu;
import com.example.leesanghyuk.BackTools.PopMenuItem;
import com.example.leesanghyuk.BackTools.PopMenuItemListener;
import com.example.leesanghyuk.LayoutDemo.PushStoryDemo;
import com.example.leesanghyuk.LayoutDemo.TtsDemo;
import com.example.sf.CONSTANTS_SF;
import com.example.sf.DataBase.Poetry;
import com.example.sf.DataBase.DataInit;
import com.example.sf.DrawMap.DrawMark.DrawMarksWithMultiThread;
import com.example.sf.Server.Server;
import com.example.sf.TimeShaft.ComparedTimeShaft.PoetChoiceClone;
import com.example.sf.TimeShaft.ComparedTimeShaft.PoetChoiceListview.PoetMutipleChoice;
import com.example.sf.TimeShaft.PoetChoice;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;
    private AMap aMap;
    private Button basicmap;
    private Button rsmap;
    private Button nightmap;
    private Button navimap;
    private ImageButton menubutton;
    private ArrayList<String> res;
    private DrawerLayout drawer;
    private CoordinatorLayout coordin;
    private NavigationView nv;
    private String info;
    //侧滑菜单组件
    private AppBarLayout barlayout;
    private ImageButton personinfo;
    private boolean iscollapse;
    private User user;
    private String[] title={"我的好友","我的收藏","敬请期待"};

    //发布文字
    private PopMenu mPopMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//设置对应的XML布局文件

        mapView = (MapView) findViewById(R.id.map);
        user =getIntent().getExtras().getParcelable("user");
        initcomponent();
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        aMap = mapView.getMap();
        ArcOptions arcOptions = new ArcOptions();
        aMap.setCustomMapStylePath("/main/assets/mapstyle/style.data");
        aMap.setMapCustomEnable(true);
//        aMap.setPointToCenter(40,20);


        registerListeners();

        SpeechUtility.createUtility(com.example.sf.amap3d.MainActivity.this, SpeechConstant.APPID +"=5aa60f6b");
        requestPermissions();

        //initDatabase();
//drawAllPoints();
        drawHeatMap();

        initPopMenu();

    }

    protected void initcomponent(){
        LinearLayout ll=(LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.temp,null);
        personinfo=(ImageButton) ll.findViewById(R.id.accimage);
        TextView tv=(TextView) (ll.findViewById(R.id.accinfo));
        if(user!=null) {
            info = user.getNickname() + "|" + (user.isSex() ? "♂" : "♀");
        }else{
            info="游客登录";
        }
        tv.setText(info);
        nv=(NavigationView) findViewById(R.id.nav);
        nv.addHeaderView(ll);
        mapView = (MapView) findViewById(R.id.map);
        coordin=(CoordinatorLayout)findViewById(R.id.coordin);
        barlayout=(AppBarLayout)findViewById(R.id.titlebar);
        menubutton=(ImageButton) (findViewById(R.id.menubutton));
        drawer=(DrawerLayout)findViewById(R.id.drawer);

    }

    private void registerListeners(){
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                barlayout.setExpanded(iscollapse);
                iscollapse=!iscollapse;
            }
        });
        personinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!=null){
                    Intent intent=new Intent(MainActivity.this, PersonCenter.class);
                    Bundle b=new Bundle();
                    b.putParcelable("user",user);
                    intent.putExtras(b);
                    startActivity(intent);}
                else{
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.openDrawer(Gravity.LEFT);
            }
        });
    }


    void initDatabase(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(isFirstRun) {
//    if(true){
            editor.putBoolean("isFirstRun", false);
            editor.commit();
            try {
                new DataInit().initDate(getAssets().open("SU_SHI.xls"));
                new DataInit().initDate(getAssets().open("DU_FU.xls"));
                new DataInit().initDate(getAssets().open("XIN_QI_JI.xls"));
                new DataInit().initDate(getAssets().open("WANG_WEI.xls"));
                new DataInit().initDate(getAssets().open("LI_BAI.xls"));
                new DataInit().initDate(getAssets().open("WANG_YU_CHENG.xls"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    void drawHeatMap(){
        List<Poetry> poetries= DataSupport.select("latitude","longtitude").find(Poetry.class);
        // 构建热力图 HeatmapTileProvider
        HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
        builder.data(Poetry.getLatLngs(poetries)).transparency(0.9);// 设置热力图绘制的数据
// Gradient 的设置可见参考手册
// 构造热力图对象
        HeatmapTileProvider heatmapTileProvider = builder.build();
        // 初始化 TileOverlayOptions
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        tileOverlayOptions.tileProvider(heatmapTileProvider); // 设置瓦片图层的提供者
// 向地图上添加 TileOverlayOptions 类对象
        aMap.addTileOverlay(tileOverlayOptions);
    }
    void drawAllPoints(){
        List<Poetry> poetries= DataSupport.select("latitude","longtitude").find(Poetry.class);
        DrawMarksWithMultiThread drawMarksWithMultiThread=new DrawMarksWithMultiThread(aMap,Poetry.getLatLngs(poetries));
        drawMarksWithMultiThread.createThread();
    }

    void initPopMenu(){
        mPopMenu = new PopMenu.Builder().attachToActivity(MainActivity.this)
                .addMenuItem(new PopMenuItem("文字", getResources().getDrawable(R.drawable.tabbar_compose_idea)))
                .addMenuItem(new PopMenuItem("时间轴", getResources().getDrawable(R.drawable.tabbar_compose_photo)))
                .addMenuItem(new PopMenuItem("对比轴", getResources().getDrawable(R.drawable.tabbar_compose_headlines)))
                .addMenuItem(new PopMenuItem("签到", getResources().getDrawable(R.drawable.tabbar_compose_lbs)))
                .addMenuItem(new PopMenuItem("点评", getResources().getDrawable(R.drawable.tabbar_compose_review)))
                .addMenuItem(new PopMenuItem("更多", getResources().getDrawable(R.drawable.tabbar_compose_more)))
                .setOnItemClickListener(new PopMenuItemListener() {
                    @Override
                    public void onItemClick(PopMenu popMenu, int position) {
                        if(position==0) {
                            Intent intent =new Intent(MainActivity.this, PushStoryDemo.class);
                            startActivity(intent);
                        }
                        if(position==1){
                            Intent intent=new Intent(MainActivity.this, PoetChoice.class);
                            startActivity(intent);
                        }
                        if(position==2){
                            Intent intent=new Intent(MainActivity.this, PoetChoiceClone.class);
                            startActivity(intent);
                        }

                    }
                })
                .build();
        findViewById(R.id.rl_bottom_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPopMenu.isShowing()) {
                    mPopMenu.show();
                }
            }
        });
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    private void requestPermissions(){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int permission = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(permission!= PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[]
                            {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.LOCATION_HARDWARE,Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.WRITE_SETTINGS,Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_CONTACTS},0x0010);
                }

                if(permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[] {
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},0x0010);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}

