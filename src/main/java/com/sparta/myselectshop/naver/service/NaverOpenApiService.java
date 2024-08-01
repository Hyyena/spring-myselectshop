package com.sparta.myselectshop.naver.service;


import com.sparta.myselectshop.naver.controller.dto.response.ItemsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j(topic = "NAVER API")
@Service
public class NaverOpenApiService {

    private final RestClient restClient;

    public NaverOpenApiService(@Value("${naver.client.id}") String clientId,
                               @Value("${naver.client.secret}") String clientSecret) {
        this.restClient = RestClient.builder()
                .baseUrl("https://openapi.naver.com")
                .defaultHeader("X-Naver-Client-Id", clientId)
                .defaultHeader("X-Naver-Client-Secret", clientSecret)
                .build();
    }

    public ItemsResponse searchItems(String query) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/search/shop.json")
                        .queryParam("query", query)
                        .queryParam("display", 15)
                        .build())
                .retrieve()
                .body(ItemsResponse.class);
    }
}
