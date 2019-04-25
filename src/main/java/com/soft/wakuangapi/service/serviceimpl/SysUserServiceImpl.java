package com.soft.wakuangapi.service.serviceimpl;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.soft.wakuangapi.dao.ArticlesRepository;
import com.soft.wakuangapi.dao.PinRepository;
import com.soft.wakuangapi.dao.SysUserRepository;
import com.soft.wakuangapi.dao.UserConcernRepository;
import com.soft.wakuangapi.entity.*;
import com.soft.wakuangapi.service.ArticleService;
import com.soft.wakuangapi.service.SysUserService;
import com.soft.wakuangapi.service.UserConcernService;
import com.soft.wakuangapi.utils.QiNiuFileUpUtil;
import com.soft.wakuangapi.utils.ResponseUtil;
import com.soft.wakuangapi.vo.ArticleVo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SysUserServiceImpl implements SysUserService {
    /**基本配置-从七牛管理后台拿到*/
    //设置好账号的ACCESS_KEY和SECRET_KEY
    public  String ACCESS_KEY = "ygvZumfzAKNxFina9rGzf3gXxED1r1_4MTWdDm06";
    public  String SECRET_KEY = "c1XrOsvbUmDv_Sa5kkZZuskY9iWggYyrq-0JPEBv";
    //要上传的空间名--
    public  String bucketname = "avatar";

    /**指定保存到七牛的文件名--同名上传会报错  {"error":"file exists"}*/
    /** {"hash":"FrQF5eX_kNsNKwgGNeJ4TbBA0Xzr","key":"aa1.jpg"} 正常返回 key为七牛空间地址 http:/xxxx.com/aa1.jpg */
    public static  String imgFilePath = "D:\\temp\\"+"222.jpg";
    //上传到七牛后保存的文件名    访问为：http://oswj11a86.bkt.clouddn.com/daimo6.png
//    public String key;
    //密钥配置
    public Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private UserConcernRepository userConcernRepository;
    @Resource
    private ArticlesRepository articlesRepository;
    @Resource
    private PinRepository pinRepository;

    @Override
    public ResponseUtil userLogin(LoginUser loginUser) {
        SysUser sysUser = sysUserRepository.findSysUserByEmail(loginUser.getEmail());
        //邮箱有效
        if (sysUser != null) {
            //判断密码是否正确
            if (loginUser.getPassword().equals(sysUser.getPassword())) {
                return new ResponseUtil(0, "login success", sysUser);
            } else {
                return new ResponseUtil(0, "password wrong");
            }
        } else {
            return new ResponseUtil(0, "account wrong");
        }
    }

    @Override
    public ResponseUtil register(SysUser sysUser) {
        SysUser sysUsers=new SysUser();
        sysUsers.setUserName(sysUser.getUserName());
        sysUsers.setUserAvatar("https://ws1.sinaimg.cn/large/006vOzdBgy1fxdmbl6aakj301s01sa9u.jpg");
        sysUsers.setEmail(sysUser.getEmail());
        sysUsers.setPassword(sysUser.getPassword());
        if (sysUserRepository.findSysUserByEmail(sysUser.getEmail())!=null){
            return new ResponseUtil(1,"you haved account",sysUser);
        }else {
            sysUserRepository.save(sysUsers);
            return new ResponseUtil(0,"register success");
        }
    }

    @Override
    public ResponseUtil findHotUsers() {
        return new ResponseUtil(0,"get Hotusers",sysUserRepository.findHotUsers());
    }

    @Override
    public SysUser findUser(Integer id) {
        return sysUserRepository.findSysUserByUserId(id);
    }

    @Override
    public SysUser getUser(String account, String base64) {
        SysUser user=sysUserRepository.findSysUserByEmail(account);
        //生成key
        UUID uuid =UUID.randomUUID();
        System.out.println(uuid);
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
        GenerateImage(base64);
        try {
            deleteFile(user.getUserAvatar().substring(23));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        user.setUserAvatar(str1);
        sysUserRepository.save(user);
        return sysUserRepository.findSysUserByEmail(account);
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
    //删除七牛云被替换图片
    public void deleteFile(String key) throws IOException {
        Configuration config = new Configuration(Zone.autoZone());
        BucketManager bucketMgr = new BucketManager(auth, config);
        //指定需要删除的文件，和文件所在的存储空间
        try {
            bucketMgr.delete(bucketname, key);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("删除成功");
    }
    @Override
    public ResponseUtil updateUser(SysUser sysUser) {
        SysUser user=sysUserRepository.findSysUserByUserId(sysUser.getUserId());//数据库对象
        if(sysUser.getUserName()!=""){
            user.setUserName(sysUser.getUserName());
        }
        if (sysUser.getUserPosition()!=""){
            user.setUserPosition(sysUser.getUserPosition());
        }
        if (sysUser.getUserCompany()!=""){
            user.setUserCompany(sysUser.getUserCompany());
        }
        if (sysUser.getDescription()!=""){
            user.setDescription(sysUser.getDescription());
        }
        return new ResponseUtil(0,"update user",sysUserRepository.save(user));
    }

    @Override
    public List<UserStatus> querySysUserList(SearchUser searchUser) {
        List<SysUser>querySysUserList=sysUserRepository.querySysUserList(searchUser.getKey());//根据关键字key获取一组用户list

        //以下根据当前用户id得到一组用户list的完整信息
        List<SysUser>userList=sysUserRepository.findAll();//获取表中所有用户信息
        List<UserUser>userUsers=userConcernRepository.findAllByUserId(searchUser.getUserId());//根据userId获取被关注用户ID组
        List<UserStatus>userStatuses0=new ArrayList<>();
        for (int i=0;i<userList.size();i++){//遍历所有用户
            SysUser sysUser=userList.get(i);
            int status=0;
            for (int j=0;j<userUsers.size();j++){
                if (userUsers.get(j).getConcerneduserId()==userList.get(i).getUserId()){
                    status=1;
                }
            }
            UserStatus userStatus=new UserStatus(sysUser.getUserId(),
                    sysUser.getUserAvatar(),sysUser.getUserName(),
                    sysUser.getDescription(),sysUser.getUserCompany(),sysUser.getUserPosition(),status);
            userStatuses0.add(userStatus);
        }
        List<UserStatus>userStatuses=new ArrayList<>();
        for (int i=0;i<userStatuses0.size();i++){
            for (int j=0;j<querySysUserList.size();j++){
                if (userStatuses0.get(i).getUserId()==querySysUserList.get(j).getUserId()){
                    userStatuses.add(userStatuses0.get(i));
                }
            }
        }
        return userStatuses;
    }

    @Override
    public ResponseUtil getMessageCount(Integer articleId) {
        Articles articles=articlesRepository.findArticlesByArticleId(articleId);
        SysUser sysUser=sysUserRepository.findSysUserByUserId(articles.getUsersId());
        List<Articles>articlesList=articlesRepository.findAllByUsersId(sysUser.getUserId());//专栏数
        List<Pins>pinsList=pinRepository.findAllByUsersId(sysUser.getUserId());//沸点数
        List<UserUser>userUsers=userConcernRepository.findAllByUserId(sysUser.getUserId());//关注数
        List<UserUser>userUserList=userConcernRepository.findAllByConcerneduserId(sysUser.getUserId());//被关注数
        MessageCount messageCount=new MessageCount(pinsList.size(),articlesList.size(),userUsers.size(),userUserList.size());
        return new ResponseUtil(0,"get message count",messageCount);
    }
}
