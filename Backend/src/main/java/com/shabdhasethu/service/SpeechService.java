package com.shabdhasethu.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.vosk.Model;
import org.vosk.Recognizer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SpeechService {

    private Model englishModel;

    private synchronized Model getModel() throws Exception {

        if (englishModel == null) {

            System.out.println("Loading Vosk Model...");

            englishModel =
                    new Model("model/English/vosk-model-en-us-0.22-lgraph");

            System.out.println("Vosk Model Loaded Successfully");
        }

        return englishModel;
    }

    public String convertSpeech(MultipartFile file) {

        try {

            File inputFile = File.createTempFile("input", ".tmp");

            file.transferTo(inputFile);

            File convertedFile = File.createTempFile("converted", ".wav");

            ProcessBuilder pb = new ProcessBuilder(
                    "ffmpeg",
                    "-loglevel", "quiet",
                    "-y",
                    "-i", inputFile.getAbsolutePath(),
                    "-ac", "1",
                    "-ar", "16000",
                    "-acodec", "pcm_s16le",
                    convertedFile.getAbsolutePath());

            Process process = pb.start();

            process.waitFor();

            InputStream ais = new FileInputStream(convertedFile);

            ais.skip(44);

            Recognizer recognizer =
                    new Recognizer(getModel(), 16000);

            byte[] buffer = new byte[4096];

            int bytesRead;

            while ((bytesRead = ais.read(buffer)) != -1) {

                recognizer.acceptWaveForm(buffer, bytesRead);

            }

            String result = recognizer.getFinalResult();

            recognizer.close();

            ais.close();

            ObjectMapper mapper = new ObjectMapper();

            JsonNode node = mapper.readTree(result);

            inputFile.delete();

            convertedFile.delete();

            return node.get("text").asText();

        }
        catch (Exception e) {

            e.printStackTrace();

            return "Speech conversion failed";

        }

    }

}