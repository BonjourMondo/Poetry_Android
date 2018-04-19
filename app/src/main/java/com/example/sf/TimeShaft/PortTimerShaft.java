package com.example.sf.TimeShaft;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.example.sf.CONSTANTS_SF;
import com.example.sf.DataBase.Poetry;
import com.example.sf.PoetryInfo.PoetryList;
import com.example.sf.Server.Server;
import com.example.sf.amap3d.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sf on 17-12-8.
 */

public class PortTimerShaft extends Activity {
    private DisplayMetrics metrics = new DisplayMetrics();
    private MapView mapView;
    private AMap aMap;
    private Button prevButton;
    private Button startButton;
    private Button nextButton;
    private int width;
    private int height;
    private int poetId;
//    按钮切换的标志位
    private boolean isStart=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poet_timer_sheft);//设置对应的XML布局文件
        Intent getPoetId = getIntent();
        poetId = getPoetId.getIntExtra(PoetChoice.POET_ID_KEY, -1);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        prevButton = (Button) findViewById(R.id.prev);

        startButton = (Button) findViewById(R.id.start);
        nextButton = (Button) findViewById(R.id.next);
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        TimerSControl();
    }

    /**
     * 时间轴的控制函数
     */
    private void TimerSControl() {
//        List<Poetry> poetries = DataSupport.where("poet_id=?", String.valueOf(poetId)).find(Poetry.class);



        final Handler hander = new Handler(){
            public void handleMessage(Message m){
                Toast.makeText(PortTimerShaft.this,"HELLO",Toast.LENGTH_SHORT);
                Log.i("TAG", "handleMessage: **********__________________))))))))))))))))))))))))))");
//                ArrayList<Integer> poetryList= (ArrayList<Integer>) m.obj;
                Poetries poetries=(Poetries)m.obj;
//                Log.i(TAG, "handleMessage: "+poetryList.toString());
                Intent intent=new Intent(PortTimerShaft.this,PoetryList.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra(TS_Thread.POETRY_ID_LIST,poetries.poetriesId);
                intent.putExtra(TS_Thread.POETRY_TITLE_LIST,poetries.poetriesName);

                startActivity(intent);
            }
        };
        final List<Poetry> poetries=new ArrayList<>();
        final Handler serMsgHander=new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                String result=msg.obj.toString();

                extraJson(result,poetries);


                final TS_Thread ts = new TS_Thread(aMap, poetries, width, height, PortTimerShaft.this,hander);
//        PortTimerShaft.this.runOnUiThread(ts);
                Thread thread = new Thread(ts);
                thread.start();
                prevButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ts.remove();
                    }
                });
                startButton.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("Range")
                    @Override
                    public void onClick(View view) {
//               自动播放按钮，设置线程的自动持续绘制及暂停
                        if (isStart) {
                            isStart=false;
                            startButton.setBackgroundResource(R.drawable.stop);
                            ts.setWait(false);
                            ts.resumeThread();
                        } else {
                            isStart=true;
                            startButton.setBackgroundResource(R.drawable.start);

                            ts.setWait(true);
                        }
                    }
                });
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                唤醒时间轴绘制线程，绘制下一步
                        ts.resumeThread();
                        //设置中心点和缩放比例
                    }
                });





            }
        };
        String sql="select * from "+ CONSTANTS_SF.TABLE_POETRIES_L.TABLE_NAME +" where "+ CONSTANTS_SF.TABLE_POETRIES_L.POET_ID+"="+poetId+ " order by age";
        Server server=new Server(serMsgHander, CONSTANTS_SF.URL_ROOT+"poetriesList");
        server.post(sql);

    }

    public static void extraJson(String result,List<Poetry> poetries){
//        List<Poetry> poetries=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(result);
            for(int i=0;i<jsonObject.length()-1;i++){
                JSONObject poetriesJson=jsonObject.getJSONObject(i+"");
                Poetry poetry=new Poetry();
                poetry.setAgeOfPoet(poetriesJson.getInt(CONSTANTS_SF.TABLE_POETRIES_L.AGE));
                poetry.setCity_ancient(poetriesJson.getString(CONSTANTS_SF.TABLE_POETRIES_L.CITY_OF_ANCIENT));
                poetry.setCity_current(poetriesJson.getString(CONSTANTS_SF.TABLE_POETRIES_L.CITY_OF_CURRENT));
                poetry.setCounty_ancient(poetriesJson.getString(CONSTANTS_SF.TABLE_POETRIES_L.COUNTY_OF_ANCIENT));
                poetry.setCounty_current(poetriesJson.getString(CONSTANTS_SF.TABLE_POETRIES_L.COUNTY_OF_CURRENT));
//                        poetry.setId(poetriesJson.getInt(CONSTANTS_SF.TABLE_POETRIES_L.ID));

                poetry.setLatitude(poetriesJson.getDouble(CONSTANTS_SF.TABLE_POETRIES_L.LATTITUDE));
                poetry.setLongtitude(poetriesJson.getDouble(CONSTANTS_SF.TABLE_POETRIES_L.LONGTITUDE));
                poetry.setTitle(poetriesJson.getString(CONSTANTS_SF.TABLE_POETRIES_L.TITLE));
                poetry.setId(poetriesJson.getInt(CONSTANTS_SF.TABLE_POETRIES_L.ID));
                poetries.add(poetry);
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        System.out.println(result);
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
}

