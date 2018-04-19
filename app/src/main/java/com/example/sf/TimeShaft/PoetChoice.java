package com.example.sf.TimeShaft;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sf.CONSTANTS_SF;
import com.example.sf.DataBase.Poet;
import com.example.sf.Server.Server;
import com.example.sf.amap3d.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sf on 17-12-10.
 */

public class PoetChoice extends ListActivity {
//    向下一个活动传递显示诗人ID的键
    public static final String POET_ID_KEY="POET_ID";
    private ListView poetListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poet_choice);
        setTitle("请选择诗人：");
       /* final List<Poet> poetList= DataSupport.findAll(Poet.class);*/
        final List<Poet> poetList=new ArrayList<>();

        Handler coubldStart=new Handler(){
            @Override
            public void handleMessage(Message msg){
                ArrayList<String> poetNameList=new ArrayList<>();
                int poetNum=poetList.size();
                for (int i = 0; i <poetNum ; i++) {
                    poetNameList.add(poetList.get(i).getName());
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(PoetChoice.this,android.R.layout.simple_list_item_1,poetNameList);
                ListView listView=(ListView)findViewById(R.id.poet_choice);
                listView.setAdapter(adapter);
                int count=listView.getCount();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Poet poet=poetList.get(i);
                        Intent intent=new Intent(PoetChoice.this,PortTimerShaft.class);
                        intent.putExtra(PoetChoice.POET_ID_KEY,poet.getId());
                        startActivity(intent);
                    }
                });
            }
        };


        getPoetList(poetList,coubldStart);



    }
    public static void getPoetList(final List<Poet> poetList, final Handler handler1){
        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                String result=msg.obj.toString();
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
                boolean b=handler1.sendMessage(new Message());
                if (!b){

                }
            }

        };
        Server server=new Server(handler, CONSTANTS_SF.URL_ROOT+"poetList");
        String sql="select name,id from poet_l";
        server.post(sql);
    }
}
