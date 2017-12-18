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
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;

public class InputAccountActivity extends BaseActivity {
    private Button accountOk;
    private TextView back;
    private EditText mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_account);

        initView();
    }

    void initView(){
        mobile=(EditText)findViewById(R.id.input_account_mobile);
        back=(TextView)findViewById(R.id.cancle_input);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        accountOk=(Button)findViewById(R.id.account_confirm);
        accountOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mobile.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"请输入手机号",Toast.LENGTH_SHORT).show();
                }else {
                    MyApplication.httpUtil.doPostAsync(
                            MyUrlConstructor.GET_SMSCODE,
                            JsonUtil.CreateGetSmsCodeJsonText(mobile.getText().toString()),
                            new OkHttpUtil.Func_jsonString() {
                                @Override
                                public void onSuccess(String result) {
                                    if (JsonUtil.getStatus(result)==0){
                                        Intent intent=new Intent(InputAccountActivity.this,AuthCodeActivity.class);
                                        intent.putExtra("mobile",mobile.getText().toString());
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"连接失败",Toast.LENGTH_SHORT).show();
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
