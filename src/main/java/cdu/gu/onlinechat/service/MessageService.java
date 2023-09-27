package cdu.gu.onlinechat.service;

import java.util.Date;

public interface MessageService {
    //群聊表
    Integer AddMessage(String name,String message);
    Integer deleteMessage(String name, Date date);
    //私聊表
    Integer PreAddMessage(String fromid,String toid,String message);
    Integer PreDelMessage(String fromid,String toid,Date date);
    Integer PreClearMessage(String fromid,String toid);
}
