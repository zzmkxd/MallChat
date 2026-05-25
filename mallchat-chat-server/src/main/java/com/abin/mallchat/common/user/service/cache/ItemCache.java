package com.abin.mallchat.common.user.service.cache;

import lombok.RequiredArgsConstructor;

import com.abin.mallchat.common.user.dao.ItemConfigDao;
import com.abin.mallchat.common.user.domain.entity.ItemConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description: 用户相关缓存
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-03-27
 */
@Component
@RequiredArgsConstructor
public class ItemCache {//todo 多级缓存    
    private final ItemConfigDao itemConfigDao;

    @Cacheable(cacheNames = "item", key = "'itemsByType:'+#type")
    public List<ItemConfig> getByType(Integer type) {
        return itemConfigDao.getByType(type);
    }

    @Cacheable(cacheNames = "item", key = "'item:'+#itemId")
    public ItemConfig getById(Long itemId) {
        return itemConfigDao.getById(itemId);
    }
}
