package authapis;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SpotfyAPITest {

    private String accessToken;

    @BeforeMethod
    public void getAccessToken() {
        RestAssured.baseURI = "https://accounts.spotify.com";

        Response response = RestAssured.given()
                .contentType(ContentType.URLENC)
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", "3995c6e40d4d4b8b8331e3c2051606f4")
                .formParam("client_secret", "1e3a7f7c25714aa0b702d20c3dcb81a3")
                .when()
                .post("/api/token").then().log().all().extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        response.prettyPrint();
        accessToken = response.jsonPath().getString("access_token");
    }

    @Test
    public void getAlbumTest() {
        Response response = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when().log().all()
                .get("https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGTy").then()
                .log().all().extract().response();

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
