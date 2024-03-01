package kr.kanzi.postsvc.presentation.dto.stock;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TickerListResponse {
    private String stockName;
    private String mktType;
    private String stockCode;
    @Builder
    private TickerListResponse(String stockName, String mktType, String stockCode) {
        this.stockName = stockName;
        this.mktType = mktType;
        this.stockCode = stockCode;
    }


}
