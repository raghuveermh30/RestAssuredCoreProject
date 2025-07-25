package fakeuserapi;

import io.qameta.allure.restassured.AllureRestAssured;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;


public class CreateFakeUserTest {

    public String getRandomEmailId() {
        return "apiAutomation" + System.currentTimeMillis() + "@gmail.com";
    }

    @Test
    public void createUserTest_UsingLombok() {

        RestAssured.baseURI = "https://fakestoreapi.com";

        User.Address.GeoLocation geoLocation = new User.Address.GeoLocation("-98.900", "-987.999");
        User.Address address = new User.Address("BLR", "Nagarabhavi", 123, "560072", geoLocation);
        User.Name name = new User.Name("sonia", "sharma");
        User user = new User("sonia@gmail.con", "soniasharma", "sonia@123", "123456789", name, address);

        given().filter(new AllureRestAssured()).log().all()
                .body(user)
                .when().log().all()
                .post("/users")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void createUserTest_UsingLombokBuilderPattern() {

        RestAssured.baseURI = "https://fakestoreapi.com";

        User.Address.GeoLocation geoLocation = new User.Address.GeoLocation.GeoLocationBuilder()
                .lat("-90.899")
                .longitude("876.33")
                .build();

        User.Address address = new User.Address.AddressBuilder()
                .city("Pune")
                .street("old road")
                .number(123)
                .zipcode("98989")
                .geolocation(geoLocation)
                .build();

        User.Name name = new User.Name.NameBuilder()
                .firstname("Piyush")
                .lastname("Sharma")
                .build();

        User user = new User.UserBuilder().email("piyush@gmail.com")
                .password("piyush@123")
                .phone("9876-090-987")
                .username("piyusapi")
                .name(name)
                .address(address)
                .build();

        given().log().all()
                .body(user)
                .when().log().all()
                .post("/users")
                .then().log().all()
                .assertThat().statusCode(200);

    }
}
