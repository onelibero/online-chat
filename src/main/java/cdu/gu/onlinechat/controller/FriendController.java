package cdu.gu.onlinechat.controller;

import cdu.gu.onlinechat.service.FriendService;
import cdu.gu.onlinechat.utils.RUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @GetMapping("/getfriend")
    public RUtils getFriend(@RequestParam("userid") String userid){
        return RUtils.ok().put("user",friendService.getFriend(userid));
    }

    @PostMapping
    public RUtils addFriend(@RequestParam("userid") String userid,@RequestParam("friend_id")String friend_id){

    }
}
