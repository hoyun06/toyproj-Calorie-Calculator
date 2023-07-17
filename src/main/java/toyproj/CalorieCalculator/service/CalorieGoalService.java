package toyproj.CalorieCalculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproj.CalorieCalculator.domain.CalorieGoal;
import toyproj.CalorieCalculator.repository.CalorieGoalRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CalorieGoalService {

    private final CalorieGoalRepository calorieGoalRepository;

    public Long addCalorieGoal(CalorieGoal calorieGoal) {
        return calorieGoalRepository.save(calorieGoal);
    }

    public Optional<CalorieGoal> getCalorieGoalById(Long id) { return calorieGoalRepository.findOne(id); }

    public List<CalorieGoal> getCalorieGoalByUserId(Long userId) { return calorieGoalRepository.findByUserId(userId); }

    public CalorieGoal getCalorieGoalByUserIdAndDate(Long userId, LocalDate date) { return calorieGoalRepository.findByUserIdAndDate(userId, date); }
}
