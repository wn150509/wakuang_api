package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.Articles;
import com.soft.wakuangapi.utils.ResponseUtil;
import com.soft.wakuangapi.vo.ArticleVo;

import java.util.List;

public interface ArticleService {
    List<Articles>findTypearticle(Integer id);

    List<Articles>findbytime(Integer id);

    List<Articles>findbycomment(Integer id);

    ResponseUtil releaseArticle(Articles article);

    List<Articles>getFollowArticle(Integer id);

    List<Articles>getFollowTime(Integer id);

    List<Articles>getFollowComment(Integer id);

    ArticleVo getoneArticle(Integer id);

    List<Articles>queryArticle(String articleTitle);
}
