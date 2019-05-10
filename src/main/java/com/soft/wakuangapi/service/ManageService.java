package com.soft.wakuangapi.service;

import com.soft.wakuangapi.utils.ResponseUtil;

public interface ManageService {
    ResponseUtil getManageCount();

    ResponseUtil getTopicPinCount();

    ResponseUtil getLabelArticleCount();
}
