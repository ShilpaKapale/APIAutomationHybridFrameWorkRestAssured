package base;

import Assert.AssertActions;
import com.TestingAPI.endpoints.APIConstants;
import com.TestingAPI.modules.PayLoadManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
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

//    requestSpecification = RestAssured.given()
//            .baseUri(APIConstants.BASE_URL)
//            .contentType(ContentType.JSON)
//            .log().all();

    requestSpecification = new RequestSpecBuilder()
            .setBaseUri(APIConstants.BASE_URL)
            .addHeader("Content-Type", "application/json")
            .build().log().all();
}
        //Below function make a post request and will give token
    public String getToken(){
        System.out.println("BaseTest Gettoken start");
        requestSpecification = RestAssured.given()
                .baseUri(APIConstants.BASE_URL)
                .basePath(APIConstants.AUTH_URL);
        //Setting payload
        String payload = payLoadManager.setAuthPayload();
        //Get token
        response = requestSpecification.contentType(ContentType.JSON).body(payload).when().post();
        //String extraction
       String token = payLoadManager.getTokenFromJSON(response.asString());
         System.out.println("BaseTest Gettoken end");
        return token;
    }
}
