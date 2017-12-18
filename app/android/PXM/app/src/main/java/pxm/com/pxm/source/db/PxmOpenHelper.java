package pxm.com.pxm.source.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dmtec on 2016/7/8.
 *
 */
public class PxmOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_TAXINFO="CREATE TABLE TaxInfo("
                                                +"rowId integer primary key autoincrement, "
                                                +"id Text UNIQUE, "
                                                +"invTitle Text, "
                                                +"taxNum Text, "
                                                +"bank Text, "
                                                +"bankAccount Text, "
                                                +"address Text, "
                                                +"tel Text)";


    public static final String CREATE_INVOICE="CREATE TABLE Invoice("
            +"rowId integer primary key autoincrement, "
            +"id Text UNIQUE, "
            +"type integer, "
            +"businessName Text, "
            +"amount Text, "
            +"result int, "
            +"content int, "
            +"rate float, "
            +"invTitle Text, "
            +"taxNum Text, "
            +"bank Text, "
            +"bankAccount Text, "
            +"address Text, "
            +"tel Text)";

    public PxmOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_INVOICE);
        sqLiteDatabase.execSQL(CREATE_TAXINFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
