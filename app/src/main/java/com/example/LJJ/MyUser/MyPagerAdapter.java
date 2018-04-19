package com.example.LJJ.MyUser;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

	private List<View> viewlist;
	private String[] titles;
	
	public MyPagerAdapter(List<View> viewlist,String[] titles) {
		// TODO Auto-generated constructor stub
	    this.viewlist=viewlist;
	    this.titles=titles;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return viewlist.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return (arg0==(View)arg1);
	}

    @Override
    public int getItemPosition(Object object) {
    	// TODO Auto-generated method stub
    	return super.getItemPosition(object);
    }
    
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
    	// TODO Auto-generated method stub
    	container.addView(viewlist.get(position));
    	return viewlist.get(position);
    }
    
    
    
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    	// TODO Auto-generated method stub
    	container.removeView(viewlist.get(position));
    	
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
    	// TODO Auto-generated method stub
    	return titles[position];
    }
}
