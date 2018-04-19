package com.example.LJJ.MyUser;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sf.TimeShaft.ComparedTimeShaft.PoetChoiceListview.PoetMutipleChoice;
import com.example.sf.amap3d.MainActivity;
import com.example.sf.amap3d.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.example.sf.Server.*;
import com.example.sf.*;
import com.example.leesanghyuk.POJO.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Isuk on 2018/3/12.
 */

public class PersonCenter extends AppCompatActivity {
    private User user;
    private List<View> viewlist=new ArrayList<View>();
    private CollapsingToolbarLayout ctl;
    private Toolbar toolbar;
    private AppBarLayout appbar;
    private MyPagerAdapter tadapter;
    private ImageView pts;
    private ViewPager vp;
    private String[] titles={"好友","收藏","敬请期待"};
    private Button back1;
    private Button edit;
    private RecyclerView mview1;
    private RecyclerView mview2;
    private View view1;
    private View view2;
    private View view3;
    private boolean fromRadioGroup;
    private boolean fromViewPager;
    private Bundle refer;
    private SlidingTabLayout stl;
    private TextView title;
    public List<PoetInfo> peotrytitles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.personal_center);//设置对应的XML布局文件
        peotrytitles =new ArrayList<PoetInfo>();
        refer=getIntent().getExtras();
        user=getIntent().getExtras().getParcelable("user");
        ctl=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
      //  ctl.setTitle(user.getNickname()+"的个人空间");
        loadinfo();
       initviews();
        buildPager();


    }

   /* void initRadioGroup() {
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (fromViewPager) {
                    fromViewPager = false;
                    return;
                }
                fromRadioGroup = true;
                switch (checkedId) {
                    case R.id.net_music:
                        vp.setCurrentItem(0, true);
                        break;
                    case R.id.local_music:
                        vp.setCurrentItem(1, true);
                        break;
                    case R.id.social:
                       vp.setCurrentItem(2, true);
                        break;
                    default:
                }
            }
        });
    }*/

    public void initviews(){
        stl=(SlidingTabLayout)findViewById(R.id.mytab);
        stl.setTextSelectColor(R.color.burlywood);
        stl.setTextUnselectColor(R.color.gray);
        stl.setUnderlineColor(R.color.burlywood);
        stl.setTabPadding(45f);
        stl.setTextsize(16f);
        title=(TextView)findViewById(R.id.title);
        title.setText(user.getNickname()+"的个人空间");
        view1=View.inflate(this, R.layout.friends, null);
        view2=View.inflate(this, R.layout.collections, null);
        view3=View.inflate(this, R.layout.view3, null);
        mview1=(RecyclerView) view1.findViewById(R.id.mscroll1);
        mview2=(RecyclerView)view2.findViewById(R.id.mscroll2);
        RecyclerAdapter cadpter=new RecyclerAdapter( peotrytitles,R.string.collected,PersonCenter.this);
        mview1.setLayoutManager(new LinearLayoutManager(this));
        mview1.setAdapter(cadpter);
        RecyclerAdapter fadpter=new RecyclerAdapter(peotrytitles,R.string.collected,PersonCenter.this);
        mview2.setLayoutManager(new LinearLayoutManager(this));
        mview2.setAdapter(fadpter);
       // radio=(RadioGroup)findViewById(R.id.mradio);
        //mview1.setAdapter();
        viewlist.add(view1);
        viewlist.add(view2);
        viewlist.add(view3);
        back1=(Button)findViewById(R.id.Back1);
        pts=(ImageView) findViewById(R.id.mstrip);
        //pts.setTextColor(getResources().getColor(R.color.gray));
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edit=(Button)findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PersonCenter.this, InfoEdit.class);
                i.putExtras(refer);
                startActivity(i);
            }
        });
    }

    protected void loadinfo(){
            Handler collectedhandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    try {
                        String result = msg.obj.toString();
                        JSONObject results = new JSONObject(new JSONTokener(result));
                        Iterator iterator=results.keys();
                        while(iterator.hasNext()){
                            PoetInfo poetInfo=new PoetInfo();
                            String mapkey=(String)iterator.next();
                            poetInfo.setTitle(results.getJSONObject(mapkey).getString("title"));
                            poetInfo.setId(results.getJSONObject(mapkey).getString("id"));
                            peotrytitles.add(poetInfo);
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }

            };
        Handler friendshandler = new Handler(){};
        Server server=new Server(collectedhandler,CONSTANTS_SF.URL_ROOT+"collected");
        System.err.println(user.getId());
        server.post(user.getId());
    }

    public void buildPager(){
        tadapter=new MyPagerAdapter(viewlist,titles);

        vp=(ViewPager)findViewById(R.id.pager);
        vp.setAdapter(tadapter);
        stl.setViewPager(vp,titles);
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
