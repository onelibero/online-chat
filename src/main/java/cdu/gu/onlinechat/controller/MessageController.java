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
     * 添加群消息
     * @param name
     * @param message
     * @return
     */
    @PostMapping("addAll")
    public RUtils AddAll(@RequestParam("name")String name,@RequestParam("message")String message){
        if (messageService.AddMessage(name, message) == 1)
            return RUtils.ok();
        else return RUtils.error();
    }

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
     * 添加私有信息
     * @param message
     * @return
     */
    @PostMapping
    public RUtils AddOne(@RequestBody Message message){
        if (messageService.PreAddMessage(message.getFromid(),message.getToid(),message.getMessgae()) == 1)
            return RUtils.ok();
        else
            return RUtils.error();
    }


}
