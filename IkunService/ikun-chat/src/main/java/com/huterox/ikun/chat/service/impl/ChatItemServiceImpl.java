package com.huterox.ikun.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huterox.common.utils.PageUtils;
import com.huterox.common.utils.R;
import com.huterox.common.utils.SerializeUtil;
import com.huterox.ikun.chat.entity.ChatItemsEntity;
import com.huterox.ikun.chat.entity.ChatMsgsEntity;
import com.huterox.ikun.chat.entity.Q.ItemsQ;
import com.huterox.ikun.chat.entity.Q.MsgsQ;
import com.huterox.ikun.chat.entity.R.MsgR;
import com.huterox.ikun.chat.entity.WUserEntity;
import com.huterox.ikun.chat.exception.BizCodeEnum;
import com.huterox.ikun.chat.service.ChatItemService;
import com.huterox.ikun.chat.service.ChatItemsService;
import com.huterox.ikun.chat.service.ChatMsgsService;
import com.huterox.ikun.chat.service.WUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ChatItemServiceImpl implements ChatItemService {

    @Autowired
    WUserService wUserService;

    @Autowired
    ChatItemsService chatItemsService;

    @Autowired
    ChatMsgsService chatMsgsService;

    @Override
    public R GetItems(ItemsQ itemsQ) throws Exception {

        //先根据wid,查到uid
        String wid = itemsQ.getWid();
        WUserEntity userEntity = wUserService.getOne(new QueryWrapper<WUserEntity>().eq("wid", wid));
        if(userEntity==null){
            return R.error(BizCodeEnum.NOT_LOGIN.getCode(),BizCodeEnum.NOT_LOGIN.getMsg());
        }
        QueryWrapper<ChatItemsEntity> wChatItem = new QueryWrapper<>();
        wChatItem.eq("uid", userEntity.getUid());

        Map<String, Object> params = new HashMap<>();
        params.put("page",String.valueOf(itemsQ.getPage()));
        params.put("limit",String.valueOf(itemsQ.getLimit()));
        params.put("accurate","many");
        //这里的key是没什么用的单纯占位置的
        params.put("key","1");
        params.put("accurate_query", SerializeUtil.serialize(wChatItem));
        PageUtils page = chatItemsService.queryPage(params);
        return R.ok().put("page",page);
    }

    @Override
    public R GetMsgs(MsgsQ msgsQ) throws Exception {
        //这里需要进行倒序处理,验证wid,然后拿到uid,然后查询
        String wid = msgsQ.getWid();
        WUserEntity userEntity = wUserService.getOne(new QueryWrapper<WUserEntity>().eq("wid", wid));
        if(userEntity==null){
            return R.error();
        }
        QueryWrapper<ChatMsgsEntity> wMsgs = new QueryWrapper<>();
        wMsgs.eq("itemsid", msgsQ.getItemid());
        wMsgs.orderByDesc("msgid");
        Map<String, Object> params = new HashMap<>();
        params.put("page",String.valueOf(msgsQ.getPage()));
        params.put("limit",String.valueOf(msgsQ.getLimit()));
        params.put("accurate","many");
        //这里的key是没什么用的单纯占位置的
        params.put("key","1");
        params.put("accurate_query", SerializeUtil.serialize(wMsgs));
        PageUtils page = chatMsgsService.queryPage(params);
        //重新组装为我想要的参数
        page = this.MsgToR(page);

        return R.ok().put("page",page);
    }

    private PageUtils MsgToR(PageUtils page){

        List<ChatMsgsEntity> Msgs = (List<ChatMsgsEntity>) page.getList();
        List<MsgR> res = new ArrayList<>();
        for (ChatMsgsEntity msg : Msgs) {
            MsgR msgR = new MsgR();
            msgR.setContent(msg.getMessage());
            if(msg.getMsgType()==1){
                msgR.setType("man");
                msgR.setAvatarUrl("../index/image/me.png");
            }else {
                msgR.setType("robot");
                msgR.setAvatarUrl("../index/image/yu.png");
            }
            res.add(msgR);
        }
        page.setList(res);
        return page;
    }


}
