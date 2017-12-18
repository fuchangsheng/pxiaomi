package pxm.com.pxm.source.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pxm.com.pxm.source.models.Invoice;
import pxm.com.pxm.source.models.TaxInformation;

/**
 * Created by dmtec on 2016/7/8.
 *
 */
public class PxmDB {
    /**
     * The name of database.
     */
    public static final String DB_NAME= "pxm";

    /**
     * database version
     */
    public static final int VERSION=1;

    /**
     * pxmDB  which can be used to do  operation on db,it is an object of PxmDB
     */
    private volatile static PxmDB pxmDB;



    private SQLiteDatabase db;

    private  PxmDB(Context context){
        PxmOpenHelper dbHelper = new PxmOpenHelper(context,DB_NAME,null,VERSION);
        db=dbHelper.getWritableDatabase();
    }

    /**
     * get instance of PxmDB.
     * @param context context
     * @return pxmDB
     */
    public synchronized static PxmDB getInstance(Context context){
        if(pxmDB==null){
            pxmDB= new PxmDB(context);
        }
        return pxmDB;
    }

    /**
     * save a TaxInfomation object into the database
     * @param info an object of TaxInfomation
     */
    public void saveTaxInfo(TaxInformation info){
        if (info!=null){
            ContentValues values =new ContentValues();
            values.put("id",info.getId());
            values.put("invTitle",info.getInvTitle());
            values.put("taxNum",info.getTaxNum());
            values.put("bank",info.getBank());
            values.put("bankAccount",info.getBankAccount());
            values.put("address",info.getAddress());
            values.put("tel",info.getTel());
            db.insert("TaxInfo",null,values);
        }
    }

    /**
     * save a Invoice object into the database
     * @param invoice an object of TaxInfomation
     */
    public void saveInvoice(Invoice invoice){
        if (invoice!=null){
            ContentValues values=new ContentValues();
            values.put("id",invoice.getId());
            values.put("type",invoice.getType());
            values.put("businessName",invoice.getBusinessName());
            values.put("amount",invoice.getAmount());
            values.put("result",invoice.getResult());
            values.put("content",invoice.getContent());
            values.put("rate",invoice.getRate());
            values.put("invTitle",invoice.getTaxInfo().getInvTitle());
            values.put("taxNum",invoice.getTaxInfo().getTaxNum());
            values.put("bank",invoice.getTaxInfo().getBank());
            values.put("bankAccount",invoice.getTaxInfo().getBankAccount());
            values.put("address",invoice.getTaxInfo().getAddress());
            values.put("tel",invoice.getTaxInfo().getTel());
            db.insert("Invoice",null,values);
        }
    }

    /**
     * delete all Invoices in table "Invoice"
     * @return number of invoices deleted
     */
    public int deleteInvoice(){
        return db.delete("Invoice",null,null);
    }

    /**
     * delete the invoice  in table "Invoice" in which the id equals invoice_id.
     * @param invoice_id id of the invoice that is to be deleted.
     * @return number of invoice deleted
     */
    public int deleteInvoice(int invoice_id){
        return db.delete("Invoice","id=?",new String[]{(invoice_id+"")});
    }

    public int deleteTaxInfo(){
        return db.delete("TaxInfo",null,null);
    }

    public int deleteTaxInfo(int info_id){
        return db.delete("TaxInfo","id=?",new String[]{(info_id+"")});
    }

    public int getInvoiceCount(){
        return db.query("Invoice",null,null,null,null,null,null).getCount();
    }


    public int getInfoCount(){
        return db.query("TaxInfo",null,null,null,null,null,null).getCount();
    }


    public List<TaxInformation> loadTaxInformation(){
        List<TaxInformation> list =new ArrayList<TaxInformation>();
        Cursor cursor =db.query("TaxInfo",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                TaxInformation info =new TaxInformation();
                info.setId(cursor.getString(cursor.getColumnIndex("id")));
                info.setInvTitle(cursor.getString(cursor.getColumnIndex("invTitle")));
                info.setTaxNum(cursor.getString(cursor.getColumnIndex("taxNum")));
                info.setBank(cursor.getString(cursor.getColumnIndex("bank")));
                info.setBankAccount(cursor.getString(cursor.getColumnIndex("bankAccount")));
                info.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                info.setTel(cursor.getString(cursor.getColumnIndex("tel")));
                list.add(info);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


    public List<Invoice> loadInvoice(){
        List<Invoice> list= new ArrayList<Invoice>();
        Cursor cursor =db.query("Invoice",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                Invoice invoice=new Invoice();
                TaxInformation info =new TaxInformation();
                info.setTel(cursor.getString(cursor.getColumnIndex("tel")));
                info.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                info.setBank(cursor.getString(cursor.getColumnIndex("bank")));
                info.setBankAccount(cursor.getString(cursor.getColumnIndex("bankAccount")));
                info.setTaxNum(cursor.getString(cursor.getColumnIndex("taxNum")));
                info.setInvTitle(cursor.getString(cursor.getColumnIndex("invTitle")));
                invoice.setTaxInfo(info);
                invoice.setAmount((cursor.getString(cursor.getColumnIndex("amount"))));
                invoice.setBusinessName(cursor.getString(cursor.getColumnIndex("businessName")));
                invoice.setId(cursor.getString(cursor.getColumnIndex("id")));
                invoice.setResult(cursor.getInt(cursor.getColumnIndex("result")));
                invoice.setType(cursor.getInt(cursor.getColumnIndex("type")));
                invoice.setContent(cursor.getInt(cursor.getColumnIndex("content")));
                invoice.setRate(cursor.getFloat(cursor.getColumnIndex("rate")));
                list.add(invoice);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
