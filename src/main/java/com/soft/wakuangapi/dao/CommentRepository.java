package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.ArticleComment;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<ArticleComment,Integer>{
    List<ArticleComment>findArticleCommentsByArticleId(Integer id);

    @Transactional
    int deleteArticleCommentByCommentId(Integer commentId);

}
