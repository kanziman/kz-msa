package kr.kanzi.usersvc.infrastructure;

import kr.kanzi.usersvc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUid(String uid);

}

