package tv.wouri.speak.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tv.wouri.speak.models.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Login implements UserDetails {

    private String username;
    private String password;
    private Boolean enabled;
    private List<GrantedAuthority> authorities;
    private User user;

    public Login() {
    }

    public Login(String username) {
        this.username = username;
    }


    public Login(User user) {
        this.username = user.getLogin();
        this.password = user.getPassword();
        this.enabled = user.getStatus();
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> auths = new ArrayList<>();
        GrantedAuthority auhority = new SimpleGrantedAuthority("ROLE_"+this.user.getRole().getLibelle());
        auths.add(auhority);

        return auths;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
        return this.enabled;
    }

    public String getFullName() {
        return user.getName();
    }
}

