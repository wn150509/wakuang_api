package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticlesRepository extends JpaRepository<Articles,Integer> {
    List<Articles> findByTypeId(Integer id);

    List<Articles> findByTypeIdOrderByCreateTimeDesc(Integer id);

    List<Articles>findByTypeIdOrderByCommentCountDesc(Integer id);

    List<Articles>findAllByLabelId(Integer id);

//    @Query("select u from Articles u where u.articleTitle like \" '%\"and str and \"%'\" ")
//    List<Articles>queryAticleList(String key);
}
