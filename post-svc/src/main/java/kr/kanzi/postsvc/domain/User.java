package kr.kanzi.postsvc.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Table(name = "Users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends BaseEntity  {

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
    private User(String email, String name, String password, String nickname, String uid, String providerType
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

    public User update(String nickname) {
        this.nickname = nickname;
        return this;
    }


}
