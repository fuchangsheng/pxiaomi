package pxm.com.pxm.source.utils;

import android.content.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dmtec on 2016-08-03.
 *
 */
public class FileUtil {

    public static boolean saveFile(Context context, String fileName, byte[] bytes){
        FileOutputStream outputStream=null;
        try{
            outputStream=context.openFileOutput(fileName,Context.MODE_PRIVATE);
            outputStream.write(bytes);
            outputStream.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] readFile(Context context,String FileName){
        FileInputStream inputStream=null;
        ByteArrayOutputStream byteArrayOutputStream=null;
        byte[] buff = new byte[1024];
        byteArrayOutputStream=new ByteArrayOutputStream();
        int length=0;
        try {
            inputStream=context.openFileInput(FileName);
            while ((length=inputStream.read(buff))!=-1){
                byteArrayOutputStream.write(buff,0,length);
            }
            byte[] result =byteArrayOutputStream.toByteArray();
            inputStream.close();
            byteArrayOutputStream.close();
            return result;
        }catch (Exception e){
            return new byte[0];
        }
    }

    public static byte[] readFile(File file){

        ByteArrayOutputStream byteArrayOutputStream=null;
        byte[] buff = new byte[1024];
        byteArrayOutputStream=new ByteArrayOutputStream();
        int length=0;
        try {
            FileInputStream inputStream=new FileInputStream(file);
            while ((length=inputStream.read(buff))!=-1){
                byteArrayOutputStream.write(buff,0,length);
            }
            byte[] result =byteArrayOutputStream.toByteArray();
            inputStream.close();
            byteArrayOutputStream.close();
            return result;
        }catch (Exception e){
            return new byte[0];
        }
    }



}
