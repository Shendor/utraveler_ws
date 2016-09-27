package com.utraveler.mapper.impl;

import org.joda.time.DateTime;
import org.testng.annotations.Test;

import com.utraveler.dao.entity.MessageEntity;
import com.utraveler.dao.entity.impl.MessageEntityImpl;
import com.utraveler.model.GeoCoordinate;
import com.utraveler.model.Message;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class MessageMapperTest {

    @Test
    public void testMapEntity_AllFieldsValid_Success()
            throws Exception {
        MessageMapper messageMapper = new MessageMapper();

        MessageEntityImpl messageEntity = createMessageEntity();

        Message message = messageMapper.mapEntity(messageEntity);

        assertEquals(message.getId(), messageEntity.getId());
        assertEquals(message.getText(), messageEntity.getText());
        assertEquals(message.getLocation().getLatitude(), messageEntity.getLatitude());
        assertEquals(message.getLocation().getLongitude(), messageEntity.getLongitude());
        assertEquals(message.getDate(), messageEntity.getDate());
    }


    @Test
    public void testMapEntity_NoLocation_LocationIsNull()
            throws Exception {
        MessageMapper messageMapper = new MessageMapper();

        MessageEntityImpl messageEntity = createMessageEntity();
        messageEntity.setLatitude(0);
        messageEntity.setLongitude(0);

        Message message = messageMapper.mapEntity(messageEntity);

        assertNull(message.getLocation());
    }


    @Test
    public void testMapModel_AllFieldsValid_Success()
            throws Exception {
        MessageMapper messageMapper = new MessageMapper();

        Message message = createMessage();

        MessageEntity messageEntity = messageMapper.mapModel(message);

        assertEquals(messageEntity.getId(), message.getId());
        assertEquals(messageEntity.getText(), message.getText());
        assertEquals(messageEntity.getLatitude(), message.getLocation().getLatitude());
        assertEquals(messageEntity.getLongitude(), message.getLocation().getLongitude());
        assertEquals(messageEntity.getDate(), message.getDate());
    }


    @Test
    public void testMapModel_NoLocation_LocationIsZero()
            throws Exception {
        MessageMapper messageMapper = new MessageMapper();

        Message message = createMessage();
        message.setLocation(null);

        MessageEntity messageEntity = messageMapper.mapModel(message);

        assertEquals(messageEntity.getLatitude(), 0d);
        assertEquals(messageEntity.getLongitude(), 0d);
    }


    private Message createMessage() {
        Message message = new Message();
        message.setId(100);
        message.setText("Text");
        message.setLocation(new GeoCoordinate(10, 20));
        message.setDate(DateTime.now());
        return message;
    }


    private MessageEntityImpl createMessageEntity() {
        MessageEntityImpl messageEntity = new MessageEntityImpl();
        messageEntity.setId(100);
        messageEntity.setLatitude(10);
        messageEntity.setLongitude(20);
        messageEntity.setText("Text");
        messageEntity.setDate(DateTime.now());
        return messageEntity;
    }
}
