package kr.kanzi.postsvc.infrastructure.stock;

import kr.kanzi.postsvc.domain.stock.KorMarket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<KorMarket, Long> {

}
