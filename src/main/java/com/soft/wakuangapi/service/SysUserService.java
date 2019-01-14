package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.LoginUser;
import com.soft.wakuangapi.entity.SysUser;
import com.soft.wakuangapi.utils.ResponseUtil;

public interface SysUserService {
    ResponseUtil userLogin(LoginUser loginUser);

    ResponseUtil register(SysUser sysUser);

    ResponseUtil findHotUsers();

    SysUser findUser(Integer id);

    ResponseUtil updateUser(SysUser sysUser);
}
