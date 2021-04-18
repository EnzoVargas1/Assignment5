package com.meritamerica.assignment5.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.meritamerica.assignment5.exceptions.InvalidRequestException;
import com.meritamerica.assignment5.models.CDOffering;
import com.meritamerica.assignment5.models.MeritBank;

@RestController
public class CDOfferingsController {
	
	ArrayList<CDOffering> cdOfferings = new ArrayList<CDOffering>();
	
	@PostMapping(value ="/CDOfferings")
	public CDOffering createCDOffering(@RequestBody CDOffering cdOffering)throws InvalidRequestException {
		
		if(cdOffering.getInterestRate() <= 0 || cdOffering.getInterestRate() >= 1) {
			throw new  InvalidRequestException("Invalid Request");
		}
		if(cdOffering.getTerm() < 1) {
			throw new  InvalidRequestException("Invalid Request");
		}
		cdOfferings.add(cdOffering);
		MeritBank.setCDOfferings(cdOfferings.toArray(new CDOffering[0]));
		return cdOffering;
	}
	
	
	@GetMapping(value ="/CDOfferings")
	public CDOffering[] getCDOfferings() {
		return cdOfferings.toArray(new CDOffering[0]);
	}
	

}
