package com.soft.wakuangapi.dao;

import com.soft.wakuangapi.entity.Topics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topics,Integer> {
    List<Topics>findAll();

    Topics findTopicsByTopicName(String name);

    Topics findTopicsByTopicId(Integer topicId);
}
