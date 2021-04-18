package com.meritamerica.assignment5.models;

import java.util.*;

public abstract class BankAccount {
	public BankAccount(double balance, double interestRate) {
		this.balance = balance;
		this.interestRate = interestRate;
		accountNumber = nextAccountNumber++;
	}

	public BankAccount(double balance, double interestRate, java.util.Date accountOpenedOn) {
		this.balance = balance;
		this.interestRate = interestRate;
		this.date = accountOpenedOn;
	}

	public BankAccount(long accountNumber, double balance, double interestRate, java.util.Date accountOpenedOn) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.interestRate = interestRate;
		this.date = accountOpenedOn;
	}

	public java.util.Date getOpenedOn() {
		return this.date;
	}

	public long getAccountNumber() {
		return this.accountNumber;
	}

	public double getBalance() {
		return this.balance;
	}

	public double getInterestRate() {
		return this.interestRate;
	}

	public boolean withdraw(double amount) {
		if ((balance - amount) >= 0) {
			balance -= amount;
			return true;
		}
		return false;
	}

	public boolean deposit(double amount) {
		if (amount > 0) {
			this.balance += amount;
			return true;
		} else {
			return false;
		}
	}

	public double futureValue(int years) {
		double futureVal = MeritBank.recursiveFutureValue(this.getBalance(), years, this.getInterestRate());
		return futureVal;
	}
	
	/*
	 * public static BankAccount readFromString(String accountData) throws
	 * java.lang.NumberFormatException{ StringTokenizer token = new
	 * StringTokenizer(accountData, ","); int numAccount =
	 * Integer.parseInt(token.nextToken()); double balance =
	 * Double.parseDouble(token.nextToken()); double rate =
	 * Double.parseDouble(token.nextToken()); Date date = new
	 * Date(token.nextToken());
	 * // BankAccount bank = new BankAccount(numAccount,balance, rate, date);
	 * return bank; }
	 */

	public String writeToString() {
		String accountString = getAccountNumber() + "," + getBalance() + "," + getInterestRate() + "," + getOpenedOn();
		return accountString;
	}

	
	public String toString() {
		String toStr =  "Acct Num "+getAccountNumber()+ " balance "+getBalance();
		return toStr;
	}
	private static long nextAccountNumber = 0;
	private double balance;
	private double interestRate;
	private long accountNumber;
	private java.util.Date date;


}