package com.sparta.myselectshop.naver.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemResponse {

    private String title;
    private String thumbnailUrl;
    private String purchaseUrl;
    private int lowestPrice;
}