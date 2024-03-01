package kr.kanzi.postsvc.infrastructure;

import kr.kanzi.postsvc.domain.Post;
import kr.kanzi.postsvc.infrastructure.search.SearchPostRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> , SearchPostRepository {

    @Query("select p,t from Post p LEFT JOIN Tag t on t.post = p WHERE p.id = :postId")
    Post getPostWithTags(@Param("postId") Long postId);

}

