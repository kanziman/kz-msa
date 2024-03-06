package kr.kanzi.usersvc.infrastructure.service;

import kr.kanzi.usersvc.presentation.response.PostResponseWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "post-svc")
public interface FeignClientService {
    @GetMapping("/api/posts/users/{uid}/likes")
    PostResponseWrapper getPosts(@PathVariable("uid") String uid);
}