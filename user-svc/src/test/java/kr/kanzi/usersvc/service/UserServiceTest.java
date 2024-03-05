package kr.kanzi.usersvc.service;

import kr.kanzi.usersvc.domain.User;
import kr.kanzi.usersvc.mock.FakeUserRepository;
import kr.kanzi.usersvc.mock.TestUuidHolder;
import kr.kanzi.usersvc.presentation.dto.UserUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserServiceTest {
    private UserServiceImpl userService;

    @BeforeEach
    void init() {
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        TestUuidHolder testUuidHolder = new TestUuidHolder("uuid-1");
        this.userService = UserServiceImpl.builder()
                .uuidHolder(testUuidHolder)
                .userRepository(fakeUserRepository)
                .build();
        fakeUserRepository.save(User.builder()
                .id(1L)
                .uid(testUuidHolder.random())
                .email("user@naver.com")
                .nickname("user")
                .build());
        fakeUserRepository.save(User.builder()
                .id(2L)
                .email("second@naver.com")
                .nickname("second")
                .build());
    }

    @Test
    @DisplayName("email 로 유저를 찾을 수 있다.")
    void get_by_email() {
        // given
        String email = "user@naver.com";

        // when
        User result = userService.getByEmail(email);

        // then
        assertThat(result.getNickname()).isEqualTo("user");
    }

    @Test
    @DisplayName("uuid 로 유저를 찾을 수 있다.")
    void get_by_id() {
        // given
        // when
        User result = userService.getByUid("uuid-1");
        // then
        assertThat(result.getNickname()).isEqualTo("user");
    }

    @Test
    @DisplayName("UserUpdate 로 유저 닉네임을 변경할 수 있다.")
    void update_user() {
        // given
        UserUpdate userUpdate = UserUpdate.builder()
                .nickName("user-n")
                .build();
        // when
        userService.update("uuid-1", userUpdate.getNickName());

        // then
        User user = userService.getByUid("uuid-1");
        assertThat(user.getId()).isNotNull();
        assertThat(user.getNickname()).isEqualTo("user-n");
    }


}
