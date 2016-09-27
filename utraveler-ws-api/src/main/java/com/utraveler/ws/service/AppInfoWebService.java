package com.utraveler.ws.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.utraveler.ws.response.JsonResponse;

@Produces("application/json;charset=UTF-8")
@Path("/app-settings-rest/")
public interface AppInfoWebService {

    @GET
    @Path("/get")
    JsonResponse getCurrentAppInfo();

}
