package bookingapitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BookingAPITest {

    private String token;

    @Test
    public void getBookingApiToken() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        Response response = given().log().all().body(new File("./src/test/resources/jsons/auth.json"))
                .contentType(ContentType.JSON).when().log().all()
                .post("/auth").then().log().all()
                .extract().response();

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);

        token = response.jsonPath().getString("token");

        System.out.println("Access Token is : " + token);

    }

    @Test
    public void getBookingDetails() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        Response response = given().log().all().when().log().all().get("/booking").then().log().all()
                .extract().response();

        response.prettyPrint();

        List<String> allBookingIds = response.jsonPath().getList("bookingid");
        System.out.println("Total number of booking id's " + allBookingIds.size());
        allBookingIds.forEach(System.out::println);

        for (String str : allBookingIds) {
            System.out.println(str);
            Assert.assertNotNull(str);
        }
    }

    //Create the Booking
    @Test
    public void createBookingDetails() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        String createBooking = "{\n" +
                "    \"firstname\": \"Susan11213\",\n" +
                "    \"lastname\": \"Wilson\",\n" +
                "    \"totalprice\": 743,\n" +
                "    \"depositpaid\": true,\n" +
                "    \"bookingdates\": {\n" +
                "        \"checkin\": \"2021-02-06\",\n" +
                "        \"checkout\": \"2023-12-07\"\n" +
                "    }\n" +
                "}";

        Response response = given().log().all().contentType(ContentType.JSON).body(createBooking).when().log().all()
                .post("/booking").then().log().all()
                .extract().response();
        response.prettyPrint();
        String bookingId = response.jsonPath().get("bookingid").toString();
        System.out.println("Booking Id is " + bookingId);

        //Get Booking
        Response response1 = given().log().all().pathParams("bookingId", bookingId).when().log().all()
                .get("/booking/{bookingId}").then().log().all()
                .extract().response();

        response1.prettyPrint();

    }
}
