package com.huterox.ikun.chat.utils;


import java.util.Map;
import java.util.HashMap;

public class MapCache <K, V> {
    private static final long DEFAULT_EXPIRED_TIME_IN_HOURS = 24L; // 默认过期时间为 24 小时
    private Map<K, Entry<V>> data;
    private final Object lock = new Object(); // 加锁确保多线程并发资源安全访问

    class Entry<V> {
        V value;
        long expiredTime;

        public Entry(V value, long expiredTime) {
            this.value = value;
            this.expiredTime = expiredTime;
        }
    }

    // 构造方法
    public MapCache() {
        this.data = new HashMap<>();
    }

    /**
     * 向缓存中添加数据，并设置过期时间（单位：小时）
     * @param key 键
     * @param value 值
     * @return 如果键已存在，返回旧值；否则返回 null。
     */
    public V put(K key, V value) {
        return this.put(key, value, DEFAULT_EXPIRED_TIME_IN_HOURS);
    }

    /**
     * 向缓存中添加数据，并设置过期时间（单位：小时）
     * @param key 键
     * @param value 值
     * @param expiredTimeInHours 过期时间
     * @return 如果键已存在，返回旧值；否则返回 null。
     */
    public V put(K key, V value, long expiredTimeInHours) {
        long currentTime = System.currentTimeMillis();
        long expiredTime = expiredTimeInHours * 60 * 60 * 1000 + currentTime;

        synchronized (lock) { // 加锁确保多线程并发资源安全访问
            Entry<V> entry = new Entry<>(value, expiredTime);
            Entry<V> oldEntry = data.put(key, entry);

            return oldEntry != null ? oldEntry.value : null;
        }
    }

    /**
     * 从缓存中获取指定键对应的值
     * @param key 键
     * @return 如果键不存在或已过期，返回 null；否则返回缓存值。
     */
    public V get(K key) {
        Entry<V> entry;

        synchronized (lock) { // 加锁确保多线程并发资源安全访问
            entry = data.get(key);
        }

        if (entry != null) {
            long currentTime = System.currentTimeMillis();

            if (entry.expiredTime < currentTime) {
                synchronized (lock) { // 加锁确保多线程并发资源安全访问
                    data.remove(key);
                }

                return null;
            }

            return entry.value;
        }

        return null;
    }

    /**
     * 移除指定键对应的缓存数据
     * @param key 键
     * @return 如果键不存在，返回 null；否则返回旧值。
     */
    public V remove(K key) {
        synchronized (lock) { // 加锁确保多线程并发资源安全访问
            Entry<V> entry = data.remove(key);
            return entry != null ? entry.value : null;
        }
    }

    /**
     * 清空缓存
     */
    public void clear() {
        synchronized (lock) { // 加锁确保多线程并发资源安全访问
            data.clear();
        }
    }

    /**
     * 判断缓存是否包含指定的键
     * @param key 键
     * @return 如果缓存中包含指定的键，返回 true；否则返回 false。
     */
    public boolean containsKey(K key) {
        synchronized (lock) { // 加锁确保多线程并发资源安全访问
            return data.containsKey(key);
        }
    }

    /**
     * 返回缓存中键值对的数量
     * @return 缓存中键值对的数量
     */
    public int size() {
        synchronized (lock) { // 加锁确保多线程并发资源安全访问
            return data.size();
        }
    }

    /**
     * 更新指定键对应的缓存数据，并更新过期时间
     * @param key 键
     * @param value 值
     * @param expiredTimeInHours 过期时间（单位：小时）
     * @return 如果键不存在或已过期，返回 null；否则返回旧值。
     */
    public V update(K key, V value, long expiredTimeInHours) {
        synchronized (lock) { // 加锁确保多线程并发资源安全访问
            Entry<V> entry = data.get(key);

            if (entry != null) {
                long currentTime = System.currentTimeMillis();
                long expiredTime = expiredTimeInHours * 60 * 60 * 1000 + currentTime;

                entry.value = value;
                entry.expiredTime = expiredTime;

                return entry.value;
            }

            return null;
        }
    }

    /**
     * 更新指定键对应的缓存数据，不更新过期时间
     * @param key 键
     * @param value 值
     * @return 如果键不存在或已过期，返回 null；否则返回旧值。
     */
    public V update(K key, V value) {
        synchronized (lock) { // 加锁确保多线程并发资源安全访问
            Entry<V> entry = data.get(key);

            if (entry != null) {
                entry.value = value;
                return entry.value;
            }

            return null;
        }
    }
}
