package com.sparta.myselectshop.service;

import com.sparta.myselectshop.controller.dto.request.ProductRequest;
import com.sparta.myselectshop.controller.dto.request.UpdateWishPriceRequest;
import com.sparta.myselectshop.controller.dto.response.ProductResponse;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.entity.UserRoleEnum;
import com.sparta.myselectshop.naver.controller.dto.response.ItemResponse;
import com.sparta.myselectshop.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<ProductResponse> getProducts(User user,
                                             int page, int size, String sortBy, boolean isAsc) {
        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // 사용자 권한 가져와서 ADMIN 이면 전체 조회, USER 면 본인이 추가한 부분 조회
        UserRoleEnum userRoleEnum = user.getRole();

        Page<Product> productList;

        if (userRoleEnum == UserRoleEnum.USER) {
            // 사용자 권한이 USER 일 경우
            productList = productRepository.findAllByUser(user, pageable);
        } else {
            productList = productRepository.findAll(pageable);
        }

        return productList.map(ProductResponse::new);
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
