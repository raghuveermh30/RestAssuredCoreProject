package jaywayjsonpath;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class FakeStoreProductAPITest {


    @Test
    public void getProductAPITest_JsonPath() {
        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = given().log().all().get("/products").then().log().all().extract().response();

        response.prettyPrint();
        String responseString = response.asString();
        System.out.println(responseString);
        System.out.println("***************");

        //Single Attributes
        ReadContext context = JsonPath.parse(responseString);
        List<Integer> idsList = context.read("$[*].id");

        System.out.println("Number of Id's is : " + idsList.size());

        //Two Attributes
        List<Map<String, ?>> idTitleList = context.read("$.[*].['id', 'title']");
        System.out.println(idTitleList);
        System.out.println(idTitleList.size());

        for (Map<String, ?> element : idTitleList) {
            int id = (Integer) element.get("id");
            String title = (String) element.get("title");
            System.out.println("Id : " + id);
            System.out.println("Title  : " + title);
            System.out.println("*************");
        }

        //Three Attributes
        List<Map<String, ?>> idTitleCatList = context.read("$.[*].['id', 'title', 'category']");
        System.out.println(idTitleCatList);
        System.out.println(idTitleCatList.size());

        for (Map<String, ?> idTitCat : idTitleCatList) {
            int id = (Integer) idTitCat.get("id");
            String title = (String) idTitCat.get("title");
            String category = (String) idTitCat.get("category");
            System.out.println("Id : " + id);
            System.out.println("Title  : " + title);
            System.out.println("Category  : " + category);
            System.out.println("*************");
        }

        //Fetch the nested json
        List<Map<String, ?>> ratingList = context.read("$.[*].rating");

        for (Map<String, ?> rating : ratingList) {
            int rate = (Integer) rating.get("rate");
            Integer count = (Integer) rating.get("count");
            System.out.println("Rate : " + rate);
            System.out.println("Count  : " + count);
            System.out.println("*************");
        }

        //Fetch the rating rate - Single Attribute
        List<Number> rateList = context.read("$.[*].rating.rate");
        System.out.println(rateList.size());
        rateList.forEach(System.out::println);

        //Fetch the Id and Title where Category is Jewellery

        List<Number> jewelIdList = context.read("$.[?(@.category=='jewelery')].['id']");
        jewelIdList.forEach(System.out::println);

        List<String> jewelTitleList = context.read("$.[?(@.category=='jewelery')].['title']");
        jewelTitleList.forEach(System.out::println);

        //ID and Title
        List<Map<String, ?>> jewelIdTitleList = context.read("$.[?(@.category=='jewelery')].['id','title']");


        List<Map<String, ?>> jewelIdTitlePriceList = context.read("$.[?(@.category=='jewelery')].['id', 'title', 'price']");

        //Fetch Rating
        List<Map<String, ?>> jewelRatingList = context.read("$.[?(@.category=='jewelery')].rating");


        //Fetch Number for Category - Jewellery
        List<Number> jewelNumberList = context.read("$.[?(@.category=='jewelery')].rating.rate");


        //$[?((@.category=='jewelery') && (@.price =='695'))].['id', 'title'] -> List of Map

        //$[?((@.category=='jewelery') && (@.price =='695'))].['id'] - List

        //$[?((@.category=='jewelery') && (@.price > 100))] --> -> List of Map

        List<Map<String, ?>> invoiceDetailsList = context.read("$.data.FreightShipment[0].FreightShipmentCharge[*].[?(@.BalanceDue>300)]");

        for (Map<String, ?> invoiceDetail : invoiceDetailsList) {
            String shipmentId = (String) invoiceDetail.get("ShipmentId");

        }

        List<String> chargeDefList = context.read("$.data.FreightShipment[0].FreightShipmentCharge[*].ChargeDefinitionId");
        chargeDefList.forEach(System.out::println);

    }
}
