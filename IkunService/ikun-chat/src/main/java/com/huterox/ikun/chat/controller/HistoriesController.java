package com.huterox.ikun.chat.controller;

import com.huterox.common.utils.R;
import com.huterox.ikun.chat.entity.Q.ItemsQ;
import com.huterox.ikun.chat.entity.Q.MsgsQ;
import com.huterox.ikun.chat.service.ChatItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/histories")
public class HistoriesController {


    @Autowired
    ChatItemService chatItemService;

    @RequestMapping("/items")
    public R items(@Validated @RequestBody ItemsQ itemsQ) throws Exception {
        return  chatItemService.GetItems(itemsQ);
    }

    @RequestMapping("/msgs")
    public R msgs(@Validated @RequestBody MsgsQ msgsQ) throws Exception {
        return  chatItemService.GetMsgs(msgsQ);
    }

}
