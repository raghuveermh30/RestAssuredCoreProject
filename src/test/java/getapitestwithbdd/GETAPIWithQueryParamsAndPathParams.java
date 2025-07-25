package getapitestwithbdd;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GETAPIWithQueryParamsAndPathParams {

    @BeforeMethod
    public void setUp() {
        RestAssured.baseURI = "https://gorest.co.in";
    }

    @Test
    public void getUserWith_QueryParams() {

        given().log().all()
                .header("Authorization", "Bearer cd9042fb6125093d54440e15ec486e769b118fa2506410ee7923fcc85fee6a95")
                .queryParam("name", "trivedi")
                .queryParam("status", "active")
                .when().log().all()
                .get("/public/v2/users")
                .then().log().all()
                .assertThat().statusCode(200)
                .and().contentType(ContentType.JSON);

    }

    @Test
    public void getUserWith_QueryParamsWithHashMap() {

        /*Map<String, String> queryMap = new HashMap<>();
        queryMap.put("name", "trivedi");
        queryMap.put("status", "active");
        queryMap.put("gender", "male");*/

        Map<String, String> queryMap = Map.of(
                "name", "trivedi",
                "status", "active",
                "gender", "male"
        );
        // the above map is called Immutable Map

        given().log().all()
                .header("Authorization", "Bearer cd9042fb6125093d54440e15ec486e769b118fa2506410ee7923fcc85fee6a95")
                .queryParams(queryMap)
                .when().log().all()
                .get("/public/v2/users")
                .then().log().all()
                .assertThat().statusCode(200)
                .and().contentType(ContentType.JSON);
    }

    @DataProvider
    public Object[][] getUserData() {
        return new Object[][]{
                {"7439565"},
                //   {"7381532"},
                //   {"7381534"}
        };
    }

    @Test(dataProvider = "getUserData")
    public void getUserWith_PathParams(String userId) {

        //public/v2/users/7048594/posts --> end point

        given().log().all()
                .pathParam("userid", userId)
                .header("Authorization", "Bearer cd9042fb6125093d54440e15ec486e769b118fa2506410ee7923fcc85fee6a95")
                .when().log().all()
                .get("/public/v2/users/{userid}/posts")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("title", hasItem("Theatrum crapula curto demens sed avoco."));
    }

    //equalTo: json object {}
    //hasItem: Json Array []
    //jayway jsonpath: $

    @DataProvider
    public Object[][] getUserData1() {
        return new Object[][]{
                {"name", "trivedi"},
                {"status", "active"}
        };
    }

    @Test(dataProvider = "getUserData1")
    public void getUserWith_QueryParamsWithDataProv(String name, String value) {

        given().log().all()
                .header("Authorization", "Bearer cd9042fb6125093d54440e15ec486e769b118fa2506410ee7923fcc85fee6a95")
                .queryParams(name, value)
                .when().log().all()
                .get("/public/v2/users")
                .then().log().all()
                .assertThat().statusCode(200)
                .and().contentType(ContentType.JSON);
    }
}
