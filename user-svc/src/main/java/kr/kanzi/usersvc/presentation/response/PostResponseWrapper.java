package kr.kanzi.usersvc.presentation.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
public class PostResponseWrapper {
    private List data;

    public PostResponseWrapper(List data) {
        this.data = data;
    }
}
