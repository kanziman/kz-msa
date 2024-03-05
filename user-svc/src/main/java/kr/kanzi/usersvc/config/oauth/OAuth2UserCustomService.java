package kr.kanzi.usersvc.config.oauth;

import kr.kanzi.usersvc.common.UuidHolder;
import kr.kanzi.usersvc.domain.User;
import kr.kanzi.usersvc.domain.UserCreate;
import kr.kanzi.usersvc.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Slf4j
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;
    private final UuidHolder uuidHolder;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        User savedUser = saveOrUpdate(user, registrationId);
        return OAuth2Provider.builder()
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .build();
    }

    // ❷ 유저가 있으면 업데이트, 없으면 유저 생성
    private User saveOrUpdate(OAuth2User oAuth2User, String registrationId) {
        log.info("save or update :" + oAuth2User.getName());
        ProviderType providerType = ProviderType.valueOf(registrationId.toUpperCase());

        Map<String, Object> attributes = oAuth2User.getAttributes();
        attributes.forEach(
                (k,v) -> System.out.println(k + " = " + v)
        );

        OAuth2Provider oAuth2Dto = OAuth2Provider.of(providerType, oAuth2User.getAttributes());
        String email = oAuth2Dto.getEmail();
        String name = oAuth2Dto.getName();

        User user = userRepository.findByEmail(email).orElse(null);

        // New user
        if (user == null) {

            UserCreate userCreate = new UserCreate(email, name, providerType.name());

            user = User.from(userCreate, uuidHolder);
            user.updateNickName(genNickName());

        }
        return userRepository.save(user);
    }

    private String genNickName() {
        String name = jdbcTemplate.queryForObject(
                    "SELECT name FROM Animal ORDER BY RAND() LIMIT 1",
                        String.class);

        // 0부터 9999 사이의 숫자 (10,000개, 4자리)
        int number = new Random().nextInt(10000);
        String fourDigitNum = String.format("%04d", number);

        return name + fourDigitNum;
    }

}

