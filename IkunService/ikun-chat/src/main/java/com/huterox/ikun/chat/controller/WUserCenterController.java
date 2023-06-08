package com.huterox.ikun.chat.controller;




import com.huterox.common.utils.R;
import com.huterox.ikun.chat.entity.Q.AutoRegisterQ;

import com.huterox.ikun.chat.service.WUserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wcenter")
public class WUserCenterController {


    @Autowired
    WUserCenterService wUserCenterService;

    @PostMapping("/login")
    public R login (@Validated @RequestBody AutoRegisterQ autoRegisterQ){
        return wUserCenterService.Register(autoRegisterQ);
    }

    @PostMapping("/count")
    public R count (@Validated @RequestBody AutoRegisterQ autoRegisterQ){
        return wUserCenterService.NowTimes(autoRegisterQ);
    }

    @PostMapping("/isregist")
    public R isRegist (@Validated @RequestBody AutoRegisterQ autoRegisterQ){
        return wUserCenterService.IsRegister(autoRegisterQ);
    }

}
