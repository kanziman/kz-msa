package kr.kanzi.postsvc.infrastructure;

import kr.kanzi.postsvc.domain.Likes;
import kr.kanzi.postsvc.domain.Post;
import kr.kanzi.postsvc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Likes, Long>  {


    @Query("select l from Likes l JOIN Post p on l.post = p JOIN User u on l.user = :user " +
            "WHERE p = :post ")
    Likes getLike(@Param("post") Post post, @Param("user") User user);

    @Query("select p from Likes l JOIN Post p on l.post = p JOIN User u on l.user = :user ")
    List<Post> getUserLikesPosts(@Param("user") User user);

}

