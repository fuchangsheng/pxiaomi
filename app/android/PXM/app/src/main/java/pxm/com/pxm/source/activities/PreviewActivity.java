package pxm.com.pxm.source.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pxm.com.pxm.R;
import pxm.com.pxm.source.models.Business;
import pxm.com.pxm.source.models.Invoice;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.Constant;
import pxm.com.pxm.source.utils.JsonUtil;
import pxm.com.pxm.source.utils.MyApplication;
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;

public class PreviewActivity extends BaseActivity {
    private Invoice invoice;
    Button previewOk, previewCancel;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        initData();
        //initialize the widgets in this interface
        initView();
    }

    void initData() {
        invoice = (Invoice) getIntent().getSerializableExtra("invoice");
    }

    void initView() {
        builder=new AlertDialog.Builder(PreviewActivity.this)
                .setMessage("\n请求失败，是否重试？\n")
                .setNegativeButton("取消",listener)
                .setPositiveButton("重试",listener);
        dialog=builder.create();

        ((TextView)findViewById(R.id.preview_title)).setText(invoice.getTaxInfo().getInvTitle());
        ((TextView)findViewById(R.id.preview_tax_num)).setText(invoice.getTaxInfo().getTaxNum());
        ((TextView)findViewById(R.id.preview_bank)).setText(invoice.getTaxInfo().getBank());
        ((TextView)findViewById(R.id.preview_account)).setText(invoice.getTaxInfo().getBankAccount());
        ((TextView)findViewById(R.id.preview_address)).setText(invoice.getTaxInfo().getAddress());
        ((TextView)findViewById(R.id.preview_mobile)).setText(invoice.getTaxInfo().getTel());
        ((TextView)findViewById(R.id.preview_hotel)).setText(invoice.getBusinessName());
        ((TextView)findViewById(R.id.preview_amount)).setText(invoice.getAmount());
        previewOk = (Button) findViewById(R.id.preview_ok);
        previewOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestInvoice();
            }
        });

        previewCancel = (Button) findViewById(R.id.preview_cancle);
        previewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PreviewActivity.this, HotelInfoActivity.class));
            }
        });
    }


    DialogInterface.OnClickListener listener=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i){
                case Constant.TRY_AGAIN:
                    requestInvoice();
                    break;
            }
        }
    };


    void requestInvoice(){
        previewOk.setClickable(false);
        Log.e("extra",JsonUtil.CreateGenerateInvoiceJsonText(invoice,(Business) getIntent().getSerializableExtra("business")));
        MyApplication.httpUtil.doPostAsync(
                MyUrlConstructor.GENERATE_INVOICE,
                JsonUtil.CreateGenerateInvoiceJsonText(invoice,(Business) getIntent().getSerializableExtra("business")),
                new OkHttpUtil.Func_jsonString() {
                    @Override
                    public void onSuccess(final String result) {
                        if (JsonUtil.getStatus(result)==0){
                            previewOk.setClickable(true);
                            new AlertDialog.Builder(PreviewActivity.this)
                                    .setMessage("\n开票请求已提交！\n")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            startActivity(new Intent(PreviewActivity.this, MainActivity.class));
                                            finish();
                                        }
                                    })
                            .create()
                            .show();
                        }
                        else{
                            dialog.show();
                            previewOk.setClickable(true);
                        }

                    }

                    @Override
                    public void onError() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.setMessage("\n网络错误!\n");
                                dialog.show();
                                previewOk.setClickable(true);
                            }
                        });
                    }
                }
        );
    }
}
