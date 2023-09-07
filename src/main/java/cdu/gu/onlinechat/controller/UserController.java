package cdu.gu.onlinechat.controller;

import cdu.gu.onlinechat.entity.User;
import cdu.gu.onlinechat.service.UserService;
import cdu.gu.onlinechat.utils.HuaweiObs;
import cdu.gu.onlinechat.utils.RUtils;
import cdu.gu.onlinechat.utils.TXCloudUtils;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private HuaweiObs huaweiObs;
    @Autowired
    private TXCloudUtils txCloudUtils;

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public RUtils register(@RequestBody User user){
      if (userService.register(user.getName(),user.getUsername(),user.getPassword(),user.getAvatar()) == 1)
          return RUtils.ok();
      else return RUtils.error("账号已经被注册");
    }
    @PostMapping("/upload")
    public RUtils upload(@RequestParam("file")MultipartFile file) throws IOException {
        String type = file.getContentType();
        System.out.println(type);
        if (type != null && type.startsWith("image")) {
            String url = txCloudUtils.upload(file);
            String url1 = huaweiObs.fileUpload(file,file.getOriginalFilename());
            System.out.println(url);
            return RUtils.ok().put("浏览地址",url).put("下载地址",url1);
        }
        else{
            return RUtils.error();
        }
    }
    @PostMapping("/login")
    public RUtils Login(@RequestParam("username")String username,@RequestParam("password")String password){
        return userService.login(username,password);
    }
}
