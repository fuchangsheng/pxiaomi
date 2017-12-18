package pxm.com.pxm.source.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import pxm.com.pxm.R;
import pxm.com.pxm.source.models.TaxInformation;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.MyApplication;
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;

public class ViewTaxInfoActivity extends BaseActivity {
    private TaxInformation info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_info);
        initData();
        initView();

    }


    void initData(){
        info = (TaxInformation) getIntent().getSerializableExtra("info");
    }

    void initView() {
        ImageButton delete, edit;

        TextView title=(TextView)findViewById(R.id.view_info_title);
        TextView taxNo=(TextView)findViewById(R.id.view_info_tax_num);
        TextView bank=(TextView)findViewById(R.id.view_info_bank);
        TextView bankNo=(TextView)findViewById(R.id.view_info_bank_num);
        TextView address=(TextView)findViewById(R.id.view_info_address);
        TextView mobile=(TextView)findViewById(R.id.view_info_mobile);

        title.setText(info.getInvTitle());
        taxNo.setText(info.getTaxNum());
        bank.setText(info.getBank());
        bankNo.setText(info.getBankAccount());
        address.setText(info.getAddress());
        mobile.setText(info.getTel());

        Button use;
        edit = (ImageButton) findViewById(R.id.edit_tax_info);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewTaxInfoActivity.this, EditInfoActivity.class);
                Bundle data=new Bundle();
                data.putSerializable("info",info);
                intent.putExtras(data);
                startActivity(intent);
            }
        });

        use = (Button) findViewById(R.id.use_tax_info);
        use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewTaxInfoActivity.this, ChooseHotelActivity.class);
                Bundle data=new Bundle();
                data.putSerializable("info",info);
                intent.putExtras(data);
                startActivity(intent);
            }
        });

        delete = (ImageButton) findViewById(R.id.delete_tax_info);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.httpUtil.doGetAsync(
                        MyUrlConstructor.DELETE_TAX_INFORMATION + "?taxId=" + info.getId(),
                        new OkHttpUtil.Func_jsonString() {
                            @Override
                            public void onSuccess(final String result) {
                                Toast.makeText(getApplicationContext(), "已删除", Toast.LENGTH_LONG).show();
                                finish();
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
        });
    }
}
