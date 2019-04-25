package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.LoginUser;
import com.soft.wakuangapi.entity.SearchUser;
import com.soft.wakuangapi.entity.SysUser;
import com.soft.wakuangapi.entity.UserStatus;
import com.soft.wakuangapi.utils.ResponseUtil;

import java.util.List;

public interface SysUserService {
    ResponseUtil userLogin(LoginUser loginUser);

    ResponseUtil register(SysUser sysUser);

    ResponseUtil findHotUsers();

    SysUser findUser(Integer id);

    SysUser getUser(String name,String base64);

    ResponseUtil updateUser(SysUser sysUser);

    List<UserStatus>querySysUserList(SearchUser searchUser);

    ResponseUtil getMessageCount(Integer articleId);
}
