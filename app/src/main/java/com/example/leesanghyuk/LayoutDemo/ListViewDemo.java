package com.example.leesanghyuk.LayoutDemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.example.CONSTANTS_MAIN;
import com.example.sf.Server.*;
import com.example.leesanghyuk.BackTools.CommentInfoAdapter;
import com.example.leesanghyuk.BackTools.SwipeFinishLayout;
import com.example.leesanghyuk.POJO.CommentInfo;
import com.example.sf.amap3d.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ListViewDemo extends SwipeFinishDemo{
    private ArrayList<CommentInfo> commentInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_demo);

        // 同时支持右滑退出和下滑退出，这也是缺省状态。
        setSlideFinishFlags(SwipeFinishLayout.FLAG_SCROLL_DOWN_FINISH
                | SwipeFinishLayout.FLAG_SCROLL_RIGHT_FINISH);
        initmData();

        //设定的点击，暂时还不需要。
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
////                startActivity(new Intent(ListViewActivity.this, NormalActivity.class));
//                //暂定
//            }
//        });

    }

    private final static String URL= CONSTANTS_MAIN.URL_ROOT;
    private void initmData(){
        Server server=new Server(handler, URL+"/commentrelative/get_id_send_comment");
        String send_id="1";
        server.post("select * from comment where id="+send_id);
    }

    private void initAdapter(){
        //该过程必须放在handler里
        ListView mListView = (ListView) findViewById(R.id.my_list_view);
        LayoutInflater inflater=getLayoutInflater();
        CommentInfoAdapter adapter=new CommentInfoAdapter(inflater,commentInfos);
        mListView.setAdapter(adapter);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result=msg.obj.toString();
            try {
                JSONObject jsonObject=new JSONObject(result);
                commentInfos=new ArrayList<>();
                for(int i=0;i<jsonObject.length()-1;i++){
                    int user_id=jsonObject.getJSONObject(i+"").getInt("user_id");
                    int id=jsonObject.getJSONObject(i+"").getInt("id");
                    String comment=jsonObject.getJSONObject(i+"").getString("comment");
                    Timestamp date= Timestamp.valueOf(jsonObject.getJSONObject(i+"").getString("time"));
                    CommentInfo commentInfo=new CommentInfo();
                    commentInfo.setUser_id(user_id);
                    commentInfo.setComment(comment);
                    commentInfo.setPoet_id(id);
                    commentInfo.setDate(date);

                    commentInfos.add(commentInfo);
                }
                initAdapter();

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    };

}
