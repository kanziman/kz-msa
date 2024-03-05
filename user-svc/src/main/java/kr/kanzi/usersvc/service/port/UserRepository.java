package kr.kanzi.usersvc.service.port;

import kr.kanzi.usersvc.domain.UserEntity;

import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUid(String uid);

    UserEntity save(UserEntity userEntity);
}
