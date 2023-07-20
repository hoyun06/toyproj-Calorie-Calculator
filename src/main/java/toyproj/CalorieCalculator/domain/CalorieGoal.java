package toyproj.CalorieCalculator.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CalorieGoal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calorie_goal_id")
    private Long id;

    private Double goalAmount;             // 사용자가 특정 날짜에 대해서 설정하고자 하는 목표 칼로리량

    private LocalDate date;                 // 사용자는 원하는 날짜를 지정하여 목표 칼로리량 설정가능

    private Double totalConsumption;       // 사용자가 해당 날짜에 섭취한 총 칼로리량. calorieGoalFoods 안에 있는 모든 인스턴스들의 actualConsumption 합한 값
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AccountUser user;               // FK 로 사용자의 ID 를 가지고 있음. 이를 통해 어느 사용자의 데이터인지 식별가능

    @OneToMany(mappedBy = "calorieGoal", cascade = CascadeType.ALL)
    private List<CalorieGoalFood> calorieGoalFoods = new ArrayList<>();

    public void addConsumption(double amount) {
        totalConsumption += amount;
    }

    public void removeConsumption(double amount) {
        if(amount > totalConsumption) {
            throw new IllegalStateException("총 칼로리량을 초과할 수 없습니다.");
        }
        totalConsumption -= amount;
    }

    // 생성 메소드 //
    // 사용자 정보, 목표 칼로리량, 날짜를 넘겨 받아 새로운 CalorieGoal 인스턴스 생성
    public static CalorieGoal createCalorieGoal(AccountUser user, double goalAmount) {
        CalorieGoal calorieGoal = new CalorieGoal();

        calorieGoal.setUser(user);
        calorieGoal.setGoalAmount(goalAmount);
        calorieGoal.setDate(LocalDate.now());

        return calorieGoal;
    }
}
