package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface LikeRepository extends JpaRepository<ArticleLike,Integer> {
    List<ArticleLike>findArticleLikeByUserId(Integer userId);

    List<ArticleLike>findArticleLikeByArticleId(Integer articleId);

    @Transactional
    int deleteArticleLikeByUserIdAndArticleId(Integer userId,Integer articleId);

    @Transactional
    int deleteAllByArticleId(Integer articleId);

}
