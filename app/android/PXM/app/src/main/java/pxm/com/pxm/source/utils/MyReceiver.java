package pxm.com.pxm.source.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;
import pxm.com.pxm.source.activities.InvoiceResultFailedActivity;
import pxm.com.pxm.source.activities.InvoiceResultOkActivity;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            int resultStatus=getStatus(bundle);
            Intent intent1;
            if (resultStatus==0){
                intent1 = new Intent(context, InvoiceResultOkActivity.class);
            }
            else {
                intent1 = new Intent(context, InvoiceResultFailedActivity.class);
            }
            intent1.putExtras(bundle);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent1);
        }
    }
    private static int getStatus(Bundle bundle){
        String result=bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.e("extra",result);
        return JsonUtil.getStatus(result);
    }
}
