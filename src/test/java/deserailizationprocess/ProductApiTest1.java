package deserailizationprocess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class ProductApiTest1 {

    @Test
    public void fetchProductApiResponse() throws JsonProcessingException {
        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = given().log().all().when().log().all().get("/products").then().log().all()
                .extract().response();

        response.prettyPrint();

        ObjectMapper mapper = new ObjectMapper();
        Product[] productResponse = mapper.readValue(response.body().asString(), Product[].class);

        for (Product product : productResponse) {
            System.out.println("Product Id is : " + product.getId());
            System.out.println("Product title is : " + product.getTitle());
            System.out.println("Product description is : " + product.getDescription());
            System.out.println("Product category is : " + product.getCategory());
            System.out.println("Product image is : " + product.getImage());
            System.out.println("Product Rating rate is : " + product.getRating().getRate());
            System.out.println("Product Rating count is : " + product.getRating().getCount());

        }

    }

    @Test
    public void fetchProductApiResponse1() {
        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = given().log().all().when().log().all().get("/products").then().log().all()
                .extract().response();

        response.prettyPrint();

        List<Map<String, Object>> productResponse = response.jsonPath().getList("");

        for (Map<String, Object> product : productResponse) {

            System.out.println("Product Id is : " + (int) product.get("id"));
            System.out.println("Product title is : " + product.get("title"));
            System.out.println("Product description is : " + product.get("description"));
            System.out.println("Product category is : " + product.get("category"));
            System.out.println("Product image is : " + product.get("image"));
            double getPrice = ((Number) product.get("price")).doubleValue();
            System.out.println("Product price is : " + getPrice);

            //Fetch the Rating Rate objects
            Map<String, Object> rating = (Map<String, Object>) product.get("rating");
            float rate = ((Number) rating.get("rate")).floatValue();
            System.out.println("Product Rating is : " + rate);
            int count = ((Number) rating.get("rate")).intValue();
            System.out.println("Product Rating is : " + count);

        }

    }


}
