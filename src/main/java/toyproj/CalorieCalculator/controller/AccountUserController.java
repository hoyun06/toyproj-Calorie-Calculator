package toyproj.CalorieCalculator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import toyproj.CalorieCalculator.domain.AccountUser;
import toyproj.CalorieCalculator.dto.AccountUserDto;
import toyproj.CalorieCalculator.service.AccountUserService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountUserController {

    private final AccountUserService accountUserService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/user/new")
    public String userForm(Model model) {
        model.addAttribute("userDto", new AccountUserDto());
        return "user/userForm";
    }

    @PostMapping("/user/new")
    public String addNewUser(@ModelAttribute("userDto") @Valid AccountUserDto userDto, BindingResult result) {
        if(result.hasErrors()) {
            return "user/userForm";
        }

        if(!(userDto.getPassword1().equals(userDto.getPassword2()))) {
            result.rejectValue("password2", "PasswordNotMatch", "비밀번호가 일치하지 않습니다.");
            return "user/userForm";
        }

        AccountUser user = new AccountUser(userDto.getName(), userDto.getUsername(),
                passwordEncoder.encode(userDto.getPassword1()), userDto.getEmail());
        user.changeRole("ROLE_USER");

        try {
            accountUserService.join(user);
        } catch (Exception e) {
            log.info(e.getMessage());
            ObjectError error = new ObjectError("globalError", "이미 존재하는 회원입니다.");
            result.addError(error);
            return "user/userForm";
        }

        return "redirect:/";
    }
}
