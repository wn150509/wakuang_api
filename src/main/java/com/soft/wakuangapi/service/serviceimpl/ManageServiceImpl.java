package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.*;
import com.soft.wakuangapi.entity.*;
import com.soft.wakuangapi.service.ManageService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManageServiceImpl implements ManageService {
    @Resource
    SysUserRepository sysUserRepository;
    @Resource
    PinRepository pinRepository;
    @Resource
    ArticlesRepository articlesRepository;
    @Resource
    LabelsRepository labelsRepository;
    @Resource
    TopicRepository topicRepository;

    @Override
    public ResponseUtil getManageCount() {
        List<Articles>articlesList=articlesRepository.findAll();
        List<Pins>pinsList=pinRepository.findAll();
        List<SysUser>sysUserList=sysUserRepository.findAll();
        List<SysUser>sysUsers=new ArrayList<>();
        for (int i=0;i<sysUserList.size();i++){
            if (sysUserList.get(i).getUserId()!=0){
                sysUsers.add(sysUserList.get(i));
            }
        }
        ManageCount manageCount=new ManageCount(sysUsers.size(),pinsList.size(),articlesList.size());
        return new ResponseUtil(0,"get manage count",manageCount);
    }

    @Override
    public ResponseUtil getTopicPinCount() {
        List<TopicPinCount>topicPinCounts=new ArrayList<>();
        List<Topics>topicsList=topicRepository.findAll();
        for (int i=0;i<topicsList.size();i++){
            List<Pins>pinsList=pinRepository.getAllByTopicId(topicsList.get(i).getTopicId());
            TopicPinCount topicPinCount=new TopicPinCount(topicsList.get(i).getTopicName(),pinsList.size());
            topicPinCounts.add(topicPinCount);
        }
        return new ResponseUtil(0,"get topic pin count",topicPinCounts);
    }

    @Override
    public ResponseUtil getLabelArticleCount() {
        List<LabelArticleCount>labelArticleCounts=new ArrayList<>();
        List<Labels>labelsList=labelsRepository.findAll();
        for (int i=0;i<labelsList.size();i++){
            List<Articles>articles=articlesRepository.findAllByLabelId(labelsList.get(i).getLabelsId());
            LabelArticleCount labelArticleCount=new LabelArticleCount(labelsList.get(i).getLabelsName(),articles.size());
            labelArticleCounts.add(labelArticleCount);
        }
        return new ResponseUtil(0,"get topic pin count",labelArticleCounts);
    }
}
