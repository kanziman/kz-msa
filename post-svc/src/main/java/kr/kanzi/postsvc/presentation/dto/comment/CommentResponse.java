package kr.kanzi.postsvc.presentation.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.kanzi.postsvc.domain.Comment;
import kr.kanzi.postsvc.domain.User;
import kr.kanzi.postsvc.util.Constants;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {

    private Long id;

    private String message;
    private String commenter;
    private String photoURL;
    private String nickName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public CommentResponse(Long id, String message, String commenter, String photoURL, String nickName, LocalDateTime createdAt) {
        this.id = id;
        this.message = message;
        this.commenter = commenter;
        this.photoURL = photoURL;
        this.nickName = nickName;
        this.createdAt = createdAt;
    }

    @Builder
    public CommentResponse(Comment comment, User user) {
        this.id = comment.getId();

        this.commenter = comment.getCommenter();
        this.message = comment.getMessage();
        this.createdAt = comment.getCreatedAt();
        this.nickName = user == null? "" : user.getNickname();
        this.photoURL = user == null? "" : Constants.PHOTO_URL + comment.getCommenter();
    }


}
