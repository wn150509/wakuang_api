package com.soft.wakuangapi.service.serviceimpl;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.soft.wakuangapi.dao.*;
import com.soft.wakuangapi.entity.*;
import com.soft.wakuangapi.service.ArticleService;
import com.soft.wakuangapi.service.SysUserService;
import com.soft.wakuangapi.service.TopicUserService;
import com.soft.wakuangapi.service.UserConcernService;
import com.soft.wakuangapi.utils.HttpUtils;
import com.soft.wakuangapi.utils.QiNiuFileUpUtil;
import com.soft.wakuangapi.utils.ResponseUtil;
import com.soft.wakuangapi.vo.ArticleVo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

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
    @Resource
    private LikeRepository likeRepository;
    @Resource
    private ConcernRepository concernRepository;
    @Resource
    private PinconcernRepository pinconcernRepository;
    @Resource
    private TopicUserRepository topicUserRepository;

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

    @Override
    public ResponseUtil deleteUserAccount(Integer userId) {
        sysUserRepository.deleteSysUserByUserId(userId);//删除sysUser表中用户数据
        List<UserUser>userUserList=userConcernRepository.findAllByUserId(userId);
        if (userUserList.size()>0){
            for (int i=0; i<userUserList.size();i++){
                userConcernRepository.deleteUserUserByUserIdAndConcerneduserId(userId,userUserList.get(i).getConcerneduserId());//删除UserUser表中该用户关注的数据
            }
        }
        List<UserUser>userUsers=userConcernRepository.findAllByConcerneduserId(userId);
        if (userUsers.size()>0){
            for (int i=0;i<userUsers.size();i++){
                userConcernRepository.deleteUserUserByUserIdAndConcerneduserId(userUsers.get(i).getUserId(),userId);//删除UserUser表中该用户被关注的数据
            }
        }
        List<TopicUser>topicUserList=topicUserRepository.findAllByUserId(userId);
        if (topicUserList.size()>0){
            for (int i=0;i<topicUserList.size();i++){
                topicUserRepository.deleteTopicUserByUserIdAndTopicId(userId,topicUserList.get(i).getTopicId());//删除TopicUser表中该用户数据
            }
        }
        List<PinUser>pinUserList=pinconcernRepository.findPinUsersByUserId(userId);
        if (pinUserList.size()>0){
            for (int i=0;i<pinUserList.size();i++){
                pinconcernRepository.deletePinUserByPinIdAndUserId(pinUserList.get(i).getPinId(),userId);//删除PinUser表中该用户数据
            }
        }
        List<Pins>pinsList=pinRepository.findAllByUsersId(userId);//找到该用户所有创建的沸点
        if(pinsList.size()>0){
            for (int i=0;i<pinsList.size();i++){
                List<PinUser>pinUsers=pinconcernRepository.findPinUsersByPinId(pinsList.get(i).getPinId());//找到该用户创建的某个沸点被点赞数据
                if(pinUsers.size()>0){
                    for (int j=0;j<pinUsers.size();j++){
                        pinconcernRepository.deleteAllByPinId(pinUsers.get(j).getPinId());//删除该沸点被点赞的所有数据
                    }
                }
                pinRepository.deletePinByUsersIdAndPinId(userId,pinsList.get(i).getPinId());//删除该用户创建的沸点
            }
        }

        List<ConcernUser>concernUserList=concernRepository.findAllByUserId(userId);
        if (concernUserList.size()>0){
            for (int i=0;i<concernUserList.size();i++){
                concernRepository.deleteConcernUserByUserIdAndLabelId(userId,concernUserList.get(i).getLabelId());//删除ConcernUser表中该用户数据
            }
        }
        List<Articles>articlesList=articlesRepository.findAllByUsersId(userId);//找到该用户所有创建的文章
        if (articlesList.size()>0){
            for (int i=0;i<articlesList.size();i++){
                List<ArticleLike>articleLikes=likeRepository.findArticleLikeByArticleId(articlesList.get(i).getArticleId());//找到该用户创建的某个文章被点赞数据
                if (articleLikes.size()>0){
                    for (int j=0;j<articleLikes.size();j++){
                        likeRepository.deleteAllByArticleId(articleLikes.get(j).getArticleId());//删除该文章被点赞的所有数据
                    }
                }
                articlesRepository.deleteArticlesByArticleIdAndUsersId(articlesList.get(i).getArticleId(),userId);//删除该用户所创建的文章
            }
        }
        List<ArticleLike>articleLikeList=likeRepository.findArticleLikeByUserId(userId);//找到该用户所有点赞的文章数据
        if (articleLikeList.size()>0){
            for (int i=0;i<articleLikeList.size();i++){
                likeRepository.deleteArticleLikeByUserIdAndArticleId(userId,articleLikeList.get(i).getArticleId());//删除该用户点赞的文章数据
            }
        }
        return new ResponseUtil(0,"delete user account");
    }

    @Override
    public ResponseUtil checkMessageCode(LoginUser loginUser) {
        String host = "http://yzxyzm.market.alicloudapi.com";
        String path = "/yzx/verifySms";
        String method = "POST";
        String appcode = "cf50647bb1004b15b115de07c127c487";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();

        querys.put("phone", loginUser.getEmail());
        querys.put("templateId", "TP18040314");
        String code="";
        for (int i=0;i<6;i++){
            Random r=new Random();
            String j =String.valueOf(r.nextInt(10) );
            code+=j;
        }
        querys.put("variable", "code:"+code);
        Map<String, String> bodys = new HashMap<String, String>();
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            System.out.println(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseUtil(0,"check message code",code);
    }
}
