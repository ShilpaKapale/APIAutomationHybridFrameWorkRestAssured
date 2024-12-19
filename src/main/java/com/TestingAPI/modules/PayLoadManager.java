package com.TestingAPI.modules;

import com.TestingAPI.pojos_gson.Booking;
import com.TestingAPI.pojos_gson.BookingResponse;
import com.TestingAPI.pojos_gson.Bookingdates;
import com.google.gson.Gson;

public class PayLoadManager {

    //Converting java object to String

    Gson gson;
    public String createPayloadBookingAsString(){

        Booking booking = new Booking();
        booking.setFirstname("James");
        booking.setLastname("Brown");
        booking.setTotalprice(112);
        booking.setDepositpaid(true);

        Bookingdates bookingdate = new Bookingdates();
        bookingdate.setCheckin("2024-01-01");
        bookingdate.setCheckout("2024-01-01");
        booking.setBookingdates(bookingdate);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking);//Here booking is object which to be converted to String

        //Java Object -> String using GSON - i.e. Serialization

        Gson gson = new Gson();
        String jsonStringpayload = gson.toJson(booking);
        System.out.println(jsonStringpayload);
        return jsonStringpayload;
    }
    //Converting the String to the JAVA Object - DeSerialization

    public BookingResponse bookingResponseJava(String responseString)
    {
        gson = new Gson();
        BookingResponse bookingResponse = gson.fromJson(responseString, BookingResponse.class);
        return bookingResponse;
    }


}
