package com.sparta.myselectshop.controller;


import com.sparta.myselectshop.controller.dto.request.FolderRequest;
import com.sparta.myselectshop.security.UserDetailsImpl;
import com.sparta.myselectshop.service.FolderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FolderController {

    private FolderService folderService;

    @PostMapping("/folders")
    public void addFolders(@RequestBody FolderRequest folderRequest,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<String> folderNames = folderRequest.getFolderNames();

        folderService.addFolders(folderNames, userDetails.getUser());
    }
}
