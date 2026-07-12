package com.shabdhasethu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shabdhasethu.dto.HistoryResponse;
import com.shabdhasethu.dto.TranslateRequest;
import com.shabdhasethu.service.TranslationService;

@RestController
@RequestMapping("/translate")
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @PostMapping
    public String translate(@RequestBody TranslateRequest request,
                            Authentication authentication) {

        String email = authentication.getName();

        return translationService.translate(request, email);
    }

    @GetMapping("/history")
    public List<HistoryResponse> getHistory(Authentication authentication) {

        String email = authentication.getName();

        return translationService.getHistory(email);
    }

    @DeleteMapping("/history")
    public String clearHistory(Authentication authentication) {

        String email = authentication.getName();

        translationService.clearHistory(email);

        return "History Cleared Successfully";
    }
}