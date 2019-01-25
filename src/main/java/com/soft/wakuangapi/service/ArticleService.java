package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.Articles;
import com.soft.wakuangapi.utils.ResponseUtil;

import java.util.List;

public interface ArticleService {
    List<Articles>findTypearticle(Integer id);

    List<Articles>findbytime(Integer id);

    List<Articles>findbycomment(Integer id);

    ResponseUtil releaseArticle(Articles article);

}
