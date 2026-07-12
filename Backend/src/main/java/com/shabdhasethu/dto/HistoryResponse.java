package com.shabdhasethu.dto;

public class HistoryResponse {

    private Long id;
    private String sourceText;
    private String translatedText;
    private String sourceLang;
    private String targetLang;

    public HistoryResponse(Long id, String sourceText, String translatedText, String sourceLang, String targetLang) {
        this.id = id;
        this.sourceText = sourceText;
        this.translatedText = translatedText;
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
    }

    public Long getId() {
        return id;
    }

    public String getSourceText() {
        return sourceText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public String getSourceLang() {
        return sourceLang;
    }

    public String getTargetLang() {
        return targetLang;
    }
}