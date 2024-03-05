package kr.kanzi.usersvc.service;

import kr.kanzi.usersvc.domain.EntityNotFoundException;
import kr.kanzi.usersvc.service.port.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserEntityServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;


    @Test
    @DisplayName("유저 uuid가 존재하지 않으면, EntityNotFoundException 에러가 난다.")
    void findByUid(){
        when(userRepository.findByUid(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findByUid("non-exist"));
    }

}