package kr.kanzi.postsvc.infrastructure.stock;

import kr.kanzi.postsvc.domain.stock.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorpRepository extends JpaRepository<Ticker, Long> {
}
