package deserailizationprocess;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserApiTest {

    public String getRandomEmailId() {
        return "apiAutomation" + System.currentTimeMillis() + "@gmail.com";
    }

    @Test
    public void createUser() {
        RestAssured.baseURI = "https://gorest.co.in";

        //1. Create the user using POST
        User user = new User.UserBuilder()
                .name("kavya")
                .email(getRandomEmailId())
                .status("active")
                .gender("female")
                .build();

        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer cd9042fb6125093d54440e15ec486e769b118fa2506410ee7923fcc85fee6a95")
                .body(user) //serialization : Pojo to Json (or) Lombok to Json and it will happen automatically
                .when().log().all()
                .post("/public/v2/users")
                .then().log().all().assertThat().statusCode(201).extract().response();
        response.prettyPrint();
        int userId = response.jsonPath().get("id");
        System.out.println("Response Id is " + userId);

        System.out.println("*******************");

        //2. Get the User using User Id : Get

        Response getResponse = given().log().all()
                .header("Authorization", "Bearer cd9042fb6125093d54440e15ec486e769b118fa2506410ee7923fcc85fee6a95")
                .when().log().all()
                .get("/public/v2/users/" + userId)
                .then().log().all().extract().response();
        getResponse.prettyPrint();

        //De-Serialization: JSON to POJO
        ObjectMapper mapper = new ObjectMapper();
        try {
            User userResponse = mapper.readValue(getResponse.getBody().asString(), User.class);

            System.out.println(userResponse.getId() + " " + userResponse.getName() + " " + userResponse.getEmail() + " " + userResponse.getGender() + " " + userResponse.getStatus());

            Assert.assertEquals(userResponse.getId().intValue(), userId);
            Assert.assertEquals(userResponse.getName(), user.getName());
            Assert.assertEquals(userResponse.getStatus(), user.getStatus());
            Assert.assertEquals(userResponse.getEmail(), user.getEmail());
            Assert.assertEquals(userResponse.getGender(), user.getGender());
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
