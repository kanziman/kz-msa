package kr.kanzi.postsvc.presentation;

import jakarta.validation.Valid;
import kr.kanzi.postsvc.application.PostService;
import kr.kanzi.postsvc.presentation.dto.AddBookMarkRequest;
import kr.kanzi.postsvc.presentation.dto.AddLikeRequest;
import kr.kanzi.postsvc.presentation.dto.TagResponse;
import kr.kanzi.postsvc.presentation.dto.post.*;
import kr.kanzi.postsvc.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;


    @GetMapping("/api/posts")
    public ApiResponse<PageResultDTO> findPostsQuery(PostRequestDto params) {
        PageResultDTO postsPage = postService.getPostsPage(params);
        return ApiResponse.of(HttpStatus.OK, postsPage) ;
    }

    @PostMapping("/api/posts")
        public ApiResponse<PostResponse> addPost(@Valid @RequestBody AddPostRequest request) {
        PostResponse postResponse = postService.addPost(request);
        return ApiResponse.of(HttpStatus.CREATED, postResponse) ;
    }

    @GetMapping("/api/posts/{id}")
    public ApiResponse<PostResponse> findPost(@PathVariable long id) {
        PostResponse postResponse = postService.findById(id);
        return ApiResponse.of(HttpStatus.OK, postResponse) ;
    }

    @DeleteMapping("/api/posts/{id}/{writer}")
    public ResponseEntity<Void> deletePost(@PathVariable long id, @PathVariable String writer) {
        postService.delete(id, writer);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/posts/{id}")
    public ResponseEntity<PostResponse> putPost(@PathVariable long id, @RequestBody UpdatePostRequest request

    ) {
        PostResponse response = postService.update(id, request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }


    // === LIKE
    @PostMapping("/api/posts/{postId}/like")
    public ResponseEntity<String> addPostLike( @PathVariable long postId, @RequestBody AddLikeRequest addLikeRequest) {
        postService.addLike(postId, addLikeRequest.getUid());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("saved");
    }
    @GetMapping("/api/posts/{postId}/like/{uid}")
    public ResponseEntity<Boolean> getPostLike(@PathVariable long postId, @PathVariable String uid) {
        boolean hasLike = postService.hasLike(postId, uid);
        return ResponseEntity.ok()
                .body(hasLike);
    }

    // == BOOK MARK
    @PostMapping("/api/posts/{postId}/bookMarks")
    public ResponseEntity<String> addPostBookMark( @PathVariable long postId, @RequestBody AddBookMarkRequest bookMarkRequest            ) {
        postService.addBookMark(postId, bookMarkRequest.getUid());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("saved");
    }
    @GetMapping("/api/posts/{postId}/bookMarks/{uid}")
    public ResponseEntity<Boolean> getPostBookMark(@PathVariable long postId, @PathVariable String uid) {
        boolean hasBookMark = postService.hasBookMark(postId, uid);
        return ResponseEntity.ok()
                .body(hasBookMark);
    }

    // == TAG
    @GetMapping("/api/posts/tags")
    public ApiResponse<List<TagResponse>> getTopTags() {
        List<TagResponse> tags = postService.getTopTags();
        return ApiResponse.of(HttpStatus.OK, tags) ;
    }


    @GetMapping("/api/posts/users/{uid}/bookMarks")
    public ApiResponse<List<PostResponse>> getUserBookMark(@PathVariable String uid) {
        List<PostResponse> userBookMarksPosts = postService.getUserBookMarks(uid);

        return ApiResponse.of(HttpStatus.OK, userBookMarksPosts);
    }
    @GetMapping("/api/posts/users/{uid}/likes")
    public ApiResponse<List<PostResponse>> getUserLikes(@PathVariable String uid) {
        List<PostResponse> userLikes = postService.getUserLikes(uid);
        return ApiResponse.of(HttpStatus.OK, userLikes);
    }
}
