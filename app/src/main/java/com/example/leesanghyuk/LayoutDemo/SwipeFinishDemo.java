package com.example.leesanghyuk.LayoutDemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.leesanghyuk.BackTools.SwipeFinishLayout;
import com.example.sf.amap3d.R;

public class SwipeFinishDemo extends Activity {
    protected SwipeFinishLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout = (SwipeFinishLayout) LayoutInflater.from(this).inflate(R.layout.base_content, null);
        layout.attachToActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(0, R.anim.slide_bottom_out);
    }

    @Override
    public void finish() {
        if (layout.attachedActivityShouldFinish()) {
            super.finish();
        } else {
            layout.finishActivityBottomOut();
        }
    }

    /**
     * 设置滑动退出的模式，目前支持三种模式：
     * 右滑退出：FLAG_SCROLL_RIGHT_FINISH
     * 下滑退出：FLAG_SCROLL_DOWN_FINISH
     * 右滑和下滑：FLAG_SCROLL_RIGHT_FINISH | FLAG_SCROLL_DOWN_FINISH
     * @param flags
     */
    public void setSlideFinishFlags(int flags){
        layout.setFlags(flags);
    }
}
