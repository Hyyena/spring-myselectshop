package com.sparta.myselectshop.naver.controller.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Getter
@NoArgsConstructor
public class itemResponse {
    private String title;
    private String thumbnailUrl;
    private String purchaseUrl;
    private int lowestPrice;

    public itemResponse(JSONObject itemJson) {
        this.title = itemJson.getString("title");
        this.thumbnailUrl = itemJson.getString("image");
        this.purchaseUrl = itemJson.getString("link");
        this.lowestPrice = itemJson.getInt("lprice");
    }
}