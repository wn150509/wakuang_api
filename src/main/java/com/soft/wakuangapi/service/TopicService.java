package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.UserTopicPin;
import com.soft.wakuangapi.utils.ResponseUtil;

public interface TopicService {
    ResponseUtil getAll();

    ResponseUtil getTopicIdbyName(String topicName);

    ResponseUtil getOneTopic(UserTopicPin userTopicPin);
}
