package authapis;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class OAuth2APITest {

    private String accessToken;

    @BeforeMethod
    public void getAccessToken() {
        RestAssured.baseURI = "https://test.api.amadeus.com/v1/security/oauth2/token";

        Response response = RestAssured.given()
                .contentType(ContentType.URLENC)
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", "5P4kiVdauWusiZALd8QlMh2BpAh1o1uW")
                .formParam("client_secret", "VYCvgT2PIUBYT94D")
                .when()
                .post().then().log().all().extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
        response.prettyPrint();

        accessToken = response.jsonPath().getString("access_token");
    }

    @Test
    public void getFlightDetailsTest_1() {
        //RestAssured.baseURI = "https://test.api.amadeus.com/v1/shopping/flight-destinations?origin=PAR&maxPrice=200";

        Response response = RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when().log().all()
                .get("https://test.api.amadeus.com/v1/shopping/flight-destinations?origin=PAR&maxPrice=200")
                .then().log().all().extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        response.prettyPrint();

    }

    @Test
    public void getFlightDetailsTest_2() {

        Response response = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when().log().all()
                .get("https://test.api.amadeus.com/v1/shopping/flight-destinations?origin=PAR&maxPrice=200")
                .then().log().all().extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
        response.prettyPrint();

    }
}
