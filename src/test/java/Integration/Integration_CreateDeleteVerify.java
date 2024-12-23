package Integration;

import base.BaseTest;
import com.TestingAPI.endpoints.APIConstants;
import com.TestingAPI.pojos_gson.BookingResponse;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import io.qameta.allure.Description;

import static org.assertj.core.api.Assertions.assertThat;

public class Integration_CreateDeleteVerify extends BaseTest{
    //Create Booking -> Delete it -> Verify

//---Create Booking---
@Test(groups = "I1",priority = 1)
    @Owner("Shilpa")
    @Description("TC#INT1 - Step 1. Verify that the Booking can be Created")
    public void testCreateBooking(ITestContext iTestContext){
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured.given(requestSpecification)
                .when().body(payLoadManager.createPayloadBookingAsString()).post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        BookingResponse bookingResponse = payLoadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(), "James");
        System.out.println(bookingResponse.getBookingid());

        iTestContext.setAttribute("bookingid",bookingResponse.getBookingid());
    }

//---Delete Booking---

    @Test(groups = "I1",priority = 2)
    @Owner("Shilpa")
    @Description("TC#INT1 - Step 2. Delete the Booking by ID")
    public void testDeleteBookingById(ITestContext iTestContext){

        String token = getToken();
        System.out.println("Token is:" +token);
        //When we get token we need to set token to token so that we can share to everyone as need to give to delete
        iTestContext.setAttribute("token",token);
      //  String token = (String)iTestContext.getAttribute("token");
        System.out.println("Token is:" +token);
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        System.out.println("Booking id is:" +bookingid);

        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println("URL: "+ basePathDELETE);
        requestSpecification.basePath(basePathDELETE).cookie("token", token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);
    }

//--Verify Booking is Deleted---

    @Test(groups = "I1",priority = 3)
    @Owner("Shilpa")
    @Description("TC#INT1 - Step 3. Verify that the Booking By ID")
    public void testVerifyBookingId(ITestContext iTestContext){
        System.out.println(iTestContext.getAttribute("bookingid"));
        Assert.assertTrue(true);

        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        System.out.println("Booking id: " +bookingid);
        // GET Request - to verify that the firstname after creation is James
        String basePathGET = APIConstants.CREATE_UPDATE_BOOKING_URL+"/" + bookingid;
        System.out.println("URL "+basePathGET);

        requestSpecification.basePath(basePathGET);
        response = RestAssured
                .given(requestSpecification)
                .when().get();
        validatableResponse = response.then().log().all();
        // Validatable Assertion
        validatableResponse.statusCode(404);
    }
}