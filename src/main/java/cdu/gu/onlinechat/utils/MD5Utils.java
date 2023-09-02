package cdu.gu.onlinechat.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;


@Component
public class MD5Utils {

    /**
     * 对输入的密码加密
     * @param password 要加密的密码
     * @param salt 盐值
     * @return 加密后的密码
     */
    public static String encryption(String password, String salt) {
        // 进行三次加密
        for (int i = 0; i < 3; i++) {
            // 将传入的字节通过计算保持到16位字符，并返回
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes());
        }
        return password;
    }

    /**
     * 验证密码
     * @param correct 正确的密码，从数据库中查出来的
     * @param password 输入的密码
     * @param salt 盐值 数据库中查出
     * @return 密码是否相同
     */
    public static boolean check(String correct, String password, String salt) {
        String pwd = encryption(password, salt);
        System.out.println(pwd);
        return correct.equals(pwd);
    }

}

