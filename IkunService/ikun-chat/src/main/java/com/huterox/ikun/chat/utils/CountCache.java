package com.huterox.ikun.chat.utils;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CountCache {

    private static MapCache<String, Integer> countCache = new MapCache<>();
    private CountCache() {}

    public MapCache<String, Integer> getCountCache() {
        return countCache;
    }
}


//public class CountCache {
//    private static CountCache instance;
//    private MapCache<String, Integer> countCache;
//
//    private CountCache() {
//        countCache = new MapCache<>();
//    }
//
//    public static synchronized CountCache getInstance() {
//        if (instance == null) {
//            instance = new CountCache();
//        }
//        return instance;
//    }
//
//    public MapCache<String, Integer> getCountCache() {
//        return countCache;
//    }
//}
