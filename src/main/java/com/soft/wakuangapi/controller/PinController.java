package com.soft.wakuangapi.controller;

import com.soft.wakuangapi.entity.Pins;
import com.soft.wakuangapi.service.PinService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/pin")
@CrossOrigin("http://localhost:81")
public class PinController {
    @Resource
    private PinService pinService;

    @RequestMapping(value = "/releasePin",method = RequestMethod.POST)
    ResponseUtil releasePin(@RequestBody Pins pins){
        return pinService.releasePin(pins);
    }
}
