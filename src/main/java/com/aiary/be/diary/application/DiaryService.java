package com.aiary.be.diary.application;

import com.aiary.be.diary.application.dto.DiaryInfo;
import com.aiary.be.diary.domain.Diary;
import com.aiary.be.diary.persistent.DiaryRepository;
import com.aiary.be.diary.presentation.dto.DiaryRequest;
import com.aiary.be.global.exception.CustomException;
import com.aiary.be.global.exception.errorCode.DiaryErrorCode;
import com.aiary.be.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.CustomObjectInputStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    
    @Transactional(readOnly = true)
    public Page<DiaryInfo> readDiaryInfos(LocalDateTime[] range, Pageable pageable) {
        Page<Diary> diaries = diaryRepository.findAllByCreatedAtBetween(range[0], range[1], pageable);
        return diaries.map(DiaryInfo::from);
    }
    
    @Transactional(readOnly = true)
    public DiaryInfo readDiaryInfo(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
            () -> CustomException.from(DiaryErrorCode.NOT_FOUND)
        );
        
        return DiaryInfo.from(diary);
    }
    
    @Transactional
    public void createDiary(User user, DiaryRequest diaryRequest) {
        Diary diary = new Diary(
            user,
            diaryRequest.title(), diaryRequest.content(),
            LocalDateTime.now(), LocalDateTime.now(),
            diaryRequest.depression(), diaryRequest.anger(), diaryRequest.happy()
        );
        
        diaryRepository.save(diary);
    }
    
    @Transactional
    public void updateDiary(Long diaryId, DiaryRequest diaryRequest) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
            () -> CustomException.from(DiaryErrorCode.NOT_FOUND)
        );
        
        diary.update(
            diaryRequest.title(), diaryRequest.content(),
            diaryRequest.depression(), diaryRequest.anger(), diaryRequest.happy()
        );
    }
    
    @Transactional
    public void deleteDiary(Long diaryId) {
        diaryRepository.deleteById(diaryId);
    }
}
