package com.sparta.myselectshop.scheduler;

import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.naver.controller.dto.response.ItemResponse;
import com.sparta.myselectshop.naver.controller.dto.response.ItemsResponse;
import com.sparta.myselectshop.naver.service.NaverOpenApiService;
import com.sparta.myselectshop.repository.ProductRepository;
import com.sparta.myselectshop.service.ProductService;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Scheduler")
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final NaverOpenApiService naverOpenApiService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "* * 1 * * *") // 매일 새벽 1시
    public void updatePrice() throws InterruptedException {
        log.info("가격 업데이트 실행");
        List<Product> productList = productRepository.findAll();
        for (Product product : productList) {
            // 1초에 한 상품 씩 조회합니다 (NAVER 제한)
            TimeUnit.SECONDS.sleep(1);

            // i 번째 관심 상품의 제목으로 검색을 실행합니다.
            String title = product.getTitle();
            ItemsResponse itemsResponse = naverOpenApiService.searchItems(title);
            Optional<ItemsResponse> items = Optional.ofNullable(itemsResponse);

            if (items.isPresent()) {
                ItemResponse itemResponse = items.get().getItems().get(0);
                // i 번째 관심 상품 정보를 업데이트합니다.
                Long id = product.getProductId();
                try {
                    productService.updateBySearch(id, itemResponse);
                } catch (Exception e) {
                    log.error("{}: {}", id, e.getMessage());
                }
            }
        }
    }

}