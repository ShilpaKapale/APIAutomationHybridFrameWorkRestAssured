package com.TestingAPI.modules;

import com.TestingAPI.pojos_gson.*;
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
//Deserialization for booking response
    public BookingResponse bookingResponseJava(String responseString)
    {
        gson = new Gson();
        BookingResponse bookingResponse = gson.fromJson(responseString, BookingResponse.class);
        return bookingResponse;
    }

    //Deserialization for bookingid

    public Booking getResponseFromJSON(String getResponse) {
        gson = new Gson();
        Booking booking = gson.fromJson(getResponse,Booking.class);
        return booking;
    }

    //Token serialization and deserialization
    //JSON to JAVA - serialization

    public String setAuthPayload(){
        Auth auth = new Auth();
        auth.setUsername("admin");
        auth.setPassword("password123");
        gson = new Gson();
        String jsonPayloadString = gson.toJson(auth);
        System.out.println(jsonPayloadString);
        return jsonPayloadString;
    }
    // Java to JSON - deserialization

    public String getTokenFromJSON(String tokenResponse){
        gson = new Gson();
        TokenResponse tokenResponse1 = gson.fromJson(tokenResponse, TokenResponse.class);
        return tokenResponse1.getToken();
    }

    public String fullUpdatePayloadAsString(){
        Booking booking = new Booking();
        booking.setFirstname("Priya");
        booking.setLastname("Dutta");
        booking.setTotalprice(122);
        booking.setDepositpaid(true);

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckin("2024-02-05");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");
        return gson.toJson(booking);
    }
}
