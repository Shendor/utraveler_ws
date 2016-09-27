package com.utraveler.ws.service.impl;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.utraveler.auth.AuthenticationService;
import com.utraveler.dao.EventDao;
import com.utraveler.dao.MessageDao;
import com.utraveler.dao.entity.EventEntity;
import com.utraveler.dao.entity.MessageEntity;
import com.utraveler.mapper.Mapper;
import com.utraveler.model.Message;
import com.utraveler.model.User;
import com.utraveler.ws.exception.UTravelerWebServiceException;
import com.utraveler.ws.response.ErrorCode;
import com.utraveler.ws.response.JsonResponse;
import com.utraveler.ws.service.MessageWebService;

public class MessageWebServiceImpl extends BaseWebService implements MessageWebService {

    private static final Logger LOGGER = Logger.getLogger(MessageWebServiceImpl.class);

    private MessageDao messageDao;
    private EventDao eventDao;
    private Mapper<Message, MessageEntity> messageMapper;
    private AuthenticationService authenticationService;


    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }


    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }


    public void setMessageMapper(Mapper<Message, MessageEntity> messageMapper) {
        this.messageMapper = messageMapper;
    }


    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Override
    @Transactional(readOnly = true)
    public JsonResponse getMessagesOfEvent(final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Get Messages");
                EventEntity event = getEventOfUser(eventId);
                if (event != null) {
                    Collection<Message> messages = Lists.newArrayList();
                    for (MessageEntity messageEntity : messageDao.findMessagesOfEvent(eventId)) {
                        messages.add(messageMapper.mapEntity(messageEntity));
                    }
                    LOGGER.info("Operation executed successfully: Get Messages");
                    return messages;
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have any permissions to this Event entity");
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse addMessageToEvent(final Message message, final long eventId) {
        return addMessagesToEvent(Lists.newArrayList(message), eventId);
    }


    @Override
    @Transactional
    public JsonResponse addMessagesToEvent(final List<Message> messages, final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Add Messages");
                EventEntity event = getEventOfUser(eventId);
                int messagesLimit = getCurrentUser().getSetting().getMessagesLimit();
                long messagesQuantity = messageDao.getMessagesQuantity(eventId);
                if (event != null && isExceededLimit(messagesLimit, messagesQuantity + messages.size())) {
                    Collection<MessageEntity> messageEntities = Lists.newArrayList();
                    for (Message message : messages) {
                        messageEntities.add(createMessageEntity(event, message));
                    }
                    messageDao.insert(messageEntities);
                    LOGGER.info("Operation executed successfully: Add Messages");
                    return getInsertedIds(messageEntities);
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have any permissions to this Event entity");
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse deleteMessageFromEvent(final long messageId, final long eventId) {
        return deleteMessageFromEvent(Lists.newArrayList(messageId), eventId);
    }


    @Override
    @Transactional
    public JsonResponse deleteMessageFromEvent(final Collection<Long> messagesId, final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Delete Messages");
                if (isEventBelongToUser(eventId)) {
                    messageDao.deleteFromEvent(messagesId, eventId);
                    LOGGER.info("Operation executed successfully: Delete Messages");
                    return true;
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have any permissions to this Event entity");
            }
        });
    }


    @Override
    @Transactional
    public JsonResponse updateMessage(final Message message, final long messageId, final long eventId) {
        return execute(new WebServiceMethodExecutor() {
            @Override
            public Object execute() {
                LOGGER.info("Initialize operation: Update Message");
                validateRequest(message);
                MessageEntity messageEntity = messageDao.findMessageOfEvent(messageId, eventId);
                if (messageEntity != null) {
                    messageEntity.setText(message.getText());
                    messageEntity.setFacebookPostId(message.getFacebookPostId());
                    messageEntity.setLatitude(message.getCoordinate().getLat());
                    messageEntity.setLongitude(message.getCoordinate().getLng());
                    messageDao.update(messageEntity);
                    LOGGER.info("Operation executed successfully: Update Message");
                    return true;
                }
                throw new UTravelerWebServiceException(ErrorCode.ACCESS_DENIED,
                                                       "User does not have any permissions to this Message entity");
            }
        });
    }


    private MessageEntity createMessageEntity(EventEntity event, Message message) {
        validateRequest(message);
        MessageEntity messageEntity = messageMapper.mapModel(message);
        messageEntity.setEvent(event);
        return messageEntity;
    }


    private boolean isEventBelongToUser(long eventId) {
        User currentUser = getCurrentUser();
        return currentUser != null && eventDao.isBelongToUser(eventId, currentUser.getId());
    }


    private EventEntity getEventOfUser(long eventId) {
        User currentUser = getCurrentUser();
        return eventDao.findEventOfUser(eventId, currentUser.getId());
    }


    private User getCurrentUser() {
        return authenticationService.getCurrentUser();
    }
}
