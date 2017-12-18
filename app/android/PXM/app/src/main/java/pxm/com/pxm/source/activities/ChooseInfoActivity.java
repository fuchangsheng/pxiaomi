package pxm.com.pxm.source.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pxm.com.pxm.R;
import pxm.com.pxm.source.models.TaxInformation;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.JsonUtil;
import pxm.com.pxm.source.utils.MyApplication;
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;

public class ChooseInfoActivity extends BaseActivity {
    private ImageButton btnCreate;
    private MyListViewAdapter lv_adapter;
    private ListView lv_info;

    private List<TaxInformation> informationList=new ArrayList<>();


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_info);

        //initialize the widgets in the interface
        init();

        initData();
    }

    void init(){
        btnCreate=(ImageButton)findViewById(R.id.btn_create);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseInfoActivity.this,CreateInfoActivity.class));
            }
        });

        lv_info = (ListView)findViewById(R.id.lv_info);
        lv_adapter = new MyListViewAdapter();
        lv_info.setAdapter(lv_adapter);
        lv_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ChooseInfoActivity.this,ViewTaxInfoActivity.class);
                Bundle data=new Bundle();
                data.putSerializable("info",informationList.get(position));
                intent.putExtras(data);
                startActivity(intent);
            }
        });

        lv_info.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i){
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        btnCreate.setVisibility(View.VISIBLE);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        btnCreate.setVisibility(View.GONE);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        btnCreate.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });


    }

    void initData(){
        MyApplication.httpUtil.doGetAsync(
                MyUrlConstructor.QUERY_ALL_TAX_INFORMATION+"?userId=" + MyApplication.userId,
                new OkHttpUtil.Func_jsonString() {
                    @Override
                    public void onSuccess(final String result) {
                        JSONObject resultJson=JsonUtil.getResultJson(result);
                        try {
                            if (resultJson.getInt("status")==0){
                                Log.e("LIST",result);
                                JSONArray taxArray=resultJson.getJSONArray("result");
                                MyApplication.dbOperator.deleteTaxInfo();
                                JsonUtil.jsonArrayToTaxList(taxArray);
                                informationList=MyApplication.dbOperator.loadTaxInformation();
                                lv_adapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
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


    class MyListViewAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.infolist_item,null);
                viewHolder = new ViewHolder();
                viewHolder.order = (TextView) view.findViewById(R.id.item_order);
                viewHolder.title = (TextView) view.findViewById(R.id.item_title);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            String str=position+1+"";
            viewHolder.order.setText(str);
            viewHolder.title.setText(informationList.get(position).getInvTitle());
            return view;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public Object getItem(int i) {
            return informationList.get(i);
        }

        @Override
        public int getCount() {
            return informationList.size();
        }

        class ViewHolder {
            TextView order;
            TextView title;
        }
    }
}
