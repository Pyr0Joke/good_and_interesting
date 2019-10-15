package com.pyrojoke;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;

public class StubResponseTransformerWithParams extends ResponseTransformer {

    @Override
    public Response transform(Request request, Response response, FileSource files, Parameters parameters) {
        return Response.Builder.like(response)
//                .but()
//                .body(WireMockAddGoods.returnFailedPositions())
//                .incrementInitialDelay(1000)
                .build();
    }

    @Override
    public String getName() {
        return "stub-transformer-with-params";
    }
}