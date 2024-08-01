package com.sparta.myselectshop.controller.dto.response;

import com.sparta.myselectshop.entity.Folder;
import lombok.Getter;

@Getter
public class FolderResponse {

    private Long folderId;
    private String name;

    public FolderResponse(Folder folder) {
        this.folderId = folder.getFolderId();
        this.name = folder.getName();
    }
}
