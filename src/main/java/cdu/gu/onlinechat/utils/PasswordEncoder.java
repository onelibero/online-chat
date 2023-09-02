//package cdu.gu.onlinechat.utils;
//
//import cdu.gu.onlinechat.mapper.UserMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//
//
//@Component
//public class PasswordEncoder {
//    @Autowired
//    private UserMapper userMapper;
//
//    //spring提供的工具类
//    /**
//     * 给密码加密
//     */
//    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//    public String getEncode(String password){
//        return passwordEncoder.encode(password);
//    }
//
//    /**
//     * 判断用户账号密码正确
//     * @param username
//     * @param password
//     * @return
//     */
//    public boolean login(String username,String password){
//        String encode = passwordEncoder.encode(password);
//
//        return passwordEncoder.matches(password, encode);
//    }
//}
