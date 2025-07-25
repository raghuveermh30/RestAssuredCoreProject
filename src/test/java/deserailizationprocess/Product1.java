package deserailizationprocess;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Product1 {

    private int id;
    private String title;
    private String price;
    private String description;
    private String category;
    private String image;
    private Rating rating;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Rating{
        private double rate;
        private int count;
    }
}
