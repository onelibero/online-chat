package cdu.gu.onlinechat.service.impl;

import cdu.gu.onlinechat.entity.User;
import cdu.gu.onlinechat.mapper.FriendMapper;
import cdu.gu.onlinechat.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendMapper friendMapper;
    @Override
    public User getFriend(String userid) {
        return friendMapper.getFriendByUserid(userid);
    }

    @Override
    public Integer requestAddFriend(String userid, String friend_id) {
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
        return null;
    }

    @Override
    public int addFriend(String userid, String friend_id) {
        return 0;
    }

    @Override
    public int deleteFriend(String userid, String friend_id) {
        return 0;
    }

    @Override
    public User getFriendByUserid(String userid) {
        return null;
    }
}
