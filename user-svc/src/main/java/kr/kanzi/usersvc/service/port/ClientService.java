package kr.kanzi.usersvc.service.port;

import org.springframework.http.HttpMethod;

public interface ClientService {

    <T> T open(String url, HttpMethod method, Class<T> responseType);

}
