package toyproj.CalorieCalculator.service;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproj.CalorieCalculator.domain.AccountUser;
import toyproj.CalorieCalculator.domain.CalorieGoal;
import toyproj.CalorieCalculator.domain.CalorieGoalFood;
import toyproj.CalorieCalculator.domain.Food;
import toyproj.CalorieCalculator.repository.CalorieGoalFoodRepository;
import toyproj.CalorieCalculator.repository.CalorieGoalRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CalorieGoalFoodService {

    private final CalorieGoalFoodRepository calorieGoalFoodRepository;
    private final CalorieGoalRepository calorieGoalRepository;
    public Long addCalorieGoalFood(AccountUser accountUser, Food food, double gramAmount) {

        CalorieGoal findCalorieGoal = calorieGoalRepository.findByUserIdAndDate(accountUser.getId(), LocalDate.now())
                .orElseThrow(() -> new NoResultException());

        CalorieGoalFood calorieGoalFood = CalorieGoalFood.createCalorieGoalFood(findCalorieGoal, food, gramAmount);

        findCalorieGoal.addConsumption(calorieGoalFood.getActualConsumption());

        return calorieGoalFoodRepository.save(calorieGoalFood);
    }

    public Long removeCalorieGoalFood(AccountUser accountUser, CalorieGoalFood calorieGoalFood) {
        CalorieGoal findCalorieGoal = calorieGoalRepository.findByUserIdAndDate(accountUser.getId(), LocalDate.now())
                .orElseThrow(() -> new NoResultException());

        findCalorieGoal.getCalorieGoalFoods().remove(calorieGoalFood);

        try {
            findCalorieGoal.removeConsumption(calorieGoalFood.getActualConsumption());
            return calorieGoalFoodRepository.delete(calorieGoalFood);
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public Optional<CalorieGoalFood> getCalorieGoalFoodById(Long id) {
        return calorieGoalFoodRepository.findOne(id);
    }

    public List<CalorieGoalFood> getCalorieGoalFoodsByCalorieGoalId(Long id) {
        return calorieGoalFoodRepository.findByCalorieGoalId(id);
    }
}
