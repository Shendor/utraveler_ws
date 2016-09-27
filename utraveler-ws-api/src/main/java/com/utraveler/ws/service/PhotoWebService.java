package com.utraveler.ws.service;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.utraveler.model.Photo;
import com.utraveler.ws.response.JsonResponse;

@Produces("application/json;charset=UTF-8")
@Path("/photo-rest/")
public interface PhotoWebService {

    @GET
    @Path("me/events/{eventId}/photos")
    JsonResponse getPhotosOfEvent(@PathParam("eventId") long eventId);


    @GET
    @Path("me/photos/count")
    JsonResponse getPhotosQuantityOfUser();


    @POST
    @Consumes("application/json")
    @Path("me/events/{eventId}/photos/{photoId}/update/")
    JsonResponse updatePhoto(Photo photo, @PathParam("photoId") long photoId, @PathParam("eventId") long eventId);


    @POST
    @Consumes("application/json")
    @Path("me/events/{eventId}/photos/update/many")
    JsonResponse updatePhotos(List<Photo> photos, @PathParam("eventId") long eventId);


    @POST
    @Consumes("application/json")
    @Path("me/events/{eventId}/photos/insert/")
    JsonResponse addPhotoToEvent(Photo photo, @PathParam("eventId") long eventId);


    @POST
    @Consumes("application/json")
    @Path("me/events/{eventId}/photos/insert/many")
    JsonResponse addPhotosToEvent(List<Photo> photos, @PathParam("eventId") long eventId);


    @POST
    @Path("me/events/{eventId}/photos/{photoId}/delete")
    JsonResponse deletePhotoFromEvent(@PathParam("photoId") long photoId, @PathParam("eventId") long eventId);


    @POST
    @Path("/me/events/{eventId}/photos/delete")
    JsonResponse deletePhotosFromEvent(Collection<Long> photosId, @PathParam("eventId") long eventId);
}
