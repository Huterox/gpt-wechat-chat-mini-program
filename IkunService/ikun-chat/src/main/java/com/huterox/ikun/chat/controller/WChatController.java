package com.huterox.ikun.chat.controller;


import com.huterox.common.utils.R;

import com.huterox.ikun.chat.entity.Q.ChatQ;
import com.huterox.ikun.chat.entity.Q.ChatQW;
import com.huterox.ikun.chat.service.AutoRegisterUser;
import com.huterox.ikun.chat.service.WChatService;
import com.huterox.ikun.chat.service.WUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wchat")
public class WChatController {


    @Autowired
    WChatService wChatService;

    @Autowired
    WUserService wUserService;

    @Autowired
    AutoRegisterUser autoRegisterUser;

    @PostMapping("/chat")
    public R chat(@Validated @RequestBody ChatQW chatQw){

        ChatQ chatQ = autoRegisterUser.register(chatQw);
        return wChatService.wChat(chatQ);
    }

}
