package com.aiary.be.global.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    @Qualifier("counselWebClient")
    public WebClient counselWebClient(WebClient.Builder webClientBuilder){
        String url = "https://api.odcloud.kr/api";

        // 한 번에 조회 가능한 버퍼 크기를 변경합니다.
        // 한 번에 3000개 행을 모두 조회하고 filtering 할 거에요.
        final int size = 10 * 1024 * 1024; // 10MB
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
            .build();

        return webClientBuilder
            .baseUrl(url)
            .defaultHeader("Accept", "application/json")
            .exchangeStrategies(strategies)
            .build();
    }

    @Bean
    @Qualifier("kakaoWebClient")
    public WebClient counselKakaoWebClient(WebClient.Builder webClientBuilder){
        String url = "https://dapi.kakao.com";

        final int size = 10*1024*1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(codecs-> codecs.defaultCodecs().maxInMemorySize(size))
            .build();

        return webClientBuilder
            .baseUrl(url)
            .defaultHeader("Accept", "application/json")
            .exchangeStrategies(strategies)
            .build();
    }
}
