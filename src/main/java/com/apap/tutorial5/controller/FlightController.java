package com.apap.tutorial5.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.repository.FlightDb;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;

/**
 * 
 * FlightController
 *
 */
@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired 
	private PilotService pilotService;
	
	@Autowired FlightDb flightdb;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value="licenseNumber")String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		model.addAttribute("pilot", pilot);
		model.addAttribute("flight", flight);
//		PilotModel pilotdummy = new PilotModel();
//		List<FlightModel> listflight = new ArrayList<FlightModel>();
//		FlightModel flight = new FlightModel();
//		listflight.add(flight);
//		pilotdummy.setPilotFlight(listflight);
//		
//		model.addAttribute("pilotdummy", pilotdummy);
//		model.addAttribute("licenseNumber", licenseNumber);
		return "addFlight";
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"addRow"})
	public String addRow(@PathVariable(value="licenseNumber") String licenseNumber, 
			@ModelAttribute PilotModel pilotdummy, Model model) {
		FlightModel newflight = new FlightModel();
		pilotdummy.getPilotFlight().add(newflight);
		
		model.addAttribute("pilot", pilotdummy);
	    return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params= {"submit"})
	private String addFlightSubmit(@PathVariable(value = "licenseNumber") String licenseNumber, @ModelAttribute PilotModel pilotdummy) {
		PilotModel pilottujuan = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		for (FlightModel flightGonnaBeAdded : pilotdummy.getPilotFlight()) {
			flightService.addFlight(flightGonnaBeAdded);
		}
		return "add";
	}
	
	
	@RequestMapping(value = "/flight/delete/", method = RequestMethod.POST)
	private String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
		for(FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlightById(flight.getId());
		}
		return "delete-flight";
	}
	
	@RequestMapping(value = "/flight/update/{flightNumber}")
	private String updateFlight(@ModelAttribute FlightModel oldflight, Model model, @PathVariable(value="flightNumber")String flightNumber) {
		model.addAttribute("flightNumber", flightNumber);
		model.addAttribute("flight", oldflight);
		return "update-flight";
	}
	
	
	@RequestMapping(value = "/flight/update/{flightNumber}", method = RequestMethod.POST)
	private String updateFlightSubmit(@ModelAttribute FlightModel newflight, @PathVariable(value="flightNumber")String flightNumber) {
		FlightModel oldflight = flightService.getFlightByFlightNumber(flightNumber);
		flightService.updateFlight(oldflight, newflight);
		return "flight-updated";
	}
	
	@RequestMapping(value = "/flight/viewall", method = RequestMethod.GET)
	private String viewall(Model model) {
		List<FlightModel> flights = flightService.getAll();
		model.addAttribute("flights", flights);	
		return "view-all-flight";
	}
	
	
	
}
