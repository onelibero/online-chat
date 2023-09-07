package cdu.gu.onlinechat.mapper;

import cdu.gu.onlinechat.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface UserMapper {
    Integer findUserByName(String username);
    int register(String userid, String name, String username, String password, String avatar, Date create_time,String salt);

    User findUserById(int id);

    User login(String name);
}
