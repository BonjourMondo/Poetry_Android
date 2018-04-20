package com.example.leesanghyuk.LayoutDemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.CONSTANTS_MAIN;
import com.example.sf.Server.*;
import com.example.leesanghyuk.BackTools.TtsSettings;
import com.example.leesanghyuk.POJO.PoetInfo;
import com.example.leesanghyuk.abandon.PoetryBrownser;
import com.example.sf.amap3d.R;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import static com.example.sf.PoetryInfo.PoetryList.POETRY_NAME;

public class TtsDemo extends AppCompatActivity implements View.OnClickListener{
    private static String TAG = TtsDemo.class.getSimpleName();
    public static final String POETRIES_ID_EXTRA="POETRIES_ID_EXTRA";
    // 语音合成对象
    private SpeechSynthesizer mTts;
    // 默认发音人
    private String voicer = "xiaoyan";
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    private SharedPreferences mSharedPreferences;
    private int code=ErrorCode.SUCCESS;

    //由前一个activity传递！
    public int user_id=1;//暂定
    public int poet_id=985;//暂定

    PoetInfo poetInfo;

    //其他组件位置
    private ImageView mIvPlayOrPause;
    private TextView mToolbar_title;
    private  ImageView mComment_list;
    private ImageView mPlaying_cmt;
    private EditText mComment_content;
    private LinearLayout mCmt_layout;
    private Button mSend_comment;
    private TextView mTts_text;
    private TextView mTts_name;
    private ImageView back;
    private ImageView mPlaying_fav;

