package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.PinComment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface PinCommentRepository extends JpaRepository<PinComment,Integer> {

    @Transactional
    int deletePinCommentByCommentId(Integer commentId);

    List<PinComment>findAllByPinId(Integer pinId);
    List<PinComment>findAllByUserId(Integer userId);

    @Transactional
    int deleteAllByUserId(Integer userId);
}
