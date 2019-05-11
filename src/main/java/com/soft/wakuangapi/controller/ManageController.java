package com.soft.wakuangapi.controller;

import com.soft.wakuangapi.entity.Labels;
import com.soft.wakuangapi.entity.Topics;
import com.soft.wakuangapi.service.ManageService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/manage")
public class ManageController {
    @Resource
    private ManageService manageService;

    @RequestMapping(value = "/homeCount",method = RequestMethod.GET)
    ResponseUtil getManageCount(){
        return manageService.getManageCount();
    }

    @RequestMapping(value = "/topicPin",method = RequestMethod.GET)
    ResponseUtil getTopicPinCount(){
        return manageService.getTopicPinCount();
    }

    @RequestMapping(value = "/labelArticle",method = RequestMethod.GET)
    ResponseUtil getLabelArticleCount(){
        return manageService.getLabelArticleCount();
    }

    @RequestMapping(value = "/getAllTopic",method = RequestMethod.GET)
    ResponseUtil getAllTopic(){
        return manageService.getAllTopic();
    }

    @RequestMapping(value = "/getAllLabel",method = RequestMethod.GET)
    ResponseUtil getAllLabel(){
        return manageService.getAllLabel();
    }

    @RequestMapping(value = "/changeTopic",method = RequestMethod.POST)
    ResponseUtil changeTopic(@RequestBody Topics topics){
        return manageService.changeTopic(topics);
    }

    @RequestMapping(value = "/changeLabel",method = RequestMethod.POST)
    ResponseUtil changeLabel(@RequestBody Labels labels){
        return manageService.changeLabel(labels);
    }

    @RequestMapping(value = "/addTopic",method = RequestMethod.POST)
    ResponseUtil addTopic(@RequestBody Topics topics){
        return manageService.addTopic(topics);
    }

    @RequestMapping(value = "/addLabel",method = RequestMethod.POST)
    ResponseUtil addLabel(@RequestBody Labels labels){
        return manageService.addLabel(labels);
    }

    @RequestMapping(value = "/deleteTopic",method = RequestMethod.POST)
    ResponseUtil deleteTopic(@RequestBody Topics topics){
        return manageService.deleteTopic(topics);
    }

    @RequestMapping(value = "/deleteLabel",method = RequestMethod.POST)
    ResponseUtil deleteLabel(@RequestBody Labels labels){
        return manageService.deleteLabel(labels);
    }
}
