package com.example.sf.PoetryInfo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

//import com.example.leesanghyuk.PoetryBrownser;
import com.example.leesanghyuk.LayoutDemo.TtsDemo;
import com.example.leesanghyuk.abandon.PoetryBrownser;
import com.example.sf.DataBase.Poet;
import com.example.sf.DataBase.Poetry;
import com.example.sf.TimeShaft.PoetChoice;
import com.example.sf.TimeShaft.PortTimerShaft;
import com.example.sf.TimeShaft.TS_Thread;
import com.example.sf.amap3d.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static com.example.sf.DataBase.DataInit.TAG;

/**
 * Created by sf on 17-12-12.
 */

public class PoetryList extends ListActivity {
    public static final String POETRY_NAME="POETRY_NAME";

    //    向下一个活动传递显示诗人ID的键
    public static final String POET_ID_KEY="POET_ID";
    private ListView poetListView;
    private List<Integer> poetryIdList;
    private List<String> poetryTitleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poetry_info);
        final Intent PointInfo=getIntent();
        poetryIdList = PointInfo.getIntegerArrayListExtra(TS_Thread.POETRY_ID_LIST);
        poetryTitleList=PointInfo.getStringArrayListExtra(TS_Thread.POETRY_TITLE_LIST);
//        Log.i(TAG, "onCreate:+++++++++++++++++++++____________________________ "+poetryIdList.toString());
        //final ArrayList<String> poetryNameList=new ArrayList<>();
        int poetryNum=poetryIdList.size();
      /*  if(poetryNum==0) {
            poetryNameList.add("此地没有作词信息！");
        }else
            {
        for (int i = 0; i <poetryNum ; i++) {
         //   List<Poetry> poetryList= DataSupport.select("title").where("id=?",poetryIdList.get(i)+"").find(Poetry.class);
            List<Poetry> poetryList=new ArrayList<>();
            Handler idMessage=new Handler(){
                @Override
                public void handleMessage(Message msg){

                }
            };
            Poetry poetry=poetryList.get(0);
            if(poetry.getTitle()!=null) {
                poetryNameList.add(poetry.getTitle());
            }
        }}*/
//        Log.i(TAG, "onCreate:+++++++++++++++++++++____________________________ "+poetryNameList.toString());
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(PoetryList.this,android.R.layout.simple_list_item_1,poetryTitleList);
        ListView listView=(ListView)findViewById(R.id.poetry_name);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(PoetryList.this,poetryNameList.get(i),Toast.LENGTH_SHORT).show();
               /* Intent intent=new Intent(PoetryList.this, PoetryBrownser.class);
                intent.putExtra(POETRY_NAME,poetryTitleList.get(i));
                startActivity(intent);*/
                Intent intent=new Intent(PoetryList.this, TtsDemo.class);
                intent.putExtra(TtsDemo.POETRIES_ID_EXTRA,poetryIdList.get(i));
                startActivity(intent);
            }
        });
    }
}