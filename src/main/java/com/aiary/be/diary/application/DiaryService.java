package com.aiary.be.diary.application;

import com.aiary.be.diary.application.dto.DiaryInfo;
import com.aiary.be.diary.domain.Diary;
import com.aiary.be.diary.domain.Weather;
import com.aiary.be.diary.persistent.DiaryRepository;
import com.aiary.be.diary.presentation.dto.DiaryRequest;
import com.aiary.be.global.exception.CustomException;
import com.aiary.be.global.exception.errorCode.DiaryErrorCode;
import com.aiary.be.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    
    // 오버로딩 : 페이지네이션을 적용한 특정 1달 동안의 다이어리 정보 조회
    @Transactional(readOnly = true)
    public Page<DiaryInfo> readDiaryInfos(Long userId, LocalDateTime[] range, Pageable pageable) {
        return diaryRepository.findAllByUserIdAndCreatedAtBetween(userId, range[0], range[1], pageable)
                   .map(DiaryInfo::from);
    }
    
    // 오버로딩 : 리포트 생성을 위해서 7일 또는 1달 기간의 다이어리 정보 조회
    @Transactional(readOnly = true)
    public List<DiaryInfo> readDiaryInfos(Long userId, LocalDateTime[] range) {
        return diaryRepository.findAllByUserIdAndCreatedAtBetween(userId, range[0], range[1]).stream()
                   .map(DiaryInfo::from)
                   .toList();
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
        Optional<Diary> exist = diaryRepository.findByUserIdOrderByIdDesc(user.getId());
        
        if(exist.isPresent()) {
            int year = exist.get().getCreatedAt().getYear();
            int month = exist.get().getCreatedAt().getMonthValue();
            int day = exist.get().getCreatedAt().getDayOfMonth();
            
            if(LocalDate.now().isEqual(LocalDate.of(year, month, day))) {
                throw CustomException.from(DiaryErrorCode.ALREADY_EXIST_DAY);
            }
        }
        
        Diary diary = new Diary(
            user,
            diaryRequest.title(), diaryRequest.content(),
            LocalDateTime.now(), LocalDateTime.now(),
            Weather.nameToEntity(diaryRequest.weather()),
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
            Weather.nameToEntity(diaryRequest.weather()),
            diaryRequest.depression(), diaryRequest.anger(), diaryRequest.happy()
        );
    }
    
    @Transactional
    public void deleteDiary(Long diaryId) {
        diaryRepository.deleteById(diaryId);
    }
    
    @Transactional(readOnly = true)
    public boolean userMatch(Long userId, Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
            () -> CustomException.from(DiaryErrorCode.NOT_FOUND)
        );
        
        return Objects.equals(diary.getUser().getId(), userId);
    }
    
    @Transactional
    public void deleteByUserId(Long userId) {
        diaryRepository.deleteDiariesByUserId(userId);
    }
}
