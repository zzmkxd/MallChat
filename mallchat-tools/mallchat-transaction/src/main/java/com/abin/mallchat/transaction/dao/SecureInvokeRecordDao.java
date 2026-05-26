package com.abin.mallchat.transaction.dao;

import com.abin.mallchat.transaction.domain.entity.SecureInvokeRecord;
import com.abin.mallchat.transaction.mapper.SecureInvokeRecordMapper;
import com.abin.mallchat.transaction.service.SecureInvokeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Description:
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-08-06
 */
@Component
public class SecureInvokeRecordDao extends ServiceImpl<SecureInvokeRecordMapper, SecureInvokeRecord> {

    public List<SecureInvokeRecord> getWaitRetryRecords() {
        LocalDateTime now = LocalDateTime.now();
        //查2分钟前的失败数据。避免刚入库的数据被查出来
        LocalDateTime afterTime = now.minusMinutes((long) SecureInvokeService.RETRY_INTERVAL_MINUTES);
        return lambdaQuery()
                .eq(SecureInvokeRecord::getStatus, SecureInvokeRecord.STATUS_WAIT)
                .lt(SecureInvokeRecord::getNextRetryTime, LocalDateTime.now())
                .lt(SecureInvokeRecord::getCreateTime, afterTime)
                .list();
    }
}
