package kr.kanzi.postsvc.application.stock;

import kr.kanzi.postsvc.domain.stock.KorMarket;
import kr.kanzi.postsvc.infrastructure.stock.MarketRepository;
import kr.kanzi.postsvc.presentation.dto.stock.MarketResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MarketService {
    private final MarketRepository jpaRepository;

    public List<MarketResponse> findAll() {
        List<KorMarket> all = jpaRepository.findAll();

        return all.stream()
                .map(m -> MarketResponse.of(m))
                .collect(Collectors.toList());
    }

}
