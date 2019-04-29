package com.soft.wakuangapi.utils;

import org.apache.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MessageCode {
//    public static void main(String[] args) {
//        String host = "http://yzxyzm.market.alicloudapi.com";
//        String path = "/yzx/verifySms";
//        String method = "POST";
//        String appcode = "cf50647bb1004b15b115de07c127c487";
//        Map<String, String> headers = new HashMap<String, String>();
//        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//        headers.put("Authorization", "APPCODE " + appcode);
//        Map<String, String> querys = new HashMap<String, String>();
//        querys.put("phone", "18052073669");
//        querys.put("templateId", "TP18040314");
//        String code="";
//        for (int i=0;i<6;i++){
//            Random r=new Random();
//            String j =String.valueOf(r.nextInt(10) );
//            code+=j;
//        }
//        System.out.println(code);
//        querys.put("variable", "code:"+code);
//        Map<String, String> bodys = new HashMap<String, String>();
//        try {
//            /**
//             * 重要提示如下:
//             * HttpUtils请从
//             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
//             * 下载
//             *
//             * 相应的依赖请参照
//             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
//             */
//            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
//
//            System.out.println(response.toString());
//            //获取response的body
//            //System.out.println(EntityUtils.toString(response.getEntity()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        int m=5;
        for (int i=0;i<1;i++){
            int status=0;
            for (int j=0;j<7;j++){
                if (m==j){
                    status=1;
                    System.out.println("匹配到了："+j);
                }
            }
            System.out.println(status);
        }
    }
}
