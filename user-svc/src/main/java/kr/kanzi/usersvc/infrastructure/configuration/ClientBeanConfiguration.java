package kr.kanzi.usersvc.infrastructure.configuration;

import feign.Logger;
import kr.kanzi.usersvc.infrastructure.service.ClientService;
import kr.kanzi.usersvc.infrastructure.service.FeignClientService;
import kr.kanzi.usersvc.infrastructure.service.FeignClientServiceImpl;
import kr.kanzi.usersvc.infrastructure.service.RestTemplateServiceImpl;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableFeignClients
public class ClientBeanConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

//    @Bean
    public ClientService restTemplateClientService(RestTemplate restTemplate) {
        return new RestTemplateServiceImpl(restTemplate);
    }


    @Bean
    public ClientService feignClient(FeignClientService feignClientService) {
        return new FeignClientServiceImpl(feignClientService);
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}