package com.soft.wakuangapi.controller;

import com.soft.wakuangapi.entity.SysUser;
import com.soft.wakuangapi.entity.TopicUser;
import com.soft.wakuangapi.entity.Topics;
import com.soft.wakuangapi.entity.UserTopicPin;
import com.soft.wakuangapi.service.TopicService;
import com.soft.wakuangapi.service.TopicUserService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@RestController
@RequestMapping(value = "/topics")
public class TopicController {
    @Resource
    private TopicService topicService;
    @Resource
    private TopicUserService topicUserService;

    @RequestMapping(value = "/topicoption",method = RequestMethod.GET)
    ResponseUtil gettopicOption(){
        return new ResponseUtil(0,"all topics option",topicService.getAll());
    }

    @RequestMapping(value = "/all",method = RequestMethod.POST)
    ResponseUtil getAllTopics(@RequestBody SysUser sysUser){
        return new ResponseUtil(0,"all topics",topicUserService.getTopicUser(sysUser.getUserId()));
    }
    @RequestMapping(value = "/inserttopic",method = RequestMethod.POST)
    ResponseUtil insertTopic(@RequestBody TopicUser topicUser){
        return new ResponseUtil(0,"insert topic",topicUserService.insertTopicUser(topicUser));
    }
    @RequestMapping(value = "/deletetopic",method = RequestMethod.POST)
    ResponseUtil deleteTopic(@RequestBody TopicUser topicUser){
        return new ResponseUtil(0,"delete topic",topicUserService.deleteTopicUser(topicUser));
    }
    @RequestMapping(value = "/concerned",method = RequestMethod.POST)
    ResponseUtil getAllConcernedTopics(@RequestBody SysUser sysUser){
        return new ResponseUtil(0,"all concerned topics",topicUserService.getConcernedTopics(sysUser.getUserId()));
    }
    @RequestMapping(value = "/topicname",method = RequestMethod.POST)
    ResponseUtil getOneTopicByTopicName(@RequestBody Topics topics){
        return topicService.getTopicIdbyName(topics.getTopicName());
    }

    @RequestMapping(value = "/oneTopic",method = RequestMethod.POST)
    ResponseUtil getOneTopic(@RequestBody UserTopicPin userTopicPin){
        return topicService.getOneTopic(userTopicPin);
    }
}
