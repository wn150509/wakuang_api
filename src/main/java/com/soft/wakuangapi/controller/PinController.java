package com.soft.wakuangapi.controller;

import com.soft.wakuangapi.entity.PinUser;
import com.soft.wakuangapi.entity.Pins;
import com.soft.wakuangapi.entity.UserTopicPin;
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

    @RequestMapping(value = "/insertPinUser",method = RequestMethod.POST)
    ResponseUtil insertPinUser(@RequestBody PinUser pinUser){
        return pinService.insertPinsConcerned(pinUser);
    }

    @RequestMapping(value = "/deletePinUser",method = RequestMethod.POST)
    ResponseUtil deletePinUser(@RequestBody PinUser pinUser){
        return pinService.deletePinsConcerned(pinUser);
    }

    @RequestMapping(value = "/getPinsByLike",method = RequestMethod.POST)
    ResponseUtil getPinsByLike(@RequestBody UserTopicPin userTopicPin){
        return pinService.getPinsByLike(userTopicPin);
    }

    @RequestMapping(value = "/getPinsByConcerned",method = RequestMethod.POST)
    ResponseUtil getPinsByConcerned(@RequestBody UserTopicPin userTopicPin){
        return pinService.getPinsByConcerned(userTopicPin);
    }

    @RequestMapping(value = "/getUserPins",method = RequestMethod.POST)
    ResponseUtil getUserPins(@RequestBody UserTopicPin userTopicPin){
        return pinService.getUserPins(userTopicPin);
    }

    @RequestMapping(value = "/getOtherUserPins",method = RequestMethod.POST)
    ResponseUtil getOtherUserPins(@RequestBody UserTopicPin userTopicPin){
        return pinService.getOtherUserPins(userTopicPin);
    }

    @RequestMapping(value = "/getUserLikePins",method = RequestMethod.POST)
    ResponseUtil getUserLikePins(@RequestBody UserTopicPin userTopicPin){
        return pinService.getUserLikePins(userTopicPin);
    }

    @RequestMapping(value = "/getOtherUserLikePins",method = RequestMethod.POST)
    ResponseUtil getOtherUserLikePins(@RequestBody UserTopicPin userTopicPin){
        return pinService.getOtherUserLikePins(userTopicPin);
    }
}
