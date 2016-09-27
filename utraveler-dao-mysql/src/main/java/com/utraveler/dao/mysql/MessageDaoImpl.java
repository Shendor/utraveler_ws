package com.utraveler.dao.mysql;

import java.util.Collection;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.utraveler.dao.MessageDao;
import com.utraveler.dao.entity.MessageEntity;
import com.utraveler.dao.mysql.common.BaseDao;
import com.utraveler.dao.mysql.entity.MessageEntityImpl;

@Repository("messageDao")
public class MessageDaoImpl extends BaseDao<MessageEntity> implements MessageDao {

    @Override
    public Collection<? extends MessageEntity> findMessagesOfEvent(long eventId) {
        Query query = getSession().getNamedQuery("Message.findMessagesOfEvent");
        query.setLong("eventId", eventId);

        return query.list();
    }


    @Override
    public MessageEntity findMessageOfEvent(long id, long eventId) {
        Query query = getSession().getNamedQuery("Message.findMessageOfEvent");
        query.setLong("id", id);
        query.setLong("eventId", eventId);

        return (MessageEntity)query.uniqueResult();
    }


    @Override
    public MessageEntity getById(long id) {
        return (MessageEntityImpl)getSession().get(MessageEntityImpl.class, id);
    }


    @Override
    public boolean deleteFromEvent(long eventId) {
        Query query = getSession().getNamedQuery("Message.deleteFromEvent");
        query.setLong("eventId", eventId);
        return query.executeUpdate() > 0;
    }


    @Override
    public boolean deleteFromEvent(Collection<Long> messagesId, long eventId) {
        Query query = getSession().getNamedQuery("Message.deleteMessagesFromEvent");
        query.setLong("eventId", eventId);
        query.setParameterList("messagesId", messagesId);
        return query.executeUpdate() > 0;
    }


    @Override
    public long getMessagesQuantity(long eventId) {
        Query query = getSession().getNamedQuery("Message.getMessagesQuantity");
        query.setLong("eventId", eventId);

        return (Long)query.uniqueResult();
    }
}
