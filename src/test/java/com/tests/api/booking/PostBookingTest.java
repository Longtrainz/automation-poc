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
public class PostBookingTest {
    private final Utils utils = new Utils();
    private final BookingService bookingService = new BookingService();

    @BeforeEach()
    void before() {
        bookingService.healthCheckRequest();
    }

    @Test
    @DisplayName("Create booking via API test")
    void postBookingTest() {
        // Get random Booking data object
        Booking expectedBookingData = utils.getRandomBookingData();

        // Create new Booking using POST API method and extract response
        Response response = bookingService.post(expectedBookingData);

        // Get Booking object from response
        Booking actualBookingData = response.as(BookingResponse.class).booking();

        step("Verify payload booking object equals to response booking object", () -> {
            utils.verifyObjectsEqual(actualBookingData, expectedBookingData);
        });
    }
}
