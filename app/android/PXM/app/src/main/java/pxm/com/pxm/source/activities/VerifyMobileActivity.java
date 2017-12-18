package pxm.com.pxm.source.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pxm.com.pxm.R;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.Constant;
import pxm.com.pxm.source.utils.JsonUtil;
import pxm.com.pxm.source.utils.MyApplication;
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;


public class VerifyMobileActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_register,getCode;
    private EditText mobile,code;
    int count=60;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Constant.SUCCESSFUL:
                    Toast.makeText(getApplicationContext(),(String)msg.obj,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(VerifyMobileActivity.this,RegisterActivity.class).putExtra("mobile",mobile.getText().toString()));
                    break;
                case Constant.FAILED:
                    Toast.makeText(getApplicationContext(),"验证码错误",Toast.LENGTH_LONG).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setInitTitleAllowed(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile);

        init();
    }

    void init(){
        btn_register=(Button)findViewById(R.id.btn_regester);
        btn_register.setOnClickListener(this);

        getCode=(Button)findViewById(R.id.get_sms_code);
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mobile.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"请输入手机号！",Toast.LENGTH_SHORT).show();
                }else {
                    setCounter(getCode);
                    MyApplication.httpUtil.doPostAsync(
                            MyUrlConstructor.GET_SMSCODE,
                            JsonUtil.CreateGetSmsCodeJsonText(mobile.getText().toString()),
                            new OkHttpUtil.Func_jsonString() {
                                @Override
                                public void onSuccess(String result) {
                                    if (JsonUtil.getStatus(result)==0){
                                        Toast.makeText(getApplicationContext(),"验证码已发送，请注意查收",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onError() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(),"请求失败，请重新尝试",Toast.LENGTH_LONG).show();
                                            count=-1;
                                        }
                                    });
                                }
                            }
                    );
                }

            }
        });
        mobile=(EditText)findViewById(R.id.register_01_mobile);
        code=(EditText)findViewById(R.id.register_01_code);
    }

    void setCounter(final TextView getCode){
        getCode.setClickable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                count=60;
                while (count>1){
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                count--;
                                String countString=count+"s后重新获取";
                                getCode.setText(countString);
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getCode.setText("获取验证码");
                        getCode.setClickable(true);
                    }
                });
            }
        }).start();
    }


    @Override
    public void onClick(View view) {
        if (code.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"请输入验证码！",Toast.LENGTH_SHORT).show();
        }else {
            switch (view.getId()){
                case R.id.btn_regester:
                    MyApplication.httpUtil.doPostAsync(MyUrlConstructor.VERIFY,
                            JsonUtil.CreateVerifyJsonText(mobile.getText().toString(),code.getText().toString()),
                            new OkHttpUtil.Func_jsonString() {
                                @Override
                                public void onSuccess(String result) {
                                    Message message=new Message();
                                    if (JsonUtil.getStatus(result)==0){
                                        message.obj=result;
                                        message.what= Constant.SUCCESSFUL;
                                    }else {
                                        message.what=Constant.FAILED;
                                    }
                                    handler.sendMessage(message);
                                }

                                @Override
                                public void onError() {
                                    handler.sendEmptyMessage(Constant.FAILED);
                                }
                            });
                    break;
            }
        }
    }
}
