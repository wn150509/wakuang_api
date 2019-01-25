package com.soft.wakuangapi.controller;

import com.soft.wakuangapi.entity.SysUser;
import com.soft.wakuangapi.entity.TopicUser;
import com.soft.wakuangapi.service.TopicService;
import com.soft.wakuangapi.service.TopicUserService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@RestController
@RequestMapping(value = "/topics")
@CrossOrigin("http://localhost:81")
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
}
