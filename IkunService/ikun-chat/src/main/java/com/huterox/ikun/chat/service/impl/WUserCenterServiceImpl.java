package com.huterox.ikun.chat.service.impl;
import java.util.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huterox.common.utils.DateUtils;
import com.huterox.common.utils.R;
import com.huterox.ikun.chat.entity.Q.AutoRegisterQ;
import com.huterox.ikun.chat.entity.WUserEntity;
import com.huterox.ikun.chat.exception.BizCodeEnum;
import com.huterox.ikun.chat.service.ChatItemsService;
import com.huterox.ikun.chat.service.WUserCenterService;
import com.huterox.ikun.chat.service.WUserService;
import com.huterox.ikun.chat.utils.CountCache;
import com.huterox.ikun.chat.utils.MapCache;
import com.huterox.ikun.chat.utils.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WUserCenterServiceImpl implements WUserCenterService {

    @Autowired
    CountCache countCache;

    @Autowired
    UserCache userCache;

    @Autowired
    WUserService wUserService;

    @Autowired
    ChatItemsService chatItemsService;


    @Override
    public R NowTimes(AutoRegisterQ autoRegisterQ) {

        MapCache<String, Integer> countCache = this.countCache.getCountCache();
        String wid = autoRegisterQ.getWid();

        WUserEntity wuser = wUserService.getOne(new QueryWrapper<WUserEntity>().eq("wid", wid));
        if(wuser==null){
            return R.error(BizCodeEnum.NOT_LOGIN.getCode(),BizCodeEnum.NOT_LOGIN.getMsg());
        }
        Long uid = wuser.getUid();
        Integer integer = countCache.get(String.valueOf(uid));
        if(integer==null){
            countCache.put(String.valueOf(uid),100,24);
            integer = 100;
        }
        return R.ok().put("msg",integer);
    }

    @Override
    public R IsRegister(AutoRegisterQ autoRegisterQ) {

        MapCache<String, Long> userC = userCache.getUserCache();
        MapCache<String, Integer> countCache = this.countCache.getCountCache();
        String wid = autoRegisterQ.getWid();

        WUserEntity wuser = wUserService.getOne(new QueryWrapper<WUserEntity>().eq("wid", wid));
        if(wuser==null){
            return R.error().put("msg","-1");
        }
        userC.put(wid,wuser.getUid());
        R ok = R.ok();
        String uuid = UUID.randomUUID().toString();
        ok.put("msg","1");
        ok.put("itemid",uuid);
        return ok;
    }

    @Override
    public R Register(AutoRegisterQ autoRegisterQ) {
        String wid = autoRegisterQ.getWid();
        MapCache<String, Long> userC = userCache.getUserCache();
        if(userC.containsKey(wid)){
            String uuid = UUID.randomUUID().toString();
            return R.ok().put("itemid",uuid);
        }else {
            WUserEntity wid1 = wUserService.getOne(new QueryWrapper<WUserEntity>().eq("wid", wid));
            if(wid1!=null){
                userC.put(wid,wid1.getUid());
                return R.ok();
            }else {
                WUserEntity wUserEntity = new WUserEntity();
                wUserEntity.setWid(wid);
                wUserEntity.setCreateTime(DateUtils.getCurrentTime());
                //用户昵称先不存了，没必要
                userC.put(wid,wUserEntity.getUid());
                String uuid = UUID.randomUUID().toString();
                return R.ok().put("itemid",uuid);
            }
        }
    }
}
