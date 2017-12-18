package pxm.com.pxm.source.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;
import pxm.com.pxm.R;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.JsonUtil;

public class InvoiceResultFailedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_result_failed);
        String reason=getIntent().getExtras().getString(JPushInterface.EXTRA_EXTRA);
        switch (JsonUtil.getStatus(reason)){
            case 0:
                break;
            case 1:
                reason="连接错误！";
                break;
            case 2:
                reason="酒店未连接！";
                break;
            case 3:
                reason="系统错误！";
                break;
        }
            String string=" 失败原因："+reason;
            ((TextView)findViewById(R.id.reason_fail)).setText(string);
    }

}
