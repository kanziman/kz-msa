package kr.kanzi.usersvc.presentation.port;

import kr.kanzi.usersvc.domain.User;

public interface UserReadService {
    User getByUid(String uid);
    User getByEmail(String email);
    User getById(Long id);
}
