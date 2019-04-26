package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ArticlesRepository extends JpaRepository<Articles,Integer> {
    List<Articles> findByTypeId(Integer id);

    List<Articles> findByTypeIdOrderByCreateTimeDesc(Integer id);

    List<Articles>findByTypeIdOrderByCommentCountDesc(Integer id);

    List<Articles>findAllByLabelId(Integer id);

    Articles findArticlesByArticleId(Integer id);

    /**
     *
     * @param article_title 传入参数
     * @return
     */
    @Query(value = "select * from articles where article_title LIKE CONCAT('%',:article_title,'%')", nativeQuery = true)
    List<Articles> queryAticleList(@Param("article_title") String article_title);

    List<Articles>findAllByUsersIdOrderByCreateTimeDesc(Integer userId);

    List<Articles>findAllByUsersIdOrderByLikeCountDesc(Integer userId);

    List<Articles>findAllByUsersId(Integer userId);

    //删除文章
    @Transactional
    int deleteArticlesByArticleIdAndUsersId(Integer articleId,Integer userId);
}
