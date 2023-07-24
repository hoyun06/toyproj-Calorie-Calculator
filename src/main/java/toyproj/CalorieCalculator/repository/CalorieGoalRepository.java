package toyproj.CalorieCalculator.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproj.CalorieCalculator.domain.CalorieGoal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CalorieGoalRepository {

    private final EntityManager em;

    public Long save(CalorieGoal calorieGoal) {
        em.persist(calorieGoal);
        return calorieGoal.getId();
    }

    public void update(Long id, double amount) {
        CalorieGoal goal = em.find(CalorieGoal.class, id);
        goal.changeGoalAmount(amount);
    }

    public Long delete(CalorieGoal calorieGoal) {
        Long id = calorieGoal.getId();
        em.remove(calorieGoal);
        return id;
    }

    public Optional<CalorieGoal> findOne(Long id) {
        return Optional.ofNullable(em.find(CalorieGoal.class, id));
    }

    public List<CalorieGoal> findByUserId(Long userId) {                        // 특정 사용자가 생성한 모든 CalorieGoal 들을 리스트 형태로 조회
        try {
            return em.createQuery("select c from CalorieGoal c join c.user u" +
                            " on u.id = :userId", CalorieGoal.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }catch (NoResultException e) {
            return null;
        }
    }

    public Optional<CalorieGoal> findByUserIdAndDate(Long userId, LocalDate date) {       // 특정 사용자가 생성한 모든 CalorieGoal 중에서 특정 날짜를 통해 조회. 사용자는 하루에 한 개씩만
                                                                                          // CalorieGoal 을 생성 가능하므로 CalorieGoal 인스턴스로 반환
        try {
            CalorieGoal calorieGoal = em.createQuery("select c from CalorieGoal c join c.user u on u.id = :userId" +
                            " where c.date = :date", CalorieGoal.class)
                    .setParameter("userId", userId)
                    .setParameter("date", date)
                    .getSingleResult();
            return Optional.of(calorieGoal);
        }catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
