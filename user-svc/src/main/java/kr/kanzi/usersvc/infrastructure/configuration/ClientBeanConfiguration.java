package kr.kanzi.usersvc.infrastructure.configuration;

import feign.Logger;
import kr.kanzi.usersvc.service.port.ClientService;
import kr.kanzi.usersvc.infrastructure.service.FeignClientService;
import kr.kanzi.usersvc.infrastructure.service.FeignClientServiceImpl;
import kr.kanzi.usersvc.infrastructure.service.RestTemplateServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableFeignClients(basePackages = "kr.kanzi.usersvc.infrastructure")
public class ClientBeanConfiguration {

    @Bean("restTemplate")
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }


    @Bean
    @Primary
    public ClientService restTemplateClientService(@Qualifier("restTemplate") RestTemplate restTemplate) {
        return new RestTemplateServiceImpl(restTemplate);
    }
    @Bean("feign")
    public ClientService feignClient(FeignClientService feignClientService) {
        return new FeignClientServiceImpl(feignClientService);
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}