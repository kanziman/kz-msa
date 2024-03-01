package kr.kanzi.postsvc.application;

import kr.kanzi.postsvc.domain.Comment;
import kr.kanzi.postsvc.domain.Post;
import kr.kanzi.postsvc.domain.User;
import kr.kanzi.postsvc.domain.exception.EntityNotFoundException;
import kr.kanzi.postsvc.infrastructure.CommentRepository;
import kr.kanzi.postsvc.infrastructure.PostRepository;
import kr.kanzi.postsvc.infrastructure.UserRepository;
import kr.kanzi.postsvc.presentation.UpdateCommentRequest;
import kr.kanzi.postsvc.presentation.dto.comment.AddCommentRequest;
import kr.kanzi.postsvc.presentation.dto.comment.CommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
@Transactional(readOnly = true)
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    /**
     * COMMENTS LIST
     */

    public List<CommentResponse> findCommentByPostId(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("not found : " + postId));
        List<CommentResponse> commentsWithCommenter = commentRepository.getCommentsWithCommenter(post);

        return commentsWithCommenter;
    }

    /**
     * SAVE COMMENTS
     */
    public Long addComment(AddCommentRequest request, Long postId) {
        String uid = request.getCommenter();
        User user = userRepository.findByUid(uid)
                .orElseThrow(() -> new EntityNotFoundException("not found : " + uid));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("not found : " + postId));

        Comment entity = request.toEntity();
        entity.setPost(post);
        entity.setUserId(user.getId());

        Comment save = commentRepository.save(entity);
        return save.getId();
    }

    /**
     * UPDATE COMMENTS
     */
    @Transactional(readOnly = false)
    public Long updateComment(UpdateCommentRequest updateCommentRequest) {

        Comment comment = commentRepository.findById(updateCommentRequest.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException("not found : " + updateCommentRequest.getCommentId()));

        comment.checkWriter(updateCommentRequest.getCommenter());

        comment.change(updateCommentRequest.getMessage());

        return comment.getId();
    }

    /**
     * DELETE COMMENT
     */
    @Transactional(readOnly = false)
    public void deleteComment(Long commnetId, String commenter) {
        Comment comment = commentRepository.findById(commnetId)
                .orElseThrow(() -> new EntityNotFoundException("not found : " + commnetId));
        comment.checkWriter(commenter);
        commentRepository.delete(comment);
    }

}
