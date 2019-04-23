package com.soft.wakuangapi.vo;

import com.soft.wakuangapi.entity.PinStatus;
import com.soft.wakuangapi.entity.Topics;
import com.soft.wakuangapi.entity.UserStatus;
import lombok.Data;

@Data
public class PinVo {
    private UserStatus userStatus;
    private PinStatus pinStatus;
    private Topics topics;

    public PinVo(UserStatus userStatus, PinStatus pinStatus, Topics topics) {
        this.userStatus = userStatus;
        this.pinStatus = pinStatus;
        this.topics = topics;
    }

    public PinVo() {
    }
}
