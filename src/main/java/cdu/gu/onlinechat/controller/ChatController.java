package cdu.gu.onlinechat.controller;

import cdu.gu.onlinechat.entity.Message;
import cdu.gu.onlinechat.entity.User;
import cdu.gu.onlinechat.model.ParticipantRepository;
import cdu.gu.onlinechat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService; //用户service类
    @Autowired
    private SimpMessagingTemplate messagingTemplate; //消息模板
    @Autowired
    private ParticipantRepository participantRepository; //在线用户存储

    private static final String SUBSCRIBE_LOGIN_URI = "/topic/login"; //订阅的登录地址

    private static final String SUBSCRIBE_MESSAGE_URI = "/topic/chat/message"; //订阅接收消息地址

    private static final String IMAGE_PREFIX = "/upload/";  //服务器储存上传图片地址的前缀

    /**
     * 进入群聊
     * @param user
     * @return
     */
    @PostMapping("/qun")
    public String loginIntoChatRoom(User user){
        //登录时间
        user.setLoginDate(new Date());
        user.setPassword(null); //设空防止泄露
        //用于通过websocket向客户端发送消息的操作
        messagingTemplate.convertAndSend(SUBSCRIBE_LOGIN_URI, user);
        logger.info(user.getLoginDate() + ", " + user.getName() + " login.");
        return "chatroom";
    }

    /**
     * 接收文本消息
     * @param message
     */
    @MessageMapping("/sendmessgae")
    public void receiveMessage(Message message){
        message.setSendDate(new Date());
        message.setMessageType("text");
        logger.info(message.getSendDate()+"."+message.getUserName()+"发送了一条消息:"+message.getContent());

        messagingTemplate.convertAndSend(SUBSCRIBE_MESSAGE_URI,message);
    }

    @PostMapping("/upload")

    /**
     * 订阅用户在线人数
     * @return
     */
    @SubscribeMapping("/chat/participants")
    public Long getActiveUserNumber(){
        return Long.valueOf(participantRepository.getActiveSessions().values().size());
    }

}
