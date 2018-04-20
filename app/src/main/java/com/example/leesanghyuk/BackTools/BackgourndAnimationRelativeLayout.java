package com.example.leesanghyuk.BackTools;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import com.example.leesanghyuk.LayoutDemo.TtsDemo;
import com.example.leesanghyuk.utils.DisplayUtil;
import com.example.leesanghyuk.utils.FastBlurUtil;
import com.example.sf.amap3d.MainActivity;
import com.example.sf.amap3d.R;

import java.util.Random;


/**
 * Created by AchillesL on 2016/11/18.
 */

/**
 * 自定义一个控件，继承RelativeLayout
 * */
public class BackgourndAnimationRelativeLayout extends RelativeLayout {

    private final int DURATION_ANIMATION = 500;
    private final int INDEX_BACKGROUND = 0;
    private final int INDEX_FOREGROUND = 1;
    /**
     * LayerDrawable[0]: background drawable
     * LayerDrawable[1]: foreground drawable
     */
    private LayerDrawable layerDrawable;
    private ObjectAnimator objectAnimator;
    private int musicPicRes = -1;
    BackgourndAnimationRelativeLayout mRootLayout;
    Drawable foregroundDrawable;

    public BackgourndAnimationRelativeLayout(Context context) {
        this(context, null);
    }

    public BackgourndAnimationRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackgourndAnimationRelativeLayout(Context context, AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayerDrawable();
        initObjectAnimator();

        //读取背景图片（暂定随机）
        int number = new Random().nextInt(10) + 1;
        if (number<=3)
            initBackground(R.raw.ic_music2);
        else if (number<=6)
            initBackground(R.raw.ic_music3);
        else
            initBackground(R.raw.ic_music1);
    }

    private void initBackground(int pic){
        //设置背景图片
        mRootLayout = (BackgourndAnimationRelativeLayout) findViewById(R.id.rootLayout);
        foregroundDrawable = getForegroundDrawable(pic);
        layerDrawable.setDrawable(INDEX_FOREGROUND, foregroundDrawable);
        mRootLayout.setForeground(foregroundDrawable);
        mRootLayout.beginAnimation();
    }

    private void initLayerDrawable() {
        Drawable backgroundDrawable = getContext().getDrawable(R.drawable.ic_blackground);
        Drawable[] drawables = new Drawable[2];

        /*初始化时先将前景与背景颜色设为一致*/
        drawables[INDEX_BACKGROUND] = backgroundDrawable;
        drawables[INDEX_FOREGROUND] = backgroundDrawable;

        layerDrawable = new LayerDrawable(drawables);
    }

    private void initObjectAnimator() {
        objectAnimator = ObjectAnimator.ofFloat(this, "number", 0f, 1.0f);
        objectAnimator.setDuration(DURATION_ANIMATION);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int foregroundAlpha = (int) ((float) animation.getAnimatedValue() * 255);
                /*动态设置Drawable的透明度，让前景图逐渐显示*/
                layerDrawable.getDrawable(INDEX_FOREGROUND).setAlpha(foregroundAlpha);
                BackgourndAnimationRelativeLayout.this.setBackground(layerDrawable);
            }
        });
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /*动画结束后，记得将原来的背景图及时更新*/
                layerDrawable.setDrawable(INDEX_BACKGROUND, layerDrawable.getDrawable(
                        INDEX_FOREGROUND));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void setForeground(Drawable drawable) {
        layerDrawable.setDrawable(INDEX_FOREGROUND, drawable);
    }

    //对外提供方法，用于开始渐变动画
    public void beginAnimation() {
        objectAnimator.start();
    }

    public boolean isNeed2UpdateBackground(int musicPicRes) {
        if (this.musicPicRes == -1) return true;
        if (musicPicRes != this.musicPicRes) {
            return true;
        }
        return false;
    }
    public Drawable getForegroundDrawable(int musicPicRes) {
        DisplayMetrics dm2 = getResources().getDisplayMetrics();
        float widthHeightSize= (float) (dm2.widthPixels* 1.0 / dm2.heightPixels* 1.0);

        /*得到屏幕的宽高比，以便按比例切割图片一部分*/
        Bitmap bitmap = getForegroundBitmap(musicPicRes);
        int cropBitmapWidth = (int) (widthHeightSize * bitmap.getHeight());
        int cropBitmapWidthX = (int) ((bitmap.getWidth() - cropBitmapWidth) / 2.0);

        /*切割部分图片*/
        Bitmap cropBitmap = Bitmap.createBitmap(bitmap, cropBitmapWidthX, 0, cropBitmapWidth,
                bitmap.getHeight());
        /*缩小图片*/
        Bitmap scaleBitmap = Bitmap.createScaledBitmap(cropBitmap, bitmap.getWidth() / 50, bitmap
                .getHeight() / 50, false);
        /*模糊化*/
        final Bitmap blurBitmap = FastBlurUtil.doBlur(scaleBitmap, 8, true);

        final Drawable foregroundDrawable = new BitmapDrawable(blurBitmap);
        /*加入灰色遮罩层，避免图片过亮影响其他控件*/
        foregroundDrawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        return foregroundDrawable;
    }

    private Bitmap getForegroundBitmap(int musicPicRes) {
        DisplayMetrics dm2 = getResources().getDisplayMetrics();

        int screenWidth = dm2.widthPixels;
        int screenHeight = dm2.heightPixels;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(getResources(), musicPicRes, options);
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;

        if (imageWidth < screenWidth && imageHeight < screenHeight) {
            return BitmapFactory.decodeResource(getResources(), musicPicRes);
        }

        int sample = 2;
        int sampleX = imageWidth / dm2.widthPixels;
        int sampleY = imageHeight / dm2.heightPixels;

        if (sampleX > sampleY && sampleY > 1) {
            sample = sampleX;
        } else if (sampleY > sampleX && sampleX > 1) {
            sample = sampleY;
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = sample;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return BitmapFactory.decodeResource(getResources(), musicPicRes, options);
    }
}
