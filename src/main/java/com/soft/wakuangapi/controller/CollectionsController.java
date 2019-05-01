package com.soft.wakuangapi.controller;

import com.soft.wakuangapi.service.CollectionsService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/collections")
public class CollectionsController {
    @Resource
    private CollectionsService collectionsService;

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseUtil getAllCollections(){
        return new ResponseUtil(0,"get all collections",collectionsService.findallcollections());
    }

    @RequestMapping(value = "/type0",method = RequestMethod.GET)
    public ResponseUtil gettype0Collections(){
        return new ResponseUtil(0,"get type0 collections",collectionsService.findcollectionsbytypeid(0));
    }
    @RequestMapping(value = "/type1",method = RequestMethod.GET)
    public ResponseUtil gettype1Collections(){
        return new ResponseUtil(0,"get type1 collections",collectionsService.findcollectionsbytypeid(1));
    }
    @RequestMapping(value = "/type2",method = RequestMethod.GET)
    public ResponseUtil gettype2Collections(){
        return new ResponseUtil(0,"get type2 collections",collectionsService.findcollectionsbytypeid(2));
    }
    @RequestMapping(value = "/type3",method = RequestMethod.GET)
    public ResponseUtil gettype3Collections(){
        return new ResponseUtil(0,"get type3 collections",collectionsService.findcollectionsbytypeid(3));
    }
    @RequestMapping(value = "/type4",method = RequestMethod.GET)
    public ResponseUtil gettype4Collections(){
        return new ResponseUtil(0,"get type4 collections",collectionsService.findcollectionsbytypeid(4));
    }
    @RequestMapping(value = "/type5",method = RequestMethod.GET)
    public ResponseUtil gettype5Collections(){
        return new ResponseUtil(0,"get type5 collections",collectionsService.findcollectionsbytypeid(5));
    }
}
