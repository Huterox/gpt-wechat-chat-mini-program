package com.huterox.ikun.chat.service;

import com.huterox.ikun.chat.entity.Q.AutoRegisterQ;
import com.huterox.ikun.chat.entity.Q.ChatQ;
import com.huterox.ikun.chat.entity.Q.ChatQW;

public interface AutoRegisterUser {


    boolean register(AutoRegisterQ autoRegisterQ);

    ChatQ register(ChatQW chatQW);
}
