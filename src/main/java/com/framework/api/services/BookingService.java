package com.framework.api.services;

import com.framework.api.ApiBase;
import com.framework.models.booking.payload.Booking;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.framework.constants.Route.BOOKING;
import static com.framework.constants.Route.PING;

public class BookingService extends ApiBase {
    // @formatter:off

    @Step("Create new booking")
    public Response post(Booking booking) {
        return requestSpec()
                .body(booking)
            .when()
                .post(BOOKING)
            .then()
                .spec(responseSpec(200))
                .extract()
                .response();
    }

    @Step("Update booking")
    public Response update(Booking booking, int bookingId) {
        return requestSpec()
                .pathParam("bookingId", bookingId)
                .cookie("token", getAuthToken())
                .body(booking)
            .when()
                .put(BOOKING + "/{bookingId}")
            .then()
                .spec(responseSpec(200))
                .extract()
                .response();
    }

    @Step("Delete booking")
    public Response delete(int bookingId) {
        return requestSpec()
                .pathParam("bookingId", bookingId)
                .cookie("token", getAuthToken())
            .when()
                .delete(BOOKING + "/{bookingId}")
            .then()
                .spec(responseSpec(201))
                .extract()
                .response();
    }

    @Step("Get booking")
    public Response get(int bookingId, int expectedStatusCode) {
        return requestSpec()
                .pathParam("bookingId", bookingId)
            .when()
                .get(BOOKING + "/{bookingId}")
            .then()
                .spec(responseSpec(expectedStatusCode))
                .extract()
                .response();
    }

    @Step("Ping - health check request")
    public void healthCheckRequest() {
         requestSpec()
            .when()
                .get(PING)
            .then()
                .spec(responseSpec(201));
    }

}
