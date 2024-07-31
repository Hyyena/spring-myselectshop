package com.sparta.myselectshop.controller.dto.response;

import com.sparta.myselectshop.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponse {

    private Long productId;
    private String title;
    private String thumbnailUrl;
    private String purchaseUrl;
    private int lowestPrice;
    private int wishPrice;

    public ProductResponse(Product product) {
        this.productId = product.getProductId();
        this.title = product.getTitle();
        this.thumbnailUrl = product.getThumbnailUrl();
        this.purchaseUrl = product.getPurchaseUrl();
        this.lowestPrice = product.getLowestPrice();
        this.wishPrice = product.getWishPrice();
    }
}