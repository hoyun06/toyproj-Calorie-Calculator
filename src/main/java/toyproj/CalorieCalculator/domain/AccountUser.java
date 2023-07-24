package toyproj.CalorieCalculator.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountUser {

    public AccountUser(String name, String username, String password, String email) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;                            // 사용자 실제 이름

    @Column(nullable = false, unique = true)
    private String username;                        // 사용자 로그인 아이디

    @Column(nullable = false, length = 100)
    private String password;                        // 사용자 비밀번호

    @Column(nullable = false, unique = true)
    private String email;                           // 사용자 이메일

    private String role;

    //-- setter 는 access level private 으로 설정하고 각 필드값들을 변경하는 메소드 따로 작성 --//
    public void changeName(String name) {this.setName(name);}
    public void changeUsername(String username) {this.setUsername(username);}
    public void changePassword(String password) {this.setPassword(password);}
    public void changeEmail(String email) {this.setEmail(email);}
    public void changeRole(String role) {this.setRole(role);}
}
