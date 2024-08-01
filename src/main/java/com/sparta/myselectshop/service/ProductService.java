package com.sparta.myselectshop.service;

import com.sparta.myselectshop.controller.dto.request.ProductRequest;
import com.sparta.myselectshop.controller.dto.request.UpdateWishPriceRequest;
import com.sparta.myselectshop.controller.dto.response.ProductResponse;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.naver.controller.dto.response.ItemResponse;
import com.sparta.myselectshop.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    public static final int MIN_WISH_PRICE = 100;

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest, User user) {
        Product product = productRepository.save(new Product(productRequest, user));
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

    public List<ProductResponse> getProducts(User user) {
        List<Product> products = productRepository.findAllByUser(user);
        List<ProductResponse> productResponses = new ArrayList<>();

        for (Product product : products) {
            productResponses.add(new ProductResponse(product));
        }

        return productResponses;
    }

    @Transactional
    public void updateBySearch(Long id, ItemResponse itemResponse) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 상품이 존재하지 않습니다")
        );
        product.updateByItemResponse(itemResponse);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductResponse> responseDtoList = new ArrayList<>();

        for (Product product : productList) {
            responseDtoList.add(new ProductResponse(product));
        }
        return responseDtoList;
    }
}
