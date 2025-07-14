package com.aiary.be.report.application;

import com.aiary.be.diary.application.dto.DiaryInfo;
import com.aiary.be.global.annotation.Client;

import com.aiary.be.report.application.dto.AiResponse;
import com.aiary.be.report.domain.ReportType;
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
    
    public Mono<AiResponse> analyze(Long userId, List<DiaryInfo> diaryInfos, ReportType reportType) {
        String prompt = buildPrompt(diaryInfos, reportType);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4.1");
        requestBody.put("messages", List.of(Map.of("role", "user", "content", prompt)));
        
        return webClient.post()
                   .uri("/chat/completions")
                   .header("Authorization", "Bearer " + apiKey)
                   .bodyValue(requestBody)
                   .retrieve()
                   .bodyToMono(Map.class)
                   .map(response -> {
                        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                        return new AiResponse(
                            userId,
                            (String) message.get("content")
                        );
                        
                   });
    }
    
    private String buildPrompt(List<DiaryInfo> diaryInfos, ReportType reportType) {
        String startStr = reportType.equals(ReportType.WEEKLY) ?
                              "아래는 사용자가 이번 주에 작성한 일기야.\n" :  "아래는 사용자가 이번 달에 작성한 일기야.\n";
        
        StringBuilder prompt = new StringBuilder(startStr);
        prompt.append("아래의 일기들을 읽고, 사용자에게 공감과 위로가 담긴 따뜻한 리포트를 작성해 줘.\n" +
                          "리포트에는 다음 내용을 자연스럽게 담아 줘:\n" +
                          "- 일기를 읽고 느낀 전반적인 감정과 분위기 요약\n" +
                          "- 사용자에게 전하는 따뜻한 공감과 위로의 말\n" +
                          "- 오늘 하루를 되돌아볼 수 있는 질문이나 생각할 거리\n" +
                          "- 앞으로의 마음가짐이나 행동에 도움이 될 수 있는 조언\n" +
                          "\n" +
                          "분석적이기보다는, 감정과 공감을 중심으로 마치 친한 친구가 차 한잔하며 이야기하듯, 부드럽고 따뜻한 해요체로 작성해 줘.\n" +
                          "글은 하나의 흐름으로 이어지는 자연스러운 글로 써 주고, 마크다운 문법은 사용하지 말아 줘.");
        
        for (int i = 0; i < diaryInfos.size(); i++) {
            prompt.append("\n일기 ")
                .append(i+1)
                .append("\n제목: ")
                .append(diaryInfos.get(i).title())
                .append("\n내용: ")
                .append(diaryInfos.get(i).content())
                .append("\n");
        }
        
        log.info(prompt.toString());
        return prompt.toString();
    }
}
