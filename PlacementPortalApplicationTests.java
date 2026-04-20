package com.placement.placementPortal;

import com.placement.placementPortal.Entity.Experience;
import com.placement.placementPortal.Repository.ExperienceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@SpringBootTest
class PlacementPortalApplicationTests {

	@Autowired
	private ExperienceRepository experienceRepository;
	private Object placementType;

	@Test
	void contextLoads() {
		
	}

}
