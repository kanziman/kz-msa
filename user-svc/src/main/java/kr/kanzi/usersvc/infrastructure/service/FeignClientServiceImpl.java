package kr.kanzi.usersvc.infrastructure.service;

import kr.kanzi.usersvc.presentation.response.PostResponseWrapper;
import kr.kanzi.usersvc.service.port.ClientService;
import org.springframework.http.HttpMethod;

public class FeignClientServiceImpl implements ClientService {
    private final FeignClientService feignClientService;

    public FeignClientServiceImpl(FeignClientService feignClientService) {
        this.feignClientService = feignClientService;
    }


    @Override
    public <T> T open(String uid, HttpMethod method, Class<T> responseType) {

        if (responseType.isAssignableFrom(PostResponseWrapper.class)) {
            return responseType.cast(feignClientService.getPosts(uid));
        }
        throw new IllegalArgumentException("Unsupported response type");

    }

}
