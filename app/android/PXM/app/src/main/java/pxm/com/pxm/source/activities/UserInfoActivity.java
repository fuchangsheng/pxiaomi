package pxm.com.pxm.source.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import pxm.com.pxm.R;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.FileUtil;
import pxm.com.pxm.source.utils.JsonUtil;
import pxm.com.pxm.source.utils.MyApplication;
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;

public class UserInfoActivity extends BaseActivity {
    private Button btnOk;
    private ImageButton btnEdit;
    private CircleImageView portrait;
    private TextView userName,gender,age,email,mobile;

    @Override
    protected void onResume() {
        super.onResume();
        resetPortrait();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        //set finish type
        this.setTouchFinishAllowed(true);
        //initialize the widgets in this interface
        initView();

        initData();
    }

    void resetPortrait(){
        byte[] bytes = FileUtil.readFile(getApplicationContext(), "portrait");
        Bitmap bm;
        if ((bytes.length > 0)&&(MyApplication.loginCount>0)) {
            bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            bm = BitmapFactory.decodeResource(getResources(),R.drawable.head);
        }
        portrait.setImageBitmap(bm);
    }


    void initData(){
        MyApplication.httpUtil.doGetAsync(
                MyUrlConstructor.VIEW_USER_INFO + "?userId=" + MyApplication.userId,
                new OkHttpUtil.Func_jsonString() {
                    @Override
                    public void onSuccess(String result) {
                        if (JsonUtil.getStatus(result)==0){
                            try {
                                JSONObject userInfoJson=JsonUtil.getResultJson(result).getJSONObject("result");
                                MyUrlConstructor.portraitUrl=userInfoJson.optString("portrait");
                                String string="昵称 ："+userInfoJson.optString("userName");
                                userName.setText(string);
                                switch (userInfoJson.optInt("gender")){
                                    case 0:
                                        gender.setText("男");
                                        break;
                                    case 1:
                                        gender.setText("女");
                                        break;
                                    case 2:
                                        gender.setText("");
                                        break;
                                }
                                String s=userInfoJson.optInt("age")+"";
                                age.setText(s);
                                string="手机 ："+userInfoJson.optString("mobile");
                                mobile.setText(string);
                                string="邮箱 ："+userInfoJson.getString("email");
                                email.setText(string);
                                getSharedPreferences("account",MODE_PRIVATE).edit().putString("email",userInfoJson.getString("email")).apply();
                                getSharedPreferences("account",MODE_PRIVATE).edit().putString("userName",userInfoJson.getString("userName")).apply();
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onError() {

                    }
                }
        );
    }

    void initView(){
        btnEdit=(ImageButton) findViewById(R.id.btn_edit_user_info);
        btnOk=(Button)findViewById(R.id.btn_user_info_ok);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfoActivity.this,EditUser_infoActivity.class));
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        portrait = (CircleImageView)findViewById(R.id.portrait_view_ueser_info);
        resetPortrait();

        userName=(TextView)findViewById(R.id.view_userinfo_user_name);
        gender=(TextView)findViewById(R.id.view_user_info_gender);
        age=(TextView)findViewById(R.id.view_user_info_age);
        email=(TextView)findViewById(R.id.view_user_info_email);
        mobile=(TextView)findViewById(R.id.view_user_info_mobile);
    }
}
