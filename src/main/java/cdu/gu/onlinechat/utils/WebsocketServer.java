package cdu.gu.onlinechat.utils;

import cdu.gu.onlinechat.entity.Message;
import com.alibaba.fastjson.JSON;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;


import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/socket/{username}")
public class WebsocketServer {
    //存储当前对象
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();
    //建立连接

    /**
     * 把用户存到session中
     * @param session
     * @param username
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        System.out.println("当前用户名:"+username);
        sessionMap.put(username,session);
        //发送登录人员消息给所有客户端
        sendAllMessage(setUserList());
    }

    /**
     * 吧用户信息从session里面删除
     * 发送给所有人当前登录人的信息
     * @param username
     */
    @OnClose
    public void onClose(@PathParam("username") String username) throws IOException {
        sessionMap.remove(username);
        sendAllMessage(setUserList());
    }

    /**
     * 接收来自客户端的消息，根据是否有toid来判断群发和私发
     * @param message
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        //解析消息为java对象
        Message msg = JSON.parseObject(message,Message.class);
        Date startTime = new Date(System.currentTimeMillis());

        msg.setSendDate(new SimpleDateFormat("HH:mm:ss").format(startTime));
        System.out.println(msg);
        if (StringUtils.isEmpty(msg.getToid())){
            sendAllMessage(JSON.toJSONString(message));
        }else {
            Session session = sessionMap.get(msg.getToid());
            sendMessage(message,session);
        }
    }
    @OnError
    public void onError(Session session,Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 用来统计登录人的信息
     * @return
     */
    private String setUserList(){
        ArrayList<String> list = new ArrayList<>();
        for (String key:sessionMap.keySet()){
            list.add(key);
        }
        Message message = new Message();
        message.setUserNames(list);
        return JSON.toJSONString(message);
    }

    /**
     * 给对于session发送
     * @param message
     * @param toSession
     * @throws IOException
     */
    private void sendMessage(String message,Session toSession) throws IOException {
        toSession.getBasicRemote().sendText(message);
    }

    /**
     * 遍历所有session，给所有session对象发送
     * @param message
     * @throws IOException
     */
    private void sendAllMessage(String message) throws IOException {
        for (Session session : sessionMap.values()){
            session.getBasicRemote().sendText(message);
        }
    }
}
