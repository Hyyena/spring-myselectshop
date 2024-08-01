package com.sparta.myselectshop.naver.controller;


import com.sparta.myselectshop.naver.controller.dto.response.ItemsResponse;
import com.sparta.myselectshop.naver.service.NaverOpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NaverOpenApiController {

    private final NaverOpenApiService naverOpenApiService;

    @GetMapping("/search")
    public ItemsResponse searchItems(@RequestParam String query) {
        return naverOpenApiService.searchItems(query);
    }
}
