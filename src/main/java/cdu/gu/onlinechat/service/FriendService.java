package cdu.gu.onlinechat.service;

import cdu.gu.onlinechat.entity.User;

import java.util.List;

public interface FriendService {
    User getFriend(String userid);

    /**
     * 请求表相关
     * @param userid
     * @param friend_id
     * @return
     */
    Integer requestAddFriend(String userid,String friend_id);
    Integer isAgreeFriend(String userid,String friend_id,byte status);
    Integer deleteRequest(String userid,String friend_id);
    List<User> getRequest(String userid);

    /**
     * 好友表相关
     * @param userid
     * @param friend_id
     * @return
     */
    int addFriend(String userid,String friend_id);
    int deleteFriend(String userid,String friend_id);
    User getFriendByUserid(String userid);
}
