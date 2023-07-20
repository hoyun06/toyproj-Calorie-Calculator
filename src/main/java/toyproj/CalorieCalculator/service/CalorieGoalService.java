package toyproj.CalorieCalculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproj.CalorieCalculator.domain.AccountUser;
import toyproj.CalorieCalculator.domain.CalorieGoal;
import toyproj.CalorieCalculator.repository.CalorieGoalRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CalorieGoalService {

    private final CalorieGoalRepository calorieGoalRepository;

    public Long addCalorieGoal(AccountUser user, double goalAmount) {
        CalorieGoal calorieGoal = CalorieGoal.createCalorieGoal(user, goalAmount);

        return calorieGoalRepository.save(calorieGoal);
    }

    public Long removeCalorieGoal(AccountUser user, LocalDate date) {
        CalorieGoal calorieGoal = calorieGoalRepository.findByUserIdAndDate(user.getId(), date)
                .orElseThrow();

        return calorieGoalRepository.delete(calorieGoal);
    }

    public Optional<CalorieGoal> getCalorieGoalById(Long id) { return calorieGoalRepository.findOne(id); }

    public List<CalorieGoal> getCalorieGoalByUserId(Long userId) { return calorieGoalRepository.findByUserId(userId); }

    public Optional<CalorieGoal> getCalorieGoalByUserIdAndDate(Long userId, LocalDate date) { return calorieGoalRepository.findByUserIdAndDate(userId, date); }
}
