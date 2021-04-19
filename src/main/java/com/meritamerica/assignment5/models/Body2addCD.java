package com.meritamerica.assignment5.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class Body2addCD
{
	public Body2addCD( double balance, CDID cd )
	{
		this.balance = balance;
		this.cdOffering = cd;
	}

	@PositiveOrZero( message = "Balance is required" )
	@NotNull( message = "Balance is required" )
	@NotBlank( message = "Balance is required" )
	double balance;

	public double getBalance()
	{
		return balance;
	}

	public void setBalance( double balance )
	{
		this.balance = balance;
	}

	public CDID getCdOffering()
	{
		return cdOffering;
	}

	public void setCdOffering( CDID cdOffering )
	{
		this.cdOffering = cdOffering;
	}

	public CDID cdOffering;
}
