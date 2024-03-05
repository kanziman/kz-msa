package kr.kanzi.usersvc.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "Users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserEntity extends BaseEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "uid", nullable = false, unique = true)
    private String uid;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "providerType")
    private String providerType;

    @Builder
    private UserEntity(String email, String name, String password, String nickname, String uid, String providerType
            , Role roleType
    ) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.uid = uid;
        this.providerType =providerType;
        this.role = roleType;
    }

    public void update(String nickname) {
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
        return email;
    }

    @Override
    public String getPassword() {
        return password;
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
