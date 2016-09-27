package com.utraveler.ws.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.utraveler.model.Route;
import com.utraveler.ws.response.JsonResponse;

@Produces("application/json;charset=UTF-8")
@Path("/route-rest/")
public interface RouteWebService {

    @POST
    @Path("me/events/{eventId}/routes/insert")
    @Consumes("application/json")
    JsonResponse addRoute(Route route, @PathParam("eventId") long eventId);


    @POST
    @Path("me/events/{eventId}/routes/{routeId}/delete")
    JsonResponse deleteRoute(@PathParam("routeId") long routeId, @PathParam("eventId") long eventId);


    @GET
    @Path("me/events/{eventId}/routes")
    JsonResponse getRoutes(@PathParam("eventId") long eventId);
}
