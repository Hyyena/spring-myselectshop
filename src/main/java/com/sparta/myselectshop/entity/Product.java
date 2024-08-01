package com.sparta.myselectshop.entity;

import com.sparta.myselectshop.controller.dto.request.ProductRequest;
import com.sparta.myselectshop.controller.dto.request.UpdateWishPriceRequest;
import com.sparta.myselectshop.naver.controller.dto.response.ItemResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Product(ProductRequest productRequest, User user) {
        this.title = productRequest.getTitle();
        this.thumbnailUrl = productRequest.getThumbnailUrl();
        this.purchaseUrl = productRequest.getPurchaseUrl();
        this.lowestPrice = productRequest.getLowestPrice();
        this.user = user;
    }

    public void update(UpdateWishPriceRequest updateWishPriceRequest) {
        this.wishPrice = updateWishPriceRequest.getWishPrice();
    }

    public void updateByItemResponse(ItemResponse itemResponse) {
        this.lowestPrice = itemResponse.getLowestPrice();
    }
}