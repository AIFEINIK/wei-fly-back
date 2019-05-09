package com.wei.fly.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.wei.fly.model.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/15
 * @Version 1.0.0
 */
@Slf4j
public class CacheUtils {

    /**
     * 使用google的guava缓存
     */
    private static LoadingCache<String, Object> cache;

    static {
        cache = CacheBuilder.newBuilder().maximumSize(10000)
                .expireAfterWrite(CommonConstant.TOKEN_TIMEOUT, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Object>() {
                    //若缓存中有数据，则从缓存中加载数据，若缓存中没有数据，则从调用getFromDatabase()方法从数据库加载
                    @Override
                    public Object load(String empId) throws Exception {
                        return StringUtils.EMPTY;
                    }

                });
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return Object
     * @author lenovo
     */
    public static Object get(String key) throws Exception {
        return StringUtils.isNotEmpty(key) ? cache.get(key) : null;
    }

    /**
     * 放入缓存
     *
     * @param key
     * @param value void
     * @author lenovo
     */
    public static void set(String key, Object value) {
        if (StringUtils.isNotEmpty(key) && value != null) {
            cache.put(key, value);
        }
    }

    /**
     * 移除缓存
     *
     * @param key void
     * @author lenovo
     */
    public static void delete(String key) {
        if (StringUtils.isNotEmpty(key)) {
            cache.invalidate(key);
        }
    }

    /**
     * 批量删除缓存
     *
     * @param keys void
     * @author lenovo
     */
    public static void deletes(List<String> keys) {
        if (keys != null && keys.size() > 0) {
            cache.invalidateAll(keys);
        }
    }

    /**
     * 缓存数据数量
     *
     * @return Long
     * @author lenovo
     */
    public static Long size() {
        return cache.size();

    }

    public static void main(String[] args) {
        delete("3198417b15064ecca28dfc6feab91afe");
    }
}
