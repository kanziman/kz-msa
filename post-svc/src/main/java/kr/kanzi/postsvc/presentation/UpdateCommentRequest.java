package kr.kanzi.postsvc.presentation;

import lombok.Getter;

@Getter
public class UpdateCommentRequest {
    Long commentId;
    String message;
    String commenter;
}
