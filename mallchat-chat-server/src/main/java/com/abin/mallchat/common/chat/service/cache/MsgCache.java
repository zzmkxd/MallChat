package com.abin.mallchat.common.chat.service.cache;
    import lombok.RequiredArgsConstructor;
    import com.abin.mallchat.common.chat.dao.MessageDao;
    import com.abin.mallchat.common.chat.domain.entity.Message;
    import com.abin.mallchat.common.user.dao.BlackDao;
    import com.abin.mallchat.common.user.dao.RoleDao;
    import com.abin.mallchat.common.user.dao.UserDao;
    import org.springframework.cache.annotation.CacheEvict;
    import org.springframework.cache.annotation.Cacheable;
    import org.springframework.stereotype.Component;
    /**
 * Description: 消息相关缓存
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-03-27
 */
@Component
@RequiredArgsConstructor
public class MsgCache {    
    private final UserDao userDao;
    private final BlackDao blackDao;
    private final RoleDao roleDao;
    private final MessageDao messageDao;
    @Cacheable(cacheNames = "msg", key = "'msg'+#msgId")
    public Message getMsg(Long msgId) {
        return messageDao.getById(msgId);
    }

    @CacheEvict(cacheNames = "msg", key = "'msg'+#msgId")
    public Message evictMsg(Long msgId) {
        return null;
    }
}
