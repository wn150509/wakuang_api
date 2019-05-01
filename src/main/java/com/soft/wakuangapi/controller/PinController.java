package com.soft.wakuangapi.controller;

import com.soft.wakuangapi.entity.*;
import com.soft.wakuangapi.service.PinCommentService;
import com.soft.wakuangapi.service.PinService;
import com.soft.wakuangapi.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/pin")
public class PinController {
    @Resource
    private PinService pinService;
    @Resource
    private PinCommentService pinCommentService;

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

    @RequestMapping(value = "/getPinComments",method = RequestMethod.POST)
    ResponseUtil getPinComments(@RequestBody PinUser pinUser){
        return pinCommentService.getPinComments(pinUser);
    }

    @RequestMapping(value = "/addPinComment",method = RequestMethod.POST)
    ResponseUtil addPinComment(@RequestBody PinComment pinComment){
        return pinCommentService.addPinComment(pinComment);
    }

    @RequestMapping(value = "/deletePinComment",method = RequestMethod.POST)
    ResponseUtil deletePinComment(@RequestBody PinComment pinComment){
        return pinCommentService.deltePinComment(pinComment);
    }

    @RequestMapping(value = "/insertPinCommentLike",method = RequestMethod.POST)
    ResponseUtil insertPinCommentLike(@RequestBody PinCommentLike pinCommentLike){
        return pinCommentService.insertPinCommentLike(pinCommentLike);
    }

    @RequestMapping(value = "/deletePinCommentLike",method = RequestMethod.POST)
    ResponseUtil deletePinCommentLike(@RequestBody PinCommentLike pinCommentLike){
        return pinCommentService.deltePinCommentLike(pinCommentLike);
    }

}
