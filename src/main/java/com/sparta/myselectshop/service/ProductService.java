package com.sparta.myselectshop.service;

import com.sparta.myselectshop.controller.dto.request.ProductRequest;
import com.sparta.myselectshop.controller.dto.response.ProductResponse;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productRepository.save(new Product(productRequest));
        return new ProductResponse(product);
    }
}
