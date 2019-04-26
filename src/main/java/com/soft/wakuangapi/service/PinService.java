package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.PinUser;
import com.soft.wakuangapi.entity.Pins;
import com.soft.wakuangapi.entity.UserTopicPin;
import com.soft.wakuangapi.utils.ResponseUtil;

public interface PinService {
    ResponseUtil releasePin(Pins pins);

    ResponseUtil getPinsByLike(UserTopicPin userTopicPin);

    ResponseUtil getPinsByConcerned(UserTopicPin userTopicPin);

    ResponseUtil insertPinsConcerned(PinUser pinUser);

    ResponseUtil deletePinsConcerned(PinUser pinUser);

    ResponseUtil getUserPins(UserTopicPin userTopicPin);

    ResponseUtil getOtherUserPins(UserTopicPin userTopicPin);

    ResponseUtil getUserLikePins(UserTopicPin userTopicPin);

    ResponseUtil getOtherUserLikePins(UserTopicPin userTopicPin);
}
