package pxm.com.pxm.source.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import pxm.com.pxm.source.activities.LoginActivity;

/**
 * Created by dmtec on 2016/7/13.
 * To handle the message from timer in StartActivity ;
 */
public class MyHandler extends Handler {
    private Activity m_activity;
    public MyHandler(Activity activity) {
        super();
        this.m_activity = activity;
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what==1){
            m_activity.startActivity(new Intent(m_activity,LoginActivity.class));
            m_activity.finish();
        }
    }
}
