package toyproj.CalorieCalculator.controller;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toyproj.CalorieCalculator.domain.AccountUser;
import toyproj.CalorieCalculator.domain.CalorieGoal;
import toyproj.CalorieCalculator.domain.CalorieGoalFood;
import toyproj.CalorieCalculator.dto.FoodDto;
import toyproj.CalorieCalculator.service.AccountUserService;
import toyproj.CalorieCalculator.service.CalorieGoalService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CalorieGoalController {

    private final CalorieGoalService calorieGoalService;
    private final AccountUserService accountUserService;

    @GetMapping("/goal/new")
    public String goalForm() {
        return "goal/goalForm";
    }

    @GetMapping("/goals")
    public String goalList(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        AccountUser user = accountUserService.getUserByUsername(userDetails.getUsername()).get();

        List<CalorieGoal> calorieGoals = calorieGoalService.getCalorieGoalByUserId(user.getId());
        model.addAttribute("calorieGoals", calorieGoals);

        return "goal/goalList";
    }

    @GetMapping("/goal/delete")
    public String removeGoal(@RequestParam Long goalId) {
        try {
            calorieGoalService.removeCalorieGoal(goalId);
            return "redirect:/goals";
        }catch (NoResultException e) {
            System.out.println("error message = " + e.getMessage());
            return "redirect:/goals";
        }
    }

    @GetMapping("/goal/check/food")
    public String checkFood(@RequestParam Long goalId, Model model) {
        CalorieGoal calorieGoal = calorieGoalService.getCalorieGoalById(goalId).get();

        List<CalorieGoalFood> list = calorieGoal.getCalorieGoalFoods();

        model.addAttribute("calorieGoalList", list);

        return "goal/goalFoodList";
    }

    @PostMapping("/goal/new")
    public String addNewGoal(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String goalAmount) {
        double amount = Double.parseDouble(goalAmount);

        AccountUser user = accountUserService.getUserByUsername(userDetails.getUsername()).get();

        Optional<CalorieGoal> optionalGoal = calorieGoalService.getCalorieGoalByUserIdAndDate(user.getId(), LocalDate.now());

        if(optionalGoal.isPresent()) {
            CalorieGoal goal = optionalGoal.get();
            calorieGoalService.updateCalorieGoal(goal.getId(),amount);
        } else {
            calorieGoalService.addCalorieGoal(user, amount);
        }

        return "redirect:/goals";
    }


}
