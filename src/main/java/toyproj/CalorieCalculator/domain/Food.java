package toyproj.CalorieCalculator.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    private String name;                    // 음식 이름

    @Column(name = "calorie")
    private Double calorie;                 // open api 로부터 받아오는 데이터는 해당 음식 1회 제공량 기준 칼로리
    
    @Column(unique = true)
    private String foodCode;                // open api 로부터 받아오는 데이터에는 각 음식마다 고유한 코드번호 존재

    private Integer size;                   // 해당 음식의 1회 제공량

    public double getCaloriePerGram() {     // 음식의 칼로리를 1회 제공량으로 나눠서 그램당 칼로리 정보 리턴 
        return calorie / (double) size;
    }

    // 생성 메소드 //
    public static Food createFood(String name, double calorie, String foodCode, int size) {
        Food food = new Food();

        food.setName(name);
        food.setCalorie(calorie);
        food.setFoodCode(foodCode);
        food.setSize(size);

        return food;
    }
}
