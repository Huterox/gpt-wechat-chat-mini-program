package com.huterox.ikun.chat.utils;

import com.huterox.ikun.chat.entity.U.ItemC;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ItemCache {

    private static MapCache<String, ItemC> itemCache = new MapCache<>();
    private ItemCache() {}

    public MapCache<String, ItemC> getItemCache() {
        return itemCache;
    }
}

