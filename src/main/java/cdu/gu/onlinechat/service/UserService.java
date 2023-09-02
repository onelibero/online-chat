package cdu.gu.onlinechat.service;

import cdu.gu.onlinechat.entity.LoginInfoDo;
import cdu.gu.onlinechat.entity.User;
import cdu.gu.onlinechat.utils.RUtils;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    String upload(MultipartFile file);
    RUtils login(String username, String password);
    int register(String name,String username,String password,String avatar);
    User findUserByName(String username);

    public void addUserLoginInfo(LoginInfoDo loginInfoDo);
}
