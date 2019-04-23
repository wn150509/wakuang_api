package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.PinRepository;
import com.soft.wakuangapi.dao.TopicRepository;
import com.soft.wakuangapi.dao.TopicUserRepository;
import com.soft.wakuangapi.entity.Pins;
import com.soft.wakuangapi.entity.TopicStatus;
import com.soft.wakuangapi.entity.TopicUser;
import com.soft.wakuangapi.entity.Topics;
import com.soft.wakuangapi.service.TopicUserService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class TopicUserServiceImpl implements TopicUserService {
    @Resource
    private TopicUserRepository topicUserRepository;
    @Resource
    private TopicRepository topicRepository;
    @Resource
    private PinRepository pinRepository;
    @Override
    public List<TopicStatus> getTopicUser(Integer id) {
        return getTopicStatus(id);
    }

    @Override
    public ResponseUtil insertTopicUser(TopicUser topicUser) {
        return new ResponseUtil(0,"insert topicuser",topicUserRepository.save(topicUser));
    }

    @Override
    public ResponseUtil deleteTopicUser(TopicUser topicUser) {
        return new ResponseUtil(0,"delete topicuser",topicUserRepository.deleteTopicUserByUserIdAndTopicId(topicUser.getUserId(),topicUser.getTopicId()));
    }

    @Override
    public List<TopicStatus> getConcernedTopics(Integer userId) {
        List<TopicStatus>topicStatusList=new ArrayList<>();
        List<TopicUser>topicUserList=topicUserRepository.findAllByUserId(userId);
        List<TopicStatus>topicStatuses=getTopicStatus(userId);
        if (topicUserList.size()<=4){
            for (int i=0;i<topicUserList.size();i++){
                TopicStatus topicStatus=new TopicStatus();
                for (int j=0;j<topicStatuses.size();j++){
                    if (topicUserList.get(i).getTopicId()==topicStatuses.get(j).getTopicId()){
                        topicStatus=topicStatuses.get(j);
                    }
                }
                topicStatusList.add(topicStatus);
            }
        }else {
            for (int i=0;i<4;i++){
                TopicStatus topicStatus=new TopicStatus();
                for (int j=0;j<topicStatuses.size();j++){
                    if (topicUserList.get(i).getTopicId()==topicStatuses.get(j).getTopicId()){
                        topicStatus=topicStatuses.get(j);
                    }
                }
                topicStatusList.add(topicStatus);
            }
        }
        return topicStatusList;
    }

    public List<TopicStatus>getTopicStatus(Integer userId){
        List<Topics>topicsList=topicRepository.findAll();
        List<TopicUser>topicUserList=topicUserRepository.findAllByUserId(userId);
        List<TopicStatus>topicStatusList=new ArrayList<>();
        for (int i=0;i<topicsList.size();i++){
            Topics topics=topicsList.get(i);
            List<TopicUser>topicfanscount=topicUserRepository.findAllByTopicId(topicsList.get(i).getTopicId());
            List<Pins>topicpinscount=pinRepository.getAllByTopicId(topicsList.get(i).getTopicId());
            int status=0;
            for (int j=0;j<topicUserList.size();j++){
                if (topicUserList.get(j).getTopicId()==topicsList.get(i).getTopicId()){
                    status=1;
                }
            }
            TopicStatus topicStatus=new TopicStatus(topics.getTopicId(),topics.getTopicUrl(),topics.getTopicName(),topics.getTopicDescription(),
                    topicfanscount.size(),topicpinscount.size(),status);
            topicStatusList.add(topicStatus);
        }
        return topicStatusList;
    }
}
