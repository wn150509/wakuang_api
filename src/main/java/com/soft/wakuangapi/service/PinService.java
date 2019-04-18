package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.Pins;
import com.soft.wakuangapi.entity.UserTopicPin;
import com.soft.wakuangapi.utils.ResponseUtil;

public interface PinService {
    ResponseUtil releasePin(Pins pins);

    ResponseUtil getPinsConcerned(UserTopicPin userTopicPin);
}
