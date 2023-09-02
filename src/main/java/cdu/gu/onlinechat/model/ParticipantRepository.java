package cdu.gu.onlinechat.model;

import cdu.gu.onlinechat.entity.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ParticipantRepository {
    private Map<String, User> activeSessions = new ConcurrentHashMap<>(); //在线用户map,键：用户名称，值：用户对象

    public Map<String, User> getActiveSessions() {
        return activeSessions;
    }

    public void setActiveSessions(Map<String, User> activeSessions) {
        this.activeSessions = activeSessions;
    }


    public void add(String name, User user){
        activeSessions.put(name, user);
    }

    public User remove(String name){
        return activeSessions.remove(name);
    }

    public boolean containsUserName(String name){
        return activeSessions.containsKey(name);
    }
}
