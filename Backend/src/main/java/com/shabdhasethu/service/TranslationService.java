package com.shabdhasethu.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shabdhasethu.dto.HistoryResponse;
import com.shabdhasethu.dto.TranslateRequest;
import com.shabdhasethu.entity.TranslationHistory;
import com.shabdhasethu.entity.User;
import com.shabdhasethu.repository.TranslationHistoryRepository;
import com.shabdhasethu.repository.UserRepository;

@Service
public class TranslationService {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private TranslationHistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    public String translate(TranslateRequest request, String email) {

        System.out.println("=========== TranslationService ===========");
        System.out.println("Input : " + request.getText());
        System.out.println("Target : " + request.getTargetLang());
        System.out.println("Email : " + email);

        String translatedText =
                geminiService.translateText(
                        request.getText(),
                        request.getTargetLang());

        System.out.println("Gemini Returned : " + translatedText);

        // ---------- SAVE HISTORY ----------
        User user = userRepository.findByEmail(email);

        if (user != null) {

            TranslationHistory history = new TranslationHistory();

            history.setUser(user);
            history.setSourceText(request.getText());
            history.setTranslatedText(translatedText);
            history.setSourceLang(request.getSourceLang());
            history.setTargetLang(request.getTargetLang());
            history.setTranslatedAt(LocalDateTime.now());

            historyRepository.save(history);
        }

        return translatedText;
    }

    public List<HistoryResponse> getHistory(String email) {

        User user = userRepository.findByEmail(email);

        List<TranslationHistory> historyList =
                historyRepository.findByUserOrderByTranslatedAtDesc(user);

        return historyList.stream()
                .map(h -> new HistoryResponse(
                        h.getId(),
                        h.getSourceText(),
                        h.getTranslatedText(),
                        h.getSourceLang(),
                        h.getTargetLang()
                ))
                .toList();
    }
    
    @Transactional
    public void clearHistory(String email) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return;
        }

        int deleted = historyRepository.deleteByUser(user);

        System.out.println("Deleted Records : " + deleted);
    }

}