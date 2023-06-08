package com.huterox.ikun.chat.service.impl;

import com.huterox.common.utils.SerializeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huterox.common.utils.PageUtils;
import com.huterox.common.utils.Query;

import com.huterox.ikun.chat.dao.ChatItemsDao;
import com.huterox.ikun.chat.entity.ChatItemsEntity;
import com.huterox.ikun.chat.service.ChatItemsService;


@Service("chatItemsService")
public class ChatItemsServiceImpl extends ServiceImpl<ChatItemsDao, ChatItemsEntity> implements ChatItemsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) throws Exception {

        String key = (String) params.get("key");
        String accurate = (String) params.get("accurate");
        IPage<ChatItemsEntity> page_params = new Query<ChatItemsEntity>().getPage(params);
        QueryWrapper<ChatItemsEntity> chatItemsEntityQueryWrapper = new QueryWrapper<>();

        if(key!=null){
            if(accurate==null){
                //此时表示只有key,没有accurate，说明是后台管理系统在调用
                chatItemsEntityQueryWrapper.like("uid",key);
            }else {
                //此时有accurate说明是用户端在调用
                if(accurate.equals("single")){
                    String table_name = (String) params.get("table_name");
                    String order = (String) params.get("order");
                    chatItemsEntityQueryWrapper.eq(table_name,key);
                    if(order.equals("desc")){
                        chatItemsEntityQueryWrapper.orderByDesc("itemid");
                    }
                }else if(accurate.equals("many")){


                    Object accurate_query = params.get("accurate_query");
                    chatItemsEntityQueryWrapper = (QueryWrapper<ChatItemsEntity>) SerializeUtil.deserialize(accurate_query.toString());
                }
            }
        }
        IPage<ChatItemsEntity> page = this.page(
                page_params,
                chatItemsEntityQueryWrapper
        );

        return new PageUtils(page);
    }

}