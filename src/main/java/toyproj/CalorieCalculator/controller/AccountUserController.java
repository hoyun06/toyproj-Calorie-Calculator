package toyproj.CalorieCalculator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.StringUtils;
import toyproj.CalorieCalculator.domain.AccountUser;
import toyproj.CalorieCalculator.dto.AccountUserDto;
import toyproj.CalorieCalculator.service.AccountUserService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountUserController {

    private final AccountUserService accountUserService;

    @GetMapping("/user/new")
    public String userForm(Model model) {
        model.addAttribute("userDto", new AccountUserDto());
        return "user/userForm";
    }

    @PostMapping("/user/new")
    public String addNewUser(@Valid AccountUserDto userDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("userDto", userDto);
            return "user/userForm";
        }

        if(!(userDto.getPassword1().equals(userDto.getPassword2()))) {
            result.rejectValue("password2", "PasswordNotMatch", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("userDto", userDto);
            return "user/userForm";
        }

        AccountUser user = new AccountUser(userDto.getName(), userDto.getUsername(),userDto.getPassword1(), userDto.getEmail());

        try {
            accountUserService.join(user);
        } catch (Exception e) {
            log.info(e.getMessage());
            result.reject("UserAlreadyExist", "이미 존재하는 회원입니다.");
            model.addAttribute("userDto", userDto);
            return "user/userForm";
        }

        return "redirect:/";
    }
}
