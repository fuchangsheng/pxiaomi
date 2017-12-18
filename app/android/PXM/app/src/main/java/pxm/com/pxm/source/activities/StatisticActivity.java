package pxm.com.pxm.source.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

import pxm.com.pxm.R;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.CycleDrawer;
import pxm.com.pxm.source.utils.JsonUtil;
import pxm.com.pxm.source.utils.MyApplication;
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;

public class StatisticActivity extends BaseActivity {

    private TextView current_date,totleInvoice,specialNum,normalNum,totalAmount,specialAmount,normalAmount;
    private CycleDrawer drawer_invoice, drawer_amount;
    private int invoiceNormalCount, invoiceSpecialCount;
    private int normalMoney, specialMoney;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        initView();

        initData();



    }

    void intDrawer() {
        if (invoiceSpecialCount == 0 && invoiceNormalCount == 0) {
            Toast toast=Toast.makeText(getApplicationContext(), "你还没有开过发票", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        } else {
            drawer_invoice.setAngle(100 * invoiceSpecialCount / (invoiceSpecialCount + invoiceNormalCount));
            drawer_amount.setAngle(100 * specialMoney / (specialMoney + normalMoney));
            String string=(invoiceSpecialCount + invoiceNormalCount)+"";
            totleInvoice.setText(string);
            string=invoiceSpecialCount+"张";
            specialNum.setText(string);
            string=invoiceNormalCount+"张";
            normalNum.setText(string);
            string=specialMoney + normalMoney+"";
            totalAmount.setText(string);
            string=specialMoney+"元";
            specialAmount.setText(string);
            string=normalMoney+"元";
            normalAmount.setText(string);
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    void initView() {
        current_date = (TextView) findViewById(R.id.current_date);
        drawer_invoice = (CycleDrawer) findViewById(R.id.circle_invoice);
        drawer_amount = (CycleDrawer) findViewById(R.id.circle_amount);
        linearLayout = (LinearLayout) findViewById(R.id.has_invoice);
        linearLayout.setVisibility(View.GONE);
        totleInvoice=(TextView)findViewById(R.id.totle_invoice);
        specialNum=(TextView)findViewById(R.id.special_num);
        normalNum=(TextView)findViewById(R.id.normal_num);
        totalAmount=(TextView)findViewById(R.id.total_amont);
        specialAmount=(TextView)findViewById(R.id.special_amount);
        normalAmount=(TextView)findViewById(R.id.normal_amount);
    }

    void initData() {
        Calendar calendar = Calendar.getInstance();
        current_date.setText(current_date.getText().toString().concat(calendar.get(Calendar.YEAR) + " 年 " + (calendar.get(Calendar.MONTH) + 1) + " 月 " + calendar.get(Calendar.DAY_OF_MONTH) + " 日"));
        MyApplication.httpUtil.doGetAsync(
                MyUrlConstructor.GET_INVOICE_STAT + "?userId=" + MyApplication.userId,
                new OkHttpUtil.Func_jsonString() {
                    @Override
                    public void onSuccess(final String result) {
                        if (JsonUtil.getStatus(result) == 0) {
                            try {
                                JSONObject resJson = JsonUtil.getResultJson(result);

                                JSONObject resultJson=resJson.getJSONObject("result");

                                invoiceNormalCount = resultJson.getInt("simple");

                                invoiceSpecialCount = resultJson.getInt("complex");

                                normalMoney = Integer.parseInt(resultJson.getString("simMoney"));
                                specialMoney = Integer.parseInt(resultJson.getString("comMoney"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
                            Toast toast=Toast.makeText(getApplicationContext(),"获取数据失败，请稍后尝试",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.show();
                        }
                        intDrawer();
                    }

                    @Override
                    public void onError() {

                        invoiceNormalCount = 0;
                        invoiceSpecialCount = 0;
                        normalMoney = 0;
                        specialMoney = 0;
                    }
                }
        );
    }
}
