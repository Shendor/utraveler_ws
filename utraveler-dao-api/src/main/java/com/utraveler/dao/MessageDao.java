package com.utraveler.dao;

import java.util.Collection;

import com.utraveler.dao.common.Dao;
import com.utraveler.dao.entity.MessageEntity;

public interface MessageDao extends Dao<MessageEntity>, DeleteEventDao {

    Collection<? extends MessageEntity> findMessagesOfEvent(long eventId);


    MessageEntity findMessageOfEvent(long id, long eventId);


    long getMessagesQuantity(long eventId);
}
