package jaywayjsonpath;


import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class JsonPathTest {

    @Test
    public void getProductAPITest_JsonPath() {
        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = given().log().all().get("/products").then().log().all().extract().response();

        response.prettyPrint();
        String responseString = response.asString();
        System.out.println(responseString);
        System.out.println("***************");

        ReadContext context = JsonPath.parse(responseString);

        List<Number> priceList = context.read("$[?(@.price > 50)].price");
        priceList.forEach(s -> System.out.println(s));
        System.out.println("***************");

        List<Integer> idList = context.read("$[?(@.price > 50)].id");
        idList.forEach(s -> System.out.println(s));
        System.out.println("***************");

        List<String> titleList = context.read("$[?(@.price > 50)].title");
        titleList.forEach(s -> System.out.println(s));
        System.out.println("***************");

        List<Number> rateList = context.read("$[?(@.price > 50)].rating.rate"); //$[?(@.price > 50)]..rate
        rateList.forEach(s -> System.out.println(s));
        System.out.println("***************");

        List<Integer> countList = context.read("$[?(@.price > 50)]..count"); //$[?(@.price > 50)].rating.count
        countList.forEach(s -> System.out.println(s));
        System.out.println("***************");
    }

    @Test
    public void getProductAPITest_CondExample_with2attributes() {
        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = given().log().all().get("/products").then().log().all().extract().response();

        response.prettyPrint();
        String responseString = response.asString();
        System.out.println(responseString);
        System.out.println("***************");

        ReadContext context = JsonPath.parse(responseString);

        List<Number> idList = context.read("$[?(@.rating.rate<3)].id");
        idList.forEach(s -> System.out.println(s));
        System.out.println("***************");

        //Fetch 2 attributes from the Response
        List<Map<String, Object>> jewleryList = context.read("$[?(@.category == 'jewelery')]..['title','price']");
        System.out.println(jewleryList);
        System.out.println("Jewelery Size is " + jewleryList.size());

        for (Map<String, Object> product : jewleryList) {
            String title = (String) product.get("title");
            Number price = (Number) product.get("price");
            System.out.println("title:" + title);
            System.out.println("price:" + price);
            System.out.println("***************");
        }
    }

    @Test
    public void getProductAPITest_CondExample_with3attributes() {
        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = given().log().all().get("/products").then().log().all().extract().response();

        response.prettyPrint();
        String responseString = response.asString();
        System.out.println(responseString);
        System.out.println("***************");

        ReadContext context = JsonPath.parse(responseString);
        List<Map<String, Object>> jewleryList = context.read("$[?(@.category == 'jewelery')]..['id','title','price']");
        System.out.println("Jewelery Size is " + jewleryList.size());

        for (Map<String, Object> product : jewleryList) {
            Number id = (Number) product.get("id");
            String title = (String) product.get("title");
            Number price = (Number) product.get("price");
            System.out.println("id: " + id);
            System.out.println("title:" + title);
            System.out.println("price:" + price);
            System.out.println("***************");
        }
    }

    @Test
    public void getProductAPITest_CondExample_with4attributes() {
        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = given().log().all().get("/products").then().log().all().extract().response();

        response.prettyPrint();
        String responseString = response.asString();
        System.out.println(responseString);
        System.out.println("***************");

        ReadContext context = JsonPath.parse(responseString);
        List<Map<String, Object>> jewleryList = context.read("$[?(@.category == 'jewelery')]..['id','title','price', 'category']");
        System.out.println("Jewelery Size is " + jewleryList.size());

        for (Map<String, Object> product : jewleryList) {
            Number id = (Number) product.get("id");
            String title = (String) product.get("title");
            Number price = (Number) product.get("price");
            String category = (String) product.get("category");
            System.out.println("id: " + id);
            System.out.println("title:" + title);
            System.out.println("price:" + price);
            System.out.println("category:" + category);
            System.out.println("***************");
        }
    }


    //nested attributes: //$[?(@.category == 'jewelery')].rating.rate
    //Two Conditional statements: $[?(@.category=='jewelery') && (@.price>20)].title
    //Two Conditional statements: $[?(@.category=='jewelery') && (@.price>20)]..['id', 'title','price']
    //Two Conditional statements: $[?(@.category=='jewelery') || (@.price>100)]..['id', 'title','price']

    //$..[?(@.id==3)&& (@.username=='kevinryan')].address.city
}
