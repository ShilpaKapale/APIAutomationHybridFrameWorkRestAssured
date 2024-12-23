package Integration;

import base.BaseTest;
import com.TestingAPI.endpoints.APIConstants;
import com.TestingAPI.pojos_gson.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Integration_DeleteUpdate extends BaseTest {
    //Delete a Booking -> Try to Update it

//---Create Booking---
@Test(groups = "I3",priority = 1)
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

    @Test(groups = "I3",priority = 2)
    @Owner("Shilpa")
    @Description("TC#INT1 - Step 2. Delete the Booking by ID")
    public void testDeleteBookingById(ITestContext iTestContext){
        String token = getToken();
        System.out.println("Token is:" +token);
        //When we get token we need to set token to token so that we can share to everyone as need to give to delete
        iTestContext.setAttribute("token",token);
        System.out.println("Token is:" +token);
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        System.out.println("Booking ID is: " + bookingid );
        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println("URL is :" + basePathDELETE);
        requestSpecification.basePath(basePathDELETE).cookie("token", token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);
    }


//--Verify updating Booking ---

    @Test(groups = "I3",priority = 3)
    @Owner("Shilpa")
    @Description("TC#INT1 - Step 3. Verify Updated Booking by ID")
    public void testUpdateBookingByID(ITestContext iTestContext){
        System.out.println(iTestContext.getAttribute("bookingid"));

        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken();
        iTestContext.setAttribute("token",token);

        String basePathPUTPATCH = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathPUTPATCH);

        requestSpecification.basePath(basePathPUTPATCH);

        response = RestAssured
                .given(requestSpecification).cookie("token", token)
                .when().body(payLoadManager.fullUpdatePayloadAsString()).put();


        validatableResponse = response.then().log().all();
        // Validatable Assertion
        validatableResponse.statusCode(405);


    }


}
