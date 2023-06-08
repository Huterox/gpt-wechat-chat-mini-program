package com.huterox.ikun.chat.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huterox.common.utils.PageUtils;
import com.huterox.ikun.chat.entity.WUserEntity;

import java.util.Map;

/**
 * 
 *
 * @author Huterox
 * @email 3139541502@qq.com
 * @date 2023-05-01 09:55:08
 */
public interface WUserService extends IService<WUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

}

