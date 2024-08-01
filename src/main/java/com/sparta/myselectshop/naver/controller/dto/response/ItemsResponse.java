package com.sparta.myselectshop.naver.controller.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemsResponse {

    private List<ItemResponse> items;
}
