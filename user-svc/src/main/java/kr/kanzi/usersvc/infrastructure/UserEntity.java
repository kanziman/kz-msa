package kr.kanzi.usersvc.infrastructure;


import jakarta.persistence.*;
import kr.kanzi.usersvc.domain.BaseEntity;
import kr.kanzi.usersvc.domain.Role;
import kr.kanzi.usersvc.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "Users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserEntity extends BaseEntity {

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
    private UserEntity(Long id, String uid, String email, Role role, String name, String password, String nickname, String providerType) {
        this.id = id;
        this.uid = uid;
        this.email = email;
        this.role = role;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.providerType = providerType;
    }


    public static UserEntity from(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .uid(user.getUid())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .nickname(user.getNickname())
                .name(user.getName())
                .build();
    }
    public User toModel() {
        return User.builder()
                .id(id)
                .uid(uid)
                .email(email)
                .password(password)
                .role(role)
                .nickname(nickname)
                .name(name)
                .build();
    }
}
