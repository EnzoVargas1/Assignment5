package com.meritamerica.assignment5.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.assignment5.exceptions.InvalidRequestException;
import com.meritamerica.assignment5.exceptions.NoSuchResourceFoundException;
import com.meritamerica.assignment5.models.AccountHolder;
import com.meritamerica.assignment5.models.CheckingAccount;
import com.meritamerica.assignment5.models.ExceedsCombinedBalanceLimitException;
import com.meritamerica.assignment5.models.MeritBank;
import com.meritamerica.assignment5.models.BankAccount;
import com.meritamerica.assignment5.models.CDOffering;


@RestController
public class AccountHolderController {
	
	ArrayList<CDOffering> cdOfferings = new ArrayList<CDOffering>();
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String test() {
		return "Testing";
	}
	
	
	@PostMapping(value = "/AccountHolders")
	public AccountHolder addAccountHolder(@RequestBody AccountHolder account) throws InvalidRequestException{
		
		boolean isNull = account.getFirstName()== null || 
				account.getLastName() == null|| account.getSSN() == null;
		
		boolean isBlank = account.getFirstName().length() == 0|| 
				account.getLastName().length() == 0|| account.getSSN().length() == 0;
		
		
		if(isNull || isBlank) {
			throw new InvalidRequestException("Invalid Request");
		}
		
		MeritBank.addAccountHolder(account);
		return account;
	}
	
	
	@GetMapping(value = "/AccountHolders")
	public AccountHolder[] getAccountHolders() {
		return MeritBank.getAccountHolders();
	}
	
	
	@GetMapping(value = "/AccountHolders/{id}")
	public AccountHolder getAccountHolderById(@PathVariable int id)throws NoSuchResourceFoundException {
		if(id > MeritBank.getAccountHolders().length-1) {
			throw new  NoSuchResourceFoundException("No Such Resource Found");
		}
		return MeritBank.getAccountHolders()[id];
	}
	
	
	@PostMapping(value = "/AccountHolders/{id}/CheckingAccounts")
	public CheckingAccount createNewCheckingAccount(@RequestBody CheckingAccount account, @PathVariable int id)
	throws ExceedsCombinedBalanceLimitException, NoSuchResourceFoundException, InvalidRequestException {
		if(id > MeritBank.getAccountHolders().length - 1) {
			throw new  NoSuchResourceFoundException("No Such Resource Found");
		}
		if(account.getBalance() < 0 || MeritBank.getAccountHolders()[id].getCombinedBalance() > 250000) {
			throw new InvalidRequestException("Invalid Request");
		}
		return account;
	}
	
	
	
}
