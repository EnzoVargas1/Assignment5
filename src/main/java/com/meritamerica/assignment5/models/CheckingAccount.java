package com.meritamerica.assignment5.models;

import java.util.Date;
import java.util.StringTokenizer;

public class CheckingAccount extends BankAccount
{

	public CheckingAccount()
	{
		super( nextAccountNumber++, 0, CHECKING_INTERESTRATE, new Date() );
	}

	public CheckingAccount( double balance )
	{
		super( nextAccountNumber++, balance, CHECKING_INTERESTRATE, new Date() );
	}

	public CheckingAccount( long accountNumber, double balance, double interestRate, java.util.Date accountOpenedOn )
	{
		super( accountNumber, balance, interestRate, accountOpenedOn );
	}

	public static CheckingAccount readFromString( String accountData ) throws java.lang.NumberFormatException
	{
		StringTokenizer token = new StringTokenizer( accountData, "," );
		int numAccount = Integer.parseInt( token.nextToken() );
		double balance = Double.parseDouble( token.nextToken() );
		double rate = Double.parseDouble( token.nextToken() );
		Date date = new Date( token.nextToken() );
		CheckingAccount checking = new CheckingAccount( numAccount, balance, rate, date );
		System.out.println( "checkingbalance: " + balance );
		return checking;
	}

	public static final double CHECKING_INTERESTRATE = 0.0001;
	private static long nextAccountNumber = 0;
}
