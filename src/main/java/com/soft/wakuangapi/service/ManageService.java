package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.Labels;
import com.soft.wakuangapi.entity.Topics;
import com.soft.wakuangapi.utils.ResponseUtil;

public interface ManageService {
    ResponseUtil getManageCount();

    ResponseUtil getTopicPinCount();

    ResponseUtil getLabelArticleCount();

    ResponseUtil getAllTopic();

    ResponseUtil getAllLabel();

    ResponseUtil changeTopic(Topics topics);

    ResponseUtil changeLabel(Labels labels);

    ResponseUtil addTopic(Topics topics);

    ResponseUtil addLabel(Labels labels);

    ResponseUtil deleteTopic(Topics topics);

    ResponseUtil deleteLabel(Labels labels);
}
