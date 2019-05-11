package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.TopicUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface TopicUserRepository extends JpaRepository<TopicUser,Integer> {
    List<TopicUser>findAllByUserId(Integer id);

    @Transactional
    int deleteTopicUserByUserIdAndTopicId(Integer userid,Integer topicid);

    @Transactional
    int deleteAllByTopicId(Integer topicId);

    List<TopicUser>findAllByTopicId(Integer topicid);

    TopicUser findTopicUserByUserIdAndTopicId(Integer userid,Integer topicid);
}
