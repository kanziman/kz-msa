package kr.kanzi.usersvc.config.oauth;

import kr.kanzi.usersvc.domain.Role;
import kr.kanzi.usersvc.domain.UserEntity;
import kr.kanzi.usersvc.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        UserEntity savedUserEntity = saveOrUpdate(user,registrationId);
        return OAuth2Provider.builder()
                .name(savedUserEntity.getName())
                .email(savedUserEntity.getEmail())
                .build();
    }

    // ❷ 유저가 있으면 업데이트, 없으면 유저 생성
    private UserEntity saveOrUpdate(OAuth2User oAuth2User, String registrationId) {
        log.info("save or update :" + oAuth2User.getName());
        ProviderType providerType = ProviderType.valueOf(registrationId.toUpperCase());

        Map<String, Object> attributes = oAuth2User.getAttributes();
        attributes.forEach(
                (k,v) -> System.out.println(k + " = " + v)
        );

        OAuth2Provider oAuth2Dto = OAuth2Provider.of(providerType, oAuth2User.getAttributes());
        String email = oAuth2Dto.getEmail();
        String name = oAuth2Dto.getName();

        UserEntity userEntity = userRepository.findByEmail(email).orElse(null);

        // New user
        if (userEntity == null) {
            userEntity = UserEntity.builder()
                    .email(email)
                    .name(name)
                    .roleType(Role.USER)
                    .password(new BCryptPasswordEncoder().encode("0000"))
                    .nickname(genNickName())
                    .uid(UUID.randomUUID().toString())
                    .providerType(providerType.name())
                    .build();

        }
        return userRepository.save(userEntity);
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

