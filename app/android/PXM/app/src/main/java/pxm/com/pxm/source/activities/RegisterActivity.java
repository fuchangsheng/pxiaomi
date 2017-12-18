package pxm.com.pxm.source.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONObject;

import pxm.com.pxm.R;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.JsonUtil;
import pxm.com.pxm.source.utils.MD5Util;
import pxm.com.pxm.source.utils.MyApplication;
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_enter;
    private EditText et_password, confirm_password, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    void init() {
        btn_enter = (Button) findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(this);
        name = (EditText) findViewById(R.id.register_02_name);
        et_password = (EditText) findViewById(R.id.et_password_re);
        et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        confirm_password = (EditText) findViewById(R.id.register_02_pass_again);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_enter:
                if (name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "请输入一个昵称", Toast.LENGTH_LONG).show();
                } else if (et_password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_LONG).show();
                } else if (!(confirm_password.getText().toString().equals(et_password.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "两次密码不一致", Toast.LENGTH_LONG).show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("mobile", getIntent().getStringExtra("mobile"));
                            editor.putString("password", confirm_password.getText().toString());
                            editor.putString("userName", name.getText().toString());
                            editor.apply();
                        }
                    }).start();
                    MyApplication.httpUtil.doPostAsync(MyUrlConstructor.REGISTER,
                            JsonUtil.CreateRegisterJsonText(getIntent().getStringExtra("mobile"), name.getText().toString(), MD5Util.getMD5(MD5Util.StringToByte(confirm_password.getText().toString(), "UTF-8"))),
                            new OkHttpUtil.Func_jsonString() {
                                @Override
                                public void onSuccess(final String result) {
                                    JSONObject jsonResult = JsonUtil.getResultJson(result);
                                    try {
                                        if (jsonResult.getInt("status") == 0) {
                                            MyApplication.userId = jsonResult.getJSONObject("result").getString("userId");
                                            Toast.makeText(getApplicationContext(), "注册成功!", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                            finish();
                                        } else if (jsonResult.getInt("status") == 1) {
                                            Toast.makeText(getApplicationContext(), "用户已存在!", Toast.LENGTH_LONG).show();
                                        } else if (jsonResult.getInt("status") == 2) {
                                            Toast.makeText(getApplicationContext(), "未知错误!", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "注册失败!", Toast.LENGTH_LONG).show();
                                    }


                                }

                                @Override
                                public void onError() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });
                }
        }
    }
}
