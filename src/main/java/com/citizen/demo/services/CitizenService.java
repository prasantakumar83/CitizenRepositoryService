package com.citizen.demo.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.citizen.demo.model.Citizen;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service

public class CitizenService {
	
	@Value("citizens.json")
	File file;
	
	@Autowired
	ObjectMapper mapper;

	public List<Citizen> getAllCitizen(){
		List<Citizen>  listOfCitizens = new ArrayList<Citizen>();
		try {
			listOfCitizens =  Arrays.asList(mapper.readValue(file, Citizen[].class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return listOfCitizens;
	}

	public String addCitizen(List<Citizen> listOfCitizens,Citizen citizen) {
		
		List<Citizen> citizenList = new ArrayList<Citizen>();
		citizenList.addAll(listOfCitizens);
		citizenList.add(citizen);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		try {
			mapper.writeValue(file, citizenList);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return "Added Successfully";
		
	}

	public String deleteCitizen(List<Citizen> listOfCitizens, Citizen citizen) {
		List<Citizen> citizenList = new ArrayList<Citizen>();
		citizenList.addAll(listOfCitizens);
		citizenList.remove(citizen);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		try {
			mapper.writeValue(file, citizenList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Deleted Successfully";
	}

}
