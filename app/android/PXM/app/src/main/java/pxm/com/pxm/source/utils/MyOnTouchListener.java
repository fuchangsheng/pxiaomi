package pxm.com.pxm.source.utils;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dmtec on 2016-08-13.
 *
 */
public class MyOnTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == KeyEvent.ACTION_DOWN) {
            view.setAlpha(0.80f);
        }
        if (motionEvent.getAction() == KeyEvent.ACTION_UP) {
            view.setAlpha(1.0f);
        }
        return false;
    }
}
