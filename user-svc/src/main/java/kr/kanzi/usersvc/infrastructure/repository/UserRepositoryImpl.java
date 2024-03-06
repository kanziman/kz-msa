package kr.kanzi.usersvc.infrastructure.repository;

import kr.kanzi.usersvc.common.exception.EntityNotFoundException;
import kr.kanzi.usersvc.domain.User;
import kr.kanzi.usersvc.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;


    @Override
    public User getById(long id) {
        return findById(id).orElseThrow(()->new EntityNotFoundException("user not found"));
    }

    @Override
    public Optional<User> findById(long id) {
        return userJpaRepository.findById(id).map(UserEntity::toModel);
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findByUid(String uid) {
        return userJpaRepository.findByUid(uid).map(UserEntity::toModel);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.from(user)).toModel();
    }
}
