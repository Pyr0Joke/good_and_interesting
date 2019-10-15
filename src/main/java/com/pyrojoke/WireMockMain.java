package com.pyrojoke;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockMain {
    public static void main(String[] args) {

        WireMockServer wireMockServer = new WireMockServer();
        configureFor("0.0.0.0", 8080);
        wireMockServer.start();
        StubMapping foo = stubFor(get(urlEqualTo("/api/me"))
               .willReturn(aResponse()
                        .withStatus(200)
                       .withBody(returnFailedPositions())));
        wireMockServer.addStubMapping(foo);
    }

    static String returnFailedPositions(){
        JsonObjectBuilder user = Json.createObjectBuilder();
        user.add("firstName", "Alexander");
        user.add("lastName", "Lipatov");

        return Json.createObjectBuilder()
                .addAll(user)
                .build().toString();
    }
}
