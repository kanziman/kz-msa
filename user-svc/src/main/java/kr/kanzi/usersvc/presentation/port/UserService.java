package kr.kanzi.usersvc.presentation.port;

import kr.kanzi.usersvc.domain.User;

public interface UserService {
    void update(String uid, String nickName);
    User getByUid(String uid);
    User getByEmail(String email);
    User getById(Long id);
}
