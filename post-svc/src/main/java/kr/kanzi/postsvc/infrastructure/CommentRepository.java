package kr.kanzi.postsvc.infrastructure;

import kr.kanzi.postsvc.domain.Comment;
import kr.kanzi.postsvc.domain.Post;
import kr.kanzi.postsvc.presentation.dto.comment.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>  {

    @Query("select c from  Comment c WHERE c.post = :post")
    List<Comment> getComments(@Param("post") Post post);


    @Query("SELECT NEW kr.kanzi.postsvc.presentation.dto.comment.CommentResponse(c, u) FROM Comment c LEFT JOIN User u on c.commenter = u.uid WHERE c.post = :post order by c.createdAt desc ")
    List<CommentResponse> getCommentsWithCommenter(@Param("post") Post post);
}

