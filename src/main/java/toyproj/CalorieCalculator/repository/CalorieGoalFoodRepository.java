package toyproj.CalorieCalculator.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproj.CalorieCalculator.domain.CalorieGoalFood;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CalorieGoalFoodRepository {

    private final EntityManager em;

    public Long save(CalorieGoalFood calorieGoalFood) {
        em.persist(calorieGoalFood);
        return calorieGoalFood.getId();
    }

    public Long delete(CalorieGoalFood calorieGoalFood) {
        Long id = calorieGoalFood.getId();
        em.remove(calorieGoalFood);
        return id;
    }

    public Optional<CalorieGoalFood> findOne(Long id) {
        return Optional.ofNullable(em.find(CalorieGoalFood.class, id));
    }

    public List<CalorieGoalFood> findByCalorieGoalId(Long calorieGoalId) {
        return em.createQuery("select g from CalorieGoalFood g join g.calorieGoal c on " +
                "c.id = :calorieGoalId", CalorieGoalFood.class)
                .setParameter("calorieGoalId", calorieGoalId)
                .getResultList();
    }
}
