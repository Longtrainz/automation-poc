package com.framework.helpers;

import com.framework.models.booking.payload.Booking;
import com.framework.models.booking.payload.BookingDates;
import com.framework.models.contact_info.ContactInfo;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

public class Utils {
    private final Faker faker = new Faker();

    /**
     * Generate random contact info for sending message form
     *
     * @return contact info
     */
    public ContactInfo getRandomContactInfo() {
        return new ContactInfo()
                .name(faker.name().firstName())
                .email(faker.internet().emailAddress())
                .phone(faker.phoneNumber().phoneNumber())
                .subject(faker.name().fullName())
                .message(faker.lorem().fixedString(20));
    }

    /**
     * Generate random booking data
     *
     * @return booking data
     */
    public Booking getRandomBookingData() {
        return new Booking()
                .firstname(faker.name().firstName())
                .lastname(faker.internet().emailAddress())
                .additionalneeds(faker.animal().name())
                .totalprice(faker.random().nextInt(10, 1000))
                .depositpaid(faker.random().nextBoolean())
                .bookingdates(new BookingDates()
                        .checkin(getDate(0)).checkout(getDate(2)));
    }


    /**
     * Get date in yyyy-MM-dd format
     *
     * @param daysInFuture days to add to current date
     * @return date in format yyyy-MM-dd
     */
    public String getDate(int daysInFuture) {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, daysInFuture);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    @Step("Verify objects are equal")
    public <T> void verifyObjectsEqual(T objectActual, T objectExpected) {
        assertThat(objectActual)
                .as("Objects are not equal")
                .isEqualTo(objectExpected);
    }

    @Step("Validate json response schema: '{pathToSchema}'")
    public void validateJsonSchema(Response resp, String pathToSchema) {
        resp.then().body(matchesJsonSchemaInClasspath(pathToSchema));
    }
}
