package deserailizationprocess;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;


public class GenericProductFetch {

    @Test
    public void fetchProductApiResponse() {

        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = given().log().all().when().log().all().get("/products")
                .then().log().all().extract().response();

        response.prettyPrint();

        int targetId = 3;
        List<Map<String, Object>> productResponse = response.jsonPath().getList("");

        for (Map<String, Object> product : productResponse) {

            int id = (int) product.get("id");

            if (id == targetId) {
                System.out.println("Id : " + id);
                System.out.println("Title : " + product.get("title"));
                System.out.println("price : " + product.get("price"));
                System.out.println("description : " + product.get("description"));

                // Nested JSON (rating)
                Map<String, Object> rating = (Map<String, Object>) product.get("rating");

                System.out.println("Rating Rate: " + rating.get("rate"));
                System.out.println("Rating Count: " + rating.get("count"));

                break; // stop once found

            }
        }

        System.out.println("**************");
        List<Map<String, Object>> products = productResponse.stream().filter(p -> (int) p.get("id") == targetId).collect(Collectors.toList());
        products.stream().forEach(System.out::println);


        System.out.println("**************");
        Map<String, Object> productMap = productResponse.stream().filter(p -> (int) p.get("id") == targetId).findFirst().orElse(null);
        productMap.forEach((K, V) -> System.out.println(K + " : " + V));

    }
}
