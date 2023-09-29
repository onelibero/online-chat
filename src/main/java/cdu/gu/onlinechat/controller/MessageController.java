package cdu.gu.onlinechat.controller;

import cdu.gu.onlinechat.entity.Message;
import cdu.gu.onlinechat.service.MessageService;
import cdu.gu.onlinechat.utils.RUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 因为关于群聊主要的操作是在websocket里面进行的，所以只有删除操作才会用到
 */
@RestController
@RequestMapping("msg")
public class MessageController {
    //群聊表
    @Autowired
    private MessageService messageService;


    /**
     * 撤回
     * @param name
     * @param date
     * @return
     */
    @PostMapping("/delAll")
    public RUtils DelAll(@RequestParam("name")String name, @RequestParam("date")Date date){
        if (messageService.deleteMessage(name,date) == 1)
            return RUtils.ok();
        else return RUtils.error();
    }

    /**
     * 撤回私有消息
     * @return
     */
    @PostMapping("/delpre")
    public RUtils DelPre(@RequestBody Message msg){
        if (messageService.PreDelMessage(msg.getFromid(), msg.getToid(), msg.getSendDate()) == 1)
            return RUtils.ok();
        else return RUtils.error();
    }


}
