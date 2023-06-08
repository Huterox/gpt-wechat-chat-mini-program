package com.huterox.ikun.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huterox.common.utils.DateUtils;
import com.huterox.ikun.chat.entity.ChatItemsEntity;
import com.huterox.ikun.chat.entity.ChatMsgsEntity;
import com.huterox.ikun.chat.entity.U.ItemC;
import com.huterox.ikun.chat.service.ChatItemsService;
import com.huterox.ikun.chat.service.ChatMsgsService;
import com.huterox.ikun.chat.service.SaveChatService;
import com.huterox.ikun.chat.utils.ItemCache;
import com.huterox.ikun.chat.utils.MapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveChatServiceImpl implements SaveChatService {


    @Autowired
    ChatItemsService chatItemsService;

    @Autowired
    ItemCache itemCache;

    @Autowired
    ChatMsgsService chatMsgsService;

    @Override
    public void SaveChat(ChatMsgsEntity chatMsgsEntity) {

        MapCache<String, ItemC> itemCache = this.itemCache.getItemCache();
        String timeday = DateUtils.getCurrentDayMin();
        String itemid = null;
        String uid = String.valueOf(chatMsgsEntity.getUid());
        if(itemCache.containsKey(uid)){
            ItemC itemC = itemCache.get(uid);
            if (!itemC.getTimeDay().equals(timeday)){
                ChatItemsEntity chatItemsEntity = new ChatItemsEntity();
                chatItemsEntity.setSaveTime(timeday);
                chatItemsEntity.setUid(chatMsgsEntity.getUid());
                chatItemsEntity.setSaveTime(DateUtils.getCurrentTime());
                chatItemsService.save(chatItemsEntity);

                itemC.setItemid(chatItemsEntity.getItemid());
                itemC.setTimeDay(timeday);
                itemCache.put(uid,itemC);
                itemid = itemC.getItemid();
            }else {
                itemid = itemC.getItemid();
            }
        }else {

            ChatItemsEntity itemEntity = chatItemsService.getById(chatMsgsEntity.getItemsid());
            ItemC itemC = new ItemC();
            itemC.setItemid(itemEntity.getItemid());
            itemC.setTimeDay(timeday);
            itemCache.put(uid,itemC);
            itemid = itemC.getItemid();

        }

        chatMsgsEntity.setItemsid(itemid);
        chatMsgsService.save(chatMsgsEntity);
    }
}
