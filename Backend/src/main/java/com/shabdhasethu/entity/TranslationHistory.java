package com.shabdhasethu.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "translation_history")
public class TranslationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String sourceText;

    @Column(columnDefinition = "TEXT")
    private String translatedText;

    private String sourceLang;

    private String targetLang;

    private LocalDateTime translatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public TranslationHistory() {
    }

    public Long getId() {
        return id;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public String getSourceLang() {
        return sourceLang;
    }

    public void setSourceLang(String sourceLang) {
        this.sourceLang = sourceLang;
    }

    public String getTargetLang() {
        return targetLang;
    }

    public void setTargetLang(String targetLang) {
        this.targetLang = targetLang;
    }

    public LocalDateTime getTranslatedAt() {
        return translatedAt;
    }

    public void setTranslatedAt(LocalDateTime translatedAt) {
        this.translatedAt = translatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}