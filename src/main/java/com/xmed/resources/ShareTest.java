package com.xmed.resources;

import javax.ws.rs.core.Response;

/**
 * Handler for requests to Lambda function.
 */
public class ShareTest  {


    public Object handleRequest(final Object input) {

        //QueryParams queryParams = new Gson().fromJson(input.toString(), QueryParams.class);

        //String queryCreateTest = createQueryCreateTest(queryParams);
        //String queryUpdateTestTable = createQueryUpdateTestTable(queryParams);


        return Response
                .status(Response.Status.OK)
                .entity("")
                .build();

    }


}
