package pxm.com.pxm.source.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.File;
import java.util.Calendar;
import java.util.List;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import de.hdodenhof.circleimageview.CircleImageView;
import pxm.com.pxm.R;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.FileUtil;
import pxm.com.pxm.source.utils.JsonUtil;
import pxm.com.pxm.source.utils.MyApplication;
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;
import pxm.com.pxm.source.utils.PicassoImageLoader;

public class EditUser_infoActivity extends BaseActivity {
    private Button save, cancle, camera, gallery, dismiss;
    private TextView birth_text;
    private CircleImageView portrait;
    private View dialogView;
    private Dialog dialog;
    private RelativeLayout toDatePicker;
    private EditText editUserName, editEmail;
    private RadioButton male, female;
    private SharedPreferences sharedPreferences;
    String date;
    int age;

    @Override
    protected void onResume() {
        super.onResume();
        resetPortrait();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituser_info);
        //initialize the widgets in this interface
        initView();
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

    void showDialog() {
        dialog.setContentView(dialogView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    void initView() {
        final Calendar calendar = Calendar.getInstance();
        date = "";
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        dialogView = getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
        dialog = new Dialog(this, R.style.transparentFrameWindowStyle);

        portrait = (CircleImageView) findViewById(R.id.profile_image);
        resetPortrait();
        portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        ((TextView)findViewById(R.id.edit_user_info_mobile)).setText(getSharedPreferences("account",MODE_PRIVATE).getString("mobile",""));
        editUserName = (EditText) findViewById(R.id.edit_user_name);
        editUserName.setText(getSharedPreferences("account",MODE_PRIVATE).getString("userName",""));
        editEmail = (EditText) findViewById(R.id.edit_email);
        editEmail.setText(getSharedPreferences("account",MODE_PRIVATE).getString("email",""));
        male = (RadioButton) findViewById(R.id.button_male);
        female = (RadioButton) findViewById(R.id.button_female);

        birth_text = (TextView) findViewById(R.id.birth_text);
        birth_text.setText(sharedPreferences.getString("birthday", null));

        toDatePicker = (RelativeLayout) findViewById(R.id.rl_to_date_picker);
        final int year, month, day;
        year = sharedPreferences.getInt("year", calendar.get(Calendar.YEAR));
        month = sharedPreferences.getInt("month", calendar.get(Calendar.MONTH))-1;
        day = sharedPreferences.getInt("date", calendar.get(Calendar.DAY_OF_MONTH));
        toDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditUser_infoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                date = i + " 年" + " " + (i1 + 1) + " 月" + " " + i2 + " 日";
                                SharedPreferences.Editor editor = sharedPreferences.edit().putString("birthday", date);
                                editor.putInt("year", i);
                                editor.putInt("month", i1 + 1);
                                editor.putInt("date", i2);
                                editor.apply();
                                birth_text.setText(date);
                                age = calendar.get(Calendar.YEAR) - i;
                                if (calendar.get(Calendar.MONTH) <= (i1 + 1)) {
                                    if (calendar.get(Calendar.MONTH) < (i1 + 1)) {
                                        age = age - 1;
                                    } else {
                                        if (calendar.get(Calendar.DAY_OF_MONTH) < i2) {
                                            age = age - 1;
                                        }
                                    }
                                }
                            }
                        },
                        year,
                        month,
                        day
                ).show();
            }
        });

        save = (Button) findViewById(R.id.save_user_info);
        cancle = (Button) findViewById(R.id.cancle_edit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.httpUtil.doPostAsync(
                        MyUrlConstructor.MODIFY_USERINFO,
                        JsonUtil.CreateModifyUserInfoJsonText(
                                editUserName.getText().toString(),
                                age,
                                (male.isChecked()) ? 0 : 1,
                                editEmail.getText().toString()),
                        new OkHttpUtil.Func_jsonString() {
                            @Override
                            public void onSuccess(final String result) {
                                if (JsonUtil.getStatus(result) == 0) {
                                    Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onError() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                );
                finish();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        camera = (Button) dialogView.findViewById(R.id.from_camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                GalleryFinal.openCamera(GalleryFinal.getRequestCode(), portraitCallback);
            }
        });


        gallery = (Button) dialogView.findViewById(R.id.from_gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                GalleryFinal.openGallerySingle(GalleryFinal.getRequestCode(), portraitCallback);
            }
        });

        dismiss = (Button) dialogView.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    void handlePortrait(List<PhotoInfo> resultList) {
        final File myPhoto = new File(resultList.get(0).getPhotoPath());
        MyApplication.httpUtil.sendMultiPartAsync(
                MyUrlConstructor.UPLOAD_USER_PORTRAIT,
                "file",
                myPhoto,
                new OkHttpUtil.Func_jsonString() {
                    @Override
                    public void onSuccess(String result) {
                        if (JsonUtil.getStatus(result) == 0) {
                            Toast.makeText(getApplicationContext(), "头像上传成功", Toast.LENGTH_SHORT).show();
                            JSONObject obj = JsonUtil.getResultJson(result);
                            try {
                                MyUrlConstructor.portraitUrl = MyUrlConstructor.BASIC + obj.getJSONObject("result").getString("portraitURL");
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        byte[] bytes = FileUtil.readFile(myPhoto);
                                        if (FileUtil.saveFile(getApplicationContext(), "portrait", bytes)) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    new PicassoImageLoader().displayImage(EditUser_infoActivity.this,
                                                            myPhoto.getPath(),
                                                            portrait,
                                                            getResources().getDrawable(R.drawable.head),
                                                            300,
                                                            300
                                                    );
                                                }
                                            });

                                        }

                                    }
                                }).start();


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
        );

    }

    GalleryFinal.OnHanlderResultCallback portraitCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int requestCode, List<PhotoInfo> resultList) {
            handlePortrait(resultList);
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
        }
    };
}
