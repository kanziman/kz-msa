package kr.kanzi.usersvc.infrastructure.service;

import kr.kanzi.usersvc.service.port.ClientService;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateServiceImpl implements ClientService {
    private final RestTemplate restTemplate;

    public RestTemplateServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <T> T open(String uid, HttpMethod method, Class<T> responseType) {
        String url = String.format("http://POST-SVC/api/posts/users/%s/likes", uid);
        ResponseEntity<T> exchange = restTemplate.exchange(
                url,
                method,
                null,
                responseType
        );
        T body = exchange.getBody();
        return body;
    }
}
