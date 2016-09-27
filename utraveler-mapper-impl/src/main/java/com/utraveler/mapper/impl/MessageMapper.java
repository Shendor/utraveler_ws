package com.utraveler.mapper.impl;

import com.utraveler.dao.entity.MessageEntity;
import com.utraveler.dao.entity.impl.MessageEntityImpl;
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
        if (model.getLocation() != null) {
            messageEntity.setLatitude(model.getLocation().getLatitude());
            messageEntity.setLongitude(model.getLocation().getLongitude());
        }
        return messageEntity;
    }


    @Override
    public Message mapEntity(MessageEntity entity) {
        Message message = new Message();
        message.setId(entity.getId());
        message.setText(entity.getText());
        message.setDate(entity.getDate());
        if (entity.getLatitude() != 0 && entity.getLongitude() != 0) {
            message.setLocation(new GeoCoordinate(entity.getLatitude(), entity.getLongitude()));
        }
        return message;
    }
}
