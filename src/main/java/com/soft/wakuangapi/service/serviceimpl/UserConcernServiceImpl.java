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
    public UserStatus getUser(UserUser userUser) {
        List<UserStatus>userStatuses=getSomeOneUserStatus(userUser.getUserId());
        UserStatus userStatus1=new UserStatus();
        for (int i=0;i<userStatuses.size();i++){
            if (userStatuses.get(i).getUserId().equals(userUser.getConcerneduserId())){
                userStatus1=userStatuses.get(i);
            }
        }
        return userStatus1;
    }

    @Override
    public ResponseUtil getUserConcerner(UserUser userUser) {
        List<UserStatus>userStatusList=getSomeOneUserStatus(userUser.getUserId());

        return new ResponseUtil(0,"get user concerner",userStatusList);
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
                if (userUsers.get(j).getUserId().equals(sysUsers.get(i).getUserId())){
                    sysUserList.add(sysUsers.get(i));
                }
            }
        }
        return sysUserList;
    }

    @Override
    public ResponseUtil getOtherConcern(UserUser userUser) {
        List<UserStatus>userStatusList=getSomeOneUserStatus(userUser.getUserId());
        List<UserUser>userUserList=userConcernRepository.findAllByUserId(userUser.getConcerneduserId());
        List<UserStatus>userStatuses=new ArrayList<>();
        if (userUserList.size()>0){
            for (int i=0;i<userStatusList.size();i++){
                for (int j=0;j<userUserList.size();j++){
                    if (userStatusList.get(i).getUserId().equals(userUserList.get(j).getConcerneduserId())){
                        userStatuses.add(userStatusList.get(i));
                    }
                }
            }
        }else {
            userStatuses=null;
        }

        return new ResponseUtil(0,"get Other Concern",userStatuses);
    }

    @Override
    public ResponseUtil getOtherConcerner(UserUser userUser) {
        List<UserStatus>userStatusList=getSomeOneUserStatus(userUser.getUserId());
        List<UserUser>userUserList=userConcernRepository.findAllByConcerneduserId(userUser.getConcerneduserId());
        List<UserStatus>userStatuses=new ArrayList<>();
        if (userUserList.size()>0){
            for (int i=0;i<userStatusList.size();i++){
                for (int j=0;j<userUserList.size();j++){
                    if (userStatusList.get(i).getUserId().equals(userUserList.get(j).getUserId())){
                        userStatuses.add(userStatusList.get(i));
                    }
                }
            }
        }else {
            userStatuses=null;
        }
        return new ResponseUtil(0,"get Other Concerner",userStatuses);
    }

    public List<UserStatus>getSomeOneUserStatus(Integer userId){
        List<SysUser>userList=sysUserRepository.findAll();//获取表中所有用户信息
        List<UserUser>userUsers=userConcernRepository.findAllByUserId(userId);//根据userId获取被关注用户ID组
        List<UserStatus>userStatuses=new ArrayList<>();

        for (int i=0;i<userList.size();i++){//遍历所有用户
            SysUser sysUser=userList.get(i);
            int status=0;
            for (int j=0;j<userUsers.size();j++){
                if (userUsers.get(j).getConcerneduserId().equals(userList.get(i).getUserId())){
                    status=1;
                }
            }
            UserStatus userStatus=new UserStatus(sysUser.getUserId(),
                    sysUser.getUserAvatar(),sysUser.getUserName(),
                    sysUser.getDescription(),sysUser.getUserCompany(),sysUser.getUserPosition(),status);
            userStatuses.add(userStatus);
        }
        return userStatuses;
    }
}
