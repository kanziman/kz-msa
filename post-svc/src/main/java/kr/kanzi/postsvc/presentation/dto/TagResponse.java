package kr.kanzi.postsvc.presentation.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TagResponse {

    private final String name;
    private final Long tagCount;
    public TagResponse(String name, Long tagCount) {
        this.name = name;
        this.tagCount = tagCount;
    }

}
