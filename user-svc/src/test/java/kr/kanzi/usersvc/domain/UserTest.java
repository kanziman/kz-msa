package kr.kanzi.usersvc.domain;

import kr.kanzi.usersvc.config.oauth.ProviderType;
import kr.kanzi.usersvc.mock.TestUuidHolder;
import kr.kanzi.usersvc.presentation.response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {

    @Test
    @DisplayName("user 객체를 생성할 수 있다")
    public void user_create() {
        // given
        UserCreate userCreate = new UserCreate("user@naver.com", "username", ProviderType.NAVER.name());
        String stringUuid = UUID.randomUUID().toString();

        // when
        User user = User.from(userCreate, new TestUuidHolder(stringUuid));

        // then
        assertThat(user.getUid()).isEqualTo(stringUuid);
        assertThat(user.getEmail()).isEqualTo("user@naver.com");
        assertThat(user.getRole().getValue()).isEqualTo("user");
        assertThat(user.getProviderType()).isEqualTo("NAVER");
    }

    @Test
    @DisplayName("user nickname 업데이트 할 수 있다")
    public void user_update() {
        // given
        User user = User.builder()
                .uid(UUID.randomUUID().toString())
                .email("user@naver.com")
                .nickname("saga")
                .role(Role.USER)
                .build();

        // when
        user.updateNickName("renew");

        // then
        assertThat(user.getEmail()).isEqualTo("user@naver.com");
        assertThat(user.getNickname()).isEqualTo("renew");
        assertThat(user.getRole().getValue()).isEqualTo("user");
    }

    @Test
    @DisplayName("user 로 응답 객체를 생성할 수 있다")
    public void user_create_response() {
        // given
        User user = User.builder()
                .uid("uuid-uuid")
                .email("user@naver.com")
                .nickname("saga")
                .role(Role.USER)
                .build();

        // when
        UserResponse userResponse = UserResponse.from(user);

        // then
        assertThat(userResponse.getUid()).isEqualTo("uuid-uuid");
        assertThat(userResponse.getEmail()).isEqualTo("user@naver.com");
        assertThat(userResponse.getNickName()).isEqualTo("saga");
        assertThat(userResponse.getRole()).isEqualTo("user");
    }

}
