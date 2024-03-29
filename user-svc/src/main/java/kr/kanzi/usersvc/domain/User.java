package kr.kanzi.usersvc.domain;

import kr.kanzi.usersvc.common.UuidHolder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@ToString
public class User implements UserDetails {

    private Long id;
    private String uid;
    private String email;
    private Role role;
    private String name;
    private String password;
    private String nickname;
    private String providerType;

    @Builder
    public User(Long id, String uid, String email, Role role, String name, String password, String nickname, String providerType) {
        this.id = id;
        this.uid = uid;
        this.email = email;
        this.role = role;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.providerType = providerType;
    }
    public static User from(UserCreate userCreate, UuidHolder uuidHolder) {
        return User.builder()
                .uid(uuidHolder.random())
                .email(userCreate.getEmail())
                .role(Role.USER)
                .password(new BCryptPasswordEncoder().encode("0000"))
                .providerType(userCreate.getProviderType())
                .build();

    }

    public void updateNickName(String nickname) {
        this.nickname = nickname;
    }







    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.USER.getValue()));
    }
    public Collection<? extends GrantedAuthority> getAuthorities(String role) {
        if ("admin".equals(role)) {
            return List.of(new SimpleGrantedAuthority(Role.USER.getValue()),
                    new SimpleGrantedAuthority(Role.ADMIN.getValue())
            );
        }
        return List.of(new SimpleGrantedAuthority(Role.USER.getValue()));
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
