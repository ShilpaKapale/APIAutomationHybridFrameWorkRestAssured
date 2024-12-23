package Integration;

import base.BaseTest;
import com.TestingAPI.endpoints.APIConstants;
import com.TestingAPI.modules.PayLoadManager;
import com.TestingAPI.pojos_gson.Booking;
import com.TestingAPI.pojos_gson.BookingResponse;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import io.qameta.allure.Description;

import static org.assertj.core.api.Assertions.assertThat;

public class TestIntegrationFlow extends BaseTest {

    //  Test Integration Scenario 1

    //  1. Create a Booking -> bookingID

    // 2. Create Token -> token


    // 3. Verify that the Create Booking is working - GET Request to bookingID

    // 4. Update the booking ( bookingID, Token) - Need to get the token, bookingID from above request

    // 5. Delete the Booking - Need to get the token, bookingID from above request


    @Test(groups = "qa", priority = 1)
    @Owner("Promode")
    @Description("TC#INT1 - Step 1. Verify that the Booking can be Created")
    public void testCreateBooking(ITestContext iTestContext) {

        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured.given(requestSpecification)
                .when().body(payLoadManager.createPayloadBookingAsString()).post();
//Verify response and status code
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        //we need to verify firstname lastname from response hence we can do by deserialization
//Use 'bookingResponseJava' function for deserialization
        //here below payLoadManager is from BaseTest
        BookingResponse bookingResponse = payLoadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"James");


        System.out.println(bookingResponse.getBookingid());
        iTestContext.setAttribute("bookingid",bookingResponse.getBookingid());

    }
    @Test(groups = "qa", priority = 2)
    @Owner("Shilpa")
    @Description("Verify  the booking ID ")
    public void testVerifyBookingID(ITestContext iTestContext){
        System.out.println(iTestContext.getAttribute("Booking id is: " + "bookingid"));

        Assert.assertTrue(true);

        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        System.out.println("booking id after integer is" + bookingid);
        String basePathGET = APIConstants.CREATE_UPDATE_BOOKING_URL+"/" + bookingid;
        System.out.println(basePathGET);

        requestSpecification.basePath(basePathGET);
        response = RestAssured.given(requestSpecification)
                .when().get();
//Verify response and status code
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);
//here below payLoadManager is from BaseTest
        //Verify Name is correct on getting bookingID
        Booking booking = payLoadManager.getResponseFromJSON(response.asString());
        assertThat(booking.getFirstname()).isNotNull().isNotBlank();
        assertThat(booking.getFirstname()).isEqualTo("James").isNotEmpty().isNotNull().isAlphanumeric().isNotBlank();
    }

    @Test(groups = "qa", priority = 3)
    @Owner("Shilpa")
    @Description("Verify Update booking details")
    public void testUpdateBookingByID(ITestContext iTestContext){

//      System.out.println(iTestContext.getAttribute("For updating name Booking ID is -> " + "bookingid"));

        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        System.out.println("Check booking id Integer: "+bookingid);
        String token = getToken();
        System.out.println("Token is:" +token);
        //When we get token we need to set token to token so that we can share to everyone as need to give to delete
        iTestContext.setAttribute("token",token);

        //Now we have to perform put/update request
        String basePathPUTPATCH = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println("URL for updating request is: "+basePathPUTPATCH);

        requestSpecification.basePath(basePathPUTPATCH);

        response = RestAssured
                .given(requestSpecification).cookie("token",token)
                        .when().body(payLoadManager.fullUpdatePayloadAsString()).put();
        //System.out.println(response);
//Verify response and status code
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);
//Once we get response we need to check update is correct
        //here below payLoadManager is from BaseTest
        //Verify Name is correct on updating
        Booking booking = payLoadManager.getResponseFromJSON(response.asString());
        System.out.println("Verify after updating");
        assertThat(booking.getFirstname()).isNotNull().isNotBlank();
        assertThat(booking.getFirstname()).isEqualTo("Priya");
        assertThat(booking.getLastname()).isEqualTo("Dutta");
        System.out.println("End of update");
       // Assert.assertTrue(true);


    }

    @Test(groups = "qa", priority = 4)
    @Owner("Shilpa")
    @Description("Verify Delete booking ")
    public void testDeleteBookingById(ITestContext iTestContext){

        String token = (String)iTestContext.getAttribute("token");
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" +bookingid;

        requestSpecification.basePath(basePathDELETE).cookie("token", token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                        .when().delete().then().log().all();
        validatableResponse.statusCode(201);

        Assert.assertTrue(true);
    }
}
