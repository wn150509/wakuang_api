package com.soft.wakuangapi.service.serviceimpl;

import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.soft.wakuangapi.dao.*;
import com.soft.wakuangapi.entity.*;
import com.soft.wakuangapi.service.ArticleService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Collections;

@Service
public class ArticleServiceImpl implements ArticleService {
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
    private ArticlesRepository articlesRepository;
    @Resource
    private LikeRepository likeRepository;
    @Resource
    private ConcernRepository concernRepository;
    @Resource
    private CommentRepository commentRepository;
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private UserConcernRepository userConcernRepository;

    @Override
    public List<ArticleStatus> findTypearticle(UserType userType) {
        List<ArticleStatus>articleStatusList=new ArrayList<>();
        List<ArticleStatus>articleStatuses=getArticleStatus(userType.getUserId());
        for (int i=0;i<articleStatuses.size();i++){
            if (articleStatuses.get(i).getTypeId()==userType.getTypeId()){
                articleStatusList.add(articleStatuses.get(i));
            }
        }
        Collections.sort(articleStatusList, new Comparator<ArticleStatus>() {
            @Override
            public int compare(ArticleStatus o1, ArticleStatus o2) {
                return o2.getLikeCount().compareTo(o1.getLikeCount());
            }
        });
        return articleStatusList;
    }
    @Override
    public List<ArticleStatus> findbytime(UserType userType) {
        List<ArticleStatus>articleStatusList=new ArrayList<>();
        List<ArticleStatus>articleStatuses=getArticleStatus(userType.getUserId());
        for (int i=0;i<articleStatuses.size();i++){
            if (articleStatuses.get(i).getTypeId()==userType.getTypeId()){
                articleStatusList.add(articleStatuses.get(i));
            }
        }
        return paixuTime(articleStatusList);
    }
    @Override
    public List<ArticleStatus> findbycomment(UserType userType) {
        List<ArticleStatus>articleStatusList=new ArrayList<>();
        List<ArticleStatus>articleStatuses=getArticleStatus(userType.getUserId());
        for (int i=0;i<articleStatuses.size();i++){
            if (articleStatuses.get(i).getTypeId()==userType.getTypeId()){
                articleStatusList.add(articleStatuses.get(i));
            }
        }
        return paixuComment(articleStatusList);
    }
    @Override
    public ResponseUtil releaseArticle(Articles article) {
        Articles articles=new Articles();
        if (article.getArticlePic()==null){
            articles.setArticlePic(article.getArticlePic());
        }else {
            //给文章添加封面
            //生成key
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
            GenerateImage(article.getArticlePic());
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
            articles.setArticlePic(str1);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date date=null;
        try {
            date=df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        articles.setArticleTitle(article.getArticleTitle());
        articles.setArticleAuthor(article.getArticleAuthor());
        articles.setLabelId(article.getLabelId());
        articles.setCreateTime(date);
        articles.setArticleContent(article.getArticleContent());
        articles.setAuthorAvatar(article.getAuthorAvatar());
        articles.setCommentCount(0);
        articles.setLikeCount(0);
        articles.setUsersId(article.getUsersId());

        articles.setTypeId(article.getTypeId());
        return new ResponseUtil(0,"add new article",articlesRepository.save(articles));
    }
    @Override
    public List<ArticleStatus> getFollowArticle(Integer id) {
        List<ArticleStatus>articleStatusList=getList(id);
        Collections.sort(articleStatusList, new Comparator<ArticleStatus>() {
            @Override
            public int compare(ArticleStatus o1, ArticleStatus o2) {
                return o2.getLikeCount().compareTo(o1.getLikeCount());
            }
        });
        return articleStatusList;
    }

    @Override
    public List<ArticleStatus> getFollowTime(Integer id) {
        List<ArticleStatus>articleStatusList=getList(id);
        Collections.sort(articleStatusList, new Comparator<ArticleStatus>() {
            @Override
            public int compare(ArticleStatus o1, ArticleStatus o2) {
                return o2.getCreateTime().compareTo(o1.getCreateTime());
            }
        });
        return articleStatusList;
    }

    @Override
    public List<ArticleStatus> getFollowComment(Integer id) {
        List<ArticleStatus>articleStatusList=getList(id);
        Collections.sort(articleStatusList, new Comparator<ArticleStatus>() {
            @Override
            public int compare(ArticleStatus o1, ArticleStatus o2) {
                return o2.getCommentCount().compareTo(o1.getCommentCount());
            }
        });
        return articleStatusList;
    }

    @Override
    public ArticleVo getoneArticle(ArticleLike articleLike) {
        List<ArticleStatus>articleStatuses=getArticleStatus(articleLike.getUserId());
        ArticleStatus articleStatus=new ArticleStatus();
        for (int i=0;i<articleStatuses.size();i++){
            if (articleLike.getArticleId()==articleStatuses.get(i).getArticleId()){
                articleStatus=articleStatuses.get(i);
            }
        }
        List<UserStatus>userStatuses=getSomeOneUserStatus(articleLike.getUserId());
        UserStatus userStatus=new UserStatus();
        for (int i=0;i<userStatuses.size();i++){
            if (articleStatus.getUsersId()==userStatuses.get(i).getUserId()){
                userStatus=userStatuses.get(i);
            }
        }
        ArticleVo articleVo=new ArticleVo(userStatus,articleStatus);
        return articleVo;
    }

    @Override
    public List<ArticleStatus> queryArticle(SearchUser searchUser) {
        List<Articles>articlesList=articlesRepository.queryAticleList(searchUser.getKey());
        List<ArticleStatus>articleStatuses=getArticleStatus(searchUser.getUserId());
        List<ArticleStatus>articleStatusList=new ArrayList<>();
        for (int i=0;i<articlesList.size();i++){
            for (int j=0;j<articleStatuses.size();j++){
                if (articlesList.get(i).getArticleId()==articleStatuses.get(j).getArticleId()){
                    articleStatusList.add(articleStatuses.get(j));
                }
            }
        }
        return articleStatusList;
    }

    @Override
    public ResponseUtil getSomeOneArticlesbytime(Integer userId) {
        List<ArticleStatus>articleStatusList=getSomeOneArticleStatus(userId);
        Collections.sort(articleStatusList, new Comparator<ArticleStatus>() {
            @Override
            public int compare(ArticleStatus o1, ArticleStatus o2) {
                return o2.getCreateTime().compareTo(o1.getCreateTime());
            }
        });
        return new ResponseUtil(0,"get someone article by time",articleStatusList);
    }

    @Override
    public ResponseUtil getSomeOneArticlesbylike(Integer userId) {
       List<ArticleStatus>articleStatusList=getSomeOneArticleStatus(userId);
        Collections.sort(articleStatusList, new Comparator<ArticleStatus>() {
            @Override
            public int compare(ArticleStatus o1, ArticleStatus o2) {
                return o2.getLikeCount().compareTo(o1.getLikeCount());
            }
        });
        return new ResponseUtil(0,"get someone article by time",articleStatusList);
    }

    @Override
    public ResponseUtil getSomeOneLikeArticles(Integer userId) {
        List<ArticleLike>articleLikeList=likeRepository.findArticleLikeByUserId(userId);
        List<ArticleStatus>articleStatusList=new ArrayList<>();
        for (int i=0;i<articleLikeList.size();i++){
            Articles articles=articlesRepository.findArticlesByArticleId(articleLikeList.get(i).getArticleId());
            List<ArticleLike>articleLikes=likeRepository.findArticleLikeByArticleId(articleLikeList.get(i).getArticleId());
            ArticleStatus articleStatus=new ArticleStatus(articles.getArticleId(),articles.getArticleTitle(),
                    articles.getArticleContent(),articles.getArticleAuthor(),articles.getArticlePic(),
                    articles.getAuthorAvatar(),articles.getCommentCount(),articleLikes.size(), articles.getUsersId(), articles.getLabelId(),
                    articles.getCreateTime(), articles.getTypeId(),1);
            articleStatusList.add(articleStatus);
        }
        return new ResponseUtil(0,"get someone like articles",articleStatusList);
    }

    @Override
    public ResponseUtil getOtherArticleByTime(UserUser userUser) {
        List<ArticleStatus>articleStatusList=getArticleStatus(userUser.getUserId());
        List<ArticleStatus>articleStatuses=new ArrayList<>();
        for (int i=0;i<articleStatusList.size();i++){
            if (articleStatusList.get(i).getUsersId()==userUser.getConcerneduserId()){
                articleStatuses.add(articleStatusList.get(i));
            }
        }
        Collections.sort(articleStatuses, new Comparator<ArticleStatus>() {
            @Override
            public int compare(ArticleStatus o1, ArticleStatus o2) {
                return o2.getCreateTime().compareTo(o1.getCreateTime());
            }
        });
        return new ResponseUtil(0,"get other articles",articleStatuses);
    }

    @Override
    public ResponseUtil getOtherArticleByLike(UserUser userUser) {
        List<ArticleStatus>articleStatusList=getArticleStatus(userUser.getUserId());
        List<ArticleStatus>articleStatuses=new ArrayList<>();
        for (int i=0;i<articleStatusList.size();i++){
            if (articleStatusList.get(i).getUsersId()==userUser.getConcerneduserId()){
                articleStatuses.add(articleStatusList.get(i));
            }
        }
        Collections.sort(articleStatuses, new Comparator<ArticleStatus>() {
            @Override
            public int compare(ArticleStatus o1, ArticleStatus o2) {
                return o2.getLikeCount().compareTo(o1.getLikeCount());
            }
        });

        return new ResponseUtil(0,"get other articles",articleStatuses);
    }

    @Override
    public ResponseUtil getOtherLikeArticles(UserUser userUser) {
        List<ArticleStatus>articleStatusList=getArticleStatus(userUser.getUserId());
        List<ArticleStatus>articleStatuses=new ArrayList<>();
        List<ArticleLike>articleLikeList=likeRepository.findArticleLikeByUserId(userUser.getConcerneduserId());
        if (articleLikeList.size()>0){
            for (int i=0;i<articleStatusList.size();i++){
                for (int j=0;j<articleLikeList.size();j++){
                    if (articleStatusList.get(i).getArticleId()==articleLikeList.get(j).getArticleId()){
                        articleStatuses.add(articleStatusList.get(i));
                    }
                }
            }
        }else {
            articleStatuses=null;
        }
        return new ResponseUtil(0,"get Other Like Articles",articleStatuses);
    }

    @Override
    public ResponseUtil getRelativeArticles(ArticleLike articleLike) {
        List<ArticleStatus>articleStatusList=getArticleStatus(articleLike.getUserId());
        Articles articles=articlesRepository.findArticlesByArticleId(articleLike.getArticleId());
        List<ArticleStatus>articleStatuses=new ArrayList<>();
        List<ArticleStatus>articleStatusList1=new ArrayList<>();
        for (int i=0;i<articleStatusList.size();i++){
            ArticleStatus ar=articleStatusList.get(i);
            if ((articles.getTypeId()==ar.getTypeId())&&(articles.getArticleId()!=ar.getArticleId())){
                articleStatuses.add(ar);
            }
        }
        Collections.sort(articleStatuses, new Comparator<ArticleStatus>() {
            @Override
            public int compare(ArticleStatus o1, ArticleStatus o2) {
                return o2.getLikeCount().compareTo(o1.getLikeCount());
            }
        });
        if (articleStatuses.size()<5){
            articleStatusList1=articleStatuses;
        }else {
            for (int i=0;i<5;i++){
                articleStatusList1.add(articleStatuses.get(i));
            }
        }
        return new ResponseUtil(0,"get relative articles",articleStatusList1);
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
    //遍历List
    public List<ArticleStatus>getList(Integer userId){
        List<ConcernUser>concernUserList=concernRepository.findAllByUserId(userId);
        List<ArticleStatus>articleStatusList1=getArticleStatus(userId);
        List<ArticleStatus>articleStatusList=new ArrayList<>();
        for (int i=0;i<concernUserList.size();i++){
            for (int j=0;j<articleStatusList1.size();j++){
                if (articleStatusList1.get(j).getLabelId()==concernUserList.get(i).getLabelId()){
                    articleStatusList.add(articleStatusList1.get(j));
                }
            }
        }
        return articleStatusList;
    }
    //给文章评论数赋值
    public int getCount(Integer articleid){
        return commentRepository.findAllByArticleId(articleid).size();
    }

    public List<ArticleStatus>getArticleStatus(Integer id){
        List<Articles>articlesList=articlesRepository.findAll();//查询所有文章
        List<ArticleLike>articleLikeList=likeRepository.findArticleLikeByUserId(id);
        List<ArticleStatus>articleStatusList=new ArrayList<>();
        for (int i=0;i<articlesList.size();i++){
            Articles articles=articlesList.get(i);
            int status=0;
            List<ArticleLike>articleLikes=likeRepository.findArticleLikeByArticleId(articles.getArticleId());
            for (int j=0;j<articleLikeList.size();j++){
                if (articleLikeList.get(j).getArticleId()==articlesList.get(i).getArticleId()){
                    status=1;
                }
            }
            ArticleStatus articleStatus=new ArticleStatus(articles.getArticleId(),articles.getArticleTitle(),
                    articles.getArticleContent(),articles.getArticleAuthor(),articles.getArticlePic(),
                    articles.getAuthorAvatar(),getCount(articles.getArticleId()),articleLikes.size(), articles.getUsersId(), articles.getLabelId(),
                    articles.getCreateTime(), articles.getTypeId(),status);
            articleStatusList.add(articleStatus);
        }
        return articleStatusList;
    }

    public List<ArticleStatus> paixuTime(List<ArticleStatus>articleStatusList){

        Collections.sort(articleStatusList, new Comparator<ArticleStatus>(){
            /*
             * int compare(Student o1, Student o2) 返回一个基本类型的整型，
             * 返回负数表示：o1 小于o2，
             * 返回0 表示：o1和o2相等，
             * 返回正数表示：o1大于o2。
             */
            public int compare(ArticleStatus o1, ArticleStatus o2) {
                //进行降序排列
                //将date类型的转换为String类型，然后截取和库中相同的长度，再转换为int；这样的截取方式已比对过，无差异
                Integer dateValue1 = Integer.valueOf(String.valueOf(o1.getCreateTime().getTime()).substring(0, 10));
                Integer dataValue2 = Integer.valueOf(String.valueOf(o2.getCreateTime().getTime()).substring(0,10));
                if(dateValue1 < dataValue2){
                    return 1;
                }
                if(dateValue1 == dataValue2){
                    return 0;
                }
                return -1;
            }
        });

        return articleStatusList;
    }

    public List<ArticleStatus> paixuComment(List<ArticleStatus>articleStatusList){

        Collections.sort(articleStatusList, new Comparator<ArticleStatus>(){
            /*
             * int compare(Student o1, Student o2) 返回一个基本类型的整型，
             * 返回负数表示：o1 小于o2，
             * 返回0 表示：o1和o2相等，
             * 返回正数表示：o1大于o2。
             */
            public int compare(ArticleStatus o1, ArticleStatus o2) {
                //进行降序排列
                if(o1.getCommentCount() < o2.getCommentCount()){
                    return 1;
                }
                if(o1.getCommentCount() == o2.getCommentCount()){
                    return 0;
                }
                return -1;
            }
        });

        return articleStatusList;
    }

    public List<ArticleStatus> getSomeOneArticleStatus(Integer userId){
        List<ArticleStatus>articleStatusList=getArticleStatus(userId);
        List<ArticleStatus>articleStatuses=new ArrayList<>();
        for (int i=0;i<articleStatusList.size();i++){
            if (articleStatusList.get(i).getUsersId()==userId){
                articleStatuses.add(articleStatusList.get(i));
            }
        }
       return articleStatuses;
    }

    public List<UserStatus>getSomeOneUserStatus(Integer userId){
        List<SysUser>userList=sysUserRepository.findAll();//获取表中所有用户信息
        List<UserUser>userUsers=userConcernRepository.findAllByUserId(userId);//根据userId获取被关注用户ID组
        List<UserStatus>userStatuses=new ArrayList<>();

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
            userStatuses.add(userStatus);
        }
        return userStatuses;
    }
}
