package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.*;
import com.soft.wakuangapi.entity.*;
import com.soft.wakuangapi.service.TopicService;
import com.soft.wakuangapi.utils.ResponseUtil;
import com.soft.wakuangapi.vo.OneTopicVo;
import com.soft.wakuangapi.vo.PinVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Resource
    private TopicRepository topicRepository;
    @Resource
    private TopicUserRepository topicUserRepository;
    @Resource
    private PinRepository pinRepository;
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private UserConcernRepository userConcernRepository;
    @Resource
    private PinconcernRepository pinconcernRepository;
    @Override
    public ResponseUtil getAll() {
        return new ResponseUtil(0,"get all topics",topicRepository.findAll());
    }

    @Override
    public ResponseUtil getTopicIdbyName(String topicName) {
        return new ResponseUtil(0,"get topicid by name",topicRepository.findTopicsByTopicName(topicName));
    }

    @Override
    public ResponseUtil getOneTopic(UserTopicPin userTopicPin) {
        List<TopicStatus>topicStatusList=getTopicStatus(userTopicPin.getUserId());
        TopicStatus topicStatus=new TopicStatus();
        for (int i=0;i<topicStatusList.size();i++){
            if (topicStatusList.get(i).getTopicId().equals(userTopicPin.getTopicId())){
                topicStatus=topicStatusList.get(i);
            }
        }
        List<UserStatus>userStatusList=getSomeOneUserStatus(userTopicPin.getUserId());
        List<TopicUser>topicUserList=topicUserRepository.findAllByTopicId(userTopicPin.getTopicId());
        List<UserStatus>userStatuses=new ArrayList<>();
        if (topicUserList.size()>0){
            for (int i=0;i<userStatusList.size();i++){
                for (int j=0;j<topicUserList.size();j++){
                    if (userStatusList.get(i).getUserId().equals(topicUserList.get(j).getUserId())){
                        userStatuses.add(userStatusList.get(i));
                    }
                }
            }
        }else {
            userStatuses=null;
        }

        List<PinStatus>pinStatusList=getSomeOnePinStatus(userTopicPin.getUserId());
        List<PinVo>pinVoList=getPinVo(pinStatusList,userTopicPin);
        List<PinVo>pinVosByLike=new ArrayList<>();
        List<PinVo>pinVosByTime=new ArrayList<>();
        for (int i=0;i<pinVoList.size();i++){
            if (pinVoList.get(i).getTopics().getTopicId().equals(userTopicPin.getTopicId())){
                pinVosByLike.add(pinVoList.get(i));
                pinVosByTime.add(pinVoList.get(i));
            }
        }
        Collections.sort(pinVosByLike, new Comparator<PinVo>() {
            @Override
            public int compare(PinVo o1, PinVo o2) {
                return o2.getPinStatus().getLikeCount().compareTo(o1.getPinStatus().getLikeCount());
            }
        });
        Collections.sort(pinVosByTime, new Comparator<PinVo>() {
            @Override
            public int compare(PinVo o1, PinVo o2) {
                return o2.getPinStatus().getCreateTime().compareTo(o1.getPinStatus().getCreateTime());
            }
        });
        OneTopicVo oneTopicVo=new OneTopicVo(topicStatus,userStatuses,pinVosByLike,pinVosByTime);
        return new ResponseUtil(0,"get one topic",oneTopicVo);
    }
    //遍历所有topic
    public List<TopicStatus> getTopicStatus(Integer userId){
        List<Topics>topicsList=topicRepository.findAll();
        List<TopicUser>topicUserList=topicUserRepository.findAllByUserId(userId);
        List<TopicStatus>topicStatusList=new ArrayList<>();
        for (int i=0;i<topicsList.size();i++){
            Topics topics=topicsList.get(i);
            List<TopicUser>topicfanscount=topicUserRepository.findAllByTopicId(topicsList.get(i).getTopicId());
            List<Pins>topicpinscount=pinRepository.getAllByTopicId(topicsList.get(i).getTopicId());
            int status=0;
            for (int j=0;j<topicUserList.size();j++){
                if (topicUserList.get(j).getTopicId().equals(topicsList.get(i).getTopicId())){
                    status=1;
                }
            }
            TopicStatus topicStatus=new TopicStatus(topics.getTopicId(),topics.getTopicUrl(),topics.getTopicName(),topics.getTopicDescription(),
                    topicfanscount.size(),topicpinscount.size(),status);
            topicStatusList.add(topicStatus);
        }
        return topicStatusList;
    }
    //遍历所有用户
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

    //以登陆者角度过滤所有沸点
    public List<PinStatus>getSomeOnePinStatus(Integer userId){
        List<Pins>pinsList=pinRepository.findAll();//遍历所有pin
        List<PinStatus>pinStatusList=new ArrayList<>();
        List<PinUser>pinUserList=pinconcernRepository.findPinUsersByUserId(userId);
        for (int i=0;i<pinsList.size();i++){
            int status=0;
            Pins pins=pinsList.get(i);
            List<PinUser>pinUsers=pinconcernRepository.findPinUsersByPinId(pins.getPinId());
            for (int j=0;j<pinUserList.size();j++){
                if (pins.getPinId().equals(pinUserList.get(j).getPinId())){
                    status=1;
                }
            }
            PinStatus pinStatus=new PinStatus(pins.getPinId(),pins.getPinContent(),pins.getPinUrl(),pins.getCommentCount(),
                    pinUsers.size(),pins.getUsersId(),pins.getCreateTime(),pins.getTopicId(),status);
            pinStatusList.add(pinStatus);
        }
        return pinStatusList;
    }
    //获取某个用户状态
    public UserStatus getUserStatus(Integer userId,Integer concernUserId){
        //以下根据当前用户id得到一组用户list的完整信息
        List<SysUser>userList=sysUserRepository.findAll();//获取表中所有用户信息
        List<UserUser>userUsers=userConcernRepository.findAllByUserId(userId);//根据userId获取被关注用户ID组
        List<UserStatus>userStatuses0=new ArrayList<>();
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
            userStatuses0.add(userStatus);
        }
        UserStatus userStatus=new UserStatus();
        for (int i=0;i<userStatuses0.size();i++){
            if (userStatuses0.get(i).getUserId()==concernUserId){
                userStatus=userStatuses0.get(i);
            }
        }
        return userStatus;
    }
    //遍历所有pin
    //遍历数组获取PinVo
    public List<PinVo>getPinVo(List<PinStatus>pinStatusList, UserTopicPin userTopicPin){
        List<PinVo>pinVoList=new ArrayList<>();
        for (int i=0 ;i<pinStatusList.size();i++){
            PinVo pinVo=new PinVo();
            UserStatus userStatus=getUserStatus(userTopicPin.getUserId(),pinStatusList.get(i).getUsersId());
            Topics topics=topicRepository.findTopicsByTopicId(pinStatusList.get(i).getTopicId());
            pinVo.setUserStatus(userStatus);
            pinVo.setPinStatus(pinStatusList.get(i));
            pinVo.setTopics(topics);
            //注入到list中
            pinVoList.add(pinVo);
        }
        return pinVoList;
    }
}
