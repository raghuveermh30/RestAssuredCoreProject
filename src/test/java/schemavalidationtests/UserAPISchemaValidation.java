package schemavalidationtests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;

public class UserAPISchemaValidation {

    @Test
    public void getAuthTokenTest_WithJsonFile() {

        RestAssured.baseURI = "https://gorest.co.in";

        Integer token = given().log().all()
                .header("Authorization", "Bearer 68734ee53f25a08b6fc8baabff02bc5c364db7763d871a76874e36845fb586e9")
                .contentType(ContentType.JSON)
                .body(new File("./src/test/resources/jsons/user.json"))
                .when().log().all()
                .post("/public/v2/users")
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/create-user-json-schema.json"))
                .extract().path("id");

        System.out.println("User is " + token);
        Assert.assertNotNull(token);
    }

    @Test
    public void getAllUserId() {
        RestAssured.baseURI = "https://gorest.co.in";

        Response response = given().log().all()
                .header("Authorization", "Bearer 68734ee53f25a08b6fc8baabff02bc5c364db7763d871a76874e36845fb586e9").
                contentType(ContentType.JSON)
                .when().log().all()
                .get("/public/v2/users")
                .then().log().all()
                .assertThat().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/users-json-schema.json"))
                .extract().response();

        response.prettyPrint();
        JsonPath jsonPath = response.jsonPath();
        List<Integer> userIdList = jsonPath.getList("id");
        System.out.println("User Id List " + userIdList.size());

        for (Integer id : userIdList) {
            Assert.assertNotNull(id);
        }

    }
}
