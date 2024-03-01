package kr.kanzi.postsvc.infrastructure.search;


import kr.kanzi.postsvc.domain.Post;
import kr.kanzi.postsvc.domain.User;
import kr.kanzi.postsvc.presentation.dto.post.PostRequestDto;
import org.springframework.data.domain.PageImpl;

import java.util.List;


public interface SearchPostRepository {

    User searchPost(Post p);
    /**
     * v1 - simple query with offset and limit
     * @param offset
     * @param limit
     * @return
     */
    public List<Post> findPostsTagsUsersPage(int offset, int limit);
    /**
     * v3- 반환 타입 PageImpl (사용중)
     * @param postRequestDto
     * @return
     */
    public PageImpl searchQuery(PostRequestDto postRequestDto);
    public PageImpl searchQueryPage(PostRequestDto postRequestDto);

    /**
     * v2 - 반환 타입 List<Post>
     * @param postRequestDto
     * @return List<Post>
     */
    public List<Post> searchQueryPosts(PostRequestDto postRequestDto) ;


    public List<Post> search(PostRequestDto postRequestDto);

}
