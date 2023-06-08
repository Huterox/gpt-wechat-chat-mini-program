package com.huterox.ikun.chat.service.impl;

import com.huterox.common.utils.DateUtils;
import com.huterox.common.utils.R;
import com.huterox.ikun.chat.entity.ChatItemsEntity;
import com.huterox.ikun.chat.entity.ChatMsgsEntity;
import com.huterox.ikun.chat.entity.Q.ChatQ;
import com.huterox.ikun.chat.entity.R.ChatR;
import com.huterox.ikun.chat.service.ChatItemsService;
import com.huterox.ikun.chat.service.SaveChatService;
import com.huterox.ikun.chat.service.WChatService;
import com.huterox.ikun.chat.utils.CountCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;


@Service
public class WChatServiceImpl implements WChatService {


    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    @Autowired
    CountCache countCache;

    @Autowired
    SaveChatService saveChatService;

    @Autowired
    ChatItemsService chatItemsService;

    @Autowired
    public WChatServiceImpl(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }
    @Override
    public R wChat(ChatQ chatQ) {

        Long uid = chatQ.getUid();
        String uid_key = String.valueOf(uid);
        boolean containsKey = countCache.getCountCache().containsKey(uid_key);
        if(containsKey){
            Integer count = countCache.getCountCache().get(uid_key);
            if(count==null){
                countCache.getCountCache().put(uid_key, 99);
            }
            else if(count.equals(0)){
                ChatR chatR = new ChatR ();
                chatR.setRes("今日接口次数已用完");
                chatR.setSpendTime(0.0);
                return  R.ok().put("chatR",chatR);
            }
            else {
                countCache.getCountCache().update(uid_key,count-1);
            }
        }else {
            countCache.getCountCache().put(uid_key, 99);
        }

        String serviceName = "flaskService";
        ServiceInstance instance = discoveryClient.getInstances(serviceName).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("no available instances"));

        String url = String.format("http://%s:%d/message", instance.getHost(), instance.getPort());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ChatQ> entity = new HttpEntity<>(chatQ, headers);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {});

        Map<String, Object> body = response.getBody();

        ChatR chatR = new ChatR ();
        chatR.setRes((String) body.get("res"));
        chatR.setSpendTime((Double) body.get("spend_time"));

        //查看当前item归档
        String itemid = chatQ.getItemid();
        ChatItemsEntity byId = chatItemsService.getById(itemid);
        if(byId==null){
            ChatItemsEntity chatItemsEntity = new ChatItemsEntity();
            chatItemsEntity.setSaveTime(DateUtils.getCurrentTime());
            chatItemsEntity.setItemid(itemid);
            chatItemsEntity.setUid(chatQ.getUid());
            chatItemsService.save(chatItemsEntity);
        }

        //保存聊天内容
        ChatMsgsEntity userMsg = new ChatMsgsEntity();
        userMsg.setItemsid(chatQ.getItemid());
        userMsg.setMessage(chatQ.getMsg());
        userMsg.setUid(chatQ.getUid());
        //1表示用户，0表示机器人
        userMsg.setMsgType(1);
        userMsg.setCreateTime(DateUtils.getCurrentTime());
        saveChatService.SaveChat(userMsg);

        ChatMsgsEntity robMsg = new ChatMsgsEntity();
        robMsg.setItemsid(chatQ.getItemid());
        robMsg.setMessage(chatR.getRes());
        robMsg.setUid(chatQ.getUid());
        //1表示用户，0表示机器人
        robMsg.setMsgType(0);
        robMsg.setCreateTime(DateUtils.getCurrentTime());
        saveChatService.SaveChat(robMsg);


        return  R.ok().put("chatR",chatR);
    }
}
