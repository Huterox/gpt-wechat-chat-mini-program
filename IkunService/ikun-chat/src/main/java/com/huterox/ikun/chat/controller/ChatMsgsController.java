package com.huterox.ikun.chat.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huterox.ikun.chat.entity.ChatMsgsEntity;
import com.huterox.ikun.chat.service.ChatMsgsService;
import com.huterox.common.utils.PageUtils;
import com.huterox.common.utils.R;



/**
 * 
 *
 * @author Huterox
 * @email 3139541502@qq.com
 * @date 2023-05-01 09:55:08
 */
@RestController
@RequestMapping("chat/chatmsgs")
public class ChatMsgsController {
    @Autowired
    private ChatMsgsService chatMsgsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) throws Exception {
        PageUtils page = chatMsgsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{msgid}")
    public R info(@PathVariable("msgid") Long msgid){
		ChatMsgsEntity chatMsgs = chatMsgsService.getById(msgid);

        return R.ok().put("chatMsgs", chatMsgs);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ChatMsgsEntity chatMsgs){
		chatMsgsService.save(chatMsgs);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ChatMsgsEntity chatMsgs){
		chatMsgsService.updateById(chatMsgs);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] msgids){
		chatMsgsService.removeByIds(Arrays.asList(msgids));

        return R.ok();
    }

}
