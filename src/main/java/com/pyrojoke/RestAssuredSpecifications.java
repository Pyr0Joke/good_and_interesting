package com.pyrojoke;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestAssuredSpecifications {

    static RequestSpecification getRequestSpecification(){
        return new RequestSpecBuilder().setBaseUri("http://localhost").addFilter(new ResponseLoggingFilter()).build();
    }

    static ResponseSpecification getResponseSpecification(){
        return new ResponseSpecBuilder().expectStatusCode(200).build();
    }
}
