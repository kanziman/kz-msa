package kr.kanzi.usersvc.presentation;

import jakarta.validation.Valid;
import kr.kanzi.usersvc.domain.User;
import kr.kanzi.usersvc.presentation.dto.UserUpdate;
import kr.kanzi.usersvc.presentation.dto.UserResponse;
import kr.kanzi.usersvc.service.UserServiceImpl;
import kr.kanzi.usersvc.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/user-svc")
@RequiredArgsConstructor
public class UserController {
    private final Environment env;
    private final UserServiceImpl userServiceImpl;

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public String health(){
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", gateway ip(env)=" + env.getProperty("gateway.ip")
                + ", message=" + env.getProperty("greeting.message")
                + ", token secret=" + env.getProperty("jwt.secret_key")
                + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }

    @GetMapping("/api/users/{uid}")
    public ApiResponse<UserResponse> getUser(@PathVariable String uid) {

        User user = userServiceImpl.getByUid(uid);
        UserResponse userResponse = UserResponse.from(user);
        return ApiResponse.of(HttpStatus.OK, userResponse);
    }

    @PutMapping("/api/users")
    public ApiResponse<UserResponse> updateUser(@Valid @RequestBody UserUpdate dto) {

        userServiceImpl.update(dto.getUid(), dto.getNickName());
        UserResponse userResponse = UserResponse.from(userServiceImpl.getByUid(dto.getUid()));

        return ApiResponse.of(HttpStatus.OK, userResponse);
    }
}
