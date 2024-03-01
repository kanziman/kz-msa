package kr.kanzi.postsvc.presentation.dto.comment;

import kr.kanzi.postsvc.domain.Comment;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class AddCommentRequest {

    private String message;
    private String commenter;


    public Comment toEntity() {
        return Comment.builder()
                .commenter(commenter)
                .message(message)
                .build();
    }

}
