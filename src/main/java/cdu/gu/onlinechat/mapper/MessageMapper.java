package cdu.gu.onlinechat.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface MessageMapper {
    int addMessagequn(String name, String message, Date date);

    int deleteMessagequn(String name,String message);
    int addMessage(String fromid,String toid,Date date);
    int deleteMessage(String fromid,String toid);
}
