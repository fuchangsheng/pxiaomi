package pxm.com.pxm.source.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import pxm.com.pxm.source.models.Business;
import pxm.com.pxm.source.models.Invoice;
import pxm.com.pxm.source.models.TaxInformation;

/**
 * Created by dmtec on 2016-07-18.
 *  used to create json text and handle the json data received from server
 */
public class JsonUtil {
    /***********************************************************************************************************
    * The methods following are used to create json text ,the are called when a http post needs a json text (Entity)
     **********************************************************************************************************/


    /**
     * create a httpPost json text when the button "get smsCode" is being pressed.
     * @param mobile the user's mobile phone number which is used to register a new account;
     * @return  a httpPost json text used to sent register information
     */
    public static String CreateGetSmsCodeJsonText(String mobile){
       JSONStringer jsonText=new JSONStringer();
       try{
           jsonText.object();
                jsonText.key("mobile");
                jsonText.value(mobile);
           jsonText.endObject();

       }catch(JSONException e){
           throw  new RuntimeException(e);
       }
       return jsonText.toString();
    }


    /**
     * Called when a user need to confirm that the mobile phone number he survived belongs to him,the method
     * returns a String contains a json text that  the code and mobile phone number included;
     * @param mobile the user's mobile phone number which is used to register a new account;
     * @param code smsCode received
     * @return a String contains a json text that contains the code and mobile phone number;
     */
    public static String CreateVerifyJsonText(String mobile, String code){
        JSONStringer jsonText=new JSONStringer();
        try{
            jsonText.object();

                jsonText.key("mobile");
                jsonText.value(mobile);

                jsonText.key("smsCode");
                jsonText.value(code);

            jsonText.endObject();

        }catch(JSONException e){
            throw  new RuntimeException(e);
        }
        return jsonText.toString();
    }

    /**
     * Called when the button "enter application" is pressed
     * @param userName the userName a user defined
     * @param password the password
     * @return a json text contains relative information
     */
    public static String CreateRegisterJsonText(String mobile, String userName, String password){
        JSONStringer jsonText = new JSONStringer();
        try{
            jsonText.object();
                jsonText.key("mobile");
                jsonText.value(mobile);

                jsonText.key("userName");
                jsonText.value(userName);

                jsonText.key("password");
                jsonText.value(password);
            jsonText.endObject();
        }catch (JSONException e){
            throw new RuntimeException(e);
        }
        return jsonText.toString();
    }

    /**
     * Called when user needs to login
     * @param mobile phone number
     * @param password password
     * @return a json text contains relative information
     */
    public static String CreateLoginJsonText(String mobile,String password){
        JSONStringer jsonText=new JSONStringer();
        try {
            jsonText.object();

                jsonText.key("mobile");
                jsonText.value(mobile);

                jsonText.key("password");
                jsonText.value(password);

            jsonText.endObject();
        }catch (JSONException e){
            throw new RuntimeException(e);
        }
        return jsonText.toString();
    }

