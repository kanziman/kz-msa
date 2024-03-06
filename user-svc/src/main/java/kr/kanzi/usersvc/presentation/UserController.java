package kr.kanzi.usersvc.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import kr.kanzi.usersvc.domain.User;
import kr.kanzi.usersvc.domain.UserUpdate;
import kr.kanzi.usersvc.presentation.port.UserService;
import kr.kanzi.usersvc.presentation.response.PostResponse;
import kr.kanzi.usersvc.presentation.response.UserResponse;
import kr.kanzi.usersvc.util.ApiResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/user-svc")
@RequiredArgsConstructor
@Builder
public class UserController {
    private final Environment env;
//    private final UserServiceImpl userServiceImpl;
    private final UserService userService;
//    private final UserReadService userReadService;
//    private final UserUpdateService userUpdateService;

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

        User user = userService.getByUid(uid);
        UserResponse userResponse = UserResponse.from(user);
        return ApiResponse.of(HttpStatus.OK, userResponse);
    }
    @GetMapping("/api/users/{uid}/likes")
    public ApiResponse<UserResponse> getUserLikes(@PathVariable String uid) throws JsonProcessingException {

        List<PostResponse> byUidWithLike = userService.getByUidWithLike(uid);
        return ApiResponse.of(HttpStatus.OK, byUidWithLike);
    }

    @PutMapping("/api/users")
    public ApiResponse<UserResponse> updateUser(@Valid @RequestBody UserUpdate dto) {

        userService.update(dto.getUid(), dto.getNickName());
        UserResponse userResponse = UserResponse.from(userService.getByUid(dto.getUid()));

        return ApiResponse.of(HttpStatus.OK, userResponse);
    }
}
