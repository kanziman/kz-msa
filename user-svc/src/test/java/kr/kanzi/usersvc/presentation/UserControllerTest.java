package kr.kanzi.usersvc.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.kanzi.usersvc.common.exception.EntityNotFoundException;
import kr.kanzi.usersvc.domain.Role;
import kr.kanzi.usersvc.domain.User;
import kr.kanzi.usersvc.domain.UserUpdate;
import kr.kanzi.usersvc.mock.TestContainer;
import kr.kanzi.usersvc.mock.TestUuidHolder;
import kr.kanzi.usersvc.presentation.port.UserReadService;
import kr.kanzi.usersvc.presentation.response.UserResponse;
import kr.kanzi.usersvc.service.UserServiceImpl;
import kr.kanzi.usersvc.util.ApiResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    protected ObjectMapper objectMapper = new ObjectMapper();
    TestContainer testContainer = TestContainer.builder()
            .uuidHolder(()-> "uuid-1")
            .build();
    @Test
    @DisplayName("유저 아이디로 유저를 조회할 수 있다.")
    void getUser() {
        // when
        testContainer.userRepository.save(User.builder()
                                            .id(1L)
                                            .uid("uuid-1")
                                            .email("user@naver.com")
                                            .nickname("user")
                                            .role(Role.USER)
                                            .build());


        ApiResponse<UserResponse> result = testContainer.userController.getUser("uuid-1");
        assertThat(result.getCode()).isEqualTo(200);
        assertThat(result.getData().getEmail()).isEqualTo("user@naver.com");

    }

    @DisplayName("존재하지 않는 유저를 검색하면 404에러를 받는다.")
    @Test
    void update_user_not_exist() throws Exception {
        //given
        assertThatThrownBy(
                ()-> testContainer.userController.getUser("uuid-non-exist")
        ).isInstanceOf(EntityNotFoundException.class);

    }

}