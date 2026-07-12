package com.shabdhasethu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.shabdhasethu.dto.HistoryResponse;
import com.shabdhasethu.service.TranslationService;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private TranslationService translationService;

    @GetMapping
    public List<HistoryResponse> getHistory(Authentication authentication) {

        String email = authentication.getName();

        return translationService.getHistory(email);
    }
}