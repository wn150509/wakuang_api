package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.SysUser;
import com.soft.wakuangapi.entity.UserStatus;
import com.soft.wakuangapi.entity.UserUser;
import com.soft.wakuangapi.utils.ResponseUtil;

import java.util.List;

public interface UserConcernService {
    UserStatus getUserConcern(UserUser userUser);

    ResponseUtil insertUserConcern(UserUser userUser);

    ResponseUtil deleteUserConcern(UserUser userUser);

    List<SysUser>getConcernUser(Integer id);

    ResponseUtil getOtherConcern(UserUser userUser);

    ResponseUtil getOtherConcerner(UserUser userUser);
}
