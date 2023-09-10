package cdu.gu.onlinechat.mapper;

import cdu.gu.onlinechat.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface FriendMapper {


    /**
     * 朋友请求表
     * @param userid
     * @param friend_id
     * @return
     */
    int requestAddFriend(String userid, String friend_id, Date time);
    int isagreeFriend(String userid,String friend_id,byte status);
    int deleteRequest(String userid,String friend_id);
    List<User> getRequest(String userid,byte status);
    /**
     * 朋友表
     * @param userid
     * @param friend_id
     * @return
     */
    int addFriend(String userid,String friend_id);
    int deleteFriend(String userid,String friend_id);
    User getFriendByUserid(String userid);
}
