package com.soft.wakuangapi.service.serviceimpl;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.soft.wakuangapi.dao.*;
import com.soft.wakuangapi.entity.*;
import com.soft.wakuangapi.service.ManageService;
import com.soft.wakuangapi.utils.QiNiuFileUpUtil;
import com.soft.wakuangapi.utils.ResponseUtil;
import com.soft.wakuangapi.vo.LabelManageVo;
import com.soft.wakuangapi.vo.TopicManageVo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ManageServiceImpl implements ManageService {
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
    SysUserRepository sysUserRepository;
    @Resource
    PinRepository pinRepository;
    @Resource
    ArticlesRepository articlesRepository;
    @Resource
    LabelsRepository labelsRepository;
    @Resource
    TopicRepository topicRepository;
    @Resource
    TopicUserRepository topicUserRepository;
    @Resource
    ConcernRepository concernRepository;
    @Resource
    PinconcernRepository pinconcernRepository;
    @Resource
    PinCommentLikeRepository pinCommentLikeRepository;
    @Resource
    PinCommentRepository pinCommentRepository;
    @Resource
    CommentRepository commentRepository;
    @Resource
    CommentlikeRepository commentlikeRepository;
    @Resource
    LikeRepository likeRepository;

    @Override
    public ResponseUtil getManageCount() {
        List<Articles>articlesList=articlesRepository.findAll();
        List<Pins>pinsList=pinRepository.findAll();
        List<SysUser>sysUserList=sysUserRepository.findAll();
        List<SysUser>sysUsers=new ArrayList<>();
        for (int i=0;i<sysUserList.size();i++){
            if (sysUserList.get(i).getUserId()!=0){
                sysUsers.add(sysUserList.get(i));
            }
        }
        ManageCount manageCount=new ManageCount(sysUsers.size(),pinsList.size(),articlesList.size());
        return new ResponseUtil(0,"get manage count",manageCount);
    }
    @Override
    public ResponseUtil getTopicPinCount() {
        List<TopicPinCount>topicPinCounts=new ArrayList<>();
        List<Topics>topicsList=topicRepository.findAll();
        for (int i=0;i<topicsList.size();i++){
            List<Pins>pinsList=pinRepository.getAllByTopicId(topicsList.get(i).getTopicId());
            TopicPinCount topicPinCount=new TopicPinCount(topicsList.get(i).getTopicName(),pinsList.size());
            topicPinCounts.add(topicPinCount);
        }
        return new ResponseUtil(0,"get topic pin count",topicPinCounts);
    }
    @Override
    public ResponseUtil getLabelArticleCount() {
        List<LabelArticleCount>labelArticleCounts=new ArrayList<>();
        List<Labels>labelsList=labelsRepository.findAll();
        for (int i=0;i<labelsList.size();i++){
            List<Articles>articles=articlesRepository.findAllByLabelId(labelsList.get(i).getLabelsId());
            LabelArticleCount labelArticleCount=new LabelArticleCount(labelsList.get(i).getLabelsName(),articles.size());
            labelArticleCounts.add(labelArticleCount);
        }
        return new ResponseUtil(0,"get topic pin count",labelArticleCounts);
    }
    @Override
    public ResponseUtil getAllTopic() {
        List<Topics>topicsList=topicRepository.findAll();
        List<TopicManageVo>topicManageVos=new ArrayList<>();
        for (int i=0;i<topicsList.size();i++){
            List<TopicUser>topicUserList=topicUserRepository.findAllByTopicId(topicsList.get(i).getTopicId());
            List<Pins>pinsList=pinRepository.getAllByTopicId(topicsList.get(i).getTopicId());
            TopicManageVo topicManageVo=new TopicManageVo(topicsList.get(i),topicUserList.size(),pinsList.size());
            topicManageVos.add(topicManageVo);
        }
        return new ResponseUtil(0,"get all topic",topicManageVos);
    }
    @Override
    public ResponseUtil getAllLabel() {
        List<Labels>labelsList=labelsRepository.findAll();
        List<LabelManageVo>labelManageVos=new ArrayList<>();
        for (int i=0;i<labelsList.size();i++){
            List<ConcernUser>concernUsers=concernRepository.findAllByLabelId(labelsList.get(i).getLabelsId());
            List<Articles>articlesList=articlesRepository.findAllByLabelId(labelsList.get(i).getLabelsId());
            LabelManageVo labelManageVo=new LabelManageVo(labelsList.get(i),concernUsers.size(),articlesList.size());
            labelManageVos.add(labelManageVo);
        }
        return new ResponseUtil(0,"get all label",labelManageVos);
    }
    @Override
    public ResponseUtil changeTopic(Topics topics) {
        Topics topics1=topicRepository.findTopicsByTopicId(topics.getTopicId());
        topics1.setTopicName(topics.getTopicName());
        topics1.setTopicDescription(topics.getTopicDescription());
        return new ResponseUtil(0,"change topic",topicRepository.save(topics1));
    }
    @Override
    public ResponseUtil changeLabel(Labels labels) {
        Labels labels1=labelsRepository.findLabelsByLabelsId(labels.getLabelsId());
        labels1.setLabelsName(labels.getLabelsName());
        return new ResponseUtil(0,"change label",labelsRepository.save(labels1));
    }
    @Override
    public ResponseUtil addTopic(Topics topics) {
        Topics topics1=new Topics();
        String str1=getURL(topics.getTopicUrl());
        topics1.setTopicUrl(str1);
        topics1.setTopicDescription(topics.getTopicDescription());
        topics1.setTopicName(topics.getTopicName());
        return new ResponseUtil(0,"add new topic",topicRepository.save(topics1));
    }

    @Override
    public ResponseUtil addLabel(Labels labels) {
        Labels labels1=new Labels();
        String string=getURL(labels.getLabelsUrl());
        labels1.setLabelsUrl(string);
        labels1.setLabelsName(labels.getLabelsName());
        return new ResponseUtil(0,"add new label",labelsRepository.save(labels1));
    }

    @Override
    public ResponseUtil deleteTopic(Topics topics) {
        List<Pins>pinsList=pinRepository.getAllByTopicId(topics.getTopicId());//找到话题下的所有沸点
        if (pinsList.size()>0){
            for (int i=0;i<pinsList.size();i++){
                List<PinComment>pinCommentList=pinCommentRepository.findAllByPinId(pinsList.get(i).getPinId());//找到该沸点下的评论数据
                if(pinCommentList.size()>0){
                    for (int j=0;j<pinCommentList.size();j++){
                        pinCommentLikeRepository.deleteAllByCommentId(pinCommentList.get(j).getCommentId());//删除该评论下所有被点赞数据
                        pinCommentRepository.deletePinCommentByCommentId(pinCommentList.get(j).getCommentId());//删除该评论
                    }
                }
                pinconcernRepository.deleteAllByPinId(pinsList.get(i).getPinId());//删除该沸点被点赞数据
            }
            pinRepository.deleteAllByTopicId(topics.getTopicId());//删除该话题下的所有沸点
        }
        topicUserRepository.deleteAllByTopicId(topics.getTopicId());//删除该话题所有被关注数据
        return new ResponseUtil(0,"delete topic",topicRepository.deleteTopicsByTopicId(topics.getTopicId()));
    }

    @Override
    public ResponseUtil deleteLabel(Labels labels) {
        List<Articles>articlesList=articlesRepository.findAllByLabelId(labels.getLabelsId());//找到该标签下所有的文章
        if(articlesList.size()>0){
            for (int i=0;i<articlesList.size();i++){
                List<ArticleComment>articleCommentList=commentRepository.findAllByArticleId(articlesList.get(i).getArticleId());//找到该文章下的所有评论
                if(articleCommentList.size()>0){
                    for (int j=0;j<articleCommentList.size();j++){
                        commentlikeRepository.deleteAllByCommentId(articleCommentList.get(j).getCommentId());//删除该评论所有被点赞的数据
                        commentRepository.deleteArticleCommentByCommentId(articleCommentList.get(j).getCommentId());//删除该评论
                    }
                }
                likeRepository.deleteAllByArticleId(articlesList.get(i).getArticleId());//删除该文章下所有点赞数据
            }
            articlesRepository.deleteAllByLabelId(labels.getLabelsId());//删除该标签下所有文章
        }
        concernRepository.deleteAllByLabelId(labels.getLabelsId());//删除该标签所有被关注数据
        return new ResponseUtil(0,"delete label",labelsRepository.deleteLabelsByLabelsId(labels.getLabelsId()));
    }

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken(){
        return auth.uploadToken(bucketname, null, 3600, new StringMap().put("insertOnly", 1));
    }
    //给标签或者话题添加图片
    public String getURL(String string){
        //给文章添加封面
        //生成key
        UUID uuid =UUID.randomUUID();
        String str = uuid.toString();
        // 去掉"-"符号
        String key = str.substring(0, 8) +str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) +str.substring(24);
        String file64 = string.substring(23);//base64去头
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
        return str1;
    }
}
