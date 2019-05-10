package com.soft.wakuangapi.controller;

import com.soft.wakuangapi.service.ManageService;
import com.soft.wakuangapi.utils.ResponseUtil;
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
}
