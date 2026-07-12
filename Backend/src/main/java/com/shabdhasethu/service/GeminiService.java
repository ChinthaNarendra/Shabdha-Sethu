package com.shabdhasethu.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeminiService {

	@Value("${gemini.api.key}")
	private String apiKey;

	private final HttpClient client = HttpClient.newHttpClient();

	private final ObjectMapper mapper = new ObjectMapper();

	private static final String MODEL = "gemini-3.5-flash";
//	private static final String MODEL = "gemini-2.5-flash";

//	private static final String MODEL = "gemini-2.0-flash";

	public String translateText(String inputText, String targetLanguage) {

		try {

			String prompt = buildPrompt(inputText, targetLanguage);

			String requestBody = """
					{
					  "contents":[
					    {
					      "parts":[
					        {
					          "text":"%s"
					        }
					      ]
					    }
					  ],
					"generationConfig":{
					 "temperature":0.1
					  }
					}
					""".formatted(prompt.replace("\"", "\\\""));

			String endpoint = "https://generativelanguage.googleapis.com/v1/models/" + MODEL + ":generateContent?key="
					+ apiKey;
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endpoint))
					.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(requestBody))
					.build();
			System.out.println("API Key = " + apiKey);

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			System.out.println("Endpoint : " + endpoint);
			System.out.println("Status : " + response.statusCode());
			System.out.println(response.body());
			if (response.statusCode() != 200) {

				System.out.println("Gemini Error : " + response.body());

				return inputText;
			}

			JsonNode root = mapper.readTree(response.body());

			JsonNode candidates = root.path("candidates");

			if (candidates.isMissingNode() || candidates.size() == 0) {

				return inputText;
			}

			JsonNode textNode = candidates.get(0).path("content").path("parts").get(0).path("text");

			if (textNode.isMissingNode()) {

				return inputText;
			}

			return textNode.asText().trim();

		}

		catch (IOException e) {

			e.printStackTrace();
			return inputText;

		}

		catch (InterruptedException e) {

			Thread.currentThread().interrupt();

			e.printStackTrace();

			return inputText;

		}

	}

	private String buildPrompt(String inputText, String targetLanguage) {

		String language = targetLanguage.equals("en") ? "English"
				: targetLanguage.equals("te") ? "Telugu" : targetLanguage.equals("hi") ? "Hindi" : targetLanguage;

		return """
					You are an expert translator.
				
					The input may be Roman Telugu (written using English letters).
				
					Example:
					em chestunnav -> What are you doing?
					bagunnava -> How are you?
					ekkada unnava -> Where are you?
					Thinava -> Did you eat?
				
					Translate to %s.
				
					Return only the translated sentence.
//				You are an expert translator.
//
//				If the input is Roman Telugu, first understand its meaning.
//
//				Never transliterate.
//
//				Always translate the meaning naturally.
//
//				Return only the translated sentence.

					Input:
					%s
					""".formatted(language, inputText);
	}
}