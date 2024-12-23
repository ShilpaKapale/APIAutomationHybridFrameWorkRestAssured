package Integration;

import base.BaseTest;
import com.TestingAPI.endpoints.APIConstants;
import com.TestingAPI.pojos_gson.Booking;
import com.TestingAPI.pojos_gson.BookingResponse;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import io.qameta.allure.Description;

import static org.assertj.core.api.Assertions.assertThat;

public class Integration_CreateUpdateDelete extends BaseTest{
    //Create Booking -> Update it -> Try to Delete

//---Create Booking---

    @Test(groups = "I2",priority = 1)
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

//---Update Booking----

    @Test(groups = "I2",priority = 2)
    @Owner("Shilpa")
    @Description("TC#INT1 - Step 2. Verify Updated Booking by ID")
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
        validatableResponse.statusCode(200);

        Booking booking = payLoadManager.getResponseFromJSON(response.asString());

        assertThat(booking.getFirstname()).isNotNull().isNotBlank();
        assertThat(booking.getFirstname()).isEqualTo("Priya");
        assertThat(booking.getLastname()).isEqualTo("Dutta");

    }

 //---Try to Delete---

    @Test(groups = "I2",priority = 3)
    @Owner("Shilpa")
    @Description("TC#INT1 - Step 3. Delete the Booking by ID")
    public void testDeleteBookingById(ITestContext iTestContext){

        String token = (String)iTestContext.getAttribute("token");
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;

        requestSpecification.basePath(basePathDELETE).cookie("token", token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);



    }
}
