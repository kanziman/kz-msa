package kr.kanzi.usersvc.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.kanzi.usersvc.domain.UserEntity;
import kr.kanzi.usersvc.service.UserService;
import kr.kanzi.usersvc.domain.Role;
import kr.kanzi.usersvc.presentation.dto.UpdateUserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserEntityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Test
    @DisplayName("유저 아이디로 유저를 조회할 수 있다.")
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void getUser() throws Exception {
        UserEntity userEntity = createUser("user-id", "email1@com", Role.USER);
        given(userService.findByUid(any())).willReturn(userEntity);

        mockMvc.perform(get("/api/users/{uid}", userEntity.getUid()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.uid").value(userEntity.getUid()))
                .andExpect(jsonPath("$.data.email").value(userEntity.getEmail()));
    }

    @DisplayName("유저 업데이트 할 때 아이디값은 필수값이다.")
    @Test
    void createPostWithoutTitle() throws Exception {
        //given
        UpdateUserRequest request = UpdateUserRequest.builder()
                .nickName("nick")
                .email("e@mail.com")
                .build();

        // when // then
        mockMvc.perform(
                        put("/api/users")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("아이디값은 필수입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }
    @DisplayName("유저 업데이트 할 때 닉네임은 필수값이다.")
    @Test
    void updateUserWithoutNickname() throws Exception {
        //given
        UpdateUserRequest request = UpdateUserRequest.builder()
                .email("e@mail.com")
                .uid("uid")
                .build();

        // when // then
        mockMvc.perform(
                        put("/api/users")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("별명은 필수입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    private UserEntity createUser(String uid, String email, Role role) {
        return UserEntity.builder()
                .uid(uid)
                .email(email)
                .roleType(role)
                .build();
    }
}