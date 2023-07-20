package toyproj.CalorieCalculator.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountUserDto {
    
    @NotEmpty(message = "이름은 필수입니다.")
    private String name;
    
    @NotEmpty(message = "ID는 필수입니다.")
    private String username;
    
    @NotEmpty(message = "비밀번호는 필수입니다.")
    @Size(max = 30)
    private String password1;
    
    @NotEmpty(message = "비밀번호 확인란은 필수입니다.")
    @Size(max = 30)
    private String password2;
    
    @NotEmpty(message = "이메일은 필수입니다.")
    @Email
    private String email;
}
