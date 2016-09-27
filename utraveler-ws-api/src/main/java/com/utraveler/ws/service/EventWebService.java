package com.utraveler.ws.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.utraveler.model.Event;
import com.utraveler.ws.response.JsonResponse;

@Produces("application/json;charset=UTF-8")
@Path("/event-rest/")
public interface EventWebService {

    @GET
    @Path("me/events/{eventId}")
    JsonResponse getEvent(@PathParam("eventId") long eventId);


    @GET
    @Path("{userId}/events/")
    JsonResponse getEvents(@PathParam("userId") long userId);


    @POST
    @Consumes("application/json")
    @Path("me/events/insert/")
    JsonResponse insertEvent(Event event);


    @POST
    @Consumes("application/json")
    @Path("me/events/update/")
    JsonResponse updateEvent(Event event);


    @POST
    @Consumes("application/json")
    @Path("me/events/insert/many")
    JsonResponse insertEvents(List<Event> events);


    @POST
    @Path("me/events/{eventId}/delete")
    JsonResponse deleteEvent(@PathParam("eventId") long eventId);


    @GET
    @Path("{userId}/events/count")
    JsonResponse getEventsQuantity(@PathParam("userId") long userId);
}
