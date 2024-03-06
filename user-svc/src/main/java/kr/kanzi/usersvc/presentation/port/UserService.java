package kr.kanzi.usersvc.presentation.port;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.kanzi.usersvc.domain.User;
import kr.kanzi.usersvc.presentation.response.PostResponse;

import java.util.List;

public interface UserService {
    void update(String uid, String nickName);
    User getByUid(String uid);
    User getByEmail(String email);
    User getById(Long id);
    List<PostResponse> getByUidWithLike(String uid) throws JsonProcessingException;
}
