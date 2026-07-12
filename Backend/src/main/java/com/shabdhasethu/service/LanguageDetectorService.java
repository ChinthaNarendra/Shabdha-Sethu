package com.shabdhasethu.service;

public class LanguageDetectorService {

    public static String detectLanguage(String text){

        if(text.matches(".*[అ-హ].*"))
            return "te";

        if(text.matches(".*[अ-ह].*"))
            return "hi";

        return "en";
    }

}