package com.framework.api;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;
import java.util.Map;

import static com.framework.constants.Route.AUTH;
import static com.framework.config.ConfigHelper.envConfig;


public class ApiBase {

    public RequestSpecification requestSpec() {
        return RestAssured
                .given().log().all()
                .baseUri(envConfig.apiUrl())
                .contentType(ContentType.JSON)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured());
    }

    @Step("Get auth token")
    public String getAuthToken() {
        Map<String, String> body = new HashMap<>();
        body.put("username", envConfig.username());
        body.put("password", envConfig.password());

        return requestSpec()
                .baseUri(envConfig.apiUrl())
                .body(body)
                .when()
                .post(AUTH)
                .then()
                .extract().response().path("token");
    }

    public ResponseSpecification responseSpec(int expectedStatusCode) {
        return RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured())
                .expect()
                .statusCode(expectedStatusCode);
    }
}
