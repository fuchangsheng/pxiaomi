package pxm.com.pxm.source.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pxm.com.pxm.R;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.JsonUtil;
import pxm.com.pxm.source.utils.MD5Util;
import pxm.com.pxm.source.utils.MyApplication;
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;

public class RetrievePasswordActivity extends BaseActivity {
    private Button retrieveOk;
    private EditText password,confirm_password;
    private TextView back,confirmListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);

        initView();
    }

    void initView(){
        confirmListener=(TextView)findViewById(R.id.tv_confirm_pass);
        back=(TextView)findViewById(R.id.cancle_retrieve);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        password=(EditText)findViewById(R.id.retrieve_pas);
        confirm_password=(EditText)findViewById(R.id.retrieve_confirm_pas);
        password.addTextChangedListener(watcher);
        confirm_password.addTextChangedListener(watcher);
        retrieveOk=(Button)findViewById(R.id.retrieve_ok);
        retrieveOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.httpUtil.doPostAsync(
                        MyUrlConstructor.INPUT_NEW_PASSWORD,
                        JsonUtil.CreateInputPasswordJsonText(getIntent().getStringExtra("mobile"), MD5Util.getMD5(MD5Util.StringToByte(password.getText().toString(),"UTF-8"))),
                        new OkHttpUtil.Func_jsonString() {
                            @Override
                            public void onSuccess(String result) {
                                if (JsonUtil.getStatus(result)==0){
                                    Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RetrievePasswordActivity.this,MainActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"修改失败",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError() {
                                Toast.makeText(getApplicationContext(),"修改失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                startActivity(new Intent(RetrievePasswordActivity.this,LoginActivity.class));
                finish();
            }
        });
    }

    TextWatcher watcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if ((password.getText().toString()).equals(confirm_password.getText().toString())){
                confirmListener.setVisibility(View.INVISIBLE);
                retrieveOk.setClickable(true);
            }else{
                confirmListener.setVisibility(View.VISIBLE);
                retrieveOk.setClickable(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
