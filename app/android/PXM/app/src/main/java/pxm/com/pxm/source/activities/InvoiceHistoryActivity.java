package pxm.com.pxm.source.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pxm.com.pxm.R;
import pxm.com.pxm.source.models.Invoice;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.JsonUtil;
import pxm.com.pxm.source.utils.MyApplication;
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;

public class InvoiceHistoryActivity extends BaseActivity {
    private List<Invoice> invoiceList = new ArrayList<>();
    private ListView listView_invoice;
    private ImageButton search;
    private MyListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_history);

        initView();

        initData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    void initView() {
        search = (ImageButton) findViewById(R.id.search_invoice);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
            }
        });

        listView_invoice = (ListView) findViewById(R.id.lv_invoice);
        adapter = new MyListViewAdapter();
        listView_invoice.setAdapter(adapter);
        listView_invoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InvoiceHistoryActivity.this, ViewInvoiceActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("invoice", invoiceList.get(position));
                intent.putExtras(data);
                startActivity(intent);
            }
        });

        listView_invoice.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        search.setVisibility(View.VISIBLE);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        search.setVisibility(View.GONE);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        search.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    void initData() {
        invoiceList = new ArrayList<>();
        MyApplication.httpUtil.doGetAsync(
                MyUrlConstructor.QUERY_ALL_INVOICE + "?userId=" + MyApplication.userId,
                new OkHttpUtil.Func_jsonString() {
                    @Override
                    public void onSuccess(final String result) {
                        if (JsonUtil.getStatus(result) == 0) {
                            try {
                                JSONObject resultJson = new JSONObject(result);
                                JSONArray array = resultJson.getJSONArray("result");
                                MyApplication.dbOperator.deleteInvoice();
                                JsonUtil.jsonArrayToInvoiceList(array);
                                invoiceList = MyApplication.dbOperator.loadInvoice();
                                adapter.notifyDataSetChanged();
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
                                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
        );
    }


    class MyListViewAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.infolist_item, null);
                viewHolder = new ViewHolder();
                viewHolder.order = (TextView) view.findViewById(R.id.item_order);
                viewHolder.title = (TextView) view.findViewById(R.id.item_title);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            String str = position + 1 + "";
            viewHolder.order.setText(str);
            viewHolder.title.setText(invoiceList.get(position).getTaxInfo().getInvTitle());
            return view;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public Object getItem(int i) {
            return invoiceList.get(i);
        }

        @Override
        public int getCount() {
            return invoiceList.size();
        }

        class ViewHolder {
            TextView order;
            TextView title;
        }
    }
}
