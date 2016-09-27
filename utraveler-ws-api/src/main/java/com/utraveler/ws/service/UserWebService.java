package com.utraveler.ws.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.utraveler.model.BaseUser;
import com.utraveler.model.User;
import com.utraveler.model.UserSetting;
import com.utraveler.ws.response.JsonResponse;

@Produces("application/json;charset=UTF-8")
@Path("/user-rest/")
public interface UserWebService {

    @GET
    @Path("/me")
    JsonResponse getUserProfile();


    @POST
    @Consumes("application/json")
    @Path("/register/{appCode}")
    JsonResponse registerUser(@PathParam("appCode") String appCode, User user);


    @POST
    @Consumes("application/json")
    @Path("/me/update")
    JsonResponse updateUser(BaseUser user);


    @POST
    @Consumes("application/json")
    @Path("/me/settings/update")
    JsonResponse updateUserSettings(long userId, UserSetting userSetting);


    @GET
    @Path("/me/settings")
    JsonResponse getUserSettings();


    @POST
    @Path("/requestResetPassword/{email}")
    JsonResponse requestResetPassword(@PathParam("email") String email);

}
