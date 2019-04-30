package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentlikeRepository extends JpaRepository<CommentLike,Integer> {
    @Transactional
    int deleteCommentLikeByCommentIdAndUserId(Integer commentId,Integer userId);

    @Transactional
    int deleteAllByCommentId(Integer commentId);

    @Transactional
    int deleteAllByUserId(Integer userId);

    List<CommentLike>findAllByUserId(Integer userId);

    List<CommentLike>findAllByCommentId(Integer commentId);
}
