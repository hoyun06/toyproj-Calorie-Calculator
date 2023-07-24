package toyproj.CalorieCalculator.principal;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import toyproj.CalorieCalculator.domain.AccountUser;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class PrincipalDetails implements UserDetails {

    private AccountUser accountUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return accountUser.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return accountUser.getPassword();
    }

    @Override
    public String getUsername() {
        return accountUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
