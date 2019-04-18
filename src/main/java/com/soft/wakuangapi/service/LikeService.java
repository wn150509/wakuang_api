package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.ArticleLike;
import com.soft.wakuangapi.entity.ArticleStatus;
import com.soft.wakuangapi.utils.ResponseUtil;

import java.util.List;

public interface LikeService {
    List<ArticleStatus>getArticleStatus(Integer id);

    ResponseUtil insertLikeArticle(ArticleLike articleLike);

    ResponseUtil deleteLikeArticle(ArticleLike articleLike);
}
