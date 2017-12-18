package pxm.com.pxm.source.utils;

import android.app.Application;
import android.util.Log;

import java.security.PublicKey;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.jpush.android.api.JPushInterface;
import pxm.com.pxm.source.db.PxmDB;

/**
 * Created by dmtec on 2016-07-28.
 *
 */
public class MyApplication extends Application {

    /**
     * * userId is used to transfer data to the server in the whole app
     */
    public static String userId;
    public static OkHttpUtil httpUtil;
    public static PxmDB dbOperator;
    public static int loginCount;
    public static String RegistrationID;
    @Override
    public void onCreate() {
        super.onCreate();

        initUserInfo();

        initHttpUtil();

        initDBOperator();

        initJPush();

        initGalleryFinal();
    }


    void initUserInfo(){
        userId = "";
    }

    void initHttpUtil(){
        httpUtil = OkHttpUtil.getInstance();
    }

    void initDBOperator(){
        dbOperator = PxmDB.getInstance(getApplicationContext());
    }

    void initJPush(){
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        RegistrationID=JPushInterface.getRegistrationID(getApplicationContext());
        Log.e("RegistrationID",RegistrationID);
    }



    void initGalleryFinal(){
        ThemeConfig theme = new ThemeConfig.Builder()
                .build();
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

        ImageLoader imageloader = new PicassoImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(getApplicationContext(), imageloader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }
}
