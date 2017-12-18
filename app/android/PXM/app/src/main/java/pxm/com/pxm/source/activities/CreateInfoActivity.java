package pxm.com.pxm.source.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pxm.com.pxm.R;
import pxm.com.pxm.source.models.TaxInformation;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.JsonUtil;
import pxm.com.pxm.source.utils.MyApplication;
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;

public class CreateInfoActivity extends BaseActivity {
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_info);
        this.setTouchFinishAllowed(true);
        //initialize the widgets in this interface
        init();
    }

    void init(){
        final EditText title,taxNum,bank,bankAccount,address,mobile;
        title=(EditText)findViewById(R.id.create_info_title);
        taxNum=(EditText)findViewById(R.id.create_info_tax_num);
        bank=(EditText)findViewById(R.id.create_info_bank);
        bankAccount=(EditText)findViewById(R.id.create_info_bank_num);
        address=(EditText)findViewById(R.id.create_info_address);
        mobile=(EditText)findViewById(R.id.create_info_mobile);

        save=(Button)findViewById(R.id.save_tax_info);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaxInformation information=new TaxInformation();
                information.setInvTitle(title.getText().toString());
                information.setTaxNum(taxNum.getText().toString());
                information.setBank(bank.getText().toString());
                information.setBankAccount(bankAccount.getText().toString());
                information.setAddress(address.getText().toString());
                information.setTel(mobile.getText().toString());
                MyApplication.httpUtil.doPostAsync(
                        //url
                        MyUrlConstructor.ADD_TAX_INFORMATION,
                        //json string
                        JsonUtil.CreateAddTaxInfoJsonText(information),
                        //callback listener
                        new OkHttpUtil.Func_jsonString() {
                            @Override
                            public void onSuccess(final String result) {
                                if (JsonUtil.getStatus(result)==0){
                                    Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(1000);
                                                finish();
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
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
