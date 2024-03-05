package kr.kanzi.usersvc.mock;

import kr.kanzi.usersvc.common.UuidHolder;
import kr.kanzi.usersvc.presentation.UserController;
import kr.kanzi.usersvc.service.UserServiceImpl;
import kr.kanzi.usersvc.service.port.UserRepository;
import lombok.Builder;

public class TestContainer {

    public final UserRepository userRepository;
    public final UserController userController;
    @Builder
    public TestContainer(UuidHolder uuidHolder) {
        this.userRepository = new FakeUserRepository();
        UserServiceImpl userService = UserServiceImpl.builder()
                .uuidHolder(uuidHolder)
                .userRepository(userRepository)
                .build();

        this.userController = UserController.builder()
                .userService(userService)
                .build();
    }


}
