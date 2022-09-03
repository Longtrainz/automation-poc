package com.tests.api.booking;

import com.framework.api.services.BookingService;
import com.framework.helpers.Utils;
import com.framework.models.booking.payload.Booking;
import com.framework.models.booking.response.BookingResponse;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.qameta.allure.Allure.step;

@Feature("Booking")
@Story("Booking API")
@Tag("api")
@ExtendWith({AllureJunit5.class})
public class GetBookingTest {
    private int createdBookingId;
    private Booking bookingPayload;
    private final Utils utils = new Utils();
    private final BookingService bookingService = new BookingService();

    @BeforeEach()
    void createBooking() {
        bookingService.healthCheckRequest();

        step("Create booking and extract it's booking id for GET request", () -> {
            bookingPayload = utils.getRandomBookingData();
            createdBookingId = bookingService.post(bookingPayload).as(BookingResponse.class).bookingid();
        });
    }

    @Test
    @DisplayName("Get booking via API test")
    void getBookingTest() {
        Response response = bookingService.get(createdBookingId, 200);

        step("Verify payload booking object equals to response booking object", () -> {
            utils.verifyObjectsEqual(response.as(Booking.class), bookingPayload);
        });
    }
}
