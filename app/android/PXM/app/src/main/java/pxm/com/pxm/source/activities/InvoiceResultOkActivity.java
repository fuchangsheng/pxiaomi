package pxm.com.pxm.source.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import pxm.com.pxm.R;
import pxm.com.pxm.source.utils.BaseActivity;

public class InvoiceResultOkActivity extends BaseActivity {
    private Button history, ok;
    private static final String TAG = "JPush";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_result_ok);
        Log.e("extra","ok");
        init();
        Log.e("extra","init");
    }



    void init() {
        history = (Button) findViewById(R.id.view_history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InvoiceResultOkActivity.this, InvoiceHistoryActivity.class));
                finish();
            }
        });

        ok = (Button) findViewById(R.id.result_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InvoiceResultOkActivity.this, MainActivity.class));
            }
        });
    }
}
