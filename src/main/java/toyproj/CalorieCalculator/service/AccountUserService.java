package toyproj.CalorieCalculator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproj.CalorieCalculator.domain.AccountUser;
import toyproj.CalorieCalculator.repository.AccountUserRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountUserService {

    private final AccountUserRepository accountUserRepository;

    public Long join(AccountUser accountUser) {
        try {
            validate(accountUser);
            return accountUserRepository.save(accountUser);
        } catch(Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public Optional<AccountUser> getUserById(Long id) {
        return accountUserRepository.findOne(id);
    }

    public Optional<AccountUser> getUserByName(String name) {
        return accountUserRepository.findByName(name);
    }

    public Optional<AccountUser> getUserByUsername(String username) {
        return accountUserRepository.findByUsername(username);
    }

    private void validate(AccountUser accountUser) {
        if(accountUserRepository.findByName(accountUser.getName()).isPresent() ||
                accountUserRepository.findByUsername(accountUser.getUsername()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