    @SuppressLint("ShowToast")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts_demo);
        poet_id=getIntent().getIntExtra(TtsDemo.POETRIES_ID_EXTRA,985);
        getPeotryInfo(poet_id);
        //布局init
        initView();

        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(TtsDemo.this, mTtsInitListener);
        mSharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, MODE_PRIVATE);


    }

    private void initView() {
        mComment_list= (ImageView) findViewById(R.id.comment_list);
        mIvPlayOrPause = (ImageView) findViewById(R.id.ivPlayOrPause);
        mToolbar_title= (TextView) findViewById(R.id.toorbar_title);
        mPlaying_cmt= (ImageView) findViewById(R.id.playing_cmt);
        mComment_content=(EditText) findViewById(R.id.comment_content);
        mCmt_layout=(LinearLayout) findViewById(R.id.cmt_layout);
        mSend_comment=(Button)findViewById(R.id.send_comment);
        mTts_text= (TextView) findViewById(R.id.tts_text);
        mTts_name= (TextView) findViewById(R.id.tts_name);
        back=(ImageView)findViewById(R.id.play_back);
        mPlaying_fav=(ImageView)findViewById(R.id.playing_fav);

        mSend_comment.setOnClickListener(this);
        mIvPlayOrPause.setOnClickListener(this);
        mComment_list.setOnClickListener(this);
        mPlaying_cmt.setOnClickListener(this);
        back.setOnClickListener(this);
        mPlaying_fav.setOnClickListener(this);
    }

    public void setContent(){
        //设置诗词内容
        mToolbar_title.setText(poetInfo.getTitle());
        mTts_text.setText(poetInfo.getContent());
        mTts_name.setText(poetInfo.getAuthor());
    }

    private boolean flag=true;
    private boolean comment_flag=true;
    @Override
    public void onClick(View view) {
        if( null == mTts ){
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            return;
        }

        switch(view.getId()) {
            // 开始合成
            // 收到onCompleted 回调时，合成结束、生成合成音频
            // 合成的音频格式：只支持pcm格式
            case R.id.ivPlayOrPause:
                if (flag) {
//				// 移动数据分析，收集开始合成事件
//				FlowerCollector.onEvent(TtsDemo.this, "tts_play");
                    //读取数据库
                    String text1 =mToolbar_title.getText().toString();
                    String text2 =mTts_text.getText().toString();
                    // 设置参数
                    setParam();

                    code = mTts.startSpeaking(text1+"\n\n"+text2, mTtsListener);
                    if (code != ErrorCode.SUCCESS) {
                        flag=true;
                        mIvPlayOrPause.setImageResource(R.drawable.ic_play);
                        return;
                    }
                    else {
                        flag=false;
                        mIvPlayOrPause.setImageResource(R.drawable.ic_pause);
                    }

                }else {
                    mTts.pauseSpeaking();
                    flag=true;
                    mIvPlayOrPause.setImageResource(R.drawable.ic_play);
                }
                break;
            case R.id.send_comment:
                comment_flag=true;
                insertComment(mComment_content.getText().toString());
                mCmt_layout.setVisibility(View.INVISIBLE);
                break;
            case R.id.comment_list:

                startActivity(new Intent(TtsDemo.this, ListViewDemo.class));
                overridePendingTransition(R.anim.slide_bottom_in, 0);
                break;

            case R.id.playing_cmt:
                if (comment_flag) {
                    mCmt_layout.setVisibility(View.VISIBLE);
                    comment_flag=false;
                }else {
                    mCmt_layout.setVisibility(View.INVISIBLE);
                    comment_flag=true;
                }
                break;
            case R.id.play_back:
                this.finish();
                break;
            case R.id.playing_fav:
                mPlaying_fav.setImageResource(R.drawable.play_icn_loved_prs);
        }
    }


    private final static String URL= CONSTANTS_MAIN.URL_ROOT;
    private void insertComment(String comment){
        Date date=new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Server server=new Server(handler_for_comment, URL+"/commentrelative/set_comment");
        String sql="insert into comment values("
                +user_id+","+poet_id+",\""+timestamp.toString()+"\",\""+comment+"\")";
        server.post(sql);
    }

    private void getPeotryInfo(int poet_id){
        Server server=new Server(handler_for_poet, URL+"/poetryrelative/get_poetry_info");
       /* String sql="select p.title,p.content,l.name from poetries p ,poet_l l where p.id="
                +poet_id+" and p.poet_id=l.id";*/
        String sql="select pl.title,pl.name,pl.poetries_content from poetries_l pl where pl.id="+poet_id;
        server.post(sql);
    }
    Handler handler_for_comment=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    Handler handler_for_poet=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result=msg.obj.toString();
            try {
                JSONObject jsonObject=new JSONObject(result);

                String name=jsonObject.getJSONObject("0").getString("name");
                poetInfo=new PoetInfo();
                poetInfo.setAuthor(name);

                String title=jsonObject.getJSONObject("0").getString("title");
                poetInfo.setTitle(title);

                String content=jsonObject.getJSONObject("0").getString("poetries_content");
                poetInfo.setContent(content);



                setContent();


            }catch (JSONException e) {
                Intent intent=new Intent(TtsDemo.this, PoetryBrownser.class);
                intent.putExtra(POETRY_NAME,poetInfo.getTitle());
                startActivity(intent);
                e.printStackTrace();
            }
        }
    };


    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                return;
            }
        }
    };

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
        }

        @Override
        public void onSpeakPaused() {
        }

        @Override
        public void onSpeakResumed() {
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度

        }

        @Override
        public void onCompleted(SpeechError error) {
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };



    /**
     * 参数设置
     * @return
     */
    private void setParam(){
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if(mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, mSharedPreferences.getString("speed_preference", "50"));
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, mSharedPreferences.getString("pitch_preference", "50"));
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, mSharedPreferences.getString("volume_preference", "50"));
        }else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");
            /**
             * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */
        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString("stream_preference", "3"));
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if( null != mTts ){
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
    }

    @Override
    protected void onResume() {
        //移动数据统计分析
        FlowerCollector.onResume(TtsDemo.this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }
    @Override
    protected void onPause() {
        //移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(TtsDemo.this);
        super.onPause();
    }



}
