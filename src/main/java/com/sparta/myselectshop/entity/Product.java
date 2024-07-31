package com.sparta.myselectshop.entity;

import com.sparta.myselectshop.controller.dto.request.ProductRequest;
import com.sparta.myselectshop.controller.dto.request.UpdateWishPriceRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "product")
@Entity
public class Product extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String thumbnailUrl;

    @Column(nullable = false)
    private String purchaseUrl;

    @Column(nullable = false)
    private int lowestPrice;

    @Column(nullable = false)
    private int wishPrice;

    public Product(ProductRequest productRequest) {
        this.title = productRequest.getTitle();
        this.thumbnailUrl = productRequest.getThumbnailUrl();
        this.purchaseUrl = productRequest.getPurchaseUrl();
        this.lowestPrice = productRequest.getLowestPrice();
    }

    public void update(UpdateWishPriceRequest updateWishPriceRequest) {
        this.wishPrice = updateWishPriceRequest.getWishPrice();
    }
}