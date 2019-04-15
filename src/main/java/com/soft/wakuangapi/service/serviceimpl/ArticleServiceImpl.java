package com.soft.wakuangapi.service.serviceimpl;

import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.soft.wakuangapi.dao.*;
import com.soft.wakuangapi.entity.Articles;
import com.soft.wakuangapi.entity.ConcernUser;
import com.soft.wakuangapi.entity.SysUser;
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
    private ConcernRepository concernRepository;
    @Resource
    private CommentRepository commentRepository;
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private CommentChildRepository commentChildRepository;

    @Override
    public List<Articles> findTypearticle(Integer id) {
        for(int i=0;i<articlesRepository.findByTypeId(id).size();i++){
            articlesRepository.findByTypeId(id).get(i).setCommentCount(getCount(id));
            articlesRepository.save(articlesRepository.findByTypeId(id).get(i));
        }
        return articlesRepository.findByTypeId(id);
    }
    @Override
    public List<Articles> findbytime(Integer id) {
        for(int i=0;i<articlesRepository.findByTypeId(id).size();i++){
            articlesRepository.findByTypeId(id).get(i).setCommentCount(getCount(id));
            articlesRepository.save(articlesRepository.findByTypeId(id).get(i));
        }
        return articlesRepository.findByTypeIdOrderByCreateTimeDesc(id);
    }
    @Override
    public List<Articles> findbycomment(Integer id) {
        for(int i=0;i<articlesRepository.findByTypeId(id).size();i++){
            articlesRepository.findByTypeId(id).get(i).setCommentCount(getCount(id));
            articlesRepository.save(articlesRepository.findByTypeId(id).get(i));
        }
        return articlesRepository.findByTypeIdOrderByCommentCountDesc(id);
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
    public List<Articles> getFollowArticle(Integer id) {
        return getList(id);
    }

    @Override
    public List<Articles> getFollowTime(Integer id) {
        List<Articles>articlesList=new ArrayList<>();
        articlesList.addAll(getList(id));
        Collections.sort(articlesList, new Comparator<Articles>() {
            @Override
            public int compare(Articles o1, Articles o2) {
                return o2.getCreateTime().compareTo(o1.getCreateTime());
            }
        });
        return articlesList;
    }

    @Override
    public List<Articles> getFollowComment(Integer id) {
        List<Articles>articlesList=new ArrayList<>();
        articlesList.addAll(getList(id));
        Collections.sort(articlesList, new Comparator<Articles>() {
            @Override
            public int compare(Articles o1, Articles o2) {
                return o2.getCommentCount().compareTo(o1.getCommentCount());
            }
        });
        return articlesList;
    }

    @Override
    public ArticleVo getoneArticle(Integer articleid) {
        Articles articles=articlesRepository.findArticlesByArticleId(articleid);
        articles.setCommentCount(getCount(articleid));
        articlesRepository.save(articles);
        SysUser sysUser=sysUserRepository.findSysUserByUserId(articles.getUsersId());
        ArticleVo articleVo=new ArticleVo(sysUser,articles);
        return articleVo;
    }

    @Override
    public List<Articles> queryArticle(String articleTitle) {
        List<Articles>articlesList=articlesRepository.queryAticleList(articleTitle);
        return articlesList;
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
    public List<Articles>getList(Integer id){
        List<ConcernUser>concernUserList=concernRepository.findAllByUserId(id);
        List<Articles>articles=new ArrayList<>();
        for (int i=0;i<concernUserList.size();i++){
            List<Articles>articlesList=new ArrayList<>();
            articlesList=articlesRepository.findAllByLabelId(concernUserList.get(i).getLabelId());
            articles.addAll(articlesList);
        }
        for(int j=0;j<articles.size();j++){
            articles.get(j).setCommentCount(getCount(articles.get(j).getArticleId()));
        }
        return articles;
    }
    //给文章评论数赋值
    public int getCount(Integer articleid){
        return commentRepository.findArticleCommentsByArticleId(articleid).size()
                +commentChildRepository.findCommentChildrenByArticleId(articleid).size();
    }
}
