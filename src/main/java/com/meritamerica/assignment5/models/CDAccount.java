package com.meritamerica.assignment5.models;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class CDAccount extends BankAccount
{
	public CDAccount()
	{
		super( MeritBank.getNextAccountNumber(), 0, 0.01, new Date() );
	}

	public CDAccount( CDOffering offering )
	{
		super( MeritBank.getNextAccountNumber(), 0, offering.getInterestRate(), new Date() );
		this.offering = offering;
	}

	public CDAccount( CDOffering offering, double balance )
	{
		super( MeritBank.getNextAccountNumber(), balance, offering.getInterestRate(), new Date() );
		this.offering = offering;
	}

	public CDAccount( long accountNumber, double balance, double interestRate, java.util.Date accountOpenedOn,
			int term )
	{
		super( accountNumber, balance, interestRate, accountOpenedOn );
		this.offering = new CDOffering( term, interestRate );
	}

	public double getInterestRate()
	{
		return this.offering.getInterestRate();
	}

	public int getTerm()
	{
		return this.offering.getTerm();
	}

	public java.util.Date getStartDate()
	{
		Date date = new Date();
		return date;
	}

	public boolean withdraw( double amount )
	{
		return false;
	}

	public boolean deposit( double amount )
	{
		return false;
	}

	public double futureValue()
	{
		double futureVal = MeritBank.recursiveFutureValue( super.getBalance(), this.getTerm(), this.getInterestRate() );
		return futureVal;
	}

	public static CDAccount readFromString( String accountData ) throws java.lang.NumberFormatException
	{
		StringTokenizer token = new StringTokenizer( accountData, "," );
		int numAccount = Integer.parseInt( token.nextToken() );
		long balance = Long.parseLong( token.nextToken() );
		double rate = Double.parseDouble( token.nextToken() );

		Date date = new Date( token.nextToken() );
		Format f = new SimpleDateFormat( "dd/MM/yy" );
		String strDate = f.format( date );
		date = new Date( strDate );

		int term = Integer.parseInt( token.nextToken() );

		CDAccount cdAcc = new CDAccount( numAccount, balance, rate, date, term );
		return cdAcc;
	}

	public String writeToString()
	{
		String cdString = getAccountNumber() + "," + getBalance() + "," + getInterestRate() + "," + getStartDate() + ","
				+ getTerm();
		return cdString;
	}

	private CDOffering offering;

	public CDOffering getOffering()
	{
		return offering;
	}

	public void setOffering( CDOffering offering )
	{
		this.offering = offering;
	}
}
