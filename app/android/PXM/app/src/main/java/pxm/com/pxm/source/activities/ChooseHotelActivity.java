package pxm.com.pxm.source.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pxm.com.pxm.R;
import pxm.com.pxm.source.models.Business;
import pxm.com.pxm.source.models.TaxInformation;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.Constant;
import pxm.com.pxm.source.utils.JsonUtil;
import pxm.com.pxm.source.utils.MyApplication;
import pxm.com.pxm.source.utils.MyOnTouchListener;
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;

public class ChooseHotelActivity extends BaseActivity {
    private Button hotelOk;
    private SearchView searchView;
    private ListView lv_hotel;
    private TaxInformation information;
    private List<Business> businessList;
    private String[] hotelInfos;
    private List<String> hotels;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hotel);

        initData();

        //initialize the widgets in this interface
        initView();


    }


    void initData() {
        businessList=new ArrayList<>();

        information = (TaxInformation) getIntent().getSerializableExtra("info");
        initBusiness();
    }



    void initView() {
        lv_hotel = (ListView) findViewById(R.id.lv_hotel_info);
        adapter= new ArrayAdapter<>(this, R.layout.item, hotelInfos);
        lv_hotel.setAdapter(adapter);

        lv_hotel.setTextFilterEnabled(true);
        lv_hotel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hotelOk(businessList.get(i));
            }
        });


        searchView = (SearchView) findViewById(R.id.search_hotel);
        searchView.setIconified(false);
        searchView.setQueryRefinementEnabled(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryString) {
                hotelOk(new Business("123",queryString,"123","123"));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryString) {
                if (TextUtils.isEmpty(queryString)) {

                    lv_hotel.clearTextFilter();

                } else {
                    queryStringFromServer(queryString);
                    adapter.getFilter().filter(queryString);
                }
                return false;
            }
        });
    }


    void hotelOk(Business business) {
        Intent intent = new Intent(ChooseHotelActivity.this, HotelInfoActivity.class);
        Bundle data = new Bundle();
        data.putSerializable("info", information);
        data.putSerializable("business",business);
        intent.putExtras(data);
        intent.putExtra("businessName", business.getName());
        startActivity(intent);
    }

    void initBusiness() {
        hotels = new ArrayList<>();
        for (int i=0;i<hotels.size();i++){
            hotels.remove(i);
        }
        for (int i=0;i<businessList.size();i++){
            hotels.add(businessList.get(i).getName()+"\t\t"+businessList.get(i).getAddress());
        }
        hotelInfos = new String[hotels.size()];
        hotelInfos = hotels.toArray(hotelInfos);
    }


    void queryStringFromServer(String query) {
        MyApplication.httpUtil.doGetAsync(
                MyUrlConstructor.QUERY_BUSINESS + "?nameInfo=" + query,
                new OkHttpUtil.Func_jsonString() {
                    @Override
                    public void onSuccess(String result) {
                        if (JsonUtil.getStatus(result) == 0) {
                            try {
                                businessList = JsonUtil.jsonArrayToBusinessList(JsonUtil.getResultJson(result).getJSONArray("result"));
                                initBusiness();
                                adapter= new ArrayAdapter<>(getApplicationContext(),R.layout.item, hotelInfos);
                                lv_hotel.setAdapter(adapter);
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
                                Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
        );
    }
}
