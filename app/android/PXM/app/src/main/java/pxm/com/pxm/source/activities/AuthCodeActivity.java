package pxm.com.pxm.source.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pxm.com.pxm.R;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.JsonUtil;
import pxm.com.pxm.source.utils.MyApplication;
import pxm.com.pxm.source.utils.MyOnTouchListener;
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;

public class AuthCodeActivity extends BaseActivity {
    private Button authcodeOk;
    private TextView back, mobile;
    private EditText input_code;
    int count=60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_code);
        initView();

        setCounter((TextView)findViewById(R.id.auth_code_timer));
    }

    void setCounter(final TextView getCode){
        getCode.setClickable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
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

    void initView() {
        back = (TextView) findViewById(R.id.cancle_code);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        back.setOnTouchListener(new MyOnTouchListener());

        mobile = (TextView) findViewById(R.id.authcode_mobile);
        mobile.setText(getIntent().getStringExtra("mobile"));


        input_code = (EditText) findViewById(R.id.auth_code_input_code);
        authcodeOk = (Button) findViewById(R.id.code_confirm);
        authcodeOk.setOnTouchListener(new MyOnTouchListener());
        authcodeOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input_code.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
                } else {
                    MyApplication.httpUtil.doPostAsync(
                            MyUrlConstructor.PASSWORD_AUTH_CODE,
                            JsonUtil.CreateVerifyJsonText(mobile.getText().toString(), input_code.getText().toString()),
                            new OkHttpUtil.Func_jsonString() {
                                @Override
                                public void onSuccess(String result) {
                                    int status = JsonUtil.getStatus(result);
                                    switch (status) {
                                        case 0:
                                            Intent intent=new Intent(AuthCodeActivity.this,RetrievePasswordActivity.class);
                                            intent.putExtra("mobile",mobile.getText().toString());
                                            startActivity(intent);
                                            break;
                                        case 1:
                                            Toast.makeText(getApplicationContext(), "参数错误", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 2:
                                            Toast.makeText(getApplicationContext(), "用户不存在", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 3:
                                            Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 4:
                                            Toast.makeText(getApplicationContext(), "验证码输入超时，请重新获取", Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }

                                @Override
                                public void onError() {

                                }
                            }
                    );
                }
            }
        });
    }
}
