package kr.kanzi.usersvc.service.port;

import kr.kanzi.usersvc.domain.User;

import java.util.Optional;

public interface UserRepository {

    User getById(long id);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);
    Optional<User> findByUid(String uid);

    User save(User user);
}
