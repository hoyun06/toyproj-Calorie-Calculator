package toyproj.CalorieCalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class FoodDto {

    private String calorie;
    private String name;
    private String foodCode;
    private String size;
}
