package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.*;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.apache.catalina.User;

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

    ResponseUtil deleteUserAccount(Integer userId);

    ResponseUtil checkMessageCode(LoginUser loginUser);

    ResponseUtil getUserRightMessageCount(UserUser userUser);

    ResponseUtil changePassword(ChangePassword changePassword);
}
