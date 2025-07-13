package com.aiary.be.counsel.application;

import com.aiary.be.counsel.application.dto.CounselData;
import com.aiary.be.counsel.application.dto.RawResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CounselService {

    private final WebClient webClient;

    @Value("${COUNSEL_API_KEY}")
    private String serviceKey;

    // 시(city), 구(district) 정보를 받아서 해당 지역의 정신재활센터 list를 보여주는 메서드
    public Mono<List<CounselData>> getCounselListByCity(String city, String district){

        return webClient.get()
            .uri(uriBuilder ->uriBuilder
                .path("/3049990/v1/uddi:14a6ea21-af95-4440-bb05-81698f7a1987")
                .queryParam("serviceKey", serviceKey)
                .queryParam("returnType", "JSON")
                .queryParam("page", 1)
                // 총 데이터셋이 2786행. 넉넉하게 3000개 한 번에 조회합니다.
                .queryParam("perPage", 3000)
                .build())
            .retrieve()
            .bodyToMono(RawResult.class)
            .map(rawResult -> rawResult.data().stream()
                    .filter(counselData -> counselData.adr()!=null && counselData.adr().contains(city))
                    .toList());
    }
}
