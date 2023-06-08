package com.huterox.ikun.chat.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huterox.ikun.chat.entity.ChatItemsEntity;
import com.huterox.ikun.chat.service.ChatItemsService;
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
@RequestMapping("chat/chatitems")
public class ChatItemsController {
    @Autowired
    private ChatItemsService chatItemsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) throws Exception {
        PageUtils page = chatItemsService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{itemid}")
    public R info(@PathVariable("itemid") Long itemid){
		ChatItemsEntity chatItems = chatItemsService.getById(itemid);

        return R.ok().put("chatItems", chatItems);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ChatItemsEntity chatItems){
		chatItemsService.save(chatItems);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ChatItemsEntity chatItems){
		chatItemsService.updateById(chatItems);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] itemids){
		chatItemsService.removeByIds(Arrays.asList(itemids));

        return R.ok();
    }

}
