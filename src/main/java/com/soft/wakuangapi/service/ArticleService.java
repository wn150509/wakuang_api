package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.Articles;

import java.util.List;

public interface ArticleService {
    List<Articles>findTypearticle(Integer id);

    List<Articles>findbytime(Integer id);

    List<Articles>findbycomment(Integer id);

}
