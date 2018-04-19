package com.example.sf.TimeShaft.ComparedTimeShaft.PoetChoiceListview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.example.sf.DataBase.Poet;
import com.example.sf.amap3d.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PoetMutipleChoice extends AppCompatActivity {
    private ListView mListView;
    private List<PoetMutipleModel> models;
    private CheckBox mMainCkb;
    private PoetMutipleAdapter mMyAdapter;
    List<Poet> poetList=new ArrayList<>();
//    private ArrayList<>
    //监听来源
    public boolean mIsFromItem = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent getPoetId = getIntent();
        String result= getPoetId.getStringExtra("result");
        PoetMutipleChoice.getPoetList(poetList,result);
        setContentView(R.layout.poet_mutiple_choice);
      //  getSupportActionBar().hide();
        initView();
        initData();
        initViewOper();
    }

    /**
     * view初始化
     */
    private void initView() {
        mListView = (ListView) findViewById(R.id.list_main);
        mMainCkb = (CheckBox) findViewById(R.id.ckb_main);
    }

    /**
     * 数据加载
     */
    private void initData() {

        int poetNum=poetList.size();
        models = new ArrayList<>();
        PoetMutipleModel model;
        for (int i = 0; i < poetNum; i++) {
            model = new PoetMutipleModel();
            model.setSt(poetList.get(i).getName());
            model.setIscheck(false);
            models.add(model);
        }
    }

    /**
     * 数据绑定
     */
    private void initViewOper() {
        mMyAdapter = new PoetMutipleAdapter(models,poetList,this, new AllCheckListener() {

            @Override
            public void onCheckedChanged(boolean b) {
                //根据不同的情况对maincheckbox做处理
                if (!b && !mMainCkb.isChecked()) {
                    return;
                } else if (!b && mMainCkb.isChecked()) {
                    mIsFromItem = true;
                    mMainCkb.setChecked(false);
                } else if (b) {
                    mIsFromItem = true;
                    mMainCkb.setChecked(true);
                }
            }
        });
        mListView.setAdapter(mMyAdapter);

        //全选的点击监听
        mMainCkb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //当监听来源为点击item改变maincbk状态时不在监听改变，防止死循环
                if (mIsFromItem) {
                    mIsFromItem = false;
                    Log.e("mainCheckBox", "此时我不可以触发");
                    return;
                }

                //改变数据
                for (PoetMutipleModel model : models) {
                    model.setIscheck(b);
                }
                //刷新listview
                mMyAdapter.notifyDataSetChanged();
            }
        });

    }
    //对item导致maincheckbox改变做监听
    interface AllCheckListener {
        void onCheckedChanged(boolean b);
    }
    public static void getPoetList( List<Poet> poetList,  String result){

                try {
                    JSONObject jsonObject=new JSONObject(result);
                    for(int i=0;i<jsonObject.length()-1;i++){
                        String name=jsonObject.getJSONObject(i+"").getString("name");
                        int id=jsonObject.getJSONObject(i+"").getInt("id");
                        Poet poet=new Poet();
                        poet.setName(name);
                        poet.setId(id);
                        poetList.add(poet);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(result);

            }



    }


