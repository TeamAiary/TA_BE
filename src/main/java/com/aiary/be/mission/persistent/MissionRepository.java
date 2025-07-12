package com.aiary.be.mission.persistent;

import com.aiary.be.mission.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByEssentialIsTrue();
    
    List<Mission> findByEssentialIsFalse();
    
    List<Mission> findByEssentialIsFalseAndActivateIsTrue();
}
