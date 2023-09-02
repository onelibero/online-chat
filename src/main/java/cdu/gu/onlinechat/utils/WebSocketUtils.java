package cdu.gu.onlinechat.utils;

import cdu.gu.onlinechat.entity.LoginInfoDo;
import cdu.gu.onlinechat.entity.User;
import cdu.gu.onlinechat.enums.LoginTypeEnum;
import cdu.gu.onlinechat.model.ParticipantRepository;
import cdu.gu.onlinechat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.util.Date;
import java.util.Map;


/**
 * websocket断开连接处理，监听SessionDisconnectEvent事件，当有人下线就通知其他用户
 */
@Component
public class WebSocketUtils implements ApplicationListener<SessionConnectedEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private UserService userService;
    //用于订阅websocket消息的目标路径
    private final static String SUBSCRIBE_LOGOUT_URI = "/topic/logout";

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        Map<String, User> activeSessions = participantRepository.getActiveSessions();
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        User disconnectSession = (User) sessionAttributes.get("user");
        String disconnectUserName = disconnectSession.getName();
        if (participantRepository.containsUserName(disconnectUserName)){
            User removeUser = participantRepository.remove(disconnectUserName);
            removeUser.setLogoutDate(new Date());
            //保存登出信息
            User userByName = userService.findUserByName(removeUser.getName());
            LoginInfoDo loginInfo = LoginInfoDo.builder().userId(userByName == null ? null : userByName.getId())
                    .userName(removeUser.getName()).
                    status(LoginTypeEnum.LOGOUT.getCode()).createTime(new Date()).build();
            userService.addUserLoginInfo(loginInfo);
            logger.info(removeUser.getLogoutDate() + ", " + removeUser.getName() + " logout.");
            messagingTemplate.convertAndSend(SUBSCRIBE_LOGOUT_URI, removeUser);
        }
    }
}
