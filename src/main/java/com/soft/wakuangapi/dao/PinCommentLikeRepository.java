package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.PinCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface PinCommentLikeRepository extends JpaRepository<PinCommentLike,Integer> {
    @Transactional
    int deletePinCommentLikeByCommentIdAndUserId(Integer commentId,Integer userId);

    List<PinCommentLike>findAllByCommentId(Integer commentId);

    List<PinCommentLike>findAllByUserId(Integer userId);
}
