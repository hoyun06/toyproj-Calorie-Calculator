package toyproj.CalorieCalculator.controller;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toyproj.CalorieCalculator.domain.AccountUser;
import toyproj.CalorieCalculator.domain.Food;
import toyproj.CalorieCalculator.dto.FoodDto;
import toyproj.CalorieCalculator.service.AccountUserService;
import toyproj.CalorieCalculator.service.CalorieGoalFoodService;

@Controller
@RequiredArgsConstructor
public class CalorieGoalFoodController {

    private final CalorieGoalFoodService calorieGoalFoodService;
    private final AccountUserService accountUserService;

    @GetMapping("/consumption/new")
    public String consumptionForm(@ModelAttribute FoodDto foodDto) {
        return "consumption/consumptionForm";
    }

    @PostMapping("/consumption/new")
    public String newCalorieGoalFood(@ModelAttribute FoodDto foodDto
            , @RequestParam("consumption") String consumption
            , @AuthenticationPrincipal UserDetails userDetails) {

        if(foodDto.getCalorie().isEmpty()) {
            System.out.println("제공되는 데이터에 칼로리 정보가 누락되었음. 다시 시도 하세요.");
            return "api/foodForm";
        }

        AccountUser user = accountUserService.getUserByUsername(userDetails.getUsername()).orElse(null);

        try {
            if(user != null) {
                Food food = Food.createFood(foodDto.getName(), Double.parseDouble(foodDto.getCalorie())
                        , foodDto.getFoodCode(), Integer.parseInt(foodDto.getSize()));

                calorieGoalFoodService.addCalorieGoalFood(user, food, Double.parseDouble(consumption));
                return "api/foodForm";
            } else {
                System.out.println("No valid user found try again");
                return "redirect:/";
            }
        } catch (NoResultException e) {
            System.out.println("error message = " + e.getMessage());
            return "goal/goalform";
        }
    }
}
