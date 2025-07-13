package com.aiary.be.counsel.presentation;

import com.aiary.be.counsel.application.CounselService;
import com.aiary.be.counsel.presentation.dto.CounselResponse;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public Mono<?> getCounselListByCity(
        @RequestParam(name="city") String city,
        // 시/구까지 받고 있긴 한데, 시만 받아도 괜찮을 것 같긴 해요.
        @RequestParam(name="district") String district
    ){
        return counselService.getCounselListByCity(city, district)
            .map(CounselResponse::from);
    }

}
