package com.aiary.be.counsel.application;

import com.aiary.be.counsel.application.dto.CounselData;
import com.aiary.be.counsel.application.dto.RawResult;
import com.aiary.be.counsel.presentation.dto.CounselResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CounselService {

    private final WebClient counselWebClient;
    private final WebClient kakaoWebClient;
    private final String serviceKey;
    private final String kakaoServiceKey;

    public CounselService(
        @Qualifier("counselWebClient") WebClient counselWebClient,
        @Qualifier("kakaoWebClient") WebClient kakaoWebClient,
        @Value("${COUNSEL_API_KEY}") String serviceKey,
        @Value("${KAKAO_API_KEY}") String kakaoServiceKey) {
        this.counselWebClient = counselWebClient;
        this.kakaoWebClient = kakaoWebClient;
        this.serviceKey = serviceKey;
        this.kakaoServiceKey = kakaoServiceKey;
    }


    // 시(city), 구(district) 정보를 받아서 해당 지역의 정신재활센터 list를 보여주는 메서드
    public Mono<CounselResponse> getCounselListByCity(String city, String district){

        return counselWebClient.get()
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
                    .toList())
            .map(CounselResponse::from);
    }

    // 도로명주소 -> 위도/경도 추출하는 메서드(kakao map API 이용)
    public void getCounselGpsListByRoadname(List<CounselData> roadnames){

        String sample = roadnames.get(0).adr();

    }

}
