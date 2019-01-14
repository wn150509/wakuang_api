package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.ArticlesRepository;
import com.soft.wakuangapi.entity.Articles;
import com.soft.wakuangapi.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticlesRepository articlesRepository;
    @Override
    public List<Articles> findTypearticle(Integer id) {
        return articlesRepository.findByTypeId(id);
    }

    @Override
    public List<Articles> findbytime(Integer id) {
        return articlesRepository.findByTypeIdOrderByCreateTimeDesc(id);
    }

    @Override
    public List<Articles> findbycomment(Integer id) {
        return articlesRepository.findByTypeIdOrderByCommentCountDesc(id);
    }
}
