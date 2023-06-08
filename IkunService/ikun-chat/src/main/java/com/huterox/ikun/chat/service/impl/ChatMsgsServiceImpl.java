package com.huterox.ikun.chat.service.impl;

import com.huterox.common.utils.SerializeUtil;
import com.huterox.ikun.chat.entity.ChatMsgsEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huterox.common.utils.PageUtils;
import com.huterox.common.utils.Query;

import com.huterox.ikun.chat.dao.ChatMsgsDao;
import com.huterox.ikun.chat.entity.ChatMsgsEntity;
import com.huterox.ikun.chat.service.ChatMsgsService;


@Service("chatMsgsService")
public class ChatMsgsServiceImpl extends ServiceImpl<ChatMsgsDao, ChatMsgsEntity> implements ChatMsgsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) throws Exception {
        String key = (String) params.get("key");
        String accurate = (String) params.get("accurate");
        IPage<ChatMsgsEntity> page_params = new Query<ChatMsgsEntity>().getPage(params);
        QueryWrapper<ChatMsgsEntity> chatMsgsEntityEntityQueryWrapper = new QueryWrapper<>();

        if(key!=null){
            if(accurate==null){
                //此时表示只有key,没有accurate，说明是后台管理系统在调用
                chatMsgsEntityEntityQueryWrapper.like("uid",key).or().like("message",key);
            }else {
                //此时有accurate说明是用户端在调用
                if(accurate.equals("single")){
                    String table_name = (String) params.get("table_name");
                    String order = (String) params.get("order");
                    chatMsgsEntityEntityQueryWrapper.eq(table_name,key);
                    if(order.equals("desc")){
                        chatMsgsEntityEntityQueryWrapper.orderByDesc("itemid");
                    }
                }else if(accurate.equals("many")){
                    Object accurate_query = params.get("accurate_query");
                    chatMsgsEntityEntityQueryWrapper = (QueryWrapper<ChatMsgsEntity>) SerializeUtil.deserialize(accurate_query.toString());
                }
            }
        }
        IPage<ChatMsgsEntity> page = this.page(
                page_params,
                chatMsgsEntityEntityQueryWrapper
        );

        return new PageUtils(page);
    }

}