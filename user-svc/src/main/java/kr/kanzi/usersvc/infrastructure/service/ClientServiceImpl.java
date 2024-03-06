package kr.kanzi.usersvc.infrastructure.service;

import kr.kanzi.usersvc.service.port.ClientService;
import org.springframework.http.HttpMethod;

public class ClientServiceImpl implements ClientService {

    private final ClientService clientService;

    public ClientServiceImpl(ClientService clientService) {
        this.clientService =  clientService;
    }

    @Override
    public <T> T open(String url, HttpMethod method, Class<T> responseType) {
        return clientService.open(url, method, responseType);
    }
}
