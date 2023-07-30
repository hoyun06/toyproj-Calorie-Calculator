package toyproj.CalorieCalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import toyproj.CalorieCalculator.domain.Food;

@Getter @Setter
@AllArgsConstructor
public class FoodDto {

    private String calorie;
    private String name;
    private String foodCode;
    private String size;

    public static FoodDto food2FoodDto(Food food) {
        return new FoodDto(food.getCalorie().toString(), food.getName(), food.getFoodCode(), food.getSize().toString());
    }
}
