package com.meritamerica.assignment5.models;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class SavingsAccount extends BankAccount
{

	public SavingsAccount()
	{
		super( nextAccountNumber++, 0, SAVINGS_INTERESTRATE, new Date() );
	}

	public SavingsAccount( double balance )
	{
		super( nextAccountNumber++, balance, SAVINGS_INTERESTRATE, new Date() );
	}

	public SavingsAccount( long accountNumber, double balance, double interestRate, java.util.Date accountOpenedOn )
	{
		super( accountNumber, balance, interestRate, accountOpenedOn );
	}

	public static SavingsAccount readFromString( String accountData ) throws java.lang.NumberFormatException
	{
		StringTokenizer token = new StringTokenizer( accountData, "," );
		int numAccount = Integer.parseInt( token.nextToken() );
		double balance = Double.parseDouble( token.nextToken() );
		double rate = Double.parseDouble( token.nextToken() );

		Date date = new Date( token.nextToken() );
		Format f = new SimpleDateFormat( "dd/MM/yy" );
		String strDate = f.format( date );
		date = new Date( strDate );

		SavingsAccount savings = new SavingsAccount( numAccount, balance, rate, date );
		return savings;
	}

	public static final double SAVINGS_INTERESTRATE = 0.01;
	private static long nextAccountNumber = 0;
}
