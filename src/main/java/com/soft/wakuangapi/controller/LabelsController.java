package com.soft.wakuangapi.controller;

import com.soft.wakuangapi.entity.*;
import com.soft.wakuangapi.service.ConcernService;
import com.soft.wakuangapi.service.LabelsService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/labels")
public class LabelsController {
    @Resource
    private LabelsService labelsService;
    @Resource
    private ConcernService concernService;

    @RequestMapping(value = "/concern",method = RequestMethod.POST)
    public ResponseUtil getLabelStatus(@RequestBody SysUser loginUser){
        return new ResponseUtil(0,"get concern labels",concernService.getConcernLabels(loginUser.getUserId()));
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseUtil getAllLabel(){
        return new ResponseUtil(0,"get concern labels",labelsService.findAllLabels());
    }

    @RequestMapping(value = "/insertlabel",method = RequestMethod.POST)
    public ResponseUtil insetLabelConcern(@RequestBody ConcernUser concernUser){
        return new ResponseUtil(0,"insert label",concernService.insertConcern(concernUser));
    }

    @RequestMapping(value = "/deletelabel",method = RequestMethod.POST)
    public ResponseUtil deleteLabelConcern(@RequestBody ConcernUser concernUser){
        return new ResponseUtil(0,"delete label",concernService.deleteConcern(concernUser));
    }

    @RequestMapping(value = "/findonelabel",method = RequestMethod.POST)
    public ResponseUtil lookonelabel(@RequestBody ConcernUser concernUser){
        return new ResponseUtil(0,"find one label",labelsService.getonelabel(concernUser.getLabelId(),concernUser.getUserId()));
    }

    @RequestMapping(value = "/onelabel",method = RequestMethod.POST)
    public ResponseUtil getLabel(@RequestBody Labels labels){
        return new ResponseUtil(0,"get concern labels",labelsService.getoneLabel(labels.getLabelsName()));
    }

    @RequestMapping(value = "/querylabels",method = RequestMethod.POST)
    public ResponseUtil queryLabels(@RequestBody SearchLabels searchLabels){
        return new ResponseUtil(0,"query labels",labelsService.queryLabels(searchLabels));
    }

    @RequestMapping(value = "/otherlabelstatus",method = RequestMethod.POST)
    public ResponseUtil getOtherLabelStatus(@RequestBody UserUser userUser){
        return concernService.getOtherConcern(userUser);
    }
}
