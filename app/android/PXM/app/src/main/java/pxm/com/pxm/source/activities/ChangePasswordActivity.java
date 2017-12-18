package pxm.com.pxm.source.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import pxm.com.pxm.R;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.JsonUtil;
import pxm.com.pxm.source.utils.MD5Util;
import pxm.com.pxm.source.utils.MyApplication;
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;

public class ChangePasswordActivity extends BaseActivity {
    private EditText old,newPass,confirmNewPass;
    private Button confirm;
    private TextView cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        init();
    }

    void init(){
        old=(EditText)findViewById(R.id.change_old_password);
        newPass=(EditText)findViewById(R.id.change_new_pass);
        confirmNewPass=(EditText)findViewById(R.id.change_new_pass_again);
        confirm=(Button)findViewById(R.id.confirm_change);
        confirm.setTag(false);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (old.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"请输入原始密码",Toast.LENGTH_SHORT).show();
                }
                else if (newPass.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"请设置新密码",Toast.LENGTH_SHORT).show();
                }
                else if (confirmNewPass.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"请输入新密码",Toast.LENGTH_SHORT).show();
                }
                else if(!(newPass.getText().toString().equals(confirmNewPass.getText().toString()))){
                    Toast.makeText(getApplicationContext(),"两次密码不一致",Toast.LENGTH_SHORT).show();
                }else{
                    MyApplication.httpUtil.doPostAsync(
                            //url
                            MyUrlConstructor.MODIFY_PASSWORD,

                            //json string
                            JsonUtil.CreateChangePasswordJsonText(
                                    //old password
                                    MD5Util.getMD5(MD5Util.StringToByte(old.getText().toString(), "UTF-8")),
                                    //new password
                                    MD5Util.getMD5(MD5Util.StringToByte(newPass.getText().toString(), "UTF-8"))
                            ),

                            //callback listener
                            new OkHttpUtil.Func_jsonString() {
                                @Override
                                public void onSuccess(final String result) {
                                    if (JsonUtil.getStatus(result)==0){
                                        Toast.makeText(getApplicationContext(),"修改成功！",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                    else if (JsonUtil.getStatus(result)==2){
                                        Toast.makeText(getApplicationContext(),"请输入正确的原始密码！",Toast.LENGTH_LONG).show();
                                    }
                                    else if (JsonUtil.getStatus(result)==1){
                                        Toast.makeText(getApplicationContext(),"新旧密码不能相同！",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"密码修改失败\n请稍候尝试",Toast.LENGTH_LONG).show();
                                    }

                                }

                                @Override
                                public void onError() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                    );
                }

                    //finish();
            }
        });

        cancle=(TextView) findViewById(R.id.cancle_change);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
