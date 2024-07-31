package com.sparta.myselectshop.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private String title;
    private String thumbnailUrl;
    private String purchaseUrl;
    private int lowestPrice;
}