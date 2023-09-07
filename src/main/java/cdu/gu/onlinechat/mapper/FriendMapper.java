package cdu.gu.onlinechat.mapper;

import cdu.gu.onlinechat.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FriendMapper {
    User getFriendByUserid(String userid);


}
