package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.*;
import com.soft.wakuangapi.entity.*;
import com.soft.wakuangapi.service.LabelsService;
import com.soft.wakuangapi.vo.LabelVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class LabelsServiceImpl implements LabelsService {
    @Resource
    private LabelsRepository labelsRepository;
    @Resource
    private ConcernRepository concernRepository;
    @Resource
    private LikeRepository likeRepository;
    @Resource
    private CommentRepository commentRepository;
    @Resource
    private ArticlesRepository articlesRepository;
    @Override
    public List<Labels> findAllLabels() {
        List<Labels>labelsList=labelsRepository.findAll();
        //打印 30到50之间的随机数
        int min = labelsList.get(0).getLabelsId();
        int max = labelsList.get(4).getLabelsId();
        for(int i=0;i<4;i++){
            System.out.println(new Random().nextInt(max-min)+min);
        }
        return labelsList;
    }

    @Override
    public LabelVo getonelabel(Integer labelid,Integer userid) {
        ConcernUser concernUser=concernRepository.findConcernUserByLabelIdAndUserId(labelid,userid);
        List<ConcernUser>labelfanscount=concernRepository.findAllByLabelId(labelid);
        List<ArticleStatus>articleStatuses=getArticleStatus(userid);
        List<ArticleStatus>articleStatusList=new ArrayList<>();
        for (int i=0;i<articleStatuses.size();i++){
            if (articleStatuses.get(i).getLabelId().equals(labelid)){
                articleStatusList.add(articleStatuses.get(i));
            }
        }
        int status=0;
        if (concernUser!=null){
            status=1;
        }
        Labels labels=labelsRepository.findLabelsByLabelsId(labelid);
        LabelStatus labelStatus=new LabelStatus(labelid,labels.getLabelsUrl(),
                labels.getLabelsName(),articleStatusList.size(),labelfanscount.size(),status);
        LabelVo labelVo=new LabelVo(labelStatus,articleStatusList);
        return labelVo;
    }

    @Override
    public Labels getoneLabel(String name) {
        return labelsRepository.findLabelsByLabelsName(name);
    }

    @Override
    public List<LabelStatus> queryLabels(SearchLabels searchLabels) {
        List<Labels>queryLabelsList=labelsRepository.queryLabelsList(searchLabels.getKey());//根据关键词获取一组标签list

        //以下根据当前用户id得到一组标签list的完整信息
        List<Labels>labelsList=labelsRepository.findAll();
        List<ConcernUser>concernUsers=concernRepository.findAllByUserId(searchLabels.getUserId());
        List<LabelStatus>labelStatusList0=new ArrayList<>();
        for (int i=0;i<labelsList.size();i++){
            Labels labels=labelsList.get(i);
            List<ConcernUser>labelfanscount=concernRepository.findAllByLabelId(labelsList.get(i).getLabelsId());
            List<Articles>labelarticlecount=articlesRepository.findAllByLabelId(labelsList.get(i).getLabelsId());
            int status=0;
            for (int j=0;j<concernUsers.size();j++){
                if (concernUsers.get(j).getLabelId().equals(labelsList.get(i).getLabelsId())){
                    status=1;
                }
            }
            LabelStatus labelStatus=new LabelStatus(labels.getLabelsId(),
                    labels.getLabelsUrl(),labels.getLabelsName(),
                    labelarticlecount.size(),labelfanscount.size(),status);
            labelStatusList0.add(labelStatus);
        }
        List<LabelStatus>labelStatusList=new ArrayList<>();
        for (int i=0;i<labelStatusList0.size();i++){
            for (int j=0;j<queryLabelsList.size();j++){
                if (labelStatusList0.get(i).getLabelsId()==queryLabelsList.get(j).getLabelsId()){
                    labelStatusList.add(labelStatusList0.get(i));
                }
            }
        }
        return labelStatusList;
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
                if (articleLikeList.get(j).getArticleId().equals(articlesList.get(i).getArticleId())){
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
}
