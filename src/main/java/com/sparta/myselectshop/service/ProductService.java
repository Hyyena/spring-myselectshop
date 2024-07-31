package com.sparta.myselectshop.service;

import com.sparta.myselectshop.controller.dto.request.ProductRequest;
import com.sparta.myselectshop.controller.dto.request.UpdateWishPriceRequest;
import com.sparta.myselectshop.controller.dto.response.ProductResponse;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    public static final int MIN_WISH_PRICE = 100;

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productRepository.save(new Product(productRequest));
        return new ProductResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, UpdateWishPriceRequest updateWishPriceRequest) {
        int wishPrice = updateWishPriceRequest.getWishPrice();
        if (wishPrice < MIN_WISH_PRICE) {
            throw new IllegalArgumentException("유효하지 않은 관심 가격입니다. 최소 " + MIN_WISH_PRICE + "원 이상으로 설정해주세요.");
        }

        Product product = productRepository.findById(productId).orElseThrow(() ->
                new NullPointerException("해당 상품을 찾을 수 없습니다."));

        product.update(updateWishPriceRequest);

        return new ProductResponse(product);
    }
}
