package com.soft.wakuangapi.vo;

import com.soft.wakuangapi.entity.Pins;
import com.soft.wakuangapi.entity.SysUser;
import com.soft.wakuangapi.entity.TopicStatus;
import lombok.Data;

import java.util.List;

@Data
public class TopicVo {
    private TopicStatus topicStatus;
    private List<Pins> pinsList;
    private List<SysUser> sysUserList;

    public TopicVo() {
    }

    public TopicVo(TopicStatus topicStatus, List<Pins> pinsList, List<SysUser> sysUserList) {
        this.topicStatus = topicStatus;
        this.pinsList = pinsList;
        this.sysUserList = sysUserList;
    }
}
