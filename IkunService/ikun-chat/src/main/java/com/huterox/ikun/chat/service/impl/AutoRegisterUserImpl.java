package com.huterox.ikun.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huterox.common.utils.DateUtils;
import com.huterox.ikun.chat.entity.Q.AutoRegisterQ;
import com.huterox.ikun.chat.entity.Q.ChatQ;
import com.huterox.ikun.chat.entity.Q.ChatQW;
import com.huterox.ikun.chat.entity.WUserEntity;
import com.huterox.ikun.chat.service.AutoRegisterUser;
import com.huterox.ikun.chat.service.WUserService;
import com.huterox.ikun.chat.utils.MapCache;
import com.huterox.ikun.chat.utils.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoRegisterUserImpl implements AutoRegisterUser {

    @Autowired
    UserCache userCache;

    @Autowired
    WUserService wUserService;

    @Override
    public boolean register(AutoRegisterQ autoRegisterQ) {

        String wid = autoRegisterQ.getWid();
        MapCache<String, Long> userC = userCache.getUserCache();
        if(userC.containsKey(wid)){
            return true;
        }else {
            WUserEntity wid1 = wUserService.getOne(new QueryWrapper<WUserEntity>().eq("wid", wid));
            if(wid1!=null){
                userC.put(wid,wid1.getUid());
                return true;
            }else {
                WUserEntity wUserEntity = new WUserEntity();
                wUserEntity.setWid(wid);
                wUserEntity.setCreateTime(DateUtils.getCurrentTime());
                //用户昵称先不存了，没必要
                userC.put(wid,wUserEntity.getUid());
                wUserService.save(wUserEntity);
                return true;
            }
        }

    }

    @Override
    public ChatQ register(ChatQW chatQW) {
        String wid = chatQW.getWid();
        MapCache<String, Long> userC = userCache.getUserCache();
        ChatQ chatQ = new ChatQ();
        chatQ.setItemid(chatQW.getItemid());
        chatQ.setMsg(chatQW.getMsg());
        if(userC.containsKey(wid)){
            Long u = userC.get(wid);
            chatQ.setUid(u);
        }else {
            WUserEntity wid1 = wUserService.getOne(new QueryWrapper<WUserEntity>().eq("wid", wid));
            if (wid1 != null) {
                userC.put(wid, wid1.getUid());
                chatQ.setUid(wid1.getUid());
            } else {
                WUserEntity wUserEntity = new WUserEntity();
                wUserEntity.setWid(wid);
                wUserEntity.setCreateTime(DateUtils.getCurrentTime());
                //用户昵称先不存了，没必要
                wUserService.save(wUserEntity);
                userC.put(wid,wUserEntity.getUid());
                chatQ.setUid(wUserEntity.getUid());
            }
        }
        return chatQ;
    }
}
