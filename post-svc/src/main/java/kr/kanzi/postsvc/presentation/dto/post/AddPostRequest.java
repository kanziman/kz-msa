package kr.kanzi.postsvc.presentation.dto.post;

import jakarta.validation.constraints.NotBlank;
import kr.kanzi.postsvc.domain.Post;
import kr.kanzi.postsvc.domain.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor
@Getter
@ToString
public class AddPostRequest {

    @NotBlank(message = "ID는 필수입니다.")
    private String uid;
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @NotBlank(message = "카테고리는 필수입니다.")
    private String category;

    private String[] tags;


    @Builder
    public AddPostRequest(String uid, String title, String content, String category, String[] tags) {

        this.uid = uid;
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
    }

    public Post toEntity() {
        Set<Tag> tagSet = new HashSet<>();
        Optional.ofNullable(tags)
                .ifPresent(tags -> Arrays.stream(tags)
                            .map(Tag::new)
                            .forEach(tagSet::add));

        Post post = Post.builder()
                .title(title)
                .content(content)
                .category(category)
                .tags(tagSet)
                .build();
        post.getTags().forEach(t -> post.addTag(t));

        return post;
    }


}
