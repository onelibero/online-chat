package cdu.gu.onlinechat.service.impl;

import cdu.gu.onlinechat.entity.User;
import cdu.gu.onlinechat.mapper.UserMapper;
import cdu.gu.onlinechat.service.UserService;
import cdu.gu.onlinechat.utils.MD5Utils;
import cdu.gu.onlinechat.utils.RUtils;
import cdu.gu.onlinechat.utils.RedisUtils;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MD5Utils md5Utils;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public String upload(MultipartFile file) {
        return null;
    }

    @Override
    public RUtils login(String username, String password) {
        Integer id = userMapper.findUserByName(username);
        if (id != null){
            User user = userMapper.findUserById(id);
            System.out.println(user.getSalt()+"密码"+user.getPassword());
            if (md5Utils.check(user.getPassword(),password,user.getSalt())){
                // 调用Sa-Token方法，自动登录，且token随机给
                StpUtil.login(user.getId());
                // 将登录之后的token存入redis中
                redisUtils.set(user.getUsername() + ":login:", StpUtil.getTokenValue());
                // 设置过期时间 1天 60*60*24 秒
                redisUtils.expire(user.getUsername() + ":login:", 60 * 60 * 24);
                return RUtils.ok().put("用户",user);
            }
            else
                return RUtils.error("密码错误");
            }
        else
            return RUtils.error("账号不存在");

    }

    @Override
    public int register(String name, String username, String password,String avatar) {
        if (userMapper.findUserByName(username) == null){
        //生成userid
        char charr[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        StringBuilder userid = new StringBuilder();
        String salt = new String();
        Random r = new Random();
        for (int x = 0; x < 8; ++x) {
            userid.append(charr[r.nextInt(charr.length)]);
        }
        System.out.println(userid);
        //生成盐值
        salt = userid.substring(4);
        //给密码加密
        String password1 = md5Utils.encryption(password,salt);
        //获取创建时间
        Date startTime = new Date(System.currentTimeMillis());

        return userMapper.register(userid.toString(),name,username,password1,avatar,startTime,salt);
        }
        else
            return 0;
    }

    @Override
    public User findUserByName(String username) {
        return userMapper.findUserById(userMapper.findUserByName(username));
    }


}
