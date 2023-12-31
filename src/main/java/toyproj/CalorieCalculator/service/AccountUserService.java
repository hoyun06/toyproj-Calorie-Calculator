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
            if(validate(accountUser)) {
                return accountUserRepository.save(accountUser);
            } else {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
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

    private boolean validate(AccountUser accountUser) {
        return accountUserRepository.findByName(accountUser.getName()).isEmpty()
                && accountUserRepository.findByUsername(accountUser.getUsername()).isEmpty()
                && accountUserRepository.findByEmail(accountUser.getEmail()).isEmpty();
    }
}
