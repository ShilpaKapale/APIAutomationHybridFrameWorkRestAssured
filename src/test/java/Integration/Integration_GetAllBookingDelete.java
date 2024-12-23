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

public class Integration_GetAllBookingDelete extends BaseTest {

    @Test(groups = "I4",priority = 1)
    @Owner("Shilpa")
    @Description("TC#INT1 - Step 1. Get all Booking")
    public void GetAllBookingDelete() {

        String basePathGETALL = APIConstants.CREATE_UPDATE_BOOKING_URL;
        System.out.println("URL " + basePathGETALL);

        requestSpecification.basePath(basePathGETALL);
        response = RestAssured
                .given(requestSpecification)
                .when().get();
        validatableResponse = response.then().log().all();
        // Validatable Assertion
        validatableResponse.statusCode(200);
        Integer bookingid = response.jsonPath().getInt("[0].bookingid");
        System.out.println("Booking id is: "+bookingid);

        String token = getToken();
        System.out.println("Token is:" + token);

        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println("URL: " + basePathDELETE);
        requestSpecification.basePath(basePathDELETE).cookie("token", token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);

    }
}
