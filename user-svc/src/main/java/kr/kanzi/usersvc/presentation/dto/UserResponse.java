package kr.kanzi.usersvc.presentation.dto;

import kr.kanzi.usersvc.domain.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class UserResponse {
    public static String PHOTO_URL = "https://api.dicebear.com/7.x/thumbs/svg?seed=";

    private String uid;
    private String name;
    private String email;
    private String nickName;
    private String photoURL;
    private List roles;
    private String role;

    @Builder
    public UserResponse(UserEntity userEntity) {
        this.uid = userEntity.getUid();
        this.email = userEntity.getEmail();
        this.name = userEntity.getName();
        this.photoURL = PHOTO_URL + userEntity.getUid();
        this.nickName = userEntity.getNickname();
        this.role = userEntity.getRole().getValue();
        this.roles = (List) userEntity.getAuthorities(role);
    }
}
