package com.huterox.ikun.chat.service;

import com.huterox.common.utils.R;
import com.huterox.ikun.chat.entity.Q.AutoRegisterQ;

public interface WUserCenterService {

    R Register(AutoRegisterQ autoRegisterQ);

    R NowTimes(AutoRegisterQ autoRegisterQ);

    R IsRegister(AutoRegisterQ autoRegisterQ);
}
