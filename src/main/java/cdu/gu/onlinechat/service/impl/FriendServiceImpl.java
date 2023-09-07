package cdu.gu.onlinechat.service.impl;

import cdu.gu.onlinechat.entity.User;
import cdu.gu.onlinechat.mapper.FriendMapper;
import cdu.gu.onlinechat.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendMapper friendMapper;
    @Override
    public User getFriend(String userid) {
        return friendMapper.getFriendByUserid(userid);
    }
}
