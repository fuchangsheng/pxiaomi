package pxm.com.pxm.source.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pxm.com.pxm.R;
import pxm.com.pxm.source.models.TaxInformation;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.JsonUtil;
import pxm.com.pxm.source.utils.MyApplication;
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;

public class EditInfoActivity extends BaseActivity {
    private Button save;
    private TaxInformation info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        initData();

        initView();
    }

    void initData(){
        info=(TaxInformation)(getIntent().getSerializableExtra("info"));
    }

    void initView() {

        final EditText title=(EditText)findViewById(R.id.edit_info_title);
        final EditText taxNum=(EditText)findViewById(R.id.edit_info_tax_num);
        final EditText bank=(EditText)findViewById(R.id.edit_info_bank);
        final EditText bankNum=(EditText)findViewById(R.id.edit_info_bank_account);
        final EditText address=(EditText)findViewById(R.id.edit_info_address);
        final EditText mobile=(EditText)findViewById(R.id.edit_info_mobile);

        title.setText(info.getInvTitle());
        taxNum.setText(info.getTaxNum());
        bank.setText(info.getBank());
        bankNum.setText(info.getBankAccount());
        address.setText(info.getAddress());
        mobile.setText(info.getTel());

        save = (Button) findViewById(R.id.save_info_change);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info.setInvTitle(title.getText().toString());
                info.setTaxNum(taxNum.getText().toString());
                info.setBank(bank.getText().toString());
                info.setBankAccount(bankNum.getText().toString());
                info.setAddress(address.getText().toString());
                info.setTel(mobile.getText().toString());
                //save();
                MyApplication.httpUtil.doPostAsync(
                        //url
                        MyUrlConstructor.MODIFY_TAX_INFORMATION,
                        //json string
                        JsonUtil.CreateModifyTaxInfoJsonText(info),
                        //callback listener
                        new OkHttpUtil.Func_jsonString() {
                            @Override
                            public void onSuccess(final String result) {
                                if (JsonUtil.getStatus(result)==0){
                                    Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"修改失败",Toast.LENGTH_SHORT).show();
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
        });
    }
}