    /**
     * Called when a user needs to commit his user information
     * @param userName  username
     * @param age age
     * @param gender gender 0-male, 1-female, 2-unknown
     * @param email email address
     * @return  a json text contains relative information
     */
    public static String CreateModifyUserInfoJsonText(String userName,int age,int gender,String email){
        JSONStringer jsonText = new JSONStringer();
        try {
            jsonText.object();
                jsonText.key("userId");
                jsonText.value(MyApplication.userId);

                jsonText.key("userName");
                jsonText.value(userName);

                jsonText.key("age");
                jsonText.value(age);

                jsonText.key("gender");
                jsonText.value(gender);

                jsonText.key("email");
                jsonText.value(email);
            jsonText.endObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonText.toString();
    }

    /**
     * Called when a user needs to change his password
     * @param password old password
     * @param newPwd new password
     * @return  a json text contains relative information
     */
    public static String CreateChangePasswordJsonText(String password,String newPwd){
        JSONStringer jsonText=new JSONStringer();
        try {
            jsonText.object();
                jsonText.key("userId");
                jsonText.value(MyApplication.userId);

                jsonText.key("password");
                jsonText.value(password);

                jsonText.key("newPassword");
                jsonText.value(newPwd);


            jsonText.endObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonText.toString();
    }

    /**
     * Called when a user needs to change his password
     * @param mobile old password
     * @param newPwd new password
     * @return  a json text contains relative information
     */
    public static String CreateInputPasswordJsonText(String mobile,String newPwd){
        JSONStringer jsonText=new JSONStringer();
        try {
            jsonText.object();

            jsonText.key("mobile");
            jsonText.value(mobile);

            jsonText.key("password");
            jsonText.value(newPwd);

            jsonText.endObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonText.toString();
    }

    /**
     * Called when a user needs to add a tax information
     * @param info an object of TaxInformation
     * @return a json text contains relative information
     */
    public static String CreateAddTaxInfoJsonText(TaxInformation info){
        JSONStringer jsonText=new JSONStringer();
        try {
            jsonText.object();
                jsonText.key("userId");
                jsonText.value(MyApplication.userId);

                jsonText.key("title");
                jsonText.value(info.getInvTitle());

                jsonText.key("taxNo");
                jsonText.value(info.getTaxNum());

                jsonText.key("bankDeposit");
                jsonText.value(info.getBank());

                jsonText.key("accountNo");
                jsonText.value(info.getBankAccount());

                jsonText.key("address");
                jsonText.value(info.getAddress());

                jsonText.key("mobile");
                jsonText.value(info.getTel());

            jsonText.endObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonText.toString();
    }


    /**
     * Called when a user needs to modify a tax information
     * @param info an object of TaxInformation
     * @return a json text contains relative information
     */
    public static String CreateModifyTaxInfoJsonText(TaxInformation info){
        JSONStringer jsonText=new JSONStringer();
        try {
            jsonText.object();

                jsonText.key("taxId");
                jsonText.value(info.getId());

                jsonText.key("title");
                jsonText.value(info.getInvTitle());

                jsonText.key("taxNo");
                jsonText.value(info.getTaxNum());

                jsonText.key("bankDeposit");
                jsonText.value(info.getBank());

                jsonText.key("accountNo");
                jsonText.value(info.getBankAccount());

                jsonText.key("address");
                jsonText.value(info.getAddress());

                jsonText.key("mobile");
                jsonText.value(info.getTel());

            jsonText.endObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonText.toString();
    }

    public static String CreateDelTaxInfoJsonText(String taxId ){
        JSONStringer jsonText=new JSONStringer();
        try {
            jsonText.object();
                jsonText.key("taxId");
                jsonText.value(taxId);
            jsonText.endObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonText.toString();
    }


    /**
     * Called when user needs to commit a invoice
     * @param invoice an object of Invoice
     * @return a json text contains relative information
     */
    public static String CreateGenerateInvoiceJsonText(Invoice invoice, Business business){
        JSONStringer jsonText = new JSONStringer();
        try {
            jsonText.object();

                jsonText.key("userId");
                jsonText.value(MyApplication.userId);

                jsonText.key("taxId");
                jsonText.value(invoice.getTaxInfo().getId());

                jsonText.key("businessId");
                jsonText.value(business.getId());

                jsonText.key("type");
                jsonText.value(invoice.getType());

                jsonText.key("title");
                jsonText.value(invoice.getTaxInfo().getInvTitle());

                jsonText.key("taxNo");
                jsonText.value(invoice.getTaxInfo().getTaxNum());

                jsonText.key("bankDeposit");
                jsonText.value(invoice.getTaxInfo().getBank());

                jsonText.key("accountNo");
                jsonText.value(invoice.getTaxInfo().getBankAccount());

                jsonText.key("address");
                jsonText.value(invoice.getTaxInfo().getAddress());

                jsonText.key("mobile");
                jsonText.value(invoice.getTaxInfo().getTel());

                jsonText.key("businessName");
                jsonText.value(invoice.getBusinessName());

                jsonText.key("amount");
                jsonText.value(invoice.getAmount());

                jsonText.key("content");
                jsonText.value(invoice.getContent());

                jsonText.key("rate");
                jsonText.value(invoice.getRate());

                jsonText.key("RegistrationID");
                jsonText.value(MyApplication.RegistrationID);

            jsonText.endObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonText.toString();
    }



/*************************************************************************************************************************
 *  the methods following are used to handle the json data received from server
 *************************************************************************************************************************/

    /**
     * Called when the application needs to know whether  the operation has succeed
     * @param jsonString the json text received from server
     * @return the status 0 means successful while 1 means failed
     */
    public static int getStatus(String jsonString){
        try {
            JSONTokener jsonParser = new JSONTokener(jsonString);
            JSONObject status=(JSONObject) jsonParser.nextValue();
            return status.getInt("status");
         }catch (JSONException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Called while app needs to parse the json text to JsonObject
     * @param jsonString the json text received from server
     * @return a JsonObject
     */
    public static JSONObject getResultJson(String jsonString){
        try {
            return (JSONObject) new JSONTokener(jsonString).nextValue();
        }catch (JSONException e){
            throw new RuntimeException(e);
        }
    }


    public static List<TaxInformation> jsonArrayToTaxList(JSONArray taxArray){
        List<TaxInformation> list=new ArrayList<>();
        if(taxArray!=null&&taxArray.length()>0){
            for(int i=0;i<taxArray.length();i++){
                try {
                    JSONObject obj=taxArray.getJSONObject(i);
                    TaxInformation info=new TaxInformation();
                    info.setId(obj.getString("id"));
                    info.setInvTitle(obj.getString("title"));
                    info.setTaxNum(obj.getString("taxNo"));
                    info.setBank(obj.getString("bankDeposit"));
                    info.setBankAccount(obj.getString("accountNo"));
                    info.setAddress(obj.getString("address"));
                    info.setTel(obj.getString("mobile"));
                    list.add(info);
                    MyApplication.dbOperator.saveTaxInfo(info);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return list;
    }


    public static List<Invoice> jsonArrayToInvoiceList(JSONArray invoiceArray){
        List<Invoice> list=new ArrayList<>();
        if(invoiceArray!=null&&invoiceArray.length()>0){
            for(int i=0;i<invoiceArray.length();i++){
                try {
                    JSONObject obj=invoiceArray.getJSONObject(i);
                    Invoice invoice=new Invoice();
                    TaxInformation information=new TaxInformation();
                    invoice.setId(obj.getString("billId"));
                    information.setInvTitle(obj.getString("title"));
                    information.setTaxNum(obj.getString("taxNo"));
                    information.setBank(obj.getString("bankDeposit"));
                    information.setBankAccount(obj.getString("accountNo"));
                    information.setAddress(obj.getString("address"));
                    information.setTel(obj.getString("mobile"));
                    invoice.setTaxInfo(information);
                    invoice.setBusinessName(obj.getString("businessName"));
                    invoice.setAmount(obj.getString("amount"));
                    invoice.setContent(obj.getInt("content"));
                    invoice.setRate((float)(obj.getDouble("rate")));
                    invoice.setType(obj.getInt("type"));
                    invoice.setResult(obj.getInt("state"));
                    list.add(invoice);
                    MyApplication.dbOperator.saveInvoice(invoice);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return list;
    }

    public static List<Business> jsonArrayToBusinessList(JSONArray businessArray){
        List<Business> list=new ArrayList<>();
        if(businessArray!=null&&businessArray.length()>0){
            for(int i=0;i<businessArray.length();i++){
                try {
                    JSONObject obj=businessArray.getJSONObject(i);
                    Business business=new Business();
                    business.setId(obj.getString("id"));
                    business.setName(obj.getString("name"));
                    business.setTelephone(obj.getString("telephone"));
                    business.setAddress(obj.getString("address"));
                    list.add(business);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return list;
    }

}
