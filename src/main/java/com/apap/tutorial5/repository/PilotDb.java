package com.apap.tutorial5.repository;

import com.apap.tutorial5.model.PilotModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * PilotDB
 *
 */
@Repository
public interface PilotDb extends JpaRepository<PilotModel, Long>{
	PilotModel findByLicenseNumber(String licenseNumber);
	PilotModel findById(long id);
}
