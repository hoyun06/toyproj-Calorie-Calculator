package toyproj.CalorieCalculator.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
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
        try {
            AccountUser user = em.createQuery("select a from AccountUser a where a.name = :name", AccountUser.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.ofNullable(user);
        } catch(NoResultException e) {
            return Optional.empty();
        }

    }

    public Optional<AccountUser> findByUsername(String username) {
        try {
            AccountUser user = em.createQuery("select a from AccountUser a where a.username = :username", AccountUser.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.ofNullable(user);
        } catch(NoResultException e) {
            return  Optional.empty();
        }
    }
}
