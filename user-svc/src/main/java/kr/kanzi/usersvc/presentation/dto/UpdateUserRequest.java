package kr.kanzi.usersvc.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private String email;
    @NotBlank(message = "아이디값은 필수입니다.")
    private String uid;
    @NotBlank(message = "별명은 필수입니다.")
    private String nickName;
    @Builder
    public UpdateUserRequest(String email, String uid, String nickName) {
        this.email = email;
        this.uid = uid;
        this.nickName = nickName;
    }
}


