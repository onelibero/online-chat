package cdu.gu.onlinechat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String userid;
    private String name;
    private String username;
    private String password;
    private String avatar;
    private Date create_time = new Date();
    private String salt;

    private byte age;
    private String gender;
    private String email;
    private String phone;
    //关于聊天的
    private List<Integer> friendlist;
    private List<String> friendrequest;
}
