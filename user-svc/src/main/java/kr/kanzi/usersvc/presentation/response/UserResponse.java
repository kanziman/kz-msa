package kr.kanzi.usersvc.presentation.response;

import kr.kanzi.usersvc.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class UserResponse {
    public static String PHOTO_URL = "https://api.dicebear.com/7.x/thumbs/svg?seed=";

    private String uid;
    private String name;
    private String email;
    private String nickName;
    private String photoURL;
    private List roles;
    private String role;

    private List<PostResponse> posts;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .uid(user.getUid())
                .name(user.getName())
                .email(user.getEmail())
                .nickName(user.getNickname())
                .photoURL(PHOTO_URL + user.getUid())
                .role(user.getRole().getValue())
                .roles((List) user.getAuthorities())
                .build();
    }

    public void addPosts(List<PostResponse> posts){
        this.posts = posts;
    }
}
