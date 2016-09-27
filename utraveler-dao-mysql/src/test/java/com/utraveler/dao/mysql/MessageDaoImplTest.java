package com.utraveler.dao.mysql;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.utraveler.dao.MessageDao;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.MessageEntity;
import com.utraveler.dao.mysql.common.TestPersistenceDao;
import com.utraveler.dao.mysql.common.UTravelerTestFixture;
import com.utraveler.dao.mysql.common.annotations.ClearTable;
import com.utraveler.dao.mysql.entity.MessageEntityImpl;
import com.utraveler.dao.mysql.entity.UserEntityImpl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ClearTable(tables = {UTravelerTestFixture.MESSAGE_TABLE, UTravelerTestFixture.EVENT_TABLE, UTravelerTestFixture.USER_TABLE})
public class MessageDaoImplTest extends UTravelerTestFixture {

    private MessageDao messageDao;
    private TestPersistenceDao testPersistenceDao;
    private UserEntityImpl user;


    @Before
    public void setUp() {
        messageDao = context.getBean(MessageDao.class);
        testPersistenceDao = context.getBean(TestPersistenceDao.class);
        user = testPersistenceDao.saveUser("User");
    }


    @Test
    public void testInsertMessage() {
        MessageEntity message = saveMessage();

        assertNotNull(getById(messageDao, message));
    }


    @Test
    public void testUpdate() {
        MessageEntity message = saveMessage();
        message.setText("New text");
        messageDao.update(message);

        assertEquals(message.getText(), getById(messageDao, message).getText());
    }


    @Test
    public void testDeleteMessagesFromEvent() {
        MessageEntity message = saveMessage();

        messageDao.deleteFromEvent(Lists.newArrayList(message.getId()), message.getEvent().getId());

        assertNull(getById(messageDao, message));
    }


    @Test
    public void testDeleteAllMessagesFromEvent() {
        MessageEntity message = saveMessage();

        messageDao.deleteFromEvent(message.getEvent().getId());

        assertNull(getById(messageDao, message));
    }


    @Test
    public void testFindMessageOfEvent_MessageFound() {
        MessageEntity message = saveMessage();

        assertNotNull(messageDao.findMessageOfEvent(message.getId(), message.getEvent().getId()));
        assertEquals(1, messageDao.findMessagesOfEvent(message.getEvent().getId()).size());
        assertEquals(1, messageDao.getMessagesQuantity(message.getEvent().getId()));
    }


    private MessageEntity saveMessage() {
        EventEntity event = testPersistenceDao.saveEvent(user);
        MessageEntity message = createMessage(event);
        messageDao.insert(message);
        return message;
    }


    private MessageEntity createMessage(EventEntity event) {
        MessageEntityImpl message = new MessageEntityImpl();
        message.setDate(DateTime.now());
        message.setEvent(event);
        message.setText("Message");
        return message;
    }
}
