package com.aiary.be.diary.domain;

import com.aiary.be.global.exception.CustomException;
import com.aiary.be.global.exception.errorCode.DiaryErrorCode;

public enum Weather {
    SUNNY, CLOUDY, SNOWY, RAINY;
    
    public static Weather nameToEntity(String name) {
        Weather[] types = Weather.values();
        for (Weather type : types) {
            if(type.name().equals(name)) {
                return type;
            }
        }
        
        throw CustomException.from(DiaryErrorCode.NOT_MATCH_WEATHER);
    }
}
