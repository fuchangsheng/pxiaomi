package pxm.com.pxm.source.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import pxm.com.pxm.R;

/**
 * Created by dmtec on 2016-08-01.
 *
 */

public class BaseActivity extends Activity implements View.OnTouchListener {
    private  boolean touchFinishAllowed=false;

    private boolean initTitleAllowed=true;

    private int color=Color.parseColor("#41a1db");

    public void setInitTitleAllowed(boolean initTitleAllowed) {
        this.initTitleAllowed = initTitleAllowed;
    }

    public void setTouchFinishAllowed(boolean touchFinishAllowed) {
        this.touchFinishAllowed = touchFinishAllowed;
    }

    public int getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = Color.parseColor(color);
    }

    PointF downP = new PointF();

    PointF curP = new PointF();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initTitleAllowed){
            initTitleView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(touchFinishAllowed){
            curP.x = event.getX();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downP.x = event.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (curP.x- downP.x > 500) {
                        finish();
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    break;

                default:
                    break;
            }
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    void initTitleView(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(color);
    }


    @TargetApi(19)
    private void setTranslucentStatus(boolean on){
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}