package com.aiary.be.counsel.presentation.dto;

import com.aiary.be.counsel.application.dto.CounselData;
import java.util.List;

public record CounselResponse (
    int totalCount,
    List<CounselData> data
){
    public static CounselResponse from(List<CounselData> list){
        return new CounselResponse(list.size(), list);
    }
}
