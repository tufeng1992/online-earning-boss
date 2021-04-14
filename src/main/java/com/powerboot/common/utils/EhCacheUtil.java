package com.powerboot.common.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhCacheUtil {

    protected static Logger logger = LoggerFactory.getLogger(EhCacheUtil.class);
    private static CacheManager singletonManager =  CacheManager.create();
    private static final String PRONAME = "xxxxx11111";
    static {
        Cache memoryOnlyCache = singletonManager.getCache(PRONAME);
        if (memoryOnlyCache == null) {
            //项目名称   缓存最大数量 是否缓存成文件 设定缓存是否过期  对象存活时间 无访问多长时间缓存失效
            memoryOnlyCache = new Cache(PRONAME,
                10000,
                false,
                false,
                7200,
                3600);
            singletonManager.addCache(memoryOnlyCache);
        }
    }

    public static Element getElement(String key) {
        Cache cache = singletonManager.getCache(PRONAME);
        Element element = cache.get(key);
        if (element == null) {
            return null;
        }
        return element;
    }

    /**
     * 查询缓存中的数据
     * @param key
     * @return
     */
    @SuppressWarnings("deprecation")
    public static <T> T getValue(String key, Class<T> cls) {
        Cache cache = singletonManager.getCache(PRONAME);
        Element element = cache.get(key);
        if (element == null) {
            return null;
        }
        return (T) element.getValue();
    }

    /**
     * 添加缓存
     * @param key
     * @param value
     */
    public static void setValue(String key,Object value) {
        Cache cache = singletonManager.getCache(PRONAME);
        Element element = new Element(key, value);
        cache.put(element);
    }

    /**
     * 添加缓存
     * @param key
     * @param value
     * @param timeToLiveSeconds
     */
    public static void setValue(String key,Object value,Integer timeToLiveSeconds) {
        Cache cache = singletonManager.getCache(PRONAME);
        Element element = new Element(key, value);
        element.setTimeToLive(timeToLiveSeconds);
        cache.put(element);
    }

    /**
     * 删除缓存
     * @param key
     */
    public static void remove(String key) {
        Cache cache = singletonManager.getCache(PRONAME);
        Element element = cache.get(key);
        if (element != null) {
            cache.remove(key);
        }
    }

}
