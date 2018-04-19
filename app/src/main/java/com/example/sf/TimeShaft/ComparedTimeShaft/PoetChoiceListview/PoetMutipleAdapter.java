package com.example.sf.TimeShaft.ComparedTimeShaft.PoetChoiceListview;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sf.DataBase.Poet;
import com.example.sf.TimeShaft.ComparedTimeShaft.PortComapredTimerShaft;
import com.example.sf.amap3d.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LeesangHyuk on 2017/12/11.
 */

public class PoetMutipleAdapter extends BaseAdapter {
    private List<PoetMutipleModel> data;
    private Context context;
    private PoetMutipleChoice.AllCheckListener allCheckListener;

    List<Poet> poetList;

    public static final String POET_ID_KEY1="POET_ID1";//第一位诗人
    public static final String POET_ID_KEY2="POET_ID2";//第二位诗人
    public ArrayList<Integer> ids=new ArrayList<>(2);
    public int size=0;

    public PoetMutipleAdapter(List<PoetMutipleModel> data, List<Poet> poetList, Context context, PoetMutipleChoice.AllCheckListener allCheckListener) {
        this.data = data;
        this.context = context;
        this.allCheckListener = allCheckListener;
        this.poetList=poetList;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHoder hd;
        if (view == null) {
            hd = new ViewHoder();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.poet_item, null);
            hd.textView = (TextView) view.findViewById(R.id.text_title);
            hd.checkBox = (CheckBox) view.findViewById(R.id.ckb);
            view.setTag(hd);
        }
        PoetMutipleModel mModel = data.get(i);
        hd = (ViewHoder) view.getTag();
        hd.textView.setText(mModel.getSt());

        Log.e("myadapter", mModel.getSt() + "------" + mModel.ischeck());
        final ViewHoder hdFinal = hd;
        hd.checkBox.setChecked(mModel.ischeck());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = hdFinal.checkBox;
                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
                    data.get(i).setIscheck(false);
                    Poet poet=poetList.get(i);
                    if (ids.contains(new Integer(poet.getId()))){
                        ids.remove(new Integer(poet.getId()));
                        size--;
//                        Toast.makeText(context.getApplicationContext(), poet.getId(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    checkBox.setChecked(true);
                    data.get(i).setIscheck(true);
                    Poet poet=poetList.get(i);
                    ids.add(new Integer(poet.getId()));
                    Log.i("let_me_crazy_", "——————————"+poet.getId()+"——————————");
//                    for (int j = 0; j < ids.size(); j++) {
//                        Log.i("set_", "onClick: _______"+ids[0]+"__________");
//                        Log.i("set_", "onClick: _______"+poet.getId()+"__________");
//                    }
                    size++;
//                    Toast.makeText(context.getApplicationContext(), poet.getId(), Toast.LENGTH_SHORT).show();
                    if(size==2){
                        Intent intent=new Intent(context.getApplicationContext(),PortComapredTimerShaft.class);
                        intent.putExtra(PoetMutipleAdapter.POET_ID_KEY1,ids.get(0));
                        intent.putExtra(PoetMutipleAdapter.POET_ID_KEY2,ids.get(1));
                        Log.i("let_me_crazy_000", "——————————"+ids.get(0)+"——————————");
                        Log.i("let_me_crazy_000", "——————————"+ids.get(1)+"——————————");
//                        Toast.makeText(context.getApplicationContext(), poet.getId(), Toast.LENGTH_SHORT).show();
                        context.startActivity(intent);
                    }
                }
                //监听每个item，若所有checkbox都为选中状态则更改main的全选checkbox状态
                for (PoetMutipleModel model : data) {
                    if (!model.ischeck()) {
                        allCheckListener.onCheckedChanged(false);
                        return;
                    }
                }
                allCheckListener.onCheckedChanged(true);


            }
        });


        return view;
    }

    class ViewHoder {
        TextView textView;
        CheckBox checkBox;
    }
}
