package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.ArticleComment;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<ArticleComment,Integer>{
    List<ArticleComment>findAllByArticleId(Integer id);

    List<ArticleComment>findAllByUserId(Integer userId);

    @Transactional
    int deleteArticleCommentByCommentId(Integer commentId);

    @Transactional
    int deleteAllByUserId(Integer userId);

}
