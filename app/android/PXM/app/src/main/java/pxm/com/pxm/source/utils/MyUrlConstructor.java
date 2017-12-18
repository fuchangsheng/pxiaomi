package pxm.com.pxm.source.utils;

/**
 * Created by dmtec on 2016-07-18.
 *
 */
public class MyUrlConstructor {
    public final static String BASIC="http://172.16.1.124:6188/";
    /**
     * the basic url of server
     */
    public final static String BASIC_URL="http://172.16.1.124:6188/v1";

    /**
     * the url to get sms code when registering a new account
     */
    public final static String GET_SMSCODE= MyUrlConstructor.BASIC_URL+"/user/smsCode/request";

    /**
     * the url to finish the first step of a register processing
     */
    public final static String VERIFY= MyUrlConstructor.BASIC_URL+"/user/smsCode/verify";

    /**
     * the url to finish the second step of a register processing
     */
    public final static String REGISTER= MyUrlConstructor.BASIC_URL+"/user/register";

    /**
     * the url to login
     */
    public final static String LOGIN= MyUrlConstructor.BASIC_URL+"/user/login";

    /**
     * the url to logout
     * params containing userId should be appended to it when used.
     * for example:
     *     http://192.168.100.91:8080/myweb/logout?userId=00001
     */
    public final static String LOGOUT= MyUrlConstructor.BASIC_URL+"/user/logout";

    /**
     * the url to get user information
     * params containing userId should be appended to it when used.
     * for example:
     *     http://192.168.100.91:8080/myweb/userinfo?userId=00001
     */
    public final static String VIEW_USER_INFO= MyUrlConstructor.BASIC_URL+"/user/userInfo";

    /**
     * the url to modify user's information
     */
    public final static String MODIFY_USERINFO= MyUrlConstructor.BASIC_URL+"/user/updateUser";

    /**
     * the url to modify password
     */
    public final static String MODIFY_PASSWORD= MyUrlConstructor.BASIC_URL+"/user/updatePas";

    /**
     * the url to add a tax information
     */
    public final static String ADD_TAX_INFORMATION= MyUrlConstructor.BASIC_URL+"/tax/addTax";

    /**
     * the url to query all tax information
     * params containing userId should be appended to it when used.
     * for example:
     *     http://192.168.100.91:8080/myweb/userinfo?userId=00001
     */
    public final static String QUERY_ALL_TAX_INFORMATION= MyUrlConstructor.BASIC_URL+"/tax/taxInfo";

    /**
     * the url to modify a tax info
     */
    public final static String MODIFY_TAX_INFORMATION= MyUrlConstructor.BASIC_URL+"/tax/updateTax";

    /**
     * the url to delete a tax information
     * params containing taxId should be appended to it when used.
     */
    public final static String DELETE_TAX_INFORMATION= MyUrlConstructor.BASIC_URL+"/tax/deleteTax";

    /**
     * the url to query all invoice
     * params containing userId should be appended to it when used.
     * for example:
     *     http://192.168.100.91:8080/bill/showbill?userId=00001
     */
    public final static String QUERY_ALL_INVOICE= MyUrlConstructor.BASIC_URL+"/bill/showBills";

    /**
     * the url to sent invoice request
     */
    public final static String GENERATE_INVOICE= MyUrlConstructor.BASIC_URL+"/bill/userBill";

    /**
     * the url to get invoice stat
     * params containing userId should be appended to it when used.
     */
    public final static String GET_INVOICE_STAT= MyUrlConstructor.BASIC_URL+"/bill/billCount";

    public final static String UPLOAD_USER_PORTRAIT =MyUrlConstructor.BASIC_URL+"/upload/user/portrait";

    public final static String PASSWORD_AUTH_CODE=MyUrlConstructor.BASIC_URL+"/password/verify";

    public final static String INPUT_NEW_PASSWORD=MyUrlConstructor.BASIC_URL+"/password/inputNewPass";

    public final static String QUERY_BUSINESS=MyUrlConstructor.BASIC_URL+"/user/businessInfo";

    public static String portraitUrl="";

}
