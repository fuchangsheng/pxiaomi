package pxm.com.pxm.source.activities;

import android.app.Activity;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;
import pxm.com.pxm.R;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.MyHandler;
import pxm.com.pxm.source.utils.OkHttpUtil;

public class StartActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setInitTitleAllowed(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Asynchronous message processing.
        //and jump to the login interface after completing  displaying the boot interface.
        final MyHandler myHandler = new MyHandler(StartActivity.this);

        //Read Cache and wait for 2 seconds to start next interface.
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                myHandler.sendEmptyMessage(1);
            }
        },1600);
    }
}
