package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.SysUserRepository;
import com.soft.wakuangapi.entity.LoginUser;
import com.soft.wakuangapi.entity.SysUser;
import com.soft.wakuangapi.service.SysUserService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserRepository sysUserRepository;
    @Override
    public ResponseUtil userLogin(LoginUser loginUser) {
        SysUser sysUser = sysUserRepository.findSysUserByEmail(loginUser.getEmail());
        //邮箱有效
        if (sysUser != null) {
            //判断密码是否正确
            if (loginUser.getPassword().equals(sysUser.getPassword())) {
                return new ResponseUtil(0, "login success", sysUser);
            } else {
                return new ResponseUtil(0, "password wrong");
            }
        } else {
            return new ResponseUtil(0, "account wrong");
        }
    }

    @Override
    public ResponseUtil register(SysUser sysUser) {
        SysUser sysUsers=new SysUser();
        sysUsers.setUserName(sysUser.getUserName());
        sysUsers.setUserAvatar("https://ws1.sinaimg.cn/large/006vOzdBgy1fxdmbl6aakj301s01sa9u.jpg");
        sysUsers.setEmail(sysUser.getEmail());
        sysUsers.setPassword(sysUser.getPassword());
        if (sysUserRepository.findSysUserByEmail(sysUser.getEmail())!=null){
            return new ResponseUtil(1,"you haved account",sysUser);
        }else {
            sysUserRepository.save(sysUsers);
            return new ResponseUtil(0,"register success");
        }
    }

    @Override
    public ResponseUtil findHotUsers() {
        return new ResponseUtil(0,"get Hotusers",sysUserRepository.findHotUsers());
    }

    @Override
    public SysUser findUser(Integer id) {
        return sysUserRepository.findSysUserByUserId(id);
    }

    @Override
    public ResponseUtil updateUser(SysUser sysUser) {
        SysUser user=sysUserRepository.findSysUserByUserId(sysUser.getUserId());
        if(user.getUserName()==sysUser.getUserName()){
            user.setUserName(user.getUserName());
        }else {
            user.setUserName(sysUser.getUserName());
        }
        if (user.getUserPosition()==sysUser.getUserPosition()){
            user.setUserPosition(user.getUserPosition());
        }else {
            user.setUserPosition(sysUser.getUserPosition());
        }
        if (user.getUserCompany()==sysUser.getUserCompany()){
            user.setUserCompany(user.getUserCompany());
        }else {
            user.setUserCompany(sysUser.getUserCompany());
        }
        if (user.getDescription()==sysUser.getDescription()){
            user.setDescription(user.getDescription());
        }else {
            user.setDescription(sysUser.getDescription());
        }
        return new ResponseUtil(0,"update user",sysUserRepository.saveAndFlush(user));
    }
}
