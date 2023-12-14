package com.project.pescueshop.controller;

import com.project.pescueshop.model.dto.GlobalSearchResultDTO;
import com.project.pescueshop.model.dto.general.ResponseDTO;
import com.project.pescueshop.service.SearchingService;
import com.project.pescueshop.util.constant.EnumResponseCode;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
@CrossOrigin
@Api
public class SearchController {
    private final SearchingService searchingService;

    @GetMapping("/global")
    public ResponseEntity<ResponseDTO<Map<String, List<GlobalSearchResultDTO>>>> globalSearch(@RequestParam String keyword) {
        Map<String, List<GlobalSearchResultDTO>> searchResult = searchingService.globalSearch(keyword);

        ResponseDTO<Map<String, List<GlobalSearchResultDTO>>> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, searchResult, "searchResult");

        return ResponseEntity.ok(result);
    }
}
