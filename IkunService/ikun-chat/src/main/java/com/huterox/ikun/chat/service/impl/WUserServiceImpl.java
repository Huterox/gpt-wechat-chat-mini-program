package com.huterox.ikun.chat.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huterox.common.utils.PageUtils;
import com.huterox.common.utils.Query;

import com.huterox.ikun.chat.dao.WUserDao;
import com.huterox.ikun.chat.entity.WUserEntity;
import com.huterox.ikun.chat.service.WUserService;


@Service("wUserService")
public class WUserServiceImpl extends ServiceImpl<WUserDao, WUserEntity> implements WUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WUserEntity> page = this.page(
                new Query<WUserEntity>().getPage(params),
                new QueryWrapper<WUserEntity>()
        );

        return new PageUtils(page);
    }

}