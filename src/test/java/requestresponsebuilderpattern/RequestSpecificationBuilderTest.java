package requestresponsebuilderpattern;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RequestSpecificationBuilderTest {

    public static RequestSpecification user_req_spec() {
        RequestSpecification reqSpec = new RequestSpecBuilder()
                .setBaseUri("https://gorest.co.in")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
                .build();
        return reqSpec;
    }

    @Test
    public void getUser_WithReq_Spec(){
        given().log().all().spec(user_req_spec()).log().all()
                .when()
                .get("/public/v2/users").then().log().all().assertThat().statusCode(200);
    }

    @Test
    public void getUser_WithReq_Spec_QueryParam(){
        given().log().all()
                .queryParam("name", "Swami Guh")
                .queryParam("id", "7741171")
                .spec(user_req_spec()).when().log().all()
                .get("/public/v2/users").then().log().all().assertThat().statusCode(200);
    }


}
