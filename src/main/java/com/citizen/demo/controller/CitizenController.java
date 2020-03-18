package com.citizen.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.citizen.demo.model.Citizen;
import com.citizen.demo.services.CitizenService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class CitizenController {
	
	
	@Autowired
	CitizenService citizenService;
	
	
	@RequestMapping(value = "/view-all-citizen" ,method =RequestMethod.GET)
	public ResponseEntity<List<Citizen>> getAllCitizen() throws JsonParseException, JsonMappingException, IOException{
		List<Citizen> listOfCitizens =new ArrayList<Citizen>();
		listOfCitizens = citizenService.getAllCitizen();
		return new ResponseEntity<List<Citizen>>(listOfCitizens,HttpStatus.OK);
	}

	@RequestMapping(value = "/add-citizen" ,method =RequestMethod.POST)
	public ResponseEntity<String> addCitizen(@RequestBody Citizen citizen){
		
		List<Citizen> listOfCitizens = citizenService.getAllCitizen();
		
		Boolean isExist = listOfCitizens.stream().anyMatch(p->p.getId().equals(citizen.getId()));
		if(isExist) {
			return new ResponseEntity<String>("Id Already Exist",HttpStatus.PRECONDITION_FAILED);
		}
		
		String addResult =   citizenService.addCitizen(listOfCitizens,citizen);
		
		return new ResponseEntity<String>(addResult,HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/delete-citizen/{id}" ,method =RequestMethod.DELETE)
	public ResponseEntity<String> deleteCitizen(@PathVariable("id") Long id) throws JsonParseException, JsonMappingException, IOException{
		
		List<Citizen> listOfCitizens = citizenService.getAllCitizen();
		
		Optional<Citizen> citizen = listOfCitizens.stream().filter(p->p.getId().equals(id)).findFirst();
		
		if(!citizen.isPresent()) {
			return new ResponseEntity<String>("Id Does Not Exist",HttpStatus.PRECONDITION_FAILED);
		}
		
		String deleteResult =   citizenService.deleteCitizen(listOfCitizens,citizen.get());
		
		return new ResponseEntity<String>(deleteResult,HttpStatus.OK);
	}


}
