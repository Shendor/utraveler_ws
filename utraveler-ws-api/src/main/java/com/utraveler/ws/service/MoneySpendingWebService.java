package com.utraveler.ws.service;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.utraveler.model.MoneySpending;
import com.utraveler.ws.response.JsonResponse;

@Produces("application/json;charset=UTF-8")
@Path("/spending-rest/me/")
public interface MoneySpendingWebService {

    @GET
    @Path("events/{eventId}/moneyspendings/")
    JsonResponse getMoneySpendingsForEvent(@PathParam("eventId") long eventId);


    @GET
    @Path("events/{eventId}/moneyspendings/amount")
    JsonResponse getTotalSpentMoneyForEvent(@PathParam("eventId") long eventId);


    @POST
    @Path("events/{eventId}/moneyspending/insert")
    @Consumes("application/json")
    JsonResponse addMoneySpending(MoneySpending moneySpending, @PathParam("eventId") long eventId);


    @POST
    @Path("events/{eventId}/moneyspending/insert/many")
    @Consumes("application/json")
    JsonResponse addMoneySpendings(Collection<MoneySpending> moneySpendings, @PathParam("eventId") long eventId);


    @POST
    @Path("events/{eventId}/moneyspendings/{moneySpendingId}/delete")
    JsonResponse deleteMoneySpending(@PathParam("moneySpendingId") long moneySpendingId, @PathParam("eventId") long eventId);
}
