package base;

import Assert.AssertActions;
import com.TestingAPI.endpoints.APIConstants;
import com.TestingAPI.modules.PayLoadManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BaseTest {

    //Common to all testcase
    public RequestSpecification requestSpecification;
    public AssertActions assertActions;
    public PayLoadManager payLoadManager;
    public JsonPath jsonPath;
    public Response response;
    public ValidatableResponse validatableResponse;

@BeforeTest
public void setUp(){
    //Base URL, Content type JSON
    payLoadManager = new PayLoadManager();
    assertActions = new AssertActions();

    requestSpecification = RestAssured.given()
            .baseUri(APIConstants.BASE_URL)
            .contentType(ContentType.JSON)
            .log().all();

//    requestSpecification = new RequestSpecBuilder()
//            .setBaseUri(APIConstants.BASE_URL)
//            .addHeader("Content-Type", "application/json")
//            .build().log().all();
}
}
