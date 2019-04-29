package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.ArticlesRepository;
import com.soft.wakuangapi.dao.LikeRepository;
import com.soft.wakuangapi.entity.ArticleLike;
import com.soft.wakuangapi.entity.ArticleStatus;
import com.soft.wakuangapi.entity.Articles;
import com.soft.wakuangapi.service.LikeService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class LikeServiceImpl implements LikeService{
    @Resource
    private ArticlesRepository articlesRepository;
    @Resource
    private LikeRepository likeRepository;
    @Override
    public List<ArticleStatus> getArticleStatus(Integer id) {
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
                    articles.getAuthorAvatar(),articles.getCommentCount(),articleLikes.size(), articles.getUsersId(), articles.getLabelId(),
                    articles.getCreateTime(), articles.getTypeId(),status);
            articleStatusList.add(articleStatus);
        }
        return articleStatusList;
    }

    @Override
    public ResponseUtil insertLikeArticle(ArticleLike articleLike) {
        return new ResponseUtil(0,"insert like article",likeRepository.save(articleLike));
    }

    @Override
    public ResponseUtil deleteLikeArticle(ArticleLike articleLike) {
        return new ResponseUtil(0,"delete like article",likeRepository.deleteArticleLikeByUserIdAndArticleId(articleLike.getUserId(),articleLike.getArticleId()));
    }
}
