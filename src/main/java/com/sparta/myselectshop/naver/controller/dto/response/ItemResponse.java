package com.sparta.myselectshop.naver.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemResponse {

    private String title;

    @JsonProperty("image")
    private String thumbnailUrl;

    @JsonProperty("link")
    private String purchaseUrl;

    @JsonProperty("lprice")
    private int lowestPrice;
}