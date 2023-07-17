package toyproj.CalorieCalculator.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CalorieGoalFood {                  // CalorieGoal 엔티티 클래스와 Food 엔티티 클래스는 다대다 관계인데 이를 일대다 다대일 관계로 풀어주는 역할을 하는 엔티티 클래스

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calorie_goal_food_id")
    private Long id;

    private Double actualConsumption;          // open api 로부터 받아오는 칼로리 정보는 100g 기준으로 산정한 데이터이므로
                                               // 사용자가 섭취한 음식의 양(그램 수)을 고려한 실제 칼로리 섭취량

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calorie_goal_id")
    private CalorieGoal calorieGoal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;


    // 연관관계 편의 메소드 //
    public void setCalorieGoalRelation(CalorieGoal calorieGoal) {   // CalorieGoalFood 엔티티 클래스 내부의 calorieGoal 필드에 대한 연관관계를 세팅해줄 때 
                                                                    // 마찬가지로 calorieGoal 인스턴스가 가지고 있는 calorieGoalFoods 리스트에도 연관관계 세팅해주는 메소드
        this.calorieGoal = calorieGoal;
        calorieGoal.getCalorieGoalFoods().add(this);
    }


    // 생성 메소드 //
    public static CalorieGoalFood createCalorieGoalFood(Food food, double gramAmount) {    // 음식 정보와 사용자가 실제 섭취한 음식량을 전달받아 새로운 인스턴스 생성
        CalorieGoalFood calorieGoalFood = new CalorieGoalFood();
        double ratio = gramAmount / 100;

        calorieGoalFood.setFood(food);
        calorieGoalFood.setActualConsumption(ratio * food.getCaloriePerHundred());

        return calorieGoalFood;
    }
}
