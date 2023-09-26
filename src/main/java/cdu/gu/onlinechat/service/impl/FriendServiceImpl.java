package cdu.gu.onlinechat.service.impl;

import cdu.gu.onlinechat.entity.User;
import cdu.gu.onlinechat.mapper.FriendMapper;
import cdu.gu.onlinechat.mapper.UserMapper;
import cdu.gu.onlinechat.service.FriendService;
import cdu.gu.onlinechat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendMapper friendMapper;
    @Autowired
    private UserService userService;
    @Override
    public User getFriend(String userid) {
        return friendMapper.getFriendByUserid(userid);
    }

    @Override
    public Integer addFriend(String userid, String friend_id) {
        Date date = new Date();
        return friendMapper.requestAddFriend(userid,friend_id,date);
    }

    @Override
    public Integer isAgreeFriend(String userid, String friend_id, byte status) {
       if (friendMapper.isagreeFriend(userid,friend_id,status)!=0){
           if (status == 1) {
               friendMapper.deleteRequest(userid,friend_id);
           }
           return 1;
       }
       return 0;
    }

    @Override
    public Integer deleteRequest(String userid, String friend_id) {
        return friendMapper.deleteRequest(userid,friend_id);
    }

    @Override
    public List<User> getRequest(String userid) {
        List<String> requestList = friendMapper.getRequest(userid);
        List<User> userList = new ArrayList<>();
        for(String id: requestList){
           userList.add(userService.findUserByUserId(id));
        }
        return userList;
    }

    @Override
    public Integer deleteFriend(String userid, String friend_id) {
        return friendMapper.deleteFriend(userid,friend_id);
    }

    @Override
    public User getFriendByUserid(String userid) {
        return friendMapper.getFriendByUserid(userid);
    }
}
