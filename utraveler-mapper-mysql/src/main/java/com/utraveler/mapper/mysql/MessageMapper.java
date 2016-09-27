package com.utraveler.mapper.mysql;

import com.utraveler.dao.entity.MessageEntity;
import com.utraveler.dao.mysql.entity.MessageEntityImpl;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.GeoCoordinate;
import com.utraveler.model.Message;

public class MessageMapper implements Mapper<Message, MessageEntity> {

    @Override
    public MessageEntity mapModel(Message model) {
        MessageEntityImpl messageEntity = new MessageEntityImpl();
        messageEntity.setId(model.getId());
        messageEntity.setText(model.getText());
        messageEntity.setDate(model.getDate());
        messageEntity.setChangeDate(model.getChangeDate());
        messageEntity.setFacebookPostId(model.getFacebookPostId());
        if (model.getCoordinate() != null) {
            messageEntity.setLatitude(model.getCoordinate().getLat());
            messageEntity.setLongitude(model.getCoordinate().getLng());
        }
        return messageEntity;
    }


    @Override
    public Message mapEntity(MessageEntity entity) {
        Message message = new Message();
        message.setId(entity.getId());
        message.setText(entity.getText());
        message.setDate(entity.getDate());
        message.setChangeDate(entity.getChangeDate());
        message.setFacebookPostId(entity.getFacebookPostId());
        if (entity.getLatitude() != 0 && entity.getLongitude() != 0) {
            message.setCoordinate(new GeoCoordinate(entity.getLatitude(), entity.getLongitude()));
        }
        return message;
    }
}
