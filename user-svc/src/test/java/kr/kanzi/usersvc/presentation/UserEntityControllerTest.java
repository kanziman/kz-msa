package kr.kanzi.usersvc.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.kanzi.usersvc.domain.Role;
import kr.kanzi.usersvc.domain.User;
import kr.kanzi.usersvc.domain.UserUpdate;
import kr.kanzi.usersvc.service.UserServiceImpl;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserEntityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceImpl userServiceImpl;

    @Autowired
    protected ObjectMapper objectMapper;

    @Test
    @DisplayName("유저 아이디로 유저를 조회할 수 있다.")
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void getUser() throws Exception {
        User user = createUser("user-id", "email1@com", Role.USER);
        given(userServiceImpl.getByUid(any())).willReturn(user);

        mockMvc.perform(get("/api/users/{uid}", user.getUid()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.uid").value(user.getUid()))
                .andExpect(jsonPath("$.data.email").value(user.getEmail()));
    }

    @DisplayName("유저 업데이트 할 때 아이디값은 필수값이다.")
    @Test
    void createPostWithoutTitle() throws Exception {
        //given
        UserUpdate request = UserUpdate.builder()
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
        UserUpdate request = UserUpdate.builder()
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

    private User createUser(String uid, String email, Role role) {
        return User.builder()
                .uid(uid)
                .email(email)
                .role(role)
                .build();
    }
}