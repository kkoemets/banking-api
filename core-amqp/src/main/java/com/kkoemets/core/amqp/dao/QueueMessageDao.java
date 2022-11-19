package com.kkoemets.core.amqp.dao;

import com.kkoemets.core.amqp.mybatis.annotation.CoreAmqpDao;
import com.kkoemets.core.amqp.service.AddQueueMessageDto;
import org.apache.ibatis.annotations.Param;

@CoreAmqpDao
public interface QueueMessageDao {

    void add(@Param("dto") AddQueueMessageDto dto);

}
