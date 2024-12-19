package crud;

import base.BaseTest;
import com.TestingAPI.endpoints.APIConstants;
import com.TestingAPI.pojos_gson.BookingResponse;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class testCreateBooking extends BaseTest {

    @Owner("Promode")
    @TmsLink("https://google.com")
    @Link(name = "Link to TC", url = "https://bugz.atlassian.net/browse/RBT-4")
    @Issue("JIRA_RBT-4")
    @Description("Verify that POST request is working fine.")
    @Test(groups = "qa")
    public void testVerifyCreateBookingPOST01(){
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured.given(requestSpecification)
                .when().body(payLoadManager.createPayloadBookingAsString()).post();
//Verify response and status code
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        //we need to verify firstname lastname from response hence we can do by deserialization
//Use 'bookingResponseJava' function for deserialization
        BookingResponse bookingResponse = payLoadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"James");
    }
}
