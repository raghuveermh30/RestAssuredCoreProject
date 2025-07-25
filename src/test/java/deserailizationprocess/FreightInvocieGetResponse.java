package deserailizationprocess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured.*;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class FreightInvocieGetResponse {

    private String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJhQ1I2NlhmWWZRb1hkbUltaHFOWkxVSTZFVWpxVHZQaWNLSnRtdG1DTzlzIn0.eyJleHAiOjE3NzQ5Nzc5NDEsImlhdCI6MTc3NDkzNDc0MSwiYXV0aF90aW1lIjoxNzc0OTM0NzQxLCJqdGkiOiIzZGZmZjU1OC1kMTI2LTRkNTctYjVkYy1iYmZlNzU3M2Y3MmYiLCJpc3MiOiJodHRwczovL3Njbnh0Z2VuMDEtYXV0aC5jcC5tYW5oLmNsb3VkL2F1dGgvcmVhbG1zL21hYWN0aXZlIiwic3ViIjoiZ3N1c2VyIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoienV1bHNlcnZlci4xLjAuMCIsInNlc3Npb25fc3RhdGUiOiI4OGI1YzYwYi1jOGViLTQwOGItYWM1MS05YjA0MTM2M2YxZjQiLCJhY3IiOiIxIiwic2NvcGUiOiJvbW5pIHByb2ZpbGUgb3BlbmlkIGVtYWlsIiwic2lkIjoiODhiNWM2MGItYzhlYi00MDhiLWFjNTEtOWIwNDEzNjNmMWY0IiwidXNlck9yZ3MiOlsiMzI3MCJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsInVzZXJfbmFtZSI6ImdzdXNlciIsInByZWZlcnJlZF91c2VybmFtZSI6ImdzdXNlciIsImxvY2FsZSI6ImVuIiwiZ2l2ZW5fbmFtZSI6Ikphc29uIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9GQVBNYW5hZ2VyIl0sInVzZXJUaW1lWm9uZSI6IlVTL0Vhc3Rlcm4iLCJlZGdlIjoiMCIsIm9yZ2FuaXphdGlvbiI6IjMyNzAiLCJhY2Nlc3N0b0FsbEJVcyI6ZmFsc2UsIm5hbWUiOiJKYXNvbiBHZW9yZ2UiLCJ0ZW5hbnRJZCI6InNjbnh0Z2VuMDEiLCJmYW1pbHlfbmFtZSI6Ikdlb3JnZSIsImVtYWlsIjoicmhhbnVtYW50aGFyYXlhQG1hbmguY29tLHJhbmF5YWtAbWFuaC5jb20ifQ.GfOY5whCEYninuTDTn2Ugfd9Iq7HMm5a5WhCGv74MtXZY62SdaHU6gAxOYbB9eDEcHycLM7DHBs5TaSl9rG4mBoS5gD6Hnh0JZzhWHj7NPxjkdcOXn3koCXvwq9TvGjSW_vLtt7xrTL2C-ySvOrw0EFJMyvdYYesSQmh23rTe4QDdJnJe5fs5b5Xi8IjzNN0h-bWTCQHsU69P3e3b_XtZUdf-YK05CfmXIekbftQUlERURCYeHcSLKh1_Rh8Cb2L-IG1-m4eUxBPRMhqv4_e1I7ft-8pCYU9q_nYfw-wFZ1OiQO__SnVVaVgdP_NQYzQxNUN4gXDn4aAGlMzxpRF0A";

    @Test
    public void fetchFreightInvocieDetailsUsingJsonPath() {

        RestAssured.baseURI = "https://scnxtgen01.cp.manh.cloud";

        Response response = given().log().all().header("Authorization", "Bearer " + token).contentType(ContentType.JSON)
                .when().log().all()
                .get("/invoice/api/invoice/freightInvoice/freightInvoiceId/INVT27032026080501051")
                .then().extract().response();

        response.prettyPrint();
        String freightInvocieId = response.jsonPath().get("data.FreightInvoiceId");
        Assert.assertNotNull(freightInvocieId);

        // Business validation
        int planned = response.jsonPath().getInt("data.HeaderSummary.PlannedTotal");
        int approved = response.jsonPath().getInt("data.HeaderSummary.ApprovedTotal");

        Assert.assertTrue(approved <= planned, "Approved > Planned ❌");

        // Shipment validation
        List<String> shipmentIds =
                response.jsonPath().getList("data.FreightShipment.ShipmentId");

        Assert.assertFalse(shipmentIds.isEmpty(), "No shipments found ❌");

        String createdSourceName = response.jsonPath().get("data.CreatedSourceTypeId.Name");
        Assert.assertEquals(createdSourceName, "Manual");

        List<Map<String, ?>> freightShipmentResponse = response.jsonPath().getList("data.FreightShipment");

        for (Map<String, ?> shipmentResponse : freightShipmentResponse) {

            System.out.println("Shipment Mode Id is : " + shipmentResponse.get("ModeId"));
            System.out.println("Shipment Id is : " + shipmentResponse.get("ShipmentId"));

            Map<String, ?> freightShipAddAtts = (Map<String, ?>) shipmentResponse.get("FreightShpAddlAttrs");
            System.out.println("Planned Cost is : " + freightShipAddAtts.get("PlannedCost"));

        }
    }


    @Test
    public void validateFreightInvoiceEndToEnd() throws JsonProcessingException {
        RestAssured.baseURI = "https://scnxtgen01.cp.manh.cloud";

        Response response = given().log().all().header("Authorization", "Bearer " + token).contentType(ContentType.JSON)
                .when().log().all()
                .get("/invoice/api/invoice/freightInvoice/freightInvoiceId/INVT27032026080501051")
                .then().extract().response();

        response.prettyPrint();
        String freightInvoiceId = response.jsonPath().get("data.FreightInvoiceId");
        Assert.assertNotNull(freightInvoiceId);

        ObjectMapper mapper = new ObjectMapper();
        FreightInvoiceResponse freightInvoiceResponse = mapper.readValue(response.body().asString(), FreightInvoiceResponse.class);

        System.out.println(freightInvoiceResponse.getData().getFreightInvoiceId());
        System.out.println(freightInvoiceResponse.isSuccess());


        System.out.println(freightInvoiceResponse.getData().getFreightShipment().size());
        List<FreightInvoiceResponse.FreightShipment> freightShipments = freightInvoiceResponse.getData().getFreightShipment();

        for (FreightInvoiceResponse.FreightShipment freightShipment : freightShipments) {
            System.out.println(freightShipment.getShipmentId());
            System.out.println(freightShipment.getModeId());

            FreightInvoiceResponse.FreightShpAddlAttrs freightShpAddlAttrs = freightShipment.getFreightShpAddlAttrs();
            System.out.println("Planned cost of Additional Attributes "+freightShpAddlAttrs.getPlannedCost());
            System.out.println("Invoice cost of Additional Attributes "+freightShpAddlAttrs.getInvoicedAmount());
        }
    }

}
