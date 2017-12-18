package pxm.com.pxm.source.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import pxm.com.pxm.R;
import pxm.com.pxm.source.models.Business;
import pxm.com.pxm.source.models.Invoice;
import pxm.com.pxm.source.models.TaxInformation;
import pxm.com.pxm.source.utils.BaseActivity;

public class HotelInfoActivity extends BaseActivity {
    private EditText rate, amount;
    private Spinner type, service_content;
    private int modifyType = 1;
    private boolean modifyTypeConfirm = false;
    private TaxInformation information;
    private Invoice invoice;
    private Business business;
    private TextView hotelName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_info);
        setTouchFinishAllowed(true);

        initData();
        //initialize the widgets in this interface
        initView();
    }

    void initData() {
        information = (TaxInformation) getIntent().getSerializableExtra("info");
        invoice = new Invoice();
        business=(Business)getIntent().getSerializableExtra("business");
    }

    void initView() {
        Button preview, changeRate, modifyHotel;

        final AlertDialog.Builder builder = new AlertDialog.Builder(HotelInfoActivity.this);
        builder.setItems(new String[]{"扫一扫", "手动填写"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                modifyType = i;
                modifyTypeConfirm = true;
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (modifyTypeConfirm) {
                    switch (modifyType) {
                        case 0:
                            //To Scanner Activity
                            break;
                        case 1:
                            //Return to ChooseHotelActivity
                            finish();
                            break;
                    }
                }

            }
        });
        hotelName =(TextView)findViewById(R.id.hotel_info_name);
        hotelName.setText(getIntent().getStringExtra("businessName"));
        changeRate = (Button) findViewById(R.id.change_rate);
        rate = (EditText) findViewById(R.id.edit_rate);
        rate.setEnabled(false);

        changeRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rate.setEnabled(true);
                rate.setFocusable(true);
                rate.requestFocus();
                rate.setSelection(rate.getText().length() - 1);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        modifyHotel = (Button) findViewById(R.id.write_by_user);
        modifyHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyTypeConfirm = false;
                dialog.show();
            }
        });

        amount = (EditText) findViewById(R.id.input_amount);


        type = (Spinner) findViewById(R.id.type_spinner);
        service_content = (Spinner) findViewById(R.id.service_spinner);

        preview = (Button) findViewById(R.id.preview);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFormat()){
                    invoice.setTaxInfo(information);
                    invoice.setBusinessName(getIntent().getStringExtra("businessName"));
                    invoice.setAmount(amount.getText().toString());
                    invoice.setType(type.getSelectedItemPosition());
                    invoice.setContent(service_content.getSelectedItemPosition());
                    invoice.setRate(Float.parseFloat(rate.getText().toString().substring(0, rate.getText().toString().length() - 1)) / 100);
                    Intent intent = new Intent(HotelInfoActivity.this, PreviewActivity.class);
                    Bundle data = new Bundle();
                    data.putSerializable("invoice", invoice);
                    data.putSerializable("business",business);
                    intent.putExtras(data);
                    startActivity(intent);
                }

            }
        });
    }


    boolean checkFormat() {
        if (hotelName.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"尚未选择酒店",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (amount.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"尚未填写金额",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
