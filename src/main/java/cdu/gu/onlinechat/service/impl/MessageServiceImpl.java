package cdu.gu.onlinechat.service.impl;

import cdu.gu.onlinechat.mapper.MessageMapper;
import cdu.gu.onlinechat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;


    @Override
    public Integer AddMessage(String name, String message) {
        Date date = new Date();
        return messageMapper.addMessagequn(name,message,date);
    }

    @Override
    public Integer deleteMessage(String name, Date date) {
        return messageMapper.deleteMessagequn(name,date);
    }

    @Override
    public Integer PreAddMessage(String fromid, String toid, String message) {
        return messageMapper.addMessage(fromid,toid,message,new Date());
    }

    @Override
    public Integer PreDelMessage(String fromid, String toid, Date date) {
        return messageMapper.deleteMessage(fromid,toid,date);
    }

    @Override
    public Integer PreClearMessage(String fromid, String toid) {
        return messageMapper.clearMessage(fromid,toid);
    }
}
