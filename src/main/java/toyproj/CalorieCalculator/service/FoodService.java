package toyproj.CalorieCalculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproj.CalorieCalculator.domain.Food;
import toyproj.CalorieCalculator.repository.FoodRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodService {

    private final FoodRepository foodRepository;

    public Long addFood(Food food) {
        return foodRepository.save(food);
    }

    public Optional<Food> getFoodByFoodCode(String foodCode) {
        return foodRepository.findByFoodCode(foodCode);
    }
}
