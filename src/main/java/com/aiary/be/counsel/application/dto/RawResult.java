package com.aiary.be.counsel.application.dto;

import java.util.List;

public record RawResult(
    int page,
    int perPage,
    int totalCount,
    int currentCount,
    int matchCount,
    List<CounselData> data
) {}
