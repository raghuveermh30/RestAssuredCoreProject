package updateuser;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UpdateUserWithUserObjects {

    /**
     * 1. Create the user: POST
     * 2. GET
     * 3. Update the user: PUT
     * 4. GET
     */

    public String getRandomEmailId() {
        return "apiAutomation" + System.currentTimeMillis() + "@gmail.com";
    }

    @Test
    public void updateUserWith_Pojo() {

        RestAssured.baseURI = "https://gorest.co.in";

        UpdateUser user = new UpdateUser("Pooja123", "female", getRandomEmailId(), "active");

        Response postResponse = given().log().all().contentType(ContentType.JSON)
                .header("Authorization", "Bearer 81dcd70d5b3e095c23fba7c8ded79f76f4d78320d52e2b444c7daade8728cc2c")
                .body(user)
                .when().log().all()
                .post("/public/v2/users").then().log().all().extract().response();

        postResponse.prettyPrint();
        Integer userId = postResponse.jsonPath().get("id");
        System.out.println("User Id " + userId);
        System.out.println("*******************");

        //2.Update the user: PUT
        user.setName("Pooja Sharma");
        user.setStatus("inactive");
        given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 81dcd70d5b3e095c23fba7c8ded79f76f4d78320d52e2b444c7daade8728cc2c")
                .body(user)
                .when().log().all()
                .put("/public/v2/users/" + userId)
                .then().log().all().assertThat().statusCode(200)
                .and().body("id", equalTo(userId))
                .and().body("name", equalTo(user.getName()))
                .and().body("status", equalTo(user.getStatus()));
    }

}
