package getapitestwithbdd;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ContactsAPIsTest {

    //https://thinking-tester-contact-list.herokuapp.com/contactList


    @Test
    public void getContactsApiTest() {

        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

        given().log().all()
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2IwMzRjNjRmNTU4MzAwMTMzNDY0ZTgiLCJpYXQiOjE3Mzk2MDEwOTR9.mP1XsHP0N4OnB1iC6eQX24zaW05kX_ej0XTrceo9sVA")
                .when().log().all()
                .get("/contacts")
                .then().log().all()
                .statusCode(200)
                .and()
                .statusLine("HTTP/1.1 200 OK")
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body("$.size()", equalTo(1));
    }

    @Test
    public void getContactsApiTestWithInvToken() {
        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        given().log().all()
                .header("Authorization", "Bearer .eyJfaWQiOiI2N2IwMzRjNjRmNTU4MzAwMTMzNDY0ZTgiLCJpYXQiOjE3Mzk2MDEwOTR9.mP1XsHP0N4OnB1iC6eQX24zaW05kX_ej0XTrceo9sVA")
                .when().log().all()
                .get("/contacts").then().log().all().statusCode(401)
                .and().assertThat()
                .body("error", equalTo("Please authenticate."));
    }

    @Test
    public void getContactsApiTestWithInvToken_1() {
        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        String error = given().log().all()
                .header("Authorization", "Bearer .eyJfaWQiOiI2N2IwMzRjNjRmNTU4MzAwMTMzNDY0ZTgiLCJpYXQiOjE3Mzk2MDEwOTR9.mP1XsHP0N4OnB1iC6eQX24zaW05kX_ej0XTrceo9sVA")
                .when().log().all()
                .get("/contacts")
                .then().extract().path("error");
        Assert.assertEquals(error, "Please authenticate.");
    }

    @Test
    public void getSingleUser_FetchSingleUSerData() {
        RestAssured.baseURI = "https://gorest.co.in";

        Response response = given().log().all()
                .header("Authorization", "Bearer 81dcd70d5b3e095c23fba7c8ded79f76f4d78320d52e2b444c7daade8728cc2c")
                .when().log().all()
                .get("/public/v2/users/7701123");

        System.out.println("Status Code " + response.getStatusCode());
        System.out.println("Status Line " + response.getStatusLine());
        response.prettyPrint();

        JsonPath jsonPath = response.jsonPath();
        int userId = jsonPath.getInt("id");
        System.out.println("User Id : " + userId);
        String userName = jsonPath.getString("name");
        Assert.assertEquals(userName, "Aasa Iyer DO");

        String status = jsonPath.getString("status");
        Assert.assertEquals(status, "active");

    }

    @Test
    public void getSingleUser_FetchFullUSerData() {
        RestAssured.baseURI = "https://gorest.co.in";

        Response response = given().log().all()
                .header("Authorization", "Bearer cd9042fb6125093d54440e15ec486e769b118fa2506410ee7923fcc85fee6a95")
                .when().log().all()
                .get("/public/v2/users");

        System.out.println("Status Code " + response.getStatusCode());
        System.out.println("Status Line " + response.getStatusLine());
        response.prettyPrint();

        JsonPath jsonPath = response.jsonPath();
        System.out.println("********************");
        List<Integer> idList = jsonPath.getList("id");
        idList.forEach(s -> System.out.println(s));
        System.out.println("********************");

        List<String> namesList = jsonPath.getList("name");
        namesList.forEach(s -> System.out.println(s));
    }

    @Test
    public void getProduct_FetchNestedData() {
        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = given().log().all()
                .when().log().all()
                .get("/products");

        response.prettyPrint();
        System.out.println("********************");
        JsonPath jsonPath = response.jsonPath();
        List<Object> priceList = jsonPath.getList("price");
        System.out.println(priceList);
        System.out.println("********************");

        List<Object> ratingList = jsonPath.getList("rating.rate");
        System.out.println(ratingList);
        System.out.println("********************");
        List<Integer> countList = jsonPath.getList("rating.count");
        System.out.println(countList);
        System.out.println("********************");

        List<Integer> idList = jsonPath.getList("id");
        System.out.println(idList);
        System.out.println("********************");

        for (int i = 0; i < idList.size(); i++) {
            int id = idList.get(i);
            Object price = priceList.get(i);
            Object count = countList.get(i);
            System.out.println("Id :" + id + " Price: " + price + " Count: " + count);
        }

        Assert.assertTrue(ratingList.contains(4.7f));
    }
}
