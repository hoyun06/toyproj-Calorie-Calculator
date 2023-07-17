package toyproj.CalorieCalculator.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproj.CalorieCalculator.domain.Food;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FoodRepository {

    private final EntityManager em;

    public Long save(Food food) {
        em.persist(food);
        return food.getId();
    }

    public Optional<Food> findByFoodCode(String foodCode) {
        return Optional.ofNullable(em.createQuery("select f from Food f where f.foodCode = :foodCode", Food.class)
                .setParameter("foodCode", foodCode)
                .getSingleResult());
    }
}