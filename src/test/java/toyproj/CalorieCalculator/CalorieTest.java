package toyproj.CalorieCalculator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproj.CalorieCalculator.domain.AccountUser;
import toyproj.CalorieCalculator.domain.CalorieGoal;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class CalorieTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    void calorieGoalEntityTest() {
        AccountUser user = new AccountUser("hello","hoyun06", "pwd","email@gmail.com");

        //CalorieGoal goal = new CalorieGoal();
        //goal.setUser(user);
        //goal.getFood().put("닭발", "300");
        //System.out.println("result = " + goal.getFood().get("닭발"));

        em.persist(user);
        //em.persist(goal);
        //Long id = goal.getId();

        em.flush();
        em.clear();

        //alorieGoal findGoal = em.find(CalorieGoal.class, id);
       //System.out.println("result = " + findGoal.getFood().get("닭발"));
        //System.out.println("findGoal.getUser().getEmail() = " + findGoal.getUser().getEmail());

        //assertThat(findGoal.getFood().get("닭발")).isEqualTo(null);
        // hashmap 필드는 영속성 컨텍스트에 의해 관리되지 않으므로 1차 캐시가 clear 된 뒤에 다시 Calorie goal
        // 엔티티 객체 조회해오면 hashmap 안에는 이전에 저장해뒀던 값 날라감.
    }
}
