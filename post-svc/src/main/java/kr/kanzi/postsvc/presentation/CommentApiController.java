package kr.kanzi.postsvc.presentation;

import kr.kanzi.postsvc.application.CommentService;
import kr.kanzi.postsvc.presentation.dto.comment.AddCommentRequest;
import kr.kanzi.postsvc.presentation.dto.comment.CommentResponse;
import kr.kanzi.postsvc.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

//    private final PostService postService;
    private final CommentService commentService;


    @GetMapping("/api/posts/{id}/comments")
    public ApiResponse<List<CommentResponse>> findPostComments(@PathVariable long id) {
        List<CommentResponse> comments = commentService.findCommentByPostId(id);
        return ApiResponse.of(HttpStatus.OK, comments) ;
    }

    @PostMapping("/api/posts/{postId}/comments")
    public ApiResponse<Long> addComment(@PathVariable long postId, @RequestBody AddCommentRequest request) {
        Long comment = commentService.addComment(request, postId);
        return ApiResponse.of(HttpStatus.CREATED, comment) ;
    }
    @PutMapping("/api/posts/{postId}/comments")
    public ResponseEntity<Long> patchComment(@RequestBody UpdateCommentRequest updateCommentRequest) {
        Long id = commentService.updateComment(updateCommentRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(id);

    }

    @DeleteMapping("/api/posts/{id}/comments/{commentId}/{commenter}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @PathVariable String commenter) {
        commentService.deleteComment(commentId, commenter);
        return ResponseEntity.ok()
                .build();
    }


}

