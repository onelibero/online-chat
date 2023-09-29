package cdu.gu.onlinechat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String fromid;      //发送者
    private String toid;        //接受方
    private Date sendDate ; //发送日期
    private String messgae;   //发送内容
    private String messageType;//发送消息类型（“text”文本，“image”图片）
    private List<String> userNames;  //
}
