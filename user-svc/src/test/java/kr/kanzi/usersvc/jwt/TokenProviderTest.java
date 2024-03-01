package kr.kanzi.usersvc.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.kanzi.usersvc.config.jwt.JwtProperties;
import kr.kanzi.usersvc.config.jwt.TokenProvider;
import kr.kanzi.usersvc.domain.Role;
import kr.kanzi.usersvc.domain.User;
import kr.kanzi.usersvc.infrastructure.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProperties jwtProperties;



    @DisplayName("유저 정보와 함께 토큰을 만들고, 토큰을 통해 유저 아이디,롤을 확인할 수 있다.")
    @Test void new_token_parser(){
        byte[] secretKeyBytes = Base64.getEncoder().encode(jwtProperties.getSecretKey().getBytes());
        SecretKey signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());

        // given
        User testUser = userRepository.save(User.builder()
                .uid(UUID.randomUUID().toString())
                .email("user@gmail.com")
                .password("test")
                .roleType(Role.ADMIN)
                .build());

        // when
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build();

        String subject = jwtParser.parseClaimsJws(token).getBody().getSubject();
        String role = jwtParser.parseClaimsJws(token).getBody().get("role", String.class);
        assertThat(subject).isEqualTo(testUser.getUid());
        assertThat(role).isEqualTo(Role.ADMIN.name());

    }


    @DisplayName("만료된 토큰인 경우에 유효성 검증에 실패한다.")
    @Test
    void validToken_invalidToken() {
        // given
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("32자 이상이 평문키로 토큰을 만들수 있다.")
    @Test void token_make(){

        byte[] secretKeyBytes = Base64.getEncoder().encode(jwtProperties.getSecretKey().getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        Instant now = Instant.now();
        String token = Jwts.builder()
                .setSubject("subjectid")
                .setExpiration(Date.from(now.plusMillis(360000)))
                .setIssuedAt(Date.from(now))
                .signWith(secretKey)
                .compact();
        assertThat(token.length()).isGreaterThan(32);
    }


    @DisplayName("유효한 토큰인 경우에 유효성 검증에 성공한다.")
    @Test
    void validToken_validToken() {
        // given
        String token = JwtFactory.withDefaultValues()
                .createToken(jwtProperties);

        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isTrue();
    }


    @DisplayName("토큰 기반으로 인증정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {
        // given
        String userEmail = "user@email.com";
        String token = JwtFactory.builder()
                .claims(Map.of("uid", "uuid"))
                .claims(Map.of("email", userEmail))
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);

        // when
        Authentication authentication = tokenProvider.getAuthentication(token);

        // then
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);
    }

    @DisplayName("토큰으로 유저 ID를 가져올 수 있다.")
    @Test
    void getUserId() {
        // given
        String userId = "1";
        String token = JwtFactory.builder()
                .claims(Map.of("uid", userId))
                .build()
                .createToken(jwtProperties);

        // when
        String userIdByToken = tokenProvider.getUserId(token);

        // then
        assertThat(userIdByToken).isEqualTo(userId);
    }
}
