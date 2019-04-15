package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.CommentChild;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentChildRepository extends JpaRepository<CommentChild,Integer> {
    List<CommentChild>findCommentChildrenByArticleIdAndUserId(Integer articleid,Integer userid);//通过文章id及userid找到子评论数组

    List<CommentChild>findCommentChildrenByUserId(Integer userid);

    List<CommentChild>findCommentChildrenByArticleId(Integer articleid);

}
