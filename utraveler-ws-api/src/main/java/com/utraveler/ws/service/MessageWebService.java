package com.utraveler.ws.service;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.utraveler.model.Message;
import com.utraveler.ws.response.JsonResponse;

@Produces("application/json;charset=UTF-8")
@Path("/message-rest/")
public interface MessageWebService {

    @GET
    @Path("/me/events/{eventId}/messages")
    JsonResponse getMessagesOfEvent(@PathParam("eventId") long eventId);


    @POST
    @Consumes("application/json")
    @Path("/me/events/{eventId}/messages/insert")
    JsonResponse addMessageToEvent(Message message, @PathParam("eventId") long eventId);


    @POST
    @Consumes("application/json")
    @Path("me/events/{eventId}/messages/insert/many")
    JsonResponse addMessagesToEvent(List<Message> messages, @PathParam("eventId") long eventId);


    @POST
    @Path("/me/events/{eventId}/messages/{messageId}/delete")
    JsonResponse deleteMessageFromEvent(@PathParam("messageId") long messageId, @PathParam("eventId") long eventId);


    @POST
    @Path("/me/events/{eventId}/messages/delete")
    JsonResponse deleteMessageFromEvent(Collection<Long> messagesId, @PathParam("eventId") long eventId);


    @POST
    @Consumes("application/json")
    @Path("/me/events/{eventId}/messages/{messageId}/update")
    JsonResponse updateMessage(Message message, @PathParam("messageId") long messageId, @PathParam("eventId") long eventId);
}
