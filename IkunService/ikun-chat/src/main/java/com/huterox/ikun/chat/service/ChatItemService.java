package com.huterox.ikun.chat.service;

import com.huterox.common.utils.R;
import com.huterox.ikun.chat.entity.Q.AutoRegisterQ;
import com.huterox.ikun.chat.entity.Q.ItemsQ;
import com.huterox.ikun.chat.entity.Q.MsgsQ;

public interface ChatItemService {

    R GetItems(ItemsQ itemsQ) throws Exception;

    R GetMsgs(MsgsQ msgsQ) throws Exception;
}
