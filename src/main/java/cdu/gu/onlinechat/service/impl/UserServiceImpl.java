package cdu.gu.onlinechat.service.impl;

import cdu.gu.onlinechat.entity.User;
import cdu.gu.onlinechat.mapper.UserMapper;
import cdu.gu.onlinechat.service.UserService;
import cdu.gu.onlinechat.utils.MD5Utils;
import cdu.gu.onlinechat.utils.RUtils;
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

    @Override
    public String upload(MultipartFile file) {
        return null;
    }

    @Override
    public RUtils login(String username, String password) {
        int id = userMapper.findUserByName(username);
        System.out.println(id);
        User user = userMapper.findUserById(id);
        if (user != null ) {
            System.out.println(user);
            System.out.println(user.getSalt()+"密码"+user.getPassword());
            if (md5Utils.check(user.getPassword(),password,user.getSalt()))
                return RUtils.ok().put("用户",user);
            else
                return RUtils.error("密码错误");
        }
        return RUtils.error("账号不存在");
    }

    @Override
    public int register(String name, String username, String password,String avatar) {

        if (userMapper.findUserByName(username)!= null){
        //生成userid
        char charr[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        StringBuilder userid = new StringBuilder();
        String salt = new String();
        Random r = new Random();
        for (int x = 0; x < 8; ++x) {
//            userid.append(charr[r.nextInt(charr.length)]);
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
