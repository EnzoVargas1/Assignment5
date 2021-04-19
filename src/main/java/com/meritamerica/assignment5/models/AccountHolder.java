package com.meritamerica.assignment5.models;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class AccountHolder implements Comparable< AccountHolder >
{
	public AccountHolder( String firstName, String middleName, String lastName, String ssn )
	{
		this.id = ++nextId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.ssn = ssn;
	}

	public int getId()
	{
		return this.id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public String getFirstName()
	{
		return this.firstName;
	}

	public void setFirstName( String firstName )
	{
		this.firstName = firstName;
	}

	public String getMiddleName()
	{
		return this.middleName;
	}

	public void setMiddleName( String middleName )
	{
		this.middleName = middleName;
	}

	public String getLastName()
	{
		return this.lastName;
	}

	public void setLastName( String lastName )
	{
		this.lastName = lastName;
	}

	public String getSSN()
	{
		return ssn;
	}

	public void setSSN( String ssn )
	{
		this.ssn = ssn;
	}

	public CheckingAccount addCheckingAccount( double openingBalance ) throws ExceedsCombinedBalanceLimitException
	{
		CheckingAccount checking = null;
		// DepositTransaction depositTransact = null;
		if( ( getSavingsBalance() + getCheckingBalance() + openingBalance ) < 250000 )
		{
			checking = new CheckingAccount( 0 );
			this.checkingAccList.add( checking );
			/*
			 * depositTransact = new DepositTransaction(checking,openingBalance); try{
			 * depositTransact.process(); } catch (NegativeAmountException e) {
			 * System.out.println("Negative amount entered: invalid"); }catch(
			 * ExceedsFraudSuspicionLimitException e) {
			 * System.out.println("Entered amount exceeds Fraud Suspicion limit"); }
			 */
			checking.deposit( openingBalance );
		}
		else
		{
			ExceedsCombinedBalanceLimitException exceedsCombinedBal = new ExceedsCombinedBalanceLimitException();
			throw exceedsCombinedBal;
		}
		return checking;
	}

	public CheckingAccount addCheckingAccount( CheckingAccount checkingAccount )
			throws ExceedsCombinedBalanceLimitException
	{

		if( ( getSavingsBalance() + getCheckingBalance() + checkingAccount.getBalance() ) < 250000 )
		{
			this.checkingAccList.add( checkingAccount );
			return checkingAccount;
		}
		else
		{
			ExceedsCombinedBalanceLimitException exceedsCombinedBal = new ExceedsCombinedBalanceLimitException();
			throw exceedsCombinedBal;
		}
	}

	public CheckingAccount[] getCheckingAccounts()
	{
		CheckingAccount[] checking = checkingAccList.toArray( new CheckingAccount[0] );
		return checking;
	}

	// directly the size of arraylist is calculated.
	public int getNumberOfCheckingAccounts()
	{
		int numberOfCheckingAccounts = checkingAccList.size();
		return numberOfCheckingAccounts;
	}

	// adds each checking account of the account holder from the array of checking
	// accounts
	// to get the checking account array we call the already defined function
	// getCheckingAccounts()
	public double getCheckingBalance()
	{
		CheckingAccount[] checkingArr = getCheckingAccounts();
		double checkingTotal = 0;
		for( int i = 0; i < checkingArr.length; i++ )
		{
			checkingTotal += checkingArr[i].getBalance();
		}

		return checkingTotal;
	}

	// add a new savings account with a given opening balance if combined balance is
	// less than $250,000
	public SavingsAccount addSavingsAccount( double openingBalance ) throws ExceedsCombinedBalanceLimitException
	{
		SavingsAccount savings = null;

		if( ( getSavingsBalance() + getCheckingBalance() + openingBalance ) < 250000 )
		{
			savings = new SavingsAccount( 0 );
			this.savingsAccList.add( savings );
			/*
			 * depositTransact = new DepositTransaction(savings,openingBalance); try{
			 * depositTransact.process(); } catch (NegativeAmountException e) {
			 * System.out.println("Negative amount entered: invalid"); }catch(
			 * ExceedsFraudSuspicionLimitException e) {
			 * System.out.println("Entered amount exceeds Fraud Suspicion limit"); }
			 */
			savings.deposit( openingBalance );
		}
		else
		{
			ExceedsCombinedBalanceLimitException exceedsCombinedBal = new ExceedsCombinedBalanceLimitException();
			throw exceedsCombinedBal;
		}
		return savings;
	}

	// add a new savings account if combined balance is less than $250,000
	public SavingsAccount addSavingsAccount( SavingsAccount savingsAccount ) throws ExceedsCombinedBalanceLimitException
	{

		if( ( getSavingsBalance() + getCheckingBalance() + savingsAccount.getBalance() ) < 250000 )
		{
			this.savingsAccList.add( savingsAccount );

			return savingsAccount; // returning savingsAccount as the return type expected is object.
		}
		else
		{
			ExceedsCombinedBalanceLimitException exceedsCombinedBal = new ExceedsCombinedBalanceLimitException();
			throw exceedsCombinedBal;
		}
	}

	public SavingsAccount[] getSavingsAccounts()
	{
		SavingsAccount[] savings = savingsAccList.toArray( new SavingsAccount[0] ); // converting to array since, return
																					// type, an array is expected.
		return savings;
	}

	// calculates the length of the savings account array, getSavingsAccounts() AND
	// returns the array of savings account when invoked
	public int getNumberOfSavingsAccounts()
	{
		int numberOfSavingsAccount = ( getSavingsAccounts() ).length;
		return numberOfSavingsAccount;
	}

	public double getSavingsBalance()
	{
		SavingsAccount[] savingArr = getSavingsAccounts();
		double savingsTotal = 0;
		for( int i = 0; i < savingArr.length; i++ )
		{
			savingsTotal += ( savingArr[i].getBalance() );
		}
		return savingsTotal;
	}

	public CDAccount addCDAccount( CDOffering offering, double openingBalance )
			throws ExceedsFraudSuspicionLimitException
	{

		CDAccount cd = new CDAccount( offering, openingBalance );
		this.cdAccList.add( cd );
		return cd;
	}

	public CDAccount addCDAccount( CDAccount cdAccount )
	{
		this.cdAccList.add( cdAccount );
		return cdAccount;
	}

	public CDAccount[] getCDAccounts()
	{
		CDAccount[] cd = cdAccList.toArray( new CDAccount[0] );
		return cd;
	}

	public int getNumberOfCDAccounts()
	{
		int numberOfCDAccounts = cdAccList.size();
		return numberOfCDAccounts;
	}

	public double getCDBalance()
	{
		double cdTotal = 0;
		CDAccount[] cdArr = getCDAccounts();
		for( int i = 0; i < cdArr.length; i++ )
		{
			cdTotal += cdArr[i].getBalance();
		}
		System.out.println( "cd:" + cdTotal );
		return cdTotal;
	}

	public double getCombinedBalance()
	{
		return ( getSavingsBalance() + getCheckingBalance() + getCDBalance() );
	}

	public int compareTo( AccountHolder ac )
	{
		if( this.getCombinedBalance() > ac.getCombinedBalance() )
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}

	public static AccountHolder readFromString( String accountHolderData ) throws java.lang.Exception
	{
		StringTokenizer tokenizer = new StringTokenizer( accountHolderData, "," );

		String firstName = tokenizer.nextToken();
		String middleName = "";
		if( tokenizer.countTokens() == 4 )
		{
			middleName = tokenizer.nextToken();
		}
		String lastName = tokenizer.nextToken();
		String ssn = tokenizer.nextToken();

		AccountHolder account = new AccountHolder( firstName, middleName, lastName, ssn );
		return account;
	}

	public String writeToString()
	{
		String acString = getFirstName() + "," + getMiddleName() + "," + getLastName() + "," + getSSN();
		return acString;
	}

	private static int nextId = 0;
	private int id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String ssn;

	// ArrayList is created for storing (1) all the savings account,(2) all the
	// checking account,(3)all CDA account of an account holder
	private ArrayList< SavingsAccount > savingsAccList = new ArrayList< SavingsAccount >();
	private ArrayList< CheckingAccount > checkingAccList = new ArrayList< CheckingAccount >();
	private ArrayList< CDAccount > cdAccList = new ArrayList< CDAccount >();
}
