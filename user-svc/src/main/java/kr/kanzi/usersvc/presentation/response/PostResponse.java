package kr.kanzi.usersvc.presentation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

//@Getter
//@ToString(exclude = "tags")
//@Setter
@Data
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String category;
    private String uid;
    private List<String> tags;
//    private LocalDateTime createdAt;
    private int readCount;
    private int likeCount;
    private int bookmarkCount;
    // user
    private String photoURL;
    private String nickName;
    // comment count
    private long commentCount;



}
