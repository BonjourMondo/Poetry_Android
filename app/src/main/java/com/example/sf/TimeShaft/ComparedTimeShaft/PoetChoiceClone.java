package com.example.sf.TimeShaft.ComparedTimeShaft;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sf.DataBase.Poet;
import com.example.sf.TimeShaft.PoetChoice;
import com.example.sf.amap3d.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LeesangHyuk on 2017/12/11.
 */

public class PoetChoiceClone extends ListActivity {
    //    向下一个活动传递显示诗人ID的键
    public static final String POET_ID_KEY="POET_ID";
    private ListView poetListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poet_choice);
        setTitle("请选择诗人：");
        final List<Poet> poetList= DataSupport.findAll(Poet.class);
        ArrayList<String> poetNameList=new ArrayList<>();
        int poetNum=poetList.size();
        for (int i = 0; i <poetNum ; i++) {
            poetNameList.add(poetList.get(i).getName());
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(PoetChoiceClone.this,android.R.layout.simple_list_item_1,poetNameList);
        ListView listView=(ListView)findViewById(R.id.poet_choice);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Poet poet=poetList.get(i);
                Intent intent=new Intent(PoetChoiceClone.this,PortComapredTimerShaft.class);
                intent.putExtra(PoetChoice.POET_ID_KEY,poet.getId());
                startActivity(intent);
            }
        });
    }
}
