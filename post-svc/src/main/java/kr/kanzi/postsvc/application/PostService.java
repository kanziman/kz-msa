package kr.kanzi.postsvc.application;

import jakarta.persistence.EntityNotFoundException;
import kr.kanzi.postsvc.domain.BookMark;
import kr.kanzi.postsvc.domain.Likes;
import kr.kanzi.postsvc.domain.Post;
import kr.kanzi.postsvc.domain.User;
import kr.kanzi.postsvc.infrastructure.*;
import kr.kanzi.postsvc.presentation.dto.TagResponse;
import kr.kanzi.postsvc.presentation.dto.post.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Log4j2
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final BookMarkRepository bookMarkRepository;

    // v1
//    public List<PostResponse> getAllPostByUser(User user) {
//        List<Post> posts = postRepository.findAllByUser(user);
//        return posts.stream()
//                .map(post->PostResponse.of(post))
//                .collect(Collectors.toList());
//    }
//
//    // v2
//    public List<PostResponse> getPostsTagsPage(PostRequestDto postRequestDto) {
//        List<Post> posts = postRepository.searchQueryPosts(postRequestDto);
//        return posts.stream()
//                .map(post -> PostResponse.of(post))
//                .collect(Collectors.toList());
//    }

    /**
     * GET POSTS v3 사용중
     * @param postRequestDto
     * @return
     */
    public PageResultDTO getPostsPage(PostRequestDto postRequestDto) {
        PageImpl result = postRepository.searchQueryPage(postRequestDto);
        Function<Post, PostResponse> fn = (en -> PostResponse.of(en));
        return  new PageResultDTO(result, fn);
    }


    /**
     * CREATE POST
     */

    public PostResponse addPost(AddPostRequest request) {
//        String uid = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUid(request.getUid())
                .orElseThrow(() -> new EntityNotFoundException("not found user : " + request.getUid()));
        // dto -> post
        Post post = request.toEntity();
        // post + user = create
        post.addUser(user);
        // save
        Post save = postRepository.save(post);

        return PostResponse.of(save);
    }

    /**
     * GET POST ONE
     * @param id
     */
    @Transactional(readOnly = false)
    public PostResponse findById(long id) {
        Post post = getPost(id);
        post.upReadCount();

        return PostResponse.of(post);
    }

    /**
     * DELETE POST
     * @param id
     */

    @Transactional(readOnly = false)
    public void delete(long id, String writer) {

        Post post = getPost(id);
        post.checkWriter(writer);
        postRepository.deleteById(id);
    }

    /**
     * UPDATE POST
     *
     * @param id
     * @param request
     * @return
     */
    @Transactional(readOnly = false)
    public PostResponse update(long id, UpdatePostRequest request) {

        Post post = getPost(id);
        post.checkWriter(request.getWriter());

        // 기존 태그 삭제
        tagRepository.deleteAllInBatch(post.getTags());
        // update
        Post updatePost = request.toEntity();
        post.change(updatePost);

        return PostResponse.of(post);
    }



    /**
     * LIKES
     */
    @Transactional(readOnly = false)
    public void addLike(long postId, String uid) {
        User user = getUser(uid);
        Post post = getPost(postId);

        Likes hasLike = likeRepository.getLike(post, user);

        if (hasLike == null) {
            Likes like = new Likes(post, user);
            likeRepository.save(like);
            post.increaseLikeCount();
        } else {
            likeRepository.delete(hasLike);
            post.decreaseLikeCount();
        }

    }
    public boolean hasLike(long postId, String uid) {
        User user = getUser(uid);
        Post post = getPost(postId);

        Likes like = likeRepository.getLike(post, user);
        return like != null;
    }

    /**
     * BOOKMARK
     */
    @Transactional(readOnly = false)
    public void addBookMark(long postId, String uid) {

        User user = getUser(uid);
        Post post = getPost(postId);

        BookMark hasBookMark = bookMarkRepository.getBookMark(post, user);

        if (hasBookMark == null) {
            BookMark bookMark = new BookMark(post, user);
            bookMarkRepository.save(bookMark);
            post.increaseBookmarkCount();
        } else {
            bookMarkRepository.delete(hasBookMark);
            post.decreaseBookmarkCount();
        }
    }

    /**
     * TAG
     */
    public List<TagResponse> getTopTags(){
        List<TagResponse> topTags = tagRepository.getTopTags();
        return topTags;
    }



    private Post getPost(long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("not found post : " + postId));
    }
    private User getUser(String uid) {
        return userRepository.findByUid(uid)
                .orElseThrow(() -> new EntityNotFoundException("not found user : " + uid));
    }

    public boolean hasBookMark(long postId, String uid) {

        User user = getUser(uid);
        Post post = getPost(postId);

        BookMark bookMark = bookMarkRepository.getBookMark(post, user);
        return bookMark != null;
    }

    public List<PostResponse> getUserBookMarks(String uid) {
        List<PostResponse> responses = new ArrayList<>();
        try {
            User user = userRepository.findByUid(uid)
                    .orElseThrow(() -> new EntityNotFoundException("not found user : " + uid));
            //user bookmark (북마크 조회기 떄문에 북마크는 전부 true )
            List<Post> bookMarksPosts = bookMarkRepository.getUserBookMarksPosts(user);
            responses = bookMarksPosts.stream()
                    .map(b -> PostResponse.of(b))
                    .collect(Collectors.toList());
        } catch (EntityNotFoundException e){
            log.info(e.getMessage());
        }

        return responses;
    }
    public List<PostResponse> getUserLikes(String uid) {
        List<PostResponse> responses = new ArrayList<>();
        try {
            User user = userRepository.findByUid(uid)
                    .orElseThrow(() -> new EntityNotFoundException("not found user : " + uid));

            //user like check
            List<Post> userLikesPosts = likeRepository.getUserLikesPosts(user);
            responses = userLikesPosts.stream()
                    .map(p -> PostResponse.of(p))
                    .collect(Collectors.toList());
        } catch (EntityNotFoundException e){
            log.info(e.getMessage());
        }
        return responses;
    }

}
