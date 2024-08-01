package com.sparta.myselectshop.controller;

import com.sparta.myselectshop.controller.dto.request.ProductRequest;
import com.sparta.myselectshop.controller.dto.request.UpdateWishPriceRequest;
import com.sparta.myselectshop.controller.dto.response.ProductResponse;
import com.sparta.myselectshop.security.UserDetailsImpl;
import com.sparta.myselectshop.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public List<ProductResponse> getProducts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.getProducts(userDetails.getUser());
    }

    @PostMapping("/products")
    public ProductResponse createProduct(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest, userDetails.getUser());
    }

    @PutMapping("/products/{productId}")
    public ProductResponse updateProduct(@PathVariable Long productId,
                                         @RequestBody UpdateWishPriceRequest updateWishPriceRequest) {
        return productService.updateProduct(productId, updateWishPriceRequest);
    }

    @GetMapping("/admin/products")
    public List<ProductResponse> getProducts() {
        return productService.getAllProducts();
    }
}
