package com.xmed;

import com.xmed.resources.TransactionRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
@Path("/transactionservice")
@Scope("request")

public class TransactionsController {


    @Context
    UriInfo uri;


    @GET
    @Produces("application/xml")
    public String getMessages() {
        return "test";
    }

    @POST
    @Path("transactions")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewTransaction(TransactionRequest transactionRequest) {


        return Response.status(201).
                header("Location", uri.getBaseUri() + "transactionservice/transactions/")
                .build();
    }



}
