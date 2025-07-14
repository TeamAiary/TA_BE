package com.aiary.be.counsel.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CounselData(
    @JsonProperty("기관명")
    String name,

//    @JsonProperty("기관구분")
//    String category,

    @JsonProperty("주소")
    String adr

//    @JsonProperty("홈페이지")
//    String website
){}
