package com.placement.placementPortal.Repository;

import com.placement.placementPortal.Entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByCompanyNameContainingIgnoreCase(String companyName);
    List<Experience> findByPlacementTypeIgnoreCase(String type);
    List<Experience> findByCompanyNameContainingIgnoreCaseAndPlacementTypeIgnoreCase(String company, String type);
}