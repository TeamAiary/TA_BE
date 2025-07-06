package com.aiary.be.report.application;

import com.aiary.be.diary.application.dto.DiaryInfo;
import com.aiary.be.global.annotation.Client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Client
public class ReportClient {
    private final WebClient webClient;
    
    @Value("${openai.api_key}")
    private String apiKey;
    
    public ReportClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
    }
    
    public String analyze(List<DiaryInfo> diaryInfos) {
        String prompt = buildPrompt(diaryInfos);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", List.of(Map.of("role", "user", "content", prompt)));
        
        Mono<String> mono = webClient.post()
                                .uri("/chat/completions")
                                .header("Authorization", "Bearer " + apiKey)
                                .bodyValue(requestBody)
                                .retrieve()
                                .bodyToMono(Map.class)
                                .map(response -> {
                                    List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                                    return (String) message.get("content");
                                });
        return mono.block();
    }
    
    private String buildPrompt(List<DiaryInfo> diaryInfos) {
        StringBuilder prompt = new StringBuilder("아래의 사용자의 일기를 읽고 우울감과 같은 심리 지표를 분석하고, 간단한 조언을 포함한 리포트를 작성해줘.\n");
        
        for (int i = 0; i < diaryInfos.size(); i++) {
            prompt.append("\n일기 ")
                .append(i+1)
                .append("\n제목: ")
                .append(diaryInfos.get(i).title())
                .append("\n내용: ")
                .append(diaryInfos.get(i).content())
                .append("\n");
        }
        
        prompt.append("\n\n분석의 형식은 두 줄로, 첫 줄에는 리포트의 제목을, 두 번째 줄에는 리포트의 내용을 적어줘");
        
        log.info(prompt.toString());
        return prompt.toString();
    }
}
