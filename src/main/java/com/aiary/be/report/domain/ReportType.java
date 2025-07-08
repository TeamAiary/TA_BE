package com.aiary.be.report.domain;

import com.aiary.be.global.exception.CustomException;
import com.aiary.be.global.exception.errorCode.ReportErrorCode;

public enum ReportType {
    WEEKLY, MONTHLY;
    
    public static ReportType nameToEntity(String name) {
        ReportType[] types = ReportType.values();
        for (ReportType type : types) {
            if(type.name().equals(name)) {
                return type;
            }
        }
        
        throw CustomException.from(ReportErrorCode.NOT_FOUND_TYPE);
    }
}
