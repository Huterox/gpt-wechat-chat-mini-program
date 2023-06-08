package com.huterox.ikun.chat.utils;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Lazy
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserCache {

    private static MapCache<String, Long> userCache = new MapCache<>();
    private UserCache() {}

    public MapCache<String, Long> getUserCache() {
        return userCache;
    }
}

