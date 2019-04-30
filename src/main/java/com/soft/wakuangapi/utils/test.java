package com.soft.wakuangapi.utils;

import java.io.File;
import java.io.FileInputStream;
import com.qiniu.util.*;
import okhttp3.*;
public class test {
    public  String ACCESS_KEY = "ygvZumfzAKNxFina9rGzf3gXxED1r1_4MTWdDm06";
    public  String SECRET_KEY = "c1XrOsvbUmDv_Sa5kkZZuskY9iWggYyrq-0JPEBv";
    //要上传的空间名--
    /**指定保存到七牛的文件名--同名上传会报错  {"error":"file exists"}*/
    //密钥配置
    public Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    String bucketname = "avatar";    //空间名
    String key = "111.jpg";    //上传的图片名
    public String getUpToken() {
        return auth.uploadToken(bucketname, null, 3600, new StringMap().put("insertOnly", 1));
    }
    public void put64image() throws Exception {
//        String file = "D:\\temp\\1.jpg";//图片路径
//        FileInputStream fis = null;
//        int l = (int) (new File(file).length());
//        System.out.println("本地获取："+l);
//        byte[] src = new byte[l];
//        fis = new FileInputStream(new File(file));
//        fis.read(src);
//        String file64 = Base64.encodeToString(src, 0);
        String base64="/9j/4AAQSkZJRgABAQAAAQABAAD/4QAwRXhpZgAATU0AKgAAAAgAAQExAAIAAAAOAAAAGgAAAAB3\n" +
                "d3cubWVpdHUuY29tAP/bAEMAAwICAwICAwMDAwQDAwQFCAUFBAQFCgcHBggMCgwMCwoLCw0OEhAN\n" +
                "DhEOCwsQFhARExQVFRUMDxcYFhQYEhQVFP/bAEMBAwQEBQQFCQUFCRQNCw0UFBQUFBQUFBQUFBQU\n" +
                "FBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFP/AABEIAEYARgMBEQACEQEDEQH/\n" +
                "xAAfAAABBQEBAQEBAQAAAAAAAAAAAQIDBAUGBwgJCgv/xAC1EAACAQMDAgQDBQUEBAAAAX0BAgMA\n" +
                "BBEFEiExQQYTUWEHInEUMoGRoQgjQrHBFVLR8CQzYnKCCQoWFxgZGiUmJygpKjQ1Njc4OTpDREVG\n" +
                "R0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0\n" +
                "tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4eLj5OXm5+jp6vHy8/T19vf4+fr/xAAfAQADAQEBAQEB\n" +
                "AQEBAAAAAAAAAQIDBAUGBwgJCgv/xAC1EQACAQIEBAMEBwUEBAABAncAAQIDEQQFITEGEkFRB2Fx\n" +
                "EyIygQgUQpGhscEJIzNS8BVictEKFiQ04SXxFxgZGiYnKCkqNTY3ODk6Q0RFRkdISUpTVFVWV1hZ\n" +
                "WmNkZWZnaGlqc3R1dnd4eXqCg4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TF\n" +
                "xsfIycrS09TV1tfY2dri4+Tl5ufo6ery8/T19vf4+fr/2gAMAwEAAhEDEQA/AP0Ne8uFvHUScALj\n" +
                "Kg9h7VgBehu58csv/fC/4UCuc18Vvixpvwd+H+r+LtdlUWOnxZEKqoe4lPEcS8feZuPYZPahauwm\n" +
                "7H42fEX43/EL4p+K9T8SX/iC/ga7lLHybh4oYkz8sUSrgbVGAPXGTzWrmloJQe7OGn8X+L/MwviX\n" +
                "WAD3+2yZ/LdS5+pagPt/F3jZZvKTxJqkj7d2w3svzD86tVCXG59c/sIftja14V8aWvgPxbqUl74d\n" +
                "1e4EFtLenzJbC7cgL8zZJjdiFIJ4JBHerklJXRim4uzP02kurhSV3KDk5+ReP0rJHQR2V5cG/cF1\n" +
                "I2d0X1HtVAZ0jf6dJ/wH+QrBgaMLcDFBJ8If8FP/AIkvpX/CI+FHSGayeKTVGibktKGMalh/dC7s\n" +
                "e5qGm3ZM0jpqfAkOs6xrLQxq7eW7BYolQZyegXjiolyQTN0pTaSNSPT7q61prI2qmSOSOGQwZbLk\n" +
                "gYz7k8ms1VSjfua+xd3Hse2+Iv2fRonwpi8TWUpkuoh5wDDJxjDY9R/hXBTxUnV12O+phIxp6bni\n" +
                "eoaNOlhFrMKfZdTtHVlkXjzMcq3HcEEZ9q9qnNxduh4k4KSP2x8DeJJvGvgHw1r7MkJ1TTLa8ZSS\n" +
                "SGeJWP6k1rsQa9gN184klIwnVVHPSgZWlP8Ap8n4fyFY9Q0NCBiAKBI/PL/go14Q/wCE1+OXgayt\n" +
                "mLXM9hHZycEqha4O0H3w2fyrnrVfZps66NJ1Xboee/Fv4GaV4E8T+Gb7wxeadfWtkI47/SftqxSl\n" +
                "l4bBPQsCfoa82NTmg41Hqz1/Z8lSNSC2JfHHwx8RJc6ve+HvDU8ei+KngvoGiZGk06ePorlGI25G\n" +
                "eMgg9iKzjKLScnt+KNXCV2orfX0NnRPihfHQZvA/ibTobW7s4mHnpcqYyMHcMdfcDqKylT+3Td0b\n" +
                "qr9mpufNfjixl0fxBiNt9vdRsI8H5Tg4x/n1r2qD543fQ+fxEOWWmx+wXwZnRfhT4MgjkDiHSLWE\n" +
                "kHPKxKDz9RXcnc4jttOfOpTf7h/9lpoBko/4mEn4fyFY9QJndhExGchScA9aAPBNb+G8Xj+7k8W6\n" +
                "gzJdQXCz6ZCoBZo4pNzSSZ/ilZFwB9xI4x1LVwYmPMrdT08GrNsXxPoHjCa6DaBY6JrGkXsSkpqE\n" +
                "OySInrkjh1I56A+5rgdJ2vHqerCcV8TtY0bT4dG18Ly2axx2lxI+5kth+6Ukc7R6VCoS5GnuX7dO\n" +
                "afQ8ivfh5DLrP9ln4YGa7lmxJqJERiKf89S55B74x+dZ8k07L9Tfng/edrfI8fi+BT+N/wBoVPBu\n" +
                "kwGwsdBj+1arexAFYI5BkRhSMF3O0KDxgMccV6mFjJRcpniYyUJyUaXU+8/B+kWnhnS9L0HTCyWm\n" +
                "nQCHYecKvTLdya7aU/aSOStTjSjY6rTGzqU3+6f5iuo4x8oP2+T8P5CsQLOPl4GKBHLmCPw5OEhc\n" +
                "lFlDxxv0jDMTj3G7nHvXFWfJJM9PDJTg0zy34zfH3SNFka20zzbT7E4i1O7EOEsgGw3yYJ79TwBz\n" +
                "zXHicQm1CnuezgsDNR56u3TzLmn/ABy8KXGhnTxqEza1I6wQRZDO5P8Ay03HC4784+lZxxFP2dr6\n" +
                "m08txKmp8vu73NHwT8V7HWhcJdywTR2Dbbi6s5A9uwHVlbOOfTPU4rahW5nafQ5cThXCKcPtfeak\n" +
                "D6dpmqajrX9iw2PiTXmimvrGxfJLrGI4yzHHIRVBJA5zxXVWrKXuR3PPw+H5b1Krsl1Os0mxNpCo\n" +
                "K7Xb5mGc8+ma7aNP2cOU82rPnlcv6XzqM3+6f5itzEtTL/psh/3f5CsBlhsKlNJ9BbnhXxK+MNnp\n" +
                "fxs8JeAoyn23WLS7ml3tgx+WgaLHu2H+oFefjE7XXQ9HB2UnfqReMPhz/aGuHxRbR+fDf2otNVss\n" +
                "ZZtowsqDuwHBXuAMc9fOnHmSqx+Z9Fh8S4x9l2en+TOf8IfBzU725Wxi16Ofw3MnkhEjcssf9wcf\n" +
                "J6HkAe1TRoObsrWZ6mJzWhCHN7G0156fceoy/Crw74Muba4hn81bCDZa6fEiJaxSDGHwByygYGeB\n" +
                "169O6VGnRvJO7PmPrlbEaSVkZvw7uLbWPFWpRSXSy38SiQWxYMVDHAY9+xAzx1qsBScpSmznzCpa\n" +
                "MYRPT0i2kBhjPSvZSseP6CaZHm8kIPVT/wChUAWJhm+ceu3+QrDqM84/aM+NFh8C/hzda9cRm5v2\n" +
                "VorCyH/LefHyhj2UdWPp05IrSUlFWKjFyPxs8TePvEvirxFdeJr/AFO5fxKb37ct8jlZIpAQV2H+\n" +
                "ELjAA4AAFcskpuz6msZNardH33+xr+1m/wAWIX8NeI5ki8WWke/Z91L2McGRB2I/iUdOo4PHlSpy\n" +
                "ws7bwe3+R68KyrQ5lpJb/wCZ0fxa8Dk/tTfB/VbC+utLttUuLk3sVrcPGly9vH5q70BAJI4JI5AG\n" +
                "elapuN0jV2klJnc/tKfGC2+EHw61DXLqJpmiAht7YcGaduETPYE8k9gCalp1pqmjDmUE6kuh+fX7\n" +
                "Of7S3iTwH+0FaeLdWuZNRh8QTLa63CBw0DsAvlj+Ex8FR6AjvXt04xpJRijxqkpVZXe5+yMSi4tk\n" +
                "cEOrjO4dDmuncxWhRsYUj1KRS7AbDjv3FZFklww+3MD0+UH8hWK3A+Vf+Ch3hS51X4b6H4ghy8Ol\n" +
                "XTW9zHnjbLtw/wCDIB+NTWT0Z0UmtUz8yJrcaVcTzv5bqrsCjHJYDriskuYr4Xc9f/Ya0seKP2n/\n" +
                "AA3crF5cWl29zdTGIYyvllAD/wACkX8q5sU1GmoPqzow3vTbXRH6QeIdcVvjF4N0iO1jke4tb67e\n" +
                "ZwC0UcKIPl9NzSqPoDXKpXb0OtppHiP/AAURsrTUfhXorXDtDBDq8QcgZ+Zo5AP61tQkvaadjCsv\n" +
                "cPDv2Lv2Z4/il4+fVLqMzeG9MljlmuNpCsQcpECf4j1OOgP0r04e+/I8+TUEfqfI6QRiCJAFTjjt\n" +
                "XZ0OVLqY6yH+0COp8snPryKyZoaE2kTzXDOHjw6qwyT0Kj2rDqBm/EL4SR/EvwNq/he9kiWG/gMY\n" +
                "kOTsPVW6dQcVpKPNGxUW4u6PxO+OHw0vvhp4y1vQr27gu57C6aFpYN21znqMgGsIu5tNfaXU+oP+\n" +
                "CWnw+W/ufHXiktGZYjBpkasTlQQZGPTv8o/CvNxsrzjHtqd2FVoN92fZx8MyS/GnTbotGZLfQbqN\n" +
                "evAeeDPb/ZrhpztOx2Sj7l/M4v8Aai+CGpfGXwdpPhezvrOxkutds989xuKqvzgkADkjOQOhxjIr\n" +
                "vwkeabfY48RK0bM96+F3wd074W+DNO8LeHkS30+yjGZHOZJnP3pHOOWY8k9uAOBXvKKirI8e/M7s\n" +
                "6ZvD88hZN8SoOTgnP8qaQ27I5/8AsqeTWptrRhUTYASexHtWbGf/2Q==";
        String url = "http://upload.qiniup.com/putb64/" + -1+"/key/"+ UrlSafeBase64.encodeToString(key);
        //非华东空间需要根据注意事项 1 修改上传域名
        RequestBody rb = RequestBody.create(null, base64);
        Request request = new Request.Builder().
                url(url).
                addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                .post(rb).build();
        System.out.println(request.headers());
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println(response);
    }
    public static void main(String[] args) throws Exception {
        new test().put64image();
    }
}
