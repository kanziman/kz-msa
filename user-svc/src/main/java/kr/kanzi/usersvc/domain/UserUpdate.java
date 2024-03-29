package kr.kanzi.usersvc.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class UserUpdate {
    private String email;
    @NotBlank(message = "아이디값은 필수입니다.")
    private String uid;
    @NotBlank(message = "별명은 필수입니다.")
    private String nickName;
    @Builder
    public UserUpdate(String email, String uid, String nickName) {
        this.email = email;
        this.uid = uid;
        this.nickName = nickName;
    }
}


