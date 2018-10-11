package com.apap.tutorial5.repository;

import com.apap.tutorial5.model.FlightModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * FlightDb
 * @author ASUS
 *
 */
@Repository
public interface FlightDb extends JpaRepository<FlightModel, Long>{
	FlightModel findById(long id);
	FlightModel findByFlightNumber(String flightNumber);
}
