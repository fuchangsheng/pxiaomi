package pxm.com.pxm.source.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.InstrumentedActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import pxm.com.pxm.R;
import pxm.com.pxm.source.models.TaxInformation;
import pxm.com.pxm.source.utils.BaseActivity;
import pxm.com.pxm.source.utils.FileUtil;
import pxm.com.pxm.source.utils.JsonUtil;
import pxm.com.pxm.source.utils.MyApplication;
import pxm.com.pxm.source.utils.MyOnTouchListener;
import pxm.com.pxm.source.utils.MyUrlConstructor;
import pxm.com.pxm.source.utils.OkHttpUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_invoice, ll_info, ll_mine, ll_userInfo, ll_scan, ll_history, ll_data, ll_changePassword, ll_exit;
    private TextView tv_invoice, tv_info, tv_mine, tv_title, main_userName, main_mobile;
    private ImageView iv_invoice, iv_info, iv_mine;
    private ImageButton btn_create;
    private ViewPager viewPager;
    public static boolean isForeground = false;
    private ContentAdapter adapter;
    private List<View> views;
    private ListView lv_main;
    private RollPagerView rollPagerView;
    private Button btn_special, btn_normal;
    private long mExitTime;
    private CircleImageView portrait;
    private MyListViewAdapter lv_adapter;
    private List<TaxInformation> informationList = new ArrayList<>();
    MyOnTouchListener onTouchListener = new MyOnTouchListener();


    @Override
    protected void onResume() {
        super.onResume();
        initData();
        resetPortrait();
        isForeground = true;
        main_userName.setText(getSharedPreferences("account", MODE_PRIVATE).getString("userName", "未设置昵称"));
    }

    @Override
    protected void onPause() {
        isForeground = true;
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();

        initEvent();
    }

    void resetPortrait() {
        byte[] bytes = FileUtil.readFile(getApplicationContext(), "portrait");
        Bitmap bm;
        if ((bytes.length > 0) && (MyApplication.loginCount > 0)) {
            bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.head);
        }
        portrait.setImageBitmap(bm);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                MyApplication.httpUtil.doGetAsync(
                        MyUrlConstructor.LOGOUT + "?userId=" + MyApplication.userId,
                        new OkHttpUtil.Func_jsonString() {
                            @Override
                            public void onSuccess(String result) {
                                if (JsonUtil.getStatus(result) == 0) {
                                    finish();
                                }
                            }

                            @Override
                            public void onError() {

                            }
                        }
                );

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




    void initData() {
        MyApplication.httpUtil.doGetAsync(
                MyUrlConstructor.QUERY_ALL_TAX_INFORMATION + "?userId=" + MyApplication.userId,
                new OkHttpUtil.Func_jsonString() {
                    @Override
                    public void onSuccess(final String result) {
                        JSONObject resultJson = JsonUtil.getResultJson(result);
                        try {
                            if (resultJson.getInt("status") == 0) {
                                JSONArray taxArray = resultJson.getJSONArray("result");
                                MyApplication.dbOperator.deleteTaxInfo();
                                JsonUtil.jsonArrayToTaxList(taxArray);
                                informationList = MyApplication.dbOperator.loadTaxInformation();
                                lv_adapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError() {
                    }
                }
        );

    }

    void initRollPager(final View page_01) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rollPagerView = (RollPagerView) page_01.findViewById(R.id.roll_view_pager);
                        rollPagerView.setPlayDelay(2250);
                        rollPagerView.setAnimationDurtion(500);
                        rollPagerView.setAdapter(new RollAdapter());
                        rollPagerView.setHintView(new ColorPointHintView(getApplicationContext(), Color.YELLOW, Color.WHITE));
                    }
                });
            }
        }).start();

    }

    void initView() {

        ll_invoice = (LinearLayout) findViewById(R.id.ll_home);
        ll_info = (LinearLayout) findViewById(R.id.ll_address);
        ll_mine = (LinearLayout) findViewById(R.id.ll_friend);

        tv_invoice = (TextView) findViewById(R.id.tv_home);
        tv_info = (TextView) findViewById(R.id.tv_address);
        tv_mine = (TextView) findViewById(R.id.tv_friend);
        tv_title = (TextView) findViewById(R.id.app_title);

        iv_invoice = (ImageView) findViewById(R.id.iv_home);
        iv_info = (ImageView) findViewById(R.id.iv_address);
        iv_mine = (ImageView) findViewById(R.id.iv_friend);

        View page_01 = View.inflate(getApplicationContext(), R.layout.page_01, null);
        View page_02 = View.inflate(getApplicationContext(), R.layout.page_02, null);
        View page_03 = View.inflate(getApplicationContext(), R.layout.page_03, null);

        btn_normal = (Button) page_01.findViewById(R.id.btn_normal_invoice);
        btn_normal.setOnClickListener(this);
        btn_normal.setOnTouchListener(onTouchListener);
        btn_special = (Button) page_01.findViewById(R.id.btn_special_invoice);
        btn_special.setOnClickListener(this);
        btn_special.setOnTouchListener(onTouchListener);

        main_userName = (TextView) page_03.findViewById(R.id.main_username);
        initUserInfo();

        String s = "账号：" + getSharedPreferences("account", MODE_PRIVATE).getString("mobile", "");
        ((TextView) page_03.findViewById(R.id.main_mobile)).setText(s);

        initRollPager(page_01);

        portrait = (CircleImageView) page_03.findViewById(R.id.portrait_main);
        resetPortrait();

        btn_create = (ImageButton) page_02.findViewById(R.id.btn_create_info);
        btn_create.setOnClickListener(this);

        lv_main = (ListView) page_02.findViewById(R.id.list);
        lv_adapter = new MyListViewAdapter();
        lv_main.setAdapter(lv_adapter);

        lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ViewTaxInfoActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("info", informationList.get(position));
                intent.putExtras(data);
                startActivity(intent);
            }
        });

        lv_main.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        btn_create.setVisibility(View.VISIBLE);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        btn_create.setVisibility(View.GONE);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        btn_create.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

        ll_userInfo = (LinearLayout) page_03.findViewById(R.id.main_ll_userinfo);
        ll_userInfo.setOnClickListener(this);
        ll_userInfo.setOnTouchListener(onTouchListener);
        ll_scan = (LinearLayout) page_03.findViewById(R.id.main_ll_scan);
        ll_scan.setOnClickListener(this);
        ll_scan.setOnTouchListener(onTouchListener);
        ll_history = (LinearLayout) page_03.findViewById(R.id.main_ll_history);
        ll_history.setOnClickListener(this);
        ll_history.setOnTouchListener(onTouchListener);
        ll_data = (LinearLayout) page_03.findViewById(R.id.main_ll_data);
        ll_data.setOnClickListener(this);
        ll_data.setOnTouchListener(onTouchListener);
        ll_changePassword = (LinearLayout) page_03.findViewById(R.id.main_ll_changepass);
        ll_changePassword.setOnClickListener(this);
        ll_changePassword.setOnTouchListener(onTouchListener);
        ll_exit = (LinearLayout) page_03.findViewById(R.id.main_ll_exit);
        ll_exit.setOnClickListener(this);
        ll_exit.setOnTouchListener(onTouchListener);


        views = new ArrayList<>();
        views.add(page_01);
        views.add(page_02);
        views.add(page_03);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new ContentAdapter(views);
        viewPager.setAdapter(adapter);

    }


    void initUserInfo(){
        MyApplication.httpUtil.doGetAsync(
                MyUrlConstructor.VIEW_USER_INFO + "?userId=" + MyApplication.userId,
                new OkHttpUtil.Func_jsonString() {
                    @Override
                    public void onSuccess(String result) {
                        if (JsonUtil.getStatus(result)==0){
                            try {
                                JSONObject userInfoJson=JsonUtil.getResultJson(result).getJSONObject("result");
                                MyUrlConstructor.portraitUrl=userInfoJson.optString("portrait");
                                getSharedPreferences("account",MODE_PRIVATE).edit().putString("userName",userInfoJson.getString("userName")).apply();
                                main_userName.setText(getSharedPreferences("account", MODE_PRIVATE).getString("userName", "未设置昵称"));
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onError() {

                    }
                }
        );
    }

    void initEvent() {
        ll_invoice.setOnClickListener(this);
        ll_info.setOnClickListener(this);
        ll_mine.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetButton();
                switch (position) {
                    case 0:
                        iv_invoice.setBackgroundDrawable(getResources().getDrawable(R.drawable.invoice_blue));
                        tv_invoice.setTextColor(getResources().getColor(R.color.pxiaomiBlue));
                        tv_title.setText("票小秘");
                        break;
                    case 1:
                        iv_info.setBackgroundDrawable(getResources().getDrawable(R.drawable.info_blue));
                        tv_info.setTextColor(getResources().getColor(R.color.pxiaomiBlue));
                        tv_title.setText("税务信息");
                        break;
                    case 2:
                        iv_mine.setBackgroundDrawable(getResources().getDrawable(R.drawable.mine_blue));
                        tv_mine.setTextColor(getResources().getColor(R.color.pxiaomiBlue));
                        tv_title.setText("我的");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    void resetButton() {
        iv_invoice.setBackgroundDrawable(getResources().getDrawable(R.drawable.invoice_grey));
        iv_info.setBackgroundDrawable(getResources().getDrawable(R.drawable.info_grey));
        iv_mine.setBackgroundDrawable(getResources().getDrawable(R.drawable.mine_grey));

        tv_info.setTextColor(Color.BLACK);
        tv_invoice.setTextColor(Color.BLACK);
        tv_mine.setTextColor(Color.BLACK);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_home:
                resetButton();
                iv_invoice.setBackgroundDrawable(getResources().getDrawable(R.drawable.invoice_blue));
                tv_invoice.setTextColor(getResources().getColor(R.color.pxiaomiBlue));
                viewPager.setCurrentItem(0);
                tv_title.setText("票小秘");
                break;
            case R.id.ll_address:
                resetButton();
                iv_info.setBackgroundDrawable(getResources().getDrawable(R.drawable.info_blue));
                tv_info.setTextColor(getResources().getColor(R.color.pxiaomiBlue));
                viewPager.setCurrentItem(1);
                tv_title.setText("税务信息");
                break;
            case R.id.ll_friend:
                resetButton();
                iv_mine.setBackgroundDrawable(getResources().getDrawable(R.drawable.mine_blue));
                tv_mine.setTextColor(getResources().getColor(R.color.pxiaomiBlue));
                viewPager.setCurrentItem(2);
                tv_title.setText("我的");
                break;
            case R.id.btn_special_invoice:
                intent.setClass(MainActivity.this, ChooseInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_normal_invoice:
                intent.setClass(MainActivity.this, ChooseInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_create_info:
                intent.setClass(MainActivity.this, CreateInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.main_ll_userinfo:
                intent.setClass(MainActivity.this, UserInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.main_ll_scan:
                intent.setClass(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.main_ll_history:
                intent.setClass(MainActivity.this, InvoiceHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.main_ll_data:
                intent.setClass(MainActivity.this, StatisticActivity.class);
                startActivity(intent);
                break;
            case R.id.main_ll_changepass:
                intent.setClass(MainActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.main_ll_exit:
                MyApplication.httpUtil.doGetAsync(
                        MyUrlConstructor.LOGOUT + "?userId=" + MyApplication.userId,
                        new OkHttpUtil.Func_jsonString() {

                            @Override
                            public void onSuccess(String result) {
                                if (JsonUtil.getStatus(result) == 0) {
                                    finish();
                                }
                            }

                            @Override
                            public void onError() {

                            }
                        }
                );

                break;

        }

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
            viewHolder.title.setText(informationList.get(position).getInvTitle());
            view.setOnTouchListener(onTouchListener);
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


    class RollAdapter extends StaticPagerAdapter {

        private int[] imgs = {
                R.drawable.img_1,
                R.drawable.img_2,
                R.drawable.img_3,
                R.drawable.img_4
        };

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }
    }



}
