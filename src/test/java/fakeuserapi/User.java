package fakeuserapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

    private String email;
    private String username;
    private String password;
    private String phone;
    private Name name;
    private Address address;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Name {
        private String firstname;
        private String lastname;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Address {
        private String city;
        private String street;
        private int number;
        private String zipcode;
        private GeoLocation geolocation;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        @Builder
        public static class GeoLocation {
            private String lat;
            @JsonProperty("long")
            private String longitude;
        }
    }
}


