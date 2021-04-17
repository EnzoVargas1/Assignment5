package com.meritamerica.assignment5.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.assignment5.exceptions.InvalidRequestException;
import com.meritamerica.assignment5.models.AccountHolder;
import com.meritamerica.assignment5.models.MeritBank;



@RestController
public class AccountHolderController {
	
	ArrayList<AccountHolder> accountHolders = new ArrayList<AccountHolder>();
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String test() {
		return "Testing";
	}
	
	
	@PostMapping(value = "/addAccountHolders")
	public AccountHolder addAccountHolder(@RequestBody AccountHolder account) throws InvalidRequestException{
		
		boolean isNull = account.getFirstName()== null || 
				account.getLastName() == null|| account.getSSN() == null;
		
		boolean isBlank = account.getFirstName().length() == 0|| 
				account.getLastName().length() == 0|| account.getSSN().length() == 0;
		
		
		if(isNull || isBlank) {
			throw new InvalidRequestException("Invalid Request");
		}
		
		MeritBank.addAccountHolder(account);
		//accountHolders.add(account);
		return account;
	}
	
	@GetMapping(value = "/getAccountHolders")
	public AccountHolder[] getAccountHolders() {
		return MeritBank.getAccountHolders();
	}
	
	
	

}
