package getapitestwithbdd;


import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ProductAPIs {

    //BDD Style Methods
    @Test
    public void getProductsTest_1() {
        RestAssured.baseURI = "https://fakestoreapi.com";

        given().log().all().get("/products").then()
                .log().all().assertThat().statusCode(200);
        // This is a Builder Pattern
    }

    @Test
    public void getProductsTest_2() {
        RestAssured.baseURI = "https://fakestoreapi.com";

        given().log().all()
                .when().log().all()
                .get("/products")
                .then().log().all().assertThat().statusCode(200)
                .and().
                body("$.size()", equalTo(20));
    }
}
