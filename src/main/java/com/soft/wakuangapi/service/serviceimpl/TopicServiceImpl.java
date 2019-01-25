package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.TopicRepository;
import com.soft.wakuangapi.service.TopicService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TopicServiceImpl implements TopicService {
    @Resource
    private TopicRepository topicRepository;
    @Override
    public ResponseUtil getAll() {
        return new ResponseUtil(0,"get all topics",topicRepository.findAll());
    }
}
