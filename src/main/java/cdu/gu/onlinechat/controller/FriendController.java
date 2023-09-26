package cdu.gu.onlinechat.controller;

import cdu.gu.onlinechat.entity.Friend;
import cdu.gu.onlinechat.entity.User;
import cdu.gu.onlinechat.service.FriendService;
import cdu.gu.onlinechat.utils.RUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;

   //朋友请求表

    /**
     * 添加好友发起申请
     * @param userid
     * @param friend_id
     * @return
     */
    @PostMapping("/addFriend")
    public RUtils addFriend(@RequestParam("userid") String userid,@RequestParam("friend_id")String friend_id){
        if (friendService.addFriend(userid,friend_id) == 1)
            return RUtils.ok();
        else return RUtils.error().put("msg","已经是好友了");
    }

    /**
     * 处理申请 status为1表示拒绝申请，此时service层直接删除对应request记录
     *        status为2表示同意申请，数据库写了触发器会将该信息添加到好友表
     * @param friend
     * @return
     */
    @PostMapping("/isAgreeRequest")
    public RUtils isAgressRequest(@RequestBody Friend friend){
        if (friendService.isAgreeFriend(friend.getUserid(),friend.getFriend_id(),friend.getStatus()) == 1)
            return RUtils.ok("添加成功");
        else return RUtils.ok("删除成功");

    }

    /**
     * 获取申请 （获取status为0的申请）
     * @param userid
     * @return
     */
    @PostMapping("/getRequest")
    public RUtils getRequest(@RequestParam("userid") String userid){
        List<User> userList = friendService.getRequest(userid);
        if (!userList.isEmpty())return RUtils.ok().put("requestList",userList);
        else return RUtils.ok("没有好友请求");
    }
    //用户朋友表

    /**
     * 获取朋友
     * @param userid
     * @return
     */
    @GetMapping("/getFriend")
    public RUtils getFriend(@RequestParam("userid") String userid){
        User user = friendService.getFriend(userid);
        if(user != null)
            return RUtils.ok().put("user",user);
        else return RUtils.error("用户不存在");
    }
    /**
     * 删除朋友
     * @param userid
     * @param friend_id
     * @return
     */
    @PostMapping("deleteFriend")
    public RUtils deleteFriend(@RequestParam("userid") String userid,@RequestParam("friend_id")String friend_id){
        if (friendService.deleteFriend(userid,friend_id) == 1)
            return RUtils.ok();
        else return RUtils.error();
    }



}
