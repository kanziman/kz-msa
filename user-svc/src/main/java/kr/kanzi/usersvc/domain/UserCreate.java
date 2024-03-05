package kr.kanzi.usersvc.domain;

import lombok.Getter;
@Getter
public class UserCreate {

    private final String email;
    private final String name;
    private final String providerType;

    public UserCreate(String email, String name, String providerType) {
        this.email = email;
        this.name = name;
        this.providerType = providerType;
    }
}
