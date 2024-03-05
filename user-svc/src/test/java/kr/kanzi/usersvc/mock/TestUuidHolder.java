package kr.kanzi.usersvc.mock;

import kr.kanzi.usersvc.common.UuidHolder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestUuidHolder implements UuidHolder {
    private final String uuid;
    @Override
    public String random() {
        return uuid;
    }
}
