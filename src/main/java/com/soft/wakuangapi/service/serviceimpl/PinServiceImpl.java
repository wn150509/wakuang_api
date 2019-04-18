package com.soft.wakuangapi.service.serviceimpl;

import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.soft.wakuangapi.dao.PinRepository;
import com.soft.wakuangapi.dao.PinconcernRepository;
import com.soft.wakuangapi.dao.TopicRepository;
import com.soft.wakuangapi.entity.PinStatus;
import com.soft.wakuangapi.entity.PinUser;
import com.soft.wakuangapi.entity.Pins;
import com.soft.wakuangapi.entity.UserTopicPin;
import com.soft.wakuangapi.service.PinService;
import com.soft.wakuangapi.utils.QiNiuFileUpUtil;
import com.soft.wakuangapi.utils.ResponseUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PinServiceImpl implements PinService{
    /**基本配置-从七牛管理后台拿到*/
    //设置好账号的ACCESS_KEY和SECRET_KEY
    public  String ACCESS_KEY = "ygvZumfzAKNxFina9rGzf3gXxED1r1_4MTWdDm06";
    public  String SECRET_KEY = "c1XrOsvbUmDv_Sa5kkZZuskY9iWggYyrq-0JPEBv";
    //要上传的空间名--
    public  String bucketname = "avatar";

    /**指定保存到七牛的文件名--同名上传会报错  {"error":"file exists"}*/
    /** {"hash":"FrQF5eX_kNsNKwgGNeJ4TbBA0Xzr","key":"aa1.jpg"} 正常返回 key为七牛空间地址 http:/xxxx.com/aa1.jpg */
    public static  String imgFilePath = "D:\\temp\\"+"222.jpg";
    //    public String key;
    //密钥配置
    public Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    @Resource
    private PinRepository pinRepository;
    @Resource
    private PinconcernRepository pinconcernRepository;

    @Override
    public ResponseUtil releasePin(Pins pin1) {
        Pins pins=new Pins();
        if (pin1.getPinUrl()==null){
            pins.setPinUrl(pin1.getPinUrl());
        }else {
            //给文章添加封面 //生成key
            UUID uuid =UUID.randomUUID();
            String str = uuid.toString();
            // 去掉"-"符号
            String key = str.substring(0, 8) +str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) +str.substring(24);
            //删除暂存图片
            File dir=new File("D:/temp/");
            File[] lst=dir.listFiles();
            for (File f:lst) {
                if (new Date().getTime() - f.lastModified() > 24 * 60 * 60 * 1000) {
                    f.delete();
                }
            }
            GenerateImage(pin1.getPinUrl());
            try {
                FileInputStream fis = null;
                int l = (int) (new File(imgFilePath).length());
                byte[] src = new byte[l];
                fis = new FileInputStream(new File(imgFilePath));
                fis.read(src);
                String file64 = Base64.encodeToString(src, 0);
                String url = "http://upload.qiniup.com/putb64/"+l+"/key/"+ UrlSafeBase64.encodeToString(key);
                //非华东空间需要根据注意事项 1 修改上传域名
                RequestBody rb = RequestBody.create(null, file64);
                Request request = new Request.Builder().
                        url(url).
                        addHeader("Content-Type", "application/octet-stream")
                        .addHeader("Authorization", "UpToken " + getUpToken())
                        .post(rb).build();
                OkHttpClient client = new OkHttpClient();
                okhttp3.Response response = client.newCall(request).execute();
                System.out.println(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String str1=new QiNiuFileUpUtil().publicFile(key,"http://123.pen46789.cn");

            pins.setPinUrl(str1);//将返回的链接存入数据库
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date date=null;
        try {
            date=df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        pins.setTopicId(pin1.getTopicId());
        pins.setCreateTime(date);
        pins.setPinContent(pin1.getPinContent());
        pins.setLikeCount(0);
        pins.setCommentCount(0);
        pins.setUsersId(pin1.getUsersId());
        return new ResponseUtil(0,"add new pin",pinRepository.save(pins));
    }

    @Override
    public ResponseUtil getPinsConcerned(UserTopicPin userTopicPin) {
        List<Pins>pinsList=pinRepository.findAll();//遍历所有pin
        List<PinStatus>pinStatusList=new ArrayList<>();
        List<PinUser>pinUserList=pinconcernRepository.findPinUsersByUserId(userTopicPin.getUserId());
        for (int i=0;i<pinsList.size();i++){
            int status=0;
            Pins pins=pinsList.get(i);
            List<PinUser>pinUsers=pinconcernRepository.findPinUsersByPinId(pins.getPinId());
            for (int j=0;j<pinUserList.size();j++){
                if (pins.getPinId()==pinUserList.get(j).getPinId()){
                    status=1;
                }
                PinStatus pinStatus=new PinStatus(pins.getPinId(),pins.getPinContent(),pins.getPinUrl(),pins.getCommentCount(),
                        pinUsers.size(),pins.getUsersId(),pins.getCreateTime(),pins.getTopicId(),status);
                pinStatusList.add(pinStatus);
            }
        }
        return new ResponseUtil(0,"get someone pinStatus",pinStatusList);
    }


    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken(){
        StringMap putPolicy = new StringMap();
        putPolicy.put("callbackUrl", "http://api.example.com/qiniu/upload/callback");
        putPolicy.put("callbackBody", "key=$(key)&hash=$(etag)&bucket=$(bucket)&fsize=$(fsize)");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucketname, null, expireSeconds, putPolicy);
//        System.out.println(upToken);
        return auth.uploadToken(bucketname);
    }
    //base64字符串转化成图片
    public boolean GenerateImage(String imgStr){//对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        String str = imgStr.substring(23);
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(str);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
