package com.csuft.lostfound.controller;

import com.csuft.lostfound.common.ApiResponse;
import com.csuft.lostfound.common.PageResult;
import com.csuft.lostfound.dto.PublishItemRequest;
import com.csuft.lostfound.dto.UpdateItemRequest;
import com.csuft.lostfound.model.ItemView;
import com.csuft.lostfound.service.ItemService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/found")
public class FoundController {
    private final ItemService itemService;

    public FoundController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/list")
    public ApiResponse<PageResult<ItemView>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String reviewStatus,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ApiResponse.success(itemService.listItems("found", keyword, category, location, tag, status, reviewStatus, pageNum, pageSize));
    }

    @PostMapping("/publish")
    public ApiResponse<Long> publish(@Valid @RequestBody PublishItemRequest request) {
        return ApiResponse.success(itemService.publish("found", request));
    }

    @PutMapping("/update/{id}")
    public ApiResponse<Boolean> update(@PathVariable Long id, @RequestBody UpdateItemRequest request) {
        return ApiResponse.success(itemService.update("found", id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Boolean> delete(@PathVariable Long id) {
        return ApiResponse.success(itemService.delete("found", id));
    }
}
