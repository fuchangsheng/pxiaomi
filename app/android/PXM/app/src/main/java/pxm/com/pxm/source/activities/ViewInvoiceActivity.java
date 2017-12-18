package pxm.com.pxm.source.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import pxm.com.pxm.R;
import pxm.com.pxm.source.models.Invoice;
import pxm.com.pxm.source.utils.BaseActivity;

public class ViewInvoiceActivity extends BaseActivity {
    private Invoice invoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_invoice);

        initData();

        initView();
    }

    void initData() {
        invoice = (Invoice) getIntent().getSerializableExtra("invoice");
    }

    void initView() {
        TextView title = (TextView) findViewById(R.id.view_invoice_result_title);
        TextView taxNum = (TextView) findViewById(R.id.view_invoice_result_tax_num);
        TextView bank = (TextView) findViewById(R.id.view_invoice_result_bank);
        TextView bankNo = (TextView) findViewById(R.id.view_invoice_result_bank_num);
        TextView address = (TextView) findViewById(R.id.view_invoice_result_address);
        TextView mobile = (TextView) findViewById(R.id.view_invoice_result_mobile);
        TextView businessName = (TextView) findViewById(R.id.view_invoice_result_business_name);
        TextView amount = (TextView) findViewById(R.id.view_invoice_result_amount);
        TextView result = (TextView) findViewById(R.id.view_invoice_result_text);
        ImageView resultImag = (ImageView) findViewById(R.id.view_invoice_result_img);
        title.setText(invoice.getTaxInfo().getInvTitle());
        taxNum.setText(invoice.getTaxInfo().getTaxNum());
        bank.setText(invoice.getTaxInfo().getBank());
        bankNo.setText(invoice.getTaxInfo().getBankAccount());
        address.setText(invoice.getTaxInfo().getAddress());
        mobile.setText(invoice.getTaxInfo().getTel());
        businessName.setText(invoice.getBusinessName());
        amount.setText(invoice.getAmount());
        result.setText((invoice.getResult() == 0) ? "开票成功！" : "开票失败！");
        if (invoice.getResult()!=0){
            resultImag.setBackground(getResources().getDrawable(R.drawable.failure_02));
        }
        else {
            resultImag.setBackground(getResources().getDrawable(R.drawable.success_02));
        }
    }
}
