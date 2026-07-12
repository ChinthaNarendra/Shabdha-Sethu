package com.shabdhasethu.controller;

import com.shabdhasethu.service.SpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SpeechController {

    @Autowired
    private SpeechService speechService;

    @PostMapping("/speech-to-text")
    public Map<String,String> speechToText(
            @RequestParam("file") MultipartFile file){

        String text = speechService.convertSpeech(file);

        Map<String,String> response = new HashMap<>();

        response.put("text",text);

        return response;
    }
}