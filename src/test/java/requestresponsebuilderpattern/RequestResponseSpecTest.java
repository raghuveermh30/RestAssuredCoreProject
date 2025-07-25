package requestresponsebuilderpattern;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RequestResponseSpecTest {

    static String accessToken;

    public static RequestSpecification oAuth2ReqSpec() {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://test.api.amadeus.com")
                .setContentType(ContentType.URLENC)
                .addFormParam("grant_type", "client_credentials")
                .addFormParam("client_id", "5P4kiVdauWusiZALd8QlMh2BpAh1o1uW")
                .addFormParam("client_secret", "VYCvgT2PIUBYT94D").build();
        return requestSpecification;
    }

    @Test
    public void getOauth2AccessToken() {
        Response response = given().spec(oAuth2ReqSpec()).log().all()
                .when().post("/v1/security/oauth2/token").then().log().all().extract().response();
        response.prettyPrint();
        accessToken = response.jsonPath().getString("access_token");
        System.out.println("Access Token is " + accessToken);

    }


    public static RequestSpecification user_req_spec() {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://gorest.co.in")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
                .build();
        return requestSpecification;
    }

    public static ResponseSpecification user_res_spec_200() {
        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectHeader("Server", "cloudflare")
                .build();
        return responseSpecification;
    }

    @Test
    public void getUserTest() {
        given().log().all().spec(user_req_spec()).when().log().all()
                .get("/public/v2/users").then().log().all()
                .assertThat().spec(user_res_spec_200());
    }
}
