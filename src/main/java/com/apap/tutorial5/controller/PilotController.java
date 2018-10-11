package com.apap.tutorial5.controller;

import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.PilotService;
import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.service.FlightService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 * PilotController
 * 
 */
@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@Autowired 
	private FlightService flightService;
	
	@RequestMapping("/")
	private String home() {
		return "home";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("pilot", new PilotModel());
		return "addPilot";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot) {
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.GET)
	private String updatePilot(@ModelAttribute PilotModel oldpilot, Model model, @PathVariable(value="licenseNumber")String licenseNumber) {
		model.addAttribute("pilot", oldpilot);
		return "update-pilot";
	}
	
	@RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.POST)
	private String updatePilotSubmit(@ModelAttribute PilotModel newpilot, @PathVariable(value="licenseNumber")String licenseNumber) {
		PilotModel old = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		pilotService.updatePilot(old, newpilot);
		return "pilot-updated";
	}
	
	@RequestMapping(value = "/pilot/view", method = RequestMethod.GET)
	private String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("pilot", pilot);
		return "view-pilot";
	}
	
	@RequestMapping(value = "/pilot/viewall", method = RequestMethod.GET)
	private String viewall(Model model) {
		List<PilotModel> pilots = pilotService.getAll();
		model.addAttribute("pilots", pilots);
		return "view-all";
	}
	
	
	@RequestMapping(value = "/pilot/delete/{id}", method = RequestMethod.GET)
	private String delete(@PathVariable(value="id") long id, Model model) {
		PilotModel pilot = pilotService.getPilotDetailById(id);
		pilotService.deletePilot(pilot);
		return "delete-pilot";
	}
	

}
