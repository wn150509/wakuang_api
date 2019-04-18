package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.*;
import com.soft.wakuangapi.utils.ResponseUtil;
import com.soft.wakuangapi.vo.ArticleVo;

import java.util.List;

public interface ArticleService {
    List<ArticleStatus>findTypearticle(UserType userType);

    List<ArticleStatus>findbytime(UserType userType);

    List<ArticleStatus>findbycomment(UserType userType);

    List<ArticleStatus>getFollowArticle(Integer id);

    List<ArticleStatus>getFollowTime(Integer id);

    List<ArticleStatus>getFollowComment(Integer id);

    ResponseUtil releaseArticle(Articles article);

    ArticleVo getoneArticle(Integer id);

    List<ArticleStatus>queryArticle(SearchUser searchUser);

    ResponseUtil getSomeOneArticlesbytime(Integer userId);

    ResponseUtil getSomeOneArticlesbylike(Integer userId);

    ResponseUtil getSomeOneLikeArticles(Integer userId);

    ResponseUtil getOtherArticleByTime(UserUser userUser);

    ResponseUtil getOtherArticleByLike(UserUser userUser);

    ResponseUtil getOtherLikeArticles(UserUser userUser);
}
