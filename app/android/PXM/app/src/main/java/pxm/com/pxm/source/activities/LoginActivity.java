package pxm.com.pxm.source.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import pxm.com.pxm.R;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.JsonUtil;
import pxm.com.pxm.source.utils.MD5Util;
import pxm.com.pxm.source.utils.MyApplication;
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private TextView noAccount,forgetPassword;
    private Button btn_login;
    private ImageButton see_password;
    private EditText et_username,et_password;
    private boolean passwordSeen=false;
    private ViewGroup.LayoutParams params;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPwd;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            btn_login.setText("登录");
            btn_login.setClickable(true);
            JSONObject result=(JSONObject)msg.obj;
            switch (msg.what){
                //login successfully
                case 0:
                    try {
                        MyApplication.userId=result.getString("userId");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(),"登录成功！",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(),"用户不存在！",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(),"登录异常！",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(),"密码错误！",Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(getApplicationContext(),"连接失败！请检查网络设置",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setColor("#000000");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }


    /**
     * To initialize the widgets and setOnClickListener.
     */
    void init(){
        sharedPreferences=getSharedPreferences("account",MODE_PRIVATE);

        noAccount=(TextView)findViewById(R.id.tv_noaccount);
        noAccount.setOnClickListener(this);

        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        forgetPassword=(TextView)findViewById(R.id.password_forget);
        forgetPassword.setOnClickListener(this);

        see_password =(ImageButton)findViewById(R.id.see_password);
        see_password.setOnClickListener(this);

        rememberPwd=(CheckBox)findViewById(R.id.checkBox);
        rememberPwd.setChecked(sharedPreferences.getBoolean("remember_pwd_status",false));
        rememberPwd.setOnClickListener(this);

        et_username= (EditText) findViewById(R.id.et_username);
        et_username.setText(sharedPreferences.getString("mobile",""));

        et_password= (EditText) findViewById(R.id.et_password);
        et_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);

        if (sharedPreferences.getBoolean("remember_pwd_status",false)){
            et_password.setText(sharedPreferences.getString("password",""));
        }

    }

    /**
     * To handle click events when a button is clicked
     * @param view  The widget clicked.
     */
    @Override
    public void onClick(View view) {
        String userName=et_username.getText().toString();
        String password=et_password.getText().toString();
        editor=sharedPreferences.edit();
        switch (view.getId()){
            //operation to remember password and phone number
            case R.id.checkBox:
                if (rememberPwd.isChecked()){
                    editor.putBoolean("remember_pwd_status",true);
                    editor.putString("mobile",userName);
                    if(rememberPwd.isChecked()){
                        editor.putString("password",password);
                    }
                    else{
                        editor.putString("password","");
                    }
                }
                else {
                    editor.putBoolean("remember_pwd_status",false);
                }
                editor.apply();
                break;
            case R.id.tv_noaccount:
                startActivity(new Intent(LoginActivity.this,VerifyMobileActivity.class));
                break;
            case R.id.password_forget:
                startActivity(new Intent(LoginActivity.this,InputAccountActivity.class));
                break;
            case R.id.btn_login:
                editor.putString("mobile",userName);
                if (sharedPreferences.getBoolean("remember_pwd_status",false)){
                    editor.putString("password",password);
                }
                editor.apply();
                //format checks
                if(userName.isEmpty()||password.isEmpty()){
                    Toast.makeText(this,"用户名或密码不能为空！",Toast.LENGTH_SHORT).show();
                }
                else{
                    MyApplication.loginCount=sharedPreferences.getInt("loginCount",0);
                    editor.putInt("loginCount",++MyApplication.loginCount);
                    editor.apply();
                    btn_login.setText("登录中..");
                    btn_login.setClickable(false);
                    String passwordInMd5= MD5Util.getMD5(MD5Util.StringToByte(password,"UTF-8"));
                    //login method
                    String loginJsonString= JsonUtil.CreateLoginJsonText(userName,passwordInMd5);
                    MyApplication.httpUtil.doPostAsync(
                            MyUrlConstructor.LOGIN,
                            loginJsonString,
                            new OkHttpUtil.Func_jsonString() {

                                @Override
                                //success to get response from server
                                public void onSuccess(final String result) {
                                    JSONObject response=JsonUtil.getResultJson(result);
                                    try {
                                        int loginStatus=response.getInt("status");
                                        Message message=new Message();
                                        //success to login
                                        if (loginStatus==0){
                                            message.what=0;
                                            message.obj=response.getJSONObject("result");
                                            handler.sendMessage(message);
                                        }
                                        //fail to login
                                        else{
                                            handler.sendEmptyMessage(loginStatus);
                                        }

                                    } catch (Exception e) {
                                        handler.sendEmptyMessage(3);
                                    }

                                }

                                @Override
                                //fail to get response from server
                                public void onError() {
                                    handler.sendEmptyMessage(4);
                                }
                            });

                }

                break;
            case R.id.see_password:
                if(passwordSeen){
                    passwordSeen=false;
                    params= see_password.getLayoutParams();
                    params.height=params.height*2;
                    see_password.setLayoutParams(params);
                    see_password.setBackgroundResource(R.drawable.eye);
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                else{
                    passwordSeen=true;
                    params= see_password.getLayoutParams();
                    params.height=params.height/2;
                    see_password.setLayoutParams(params);
                    see_password.setBackgroundResource(R.drawable.eye_close);
                    et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
        }
    }

}
