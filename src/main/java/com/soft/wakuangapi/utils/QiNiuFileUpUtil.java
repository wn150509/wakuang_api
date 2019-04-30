package com.soft.wakuangapi.utils;

//import com.sun.deploy.net.URLEncoder;

import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
/**
 * @author wang
 * @version v1.0.0
 * @email wangn292@gmail.com
 * @date 2018/1/6 0006 14:05
 */
public class QiNiuFileUpUtil {

    public static String getUUID() {
        UUID uuid =UUID.randomUUID();
        String str = uuid.toString();
        // 去掉"-"符号
        String temp = str.substring(0, 8) +str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) +str.substring(24);
        return temp;
    }

    public static void main(String args[]) throws IOException{
//        File dir=new File("D:/temp/");
//        File[] lst=dir.listFiles();
//        for (File f:lst) {
//            if (new Date().getTime() - f.lastModified() > 24 * 60 * 60 * 1000) {
//                f.delete();
//            }
//        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        try {
            System.out.println(dateToStamp());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp() throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    /**
     * 公有空间返回文件URL
     * @param fileName
     * @param domainOfBucket
     * @return
     */
    public String publicFile(String fileName,String domainOfBucket) {
        String encodedFileName=null;
        try {
            encodedFileName = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String finalUrl = String.format("%s/%s", domainOfBucket,encodedFileName );
        //外部链接
        System.out.println(finalUrl);
        return finalUrl;
    }
}
