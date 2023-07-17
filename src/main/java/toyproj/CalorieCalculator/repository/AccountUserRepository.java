package toyproj.CalorieCalculator.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toyproj.CalorieCalculator.domain.AccountUser;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountUserRepository {

    private final EntityManager em;

    public Long save(AccountUser accountUser) {
        em.persist(accountUser);
        return accountUser.getId();
    }

    public Optional<AccountUser> findOne(Long id) {
        return Optional.ofNullable(em.find(AccountUser.class, id));
    }

    public Optional<AccountUser> findByName(String name) {
        return Optional.ofNullable(em.createQuery("select a from AccountUser a where a.name = :name"
                        , AccountUser.class)
                .setParameter("name", name)
                .getSingleResult());
    }

    public Optional<AccountUser> findByUsername(String username) {
        return Optional.ofNullable(em.createQuery("select a from AccountUser a where a.username = :username"
                        , AccountUser.class)
                .setParameter("username", username)
                .getSingleResult());
    }
}
