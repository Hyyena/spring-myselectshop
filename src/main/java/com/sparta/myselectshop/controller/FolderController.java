package com.sparta.myselectshop.controller;


import com.sparta.myselectshop.controller.dto.request.FolderRequest;
import com.sparta.myselectshop.controller.dto.response.FolderResponse;
import com.sparta.myselectshop.security.UserDetailsImpl;
import com.sparta.myselectshop.service.FolderService;
import com.sparta.myselectshop.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FolderController {

    private FolderService folderService;
    private ProductService productService;

    @PostMapping("/folders")
    public void addFolders(@RequestBody FolderRequest folderRequest,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<String> folderNames = folderRequest.getFolderNames();

        folderService.addFolders(folderNames, userDetails.getUser());
    }

    // 회원이 등록한 모든 폴더 조회
    @GetMapping("/folders")
    public List<FolderResponse> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return folderService.getFolders(userDetails.getUser());
    }

    // 상품에 폴더 추가
    @PostMapping("/products/{productId}/folder")
    public void addFolder(
            @PathVariable Long productId,
            @RequestParam Long folderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        productService.addFolder(productId, folderId, userDetails.getUser());
    }
}
