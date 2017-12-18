package pxm.com.pxm.source.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * Created by dmtec on 2016-07-25.
 * In order to encrypt the password or some important files,this class is used to convert text or files into
 * encrypted MD5 String .
 */
public class MD5Util {

    /**
     * Input type must match the byte[] form
     * @param source text or String that is going to be encrypted
     * @return MD5 String
     */
    public static String getMD5(byte[] source) {
        String s = null;
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
                'c', 'd', 'e', 'f'
        };
        try {
            MessageDigest md5 =  MessageDigest.getInstance("MD5");
            md5.update(source);

            byte tmp[] = md5.digest();

            char str[] = new char[16 * 2];
            int k = 0;

            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);
        } catch (Exception e) {
                e.printStackTrace();
        }
        return s;
    }

    /**
     * String to byte[]
     * @param str String
     * @param charEncode for example.--UTF-8
     * @return byte[]
     */
    public static byte[] StringToByte(String str,String charEncode) {
        byte[] destObj = null;
        try {
            if(null == str || str.trim().equals("")){
                destObj = new byte[0];
                return destObj;
            }else{
                destObj = str.getBytes(charEncode);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return destObj;
    }
}
