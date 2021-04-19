package com.meritamerica.assignment5.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.assignment5.exceptions.InvalidRequestException;
import com.meritamerica.assignment5.exceptions.NoSuchResourceFoundException;
import com.meritamerica.assignment5.models.CDOffering;
import com.meritamerica.assignment5.models.MeritBank;

@RestController
public class CDOfferingsController
{
	ArrayList< CDOffering > cdOfferings = new ArrayList< CDOffering >();

	@ResponseStatus( HttpStatus.CREATED )
	@PostMapping( value = "/CDOfferings" )
	public CDOffering createCDOffering( @RequestBody CDOffering cdOffering ) throws InvalidRequestException
	{

		if( cdOffering.getInterestRate() <= 0 || cdOffering.getInterestRate() >= 1 )
		{
			throw new InvalidRequestException( "Invalid Request" );
		}
		if( cdOffering.getTerm() < 1 )
		{
			cdOffering = null;
			throw new InvalidRequestException( "Invalid Request" );
		}
		cdOfferings.add( cdOffering );
		MeritBank.setCDOfferings( cdOfferings.toArray( new CDOffering[0] ) );
		return cdOffering;
	}

	@GetMapping( value = "/CDOfferings" )
	public CDOffering[] getCDOfferings()
	{
		return cdOfferings.toArray( new CDOffering[0] );
	}

	@GetMapping( value = "/CDOfferings/{id}" )
	public CDOffering getCDOfferingById( @PathVariable int id ) throws NoSuchResourceFoundException
	{
		for( CDOffering cdo : MeritBank.getCDOfferings() )
			if( cdo.getId() == id ) return cdo;

		throw new NoSuchResourceFoundException( "No Such Resource Found" );
	}
}
