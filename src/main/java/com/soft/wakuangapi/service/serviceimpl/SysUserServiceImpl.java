package com.soft.wakuangapi.service.serviceimpl;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.soft.wakuangapi.dao.*;
import com.soft.wakuangapi.entity.*;
import com.soft.wakuangapi.service.SysUserService;
import com.soft.wakuangapi.utils.HttpUtils;
import com.soft.wakuangapi.utils.QiNiuFileUpUtil;
import com.soft.wakuangapi.utils.ResponseUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;

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
    @Resource
    private PinCommentRepository pinCommentRepository;
    @Resource
    private PinCommentLikeRepository pinCommentLikeRepository;
    @Resource
    private CommentRepository commentRepository;
    @Resource
    private CommentlikeRepository commentlikeRepository;

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
        //删除之前上传的文件
        String string=user.getUserAvatar().substring(23);
        try {
            deleteFile(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //生成key
        UUID uuid =UUID.randomUUID();
        String str = uuid.toString();
        // 去掉"-"符号
        String key = str.substring(0, 8) +str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) +str.substring(24);
        String file64 = base64.substring(23);
        String url = "http://upload.qiniup.com/putb64/" + -1+"/key/"+ UrlSafeBase64.encodeToString(key);
        //非华东空间需要根据注意事项 1 修改上传域名
        RequestBody rb = RequestBody.create(null, file64);
        Request request = new Request.Builder().
                url(url).
                addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                .post(rb).build();
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
        String str1=new QiNiuFileUpUtil().publicFile(key,"http://123.pen46789.cn");
        user.setUserAvatar(str1);
        sysUserRepository.save(user);
        List<Articles>articlesList=articlesRepository.findAllByUsersId(user.getUserId());
        if (articlesList.size()>0){
            for (int i=0;i<articlesList.size();i++){
                articlesList.get(i).setAuthorAvatar(str1);
                articlesRepository.save(articlesList.get(i));
            }
        }
        return sysUserRepository.findSysUserByEmail(account);
    }
    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken(){
        return auth.uploadToken(bucketname, null, 3600, new StringMap().put("insertOnly", 1));
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
                if (userUsers.get(j).getConcerneduserId().equals(userList.get(i).getUserId())){
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
                if (userStatuses0.get(i).getUserId().equals(querySysUserList.get(j).getUserId())){
                    userStatuses.add(userStatuses0.get(i));
                }
            }
        }
        return userStatuses;
    }

    @Override
    public ResponseUtil getMessageCount(Integer id) {
        Articles articles=articlesRepository.findArticlesByArticleId(id);
        SysUser sysUser=sysUserRepository.findSysUserByUserId(articles.getUsersId());
        List<Articles>articlesList=articlesRepository.findAllByUsersId(sysUser.getUserId());//专栏数
        List<Pins>pinsList=pinRepository.findAllByUsersId(sysUser.getUserId());//沸点数
        List<UserUser>userUsers=userConcernRepository.findAllByUserId(sysUser.getUserId());//关注作者数
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
        //删除发布的文章评论被点赞数据
        List<ArticleComment>articleCommentList=commentRepository.findAllByUserId(userId);
        if (articleCommentList.size()>0){
            for (int i=0;i<articleCommentList.size();i++){
                commentlikeRepository.deleteAllByCommentId(articleCommentList.get(i).getCommentId());
            }
            //删除文章评论
            pinCommentRepository.deleteAllByUserId(userId);
        }
        //删除发布的沸点评论被点赞数据
        List<PinComment>pinCommentList=pinCommentRepository.findAllByUserId(userId);
        if (pinCommentList.size()>0){
            for(int i=0;i<pinCommentList.size();i++){
                pinCommentLikeRepository.deleteAllByCommentId(pinCommentList.get(i).getCommentId());
            }
            //删除沸点评论
            commentRepository.deleteAllByUserId(userId);
        }
        //删除点赞文章评论的数据
        commentlikeRepository.deleteAllByUserId(userId);
        //删除点赞沸点评论的数据
        pinCommentLikeRepository.deleteAllByUserId(userId);
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

    @Override
    public ResponseUtil getUserRightMessageCount(UserUser userUser) {
        List<Pins>pinsList=pinRepository.findAllByUsersId(userUser.getUserId());//沸点数
        List<Articles>articlesList=articlesRepository.findAllByUsersId(userUser.getUserId());//专栏数
        List<UserUser>userUserList=userConcernRepository.findAllByUserId(userUser.getUserId());//用户关注了作者数
        List<UserUser>userUsers=userConcernRepository.findAllByConcerneduserId(userUser.getUserId());//用户受关注数
        List<ConcernUser>concernUsers=concernRepository.findAllByUserId(userUser.getUserId());//用户关注标签数
        List<TopicUser>topicUserList=topicUserRepository.findAllByUserId(userUser.getUserId());//用户关注话题数量
        int likeCount=0;int commentCount=0;//获得赞数//被评论数
        for (int i=0;i<pinsList.size();i++){
            List<PinUser>pinUsers=pinconcernRepository.findPinUsersByPinId(pinsList.get(i).getPinId());
            List<PinComment>pinCommentList=pinCommentRepository.findAllByPinId(pinsList.get(i).getPinId());
            likeCount+=pinUsers.size();
            commentCount+=pinCommentList.size();
        }
        for (int i=0;i<articlesList.size();i++){
            List<ArticleLike>articleLikes=likeRepository.findArticleLikeByArticleId(articlesList.get(i).getArticleId());
            List<ArticleComment>articleCommentList=commentRepository.findAllByArticleId(articlesList.get(i).getArticleId());
            likeCount+=articleLikes.size();
            commentCount+=articleCommentList.size();
        }
        UserRightMessageCount userRightMessageCount=new UserRightMessageCount(pinsList.size(),articlesList.size(),userUserList.size(),
                userUsers.size(),likeCount,commentCount,concernUsers.size(),topicUserList.size());
        return new ResponseUtil(0,"get userRightMessageCount",userRightMessageCount);
    }
}
