package kr.kanzi.postsvc.presentation;

import kr.kanzi.postsvc.application.stock.CorpService;
import kr.kanzi.postsvc.application.stock.MarketService;
import kr.kanzi.postsvc.presentation.dto.stock.MarketResponse;
import kr.kanzi.postsvc.presentation.dto.stock.TickerListResponse;
import kr.kanzi.postsvc.presentation.dto.stock.TickerRequest;
import kr.kanzi.postsvc.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Log4j2
public class StockController {

    private final CorpService corpService;
    private final MarketService marketService;


    @PostMapping("/api/stock/proxy")
    public ResponseEntity proxyList(@RequestBody Map map) {
        List<String> lines = Arrays.asList(map.get("proxy").toString().split("\n"));

        Map rtn = corpService.insertProxy(lines);
        return ResponseEntity.status(HttpStatus.OK).body(rtn);
    }

    @PostMapping("/api/stock/ticker")
    public ApiResponse<Map> getTicks(@RequestBody TickerRequest option) {
        Map map = corpService.findByCodeQ(option);

        return ApiResponse.of(HttpStatus.OK, map);
    }
    @GetMapping("/api/stock/market")
    public ApiResponse<List<MarketResponse>> getMarket() {
        List<MarketResponse> marketResponses = marketService.findAll();
        return ApiResponse.of(HttpStatus.OK, marketResponses);
    }

    @GetMapping("/api/stock/tickers")
    public ApiResponse<List<TickerListResponse>> getTickers() {
        List<TickerListResponse> allTicker = corpService.findAllTicker();

        return ApiResponse.of(HttpStatus.OK, allTicker);
    }
}