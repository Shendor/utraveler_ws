package com.utraveler.ws.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.utraveler.model.Pushpin;
import com.utraveler.ws.response.JsonResponse;

@Produces("application/json")
@Path("/pushpin-rest/")
public interface PhotoPushpinWebService {

    @GET
    @Path("events/{eventId}/pushpins")
    JsonResponse getPushpinsOfEvent(@PathParam("eventId") long eventId);


    @POST
    @Path("me/pushpins/{pushpinId}/photos/{photoId}/insert")
    JsonResponse addPhotoToPushpin(@PathParam("photoId") long photoId, @PathParam("pushpinId") long pushpinId);


    @POST
    @Path("me/pushpins/{pushpinId}/photos/{photoId}/delete")
    JsonResponse deletePhotoFromPushpin(@PathParam("photoId") long photoId, @PathParam("pushpinId") long pushpinId);


    @POST
    @Path("me/events/{eventId}/pushpins/insert")
    @Consumes("application/json")
    JsonResponse addPushpinToEvent(Pushpin pushpin, @PathParam("eventId") long eventId);


    @POST
    @Path("me/events/{eventId}/pushpins/{pushpinId}/delete")
    JsonResponse deletePushpin(@PathParam("pushpinId") long pushpinId, @PathParam("eventId") long eventId);


    @GET
    @Path("pushpins/{pushpinId}/photos/quantity")
    JsonResponse getPhotosQuantity(@PathParam("pushpinId") long pushpinId);
}
