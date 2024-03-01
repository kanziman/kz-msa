package kr.kanzi.usersvc.presentation.dto;

import kr.kanzi.usersvc.domain.User;
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
    public UserResponse(User user) {
        this.uid = user.getUid();
        this.email = user.getEmail();
        this.name = user.getName();
        this.photoURL = PHOTO_URL + user.getUid();
        this.nickName = user.getNickname();
        this.role = user.getRole().getValue();
        this.roles = (List) user.getAuthorities(role);
    }
}
