package com.pyrojoke;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class WireMockAddGoods {
//    public static void main(String[] args) {
//
////        WireMockServer wireMockServer = new WireMockServer();
////        configureFor("0.0.0.0", 8080);
//        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8080).extensions(new StubResponseTransformerWithParams()));
//        wireMockServer.start();
////        StubMapping foo = stubFor(get(urlEqualTo("/api/v1/order/addGoods"))
////                .willReturn(aResponse()
////                        .withStatus(200)
////                        .withBody(returnFailedPositions())));
//        StubMapping foo = stubFor(get(urlPathMatching("/api/v1/order/addGoods")));
//        wireMockServer.addStubMapping(foo);
//    }
//
//    static String getUserJson() {
//        return Json.createObjectBuilder()
//                .add("firstName", "Slawomir")
//                .add("lastName", "Radzyminski")
//                .add("age", "18")
//                .build().toString();
//    }
//
//    static String returnFailedPositions(){
//        JsonArrayBuilder builder = Json.createArrayBuilder();
//        JsonObjectBuilder user = Json.createObjectBuilder();
//        user.add("firstName", "firstName");
//        user.add("lastName", "lastName");
//
//        return Json.createObjectBuilder()
//                .add("failedPositions", user.build().toString())
//                .add("emptyarray", builder)
//                .build().toString();
//    }
}

