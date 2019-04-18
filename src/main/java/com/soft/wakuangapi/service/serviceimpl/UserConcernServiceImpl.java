package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.SysUserRepository;
import com.soft.wakuangapi.dao.UserConcernRepository;
import com.soft.wakuangapi.entity.SysUser;
import com.soft.wakuangapi.entity.UserStatus;
import com.soft.wakuangapi.entity.UserUser;
import com.soft.wakuangapi.service.UserConcernService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserConcernServiceImpl implements UserConcernService{
    @Resource
    private UserConcernRepository userConcernRepository;
    @Resource
    private SysUserRepository sysUserRepository;

    @Override
    public UserStatus getUserConcern(UserUser userUser) {
        List<SysUser>userList=sysUserRepository.findAll();//获取表中所有用户信息
        List<UserUser>userUsers=userConcernRepository.findAllByUserId(userUser.getUserId());//根据userId获取被关注用户ID组
        List<UserStatus>userStatuses=new ArrayList<>();
        UserStatus userStatus1=new UserStatus();
        for (int i=0;i<userList.size();i++){//遍历所有用户
            SysUser sysUser=userList.get(i);
            int status=0;
            for (int j=0;j<userUsers.size();j++){
                if (userUsers.get(j).getConcerneduserId()==userList.get(i).getUserId()){
                    status=1;
                }
            }
            UserStatus userStatus=new UserStatus(sysUser.getUserId(),
                    sysUser.getUserAvatar(),sysUser.getUserName(),
                    sysUser.getDescription(),sysUser.getUserCompany(),sysUser.getUserPosition(),status);
            userStatuses.add(userStatus);
        }
        for (int i=0;i<userStatuses.size();i++){
            if (userStatuses.get(i).getUserId()==userUser.getConcerneduserId()){
                userStatus1=userStatuses.get(i);
            }
        }
        userStatuses.forEach(userStatus -> System.out.println(userStatus.getUserId()));
        System.out.println("*****************");
        System.out.println(userUser.getConcerneduserId());
        return userStatus1;
    }

    @Override
    public ResponseUtil insertUserConcern(UserUser userUser) {
        return new ResponseUtil(0,"insert userConcern",userConcernRepository.save(userUser));
    }

    @Override
    public ResponseUtil deleteUserConcern(UserUser userUser) {
        return new ResponseUtil(0,"delete userConcern",userConcernRepository.deleteUserUserByUserIdAndConcerneduserId(userUser.getUserId(),userUser.getConcerneduserId()));
    }

    @Override
    public List<SysUser> getConcernUser(Integer id) {
        List<UserUser>userUsers=userConcernRepository.findAllByConcerneduserId(id);
        List<SysUser>sysUsers=sysUserRepository.findAll();
        List<SysUser>sysUserList=new ArrayList<>();
        for (int i=0;i<sysUsers.size();i++){
            for (int j=0;j<userUsers.size();j++){
                if (userUsers.get(j).getUserId()==sysUsers.get(i).getUserId()){
                    sysUserList.add(sysUsers.get(i));
                }
            }
        }
        return sysUserList;
    }
}
