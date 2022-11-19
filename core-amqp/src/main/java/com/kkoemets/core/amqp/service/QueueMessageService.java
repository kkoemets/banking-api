package com.kkoemets.core.amqp.service;

import com.kkoemets.core.amqp.dao.QueueMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueMessageService {

    @Autowired
    private QueueMessageDao dao;

    public void add(AddQueueMessageDto dto) {
        dao.add(dto);
    }

}
