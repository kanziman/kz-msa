package kr.kanzi.usersvc.infrastructure.configuration;

import kr.kanzi.usersvc.service.port.ClientService;
import kr.kanzi.usersvc.infrastructure.service.FeignClientService;
import kr.kanzi.usersvc.presentation.response.PostResponse;
import kr.kanzi.usersvc.presentation.response.PostResponseWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;

import java.util.Collections;

import static org.mockito.Mockito.when;

@SpringBootTest
class FeignClientServiceTest {

    @Autowired
    ClientService clientService;
    @MockBean
    FeignClientService feignClientService;
    @Test
    void get() {
        // 설정할 모의 응답 객체 생성
        PostResponseWrapper mockResponse = new PostResponseWrapper(Collections.singletonList(new PostResponse()));

        // Feign 클라이언트 호출이 모의 응답을 반환하도록 설정
        when(feignClientService.getPosts("72e64e3a-b72b-42dd-a75b-b22196e9ae61"))
                .thenReturn(mockResponse);

        // 테스트 대상 메소드 호출
        PostResponseWrapper response = clientService.open("72e64e3a-b72b-42dd-a75b-b22196e9ae61",
                HttpMethod.GET, PostResponseWrapper.class);

        // 검증: 실제 응답이 예상하는 모의 응답과 같은지 확인
        Assertions.assertNotNull(response);

    }


}