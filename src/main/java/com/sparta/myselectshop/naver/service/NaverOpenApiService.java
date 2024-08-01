package com.sparta.myselectshop.naver.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.myselectshop.naver.controller.dto.response.ItemResponse;
import com.sparta.myselectshop.naver.controller.dto.response.ItemsResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j(topic = "NAVER API")
@Service
public class NaverOpenApiService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public NaverOpenApiService(@Value("${naver.client.id}") String clientId,
                               @Value("${naver.client.secret}") String clientSecret,
                               ObjectMapper objectMapper) {
        this.restClient = RestClient.builder()
                .baseUrl("https://openapi.naver.com")
                .defaultHeader("X-Naver-Client-Id", clientId)
                .defaultHeader("X-Naver-Client-Secret", clientSecret)
                .build();
        this.objectMapper = objectMapper;
    }

    public ItemsResponse searchItems(String query) {
        ResponseEntity<String> response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/search/shop.json")
                        .queryParam("query", query)
                        .queryParam("display", 15)
                        .build())
                .retrieve()
                .toEntity(String.class);

        return convertToItemsResponse(response.getBody());
    }

    private ItemsResponse convertToItemsResponse(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode items = root.get("items");

            List<ItemResponse> itemResponses = new ArrayList<>();
            for (JsonNode item : items) {
                ItemResponse itemResponse = ItemResponse.builder()
                        .title(item.get("title").asText())
                        .thumbnailUrl(item.get("image").asText())
                        .purchaseUrl(item.get("link").asText())
                        .lowestPrice(item.get("lprice").asInt())
                        .build();
                itemResponses.add(itemResponse);
            }

            return ItemsResponse.builder()
                    .items(itemResponses)
                    .build();
        } catch (Exception e) {
            log.error("Error parsing Naver API response", e);
            throw new RuntimeException("Error parsing Naver API response", e);
        }
    }
}
