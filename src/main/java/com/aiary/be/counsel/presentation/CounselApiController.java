package com.aiary.be.counsel.presentation;

import com.aiary.be.counsel.application.CounselService;
import com.aiary.be.counsel.application.dto.CounselData;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/counsel")
@RequiredArgsConstructor
public class CounselApiController {

    private final CounselService counselService;

    // 시설의 도로명 주소 리스트를 반환
    @GetMapping("/roadname")
    public Mono<ResponseEntity<?>> getCounselRoadNameListByCity(
        @RequestParam(name="city") String city,
        // 시/구까지 받고 있긴 한데, 시만 받아도 괜찮을 것 같긴 해요.
        @RequestParam(name="district") String district
    ){
        return counselService.getCounselListByCity(city, district)
            .map(ResponseEntity::ok);

    }

    // 시설의 위도/경도 리스트를 반환: 아직 구현 전
    @GetMapping("/gps")
    public Mono<?> getCounselGpsListByCity(
        @RequestParam(name="city") String city,
        @RequestParam(name="district") String district
    ){
        var roadnames = counselService.getCounselListByCity(city, district);

        counselService.getCounselGpsListByRoadname((List<CounselData>) roadnames);

        return null;
    }

}
