package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.TopicStatus;
import com.soft.wakuangapi.entity.TopicUser;
import com.soft.wakuangapi.utils.ResponseUtil;

import java.util.List;

public interface TopicUserService {
    List<TopicStatus> getTopicUser(Integer id);

    ResponseUtil insertTopicUser(TopicUser topicUser);

    ResponseUtil deleteTopicUser(TopicUser topicUser);
}
