package com.meritamerica.assignment5.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.assignment5.exceptions.InvalidRequestException;
import com.meritamerica.assignment5.exceptions.NoSuchResourceFoundException;
import com.meritamerica.assignment5.models.AccountHolder;
import com.meritamerica.assignment5.models.Body2addCD;
import com.meritamerica.assignment5.models.CDAccount;
import com.meritamerica.assignment5.models.CDOffering;
import com.meritamerica.assignment5.models.CheckingAccount;
import com.meritamerica.assignment5.models.ExceedsCombinedBalanceLimitException;
import com.meritamerica.assignment5.models.ExceedsFraudSuspicionLimitException;
import com.meritamerica.assignment5.models.MeritBank;
import com.meritamerica.assignment5.models.SavingsAccount;

@RestController
public class AccountHolderController< body2addCD >
{
	ArrayList< CDOffering > cdOfferings = new ArrayList< CDOffering >();

	@RequestMapping( value = "/", method = RequestMethod.GET )
	public String test()
	{
		return "Testing";
	}

	@PostMapping( value = "/AccountHolders" )
	public AccountHolder addAccountHolder( @RequestBody AccountHolder account ) throws InvalidRequestException
	{

		boolean isNull = account.getFirstName() == null || account.getLastName() == null || account.getSSN() == null;

		boolean isBlank = account.getFirstName().length() == 0 || account.getLastName().length() == 0
				|| account.getSSN().length() == 0;

		if( isNull || isBlank )
		{
			throw new InvalidRequestException( "Invalid Request" );
		}

		MeritBank.addAccountHolder( account );
		return account;
	}

	@GetMapping( value = "/AccountHolders" )
	public AccountHolder[] getAccountHolders()
	{
		return MeritBank.getAccountHolders();
	}

	@GetMapping( value = "/AccountHolders/{id}" )
	public AccountHolder getAccountHolderById( @PathVariable int id ) throws NoSuchResourceFoundException
	{
		return getAccountHolderByID( id );
//		if( id > MeritBank.getAccountHolders().length )
//		{
//			throw new NoSuchResourceFoundException( "No Such Resource Found" );
//		}
//		return MeritBank.getAccountHolders()[id - 1];
	}

	private AccountHolder getAccountHolderByID( int id ) throws NoSuchResourceFoundException
	{
		for( AccountHolder ah : MeritBank.getAccountHolders() )
			if( ah.getId() == id ) return ah;

		throw new NoSuchResourceFoundException( "No Such Resource Found" );
	}

	@PostMapping( value = "/AccountHolders/{id}/CheckingAccounts" )
	public CheckingAccount createNewCheckingAccount( @RequestBody CheckingAccount account, @PathVariable int id )
			throws ExceedsCombinedBalanceLimitException, NoSuchResourceFoundException, InvalidRequestException
	{
		if( id > MeritBank.getAccountHolders().length )
		{
			throw new NoSuchResourceFoundException( "No Such Resource Found" );
		}
		if( account.getBalance() < 0 || MeritBank.getAccountHolders()[id].getCombinedBalance() > 250000 )
		{
			throw new InvalidRequestException( "Invalid Request" );
		}
		return MeritBank.getAccountHolders()[id - 1].addCheckingAccount( account );
	}

	@GetMapping( value = "/AccountHolders/{id}/CheckingAccounts" )
	public CheckingAccount[] getCheckingAccount( @PathVariable int id ) throws NoSuchResourceFoundException
	{
		if( id > MeritBank.getAccountHolders().length )
			throw new NoSuchResourceFoundException( "No Such Resource Found" );

		return MeritBank.getAccountHolders()[id - 1].getCheckingAccounts();
	}

	@PostMapping( value = "/AccountHolders/{id}/SavingsAccounts" )
	public SavingsAccount createNewSavingsAccount( @RequestBody SavingsAccount account, @PathVariable int id )
			throws NoSuchResourceFoundException, InvalidRequestException, ExceedsCombinedBalanceLimitException
	{
		if( id > MeritBank.getAccountHolders().length )
			throw new NoSuchResourceFoundException( "No Such Resource Found" );

		if( account.getBalance() < 0 || MeritBank.getAccountHolders()[id - 1].getCombinedBalance() > 250000 )
			throw new InvalidRequestException( "Invalid Request" );

		return MeritBank.getAccountHolders()[id - 1].addSavingsAccount( account );
	}

	@GetMapping( value = "/AccountHolders/{id}/SavingsAccounts" )
	public SavingsAccount[] getSavingsAccounts( @PathVariable int id ) throws NoSuchResourceFoundException
	{
		if( id > MeritBank.getAccountHolders().length )
			throw new NoSuchResourceFoundException( "No Such Resource Found" );

		return MeritBank.getAccountHolders()[id - 1].getSavingsAccounts();
	}

	@PostMapping( value = "/AccountHolders/{id}/CDAccounts" )
	@ResponseStatus( HttpStatus.CREATED )
	public CDAccount addCD( @RequestBody @Valid Body2addCD body, @PathVariable int id )
			throws NoSuchResourceFoundException, InvalidRequestException, ExceedsFraudSuspicionLimitException
	{
		CDOffering cdo = MeritBank.getCDOfferingById( body.cdOffering.getId() );
		if( body.getBalance() < 0 ) throw new InvalidRequestException( "Balance cannot be negative." );
		return getAccountHolderByID( id ).addCDAccount( cdo, body.getBalance() );
	}

	@GetMapping( value = "/AccountHolders/{id}/CDAccounts" )
	public CDAccount[] getCDAccounts( @PathVariable int id ) throws NoSuchResourceFoundException
	{
		return getAccountHolderByID( id ).getCDAccounts();
	}
}
