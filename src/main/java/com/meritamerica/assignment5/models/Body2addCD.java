package com.meritamerica.assignment5.models;

public class Body2addCD
{
	public Body2addCD( double balance, CDID cd )
	{
		this.balance = balance;
		this.cdOffering = cd;
	}

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
