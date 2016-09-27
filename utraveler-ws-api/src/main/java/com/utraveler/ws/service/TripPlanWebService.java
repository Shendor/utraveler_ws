package com.utraveler.ws.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.utraveler.model.TripPlan;
import com.utraveler.ws.response.JsonResponse;

@Produces("application/json;charset=UTF-8")
@Path("/trip-plan-rest/")
public interface TripPlanWebService {

    @GET
    @Path("/me/events/{eventId}/trip-plan")
    JsonResponse getTripPlanOfEvent(@PathParam("eventId") long eventId);


    @POST
    @Consumes("application/json")
    @Path("/me/events/{eventId}/trip-plan/insert")
    JsonResponse addTripPlanToEvent(TripPlan tripPlan, @PathParam("eventId") long eventId);

    @POST
    @Path("/me/events/{eventId}/trip-plan/delete")
    JsonResponse deleteTripPlanFromEvent(@PathParam("eventId") long eventId);


    @POST
    @Consumes("application/json")
    @Path("/me/events/{eventId}/trip-plan/update")
    JsonResponse updateTripPlan(TripPlan tripPlan, @PathParam("eventId") long eventId);
}
