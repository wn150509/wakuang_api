package com.soft.wakuangapi.vo;

import com.soft.wakuangapi.entity.TopicStatus;
import com.soft.wakuangapi.entity.UserStatus;
import lombok.Data;

import java.util.List;

@Data
public class OneTopicVo {
    private TopicStatus topicStatus;
    private List<UserStatus>userStatusList;
    private List<PinVo>pinVoListByLike;
    private List<PinVo>pinVoListByTime;

    public OneTopicVo() {
    }

    public OneTopicVo(TopicStatus topicStatus, List<UserStatus> userStatusList, List<PinVo> pinVoListByLike, List<PinVo> pinVoListByTime) {
        this.topicStatus = topicStatus;
        this.userStatusList = userStatusList;
        this.pinVoListByLike = pinVoListByLike;
        this.pinVoListByTime = pinVoListByTime;
    }
}
