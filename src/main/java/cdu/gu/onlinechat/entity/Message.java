package cdu.gu.onlinechat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String userName;  //发送者
    private Date sendDate;    //发送日期
    private String content;   //发送内容
    private String messageType;//发送消息类型（“text”文本，“image”图片）
}
