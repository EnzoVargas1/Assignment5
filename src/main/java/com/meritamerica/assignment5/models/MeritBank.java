package com.meritamerica.assignment5.models;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import com.meritamerica.assignment5.exceptions.NoSuchResourceFoundException;

public class MeritBank
{
	public static void addAccountHolder( AccountHolder accountHolder )
	{
		accHolderList.add( accountHolder );
	}

	public static AccountHolder[] getAccountHolders()
	{
		AccountHolder[] accHolderArr = accHolderList.toArray( new AccountHolder[0] );
		return accHolderArr;
	}

	public static CDOffering[] getCDOfferings()
	{
		return offerings;
	}

	public static CDOffering getBestCDOffering( double depositAmount )
	{
		CDOffering bestCDOffer = offerings[0];
		for( int i = 1; i < offerings.length; i++ )
		{
			double futureVal = futureValue( depositAmount, offerings[i].getInterestRate(), offerings[i].getTerm() );
			double bestFutureVal = futureValue( depositAmount, bestCDOffer.getInterestRate(), bestCDOffer.getTerm() );
			if( futureVal > bestFutureVal )
			{
				bestFutureVal = futureVal;
				bestCDOffer = offerings[i];
			}
		}
		return bestCDOffer;
	}

	public static CDOffering getSecondBestCDOffering( double depositAmount )
	{
		CDOffering secondBestOffer = null;
		for( int i = 1; i < offerings.length; i++ )
		{
			for( int j = i + 1; j < offerings.length; j++ )
			{
				double bestFutureVal = futureValue( depositAmount, offerings[i].getInterestRate(),
						offerings[i].getTerm() );
				double futureVal = futureValue( depositAmount, offerings[j].getInterestRate(), offerings[j].getTerm() );

				CDOffering[] temp = new CDOffering[1];// a temporary array temp of length 1 to help in sorting
				if( futureVal > bestFutureVal )
				{
					temp[0] = offerings[i];
					offerings[i] = offerings[j];
					offerings[j] = temp[0];
				}
			}
		}
		secondBestOffer = offerings[1];
		return secondBestOffer;
	}

	public static void clearCDOfferings()
	{
		offerings = null;
	}

	public static void setCDOfferings( CDOffering[] offerings )
	{
		MeritBank.offerings = offerings; // cannot access static variable with 'this' keyword
	}

	public static long getNextAccountNumber()
	{
		return nextAccNumber;
	}

	public static double totalBalances()
	{
		AccountHolder[] accountHolderArr = getAccountHolders();
		double totalBalance = 0;
		for( int i = 0; i < accountHolderArr.length; i++ )
		{
			totalBalance += accountHolderArr[i].getSavingsBalance() + accountHolderArr[i].getCheckingBalance()
					+ accountHolderArr[i].getCDBalance();
		}
		return totalBalance;
	}

	public static double futureValue( double presentValue, double interestRate, int term )
	{
		double futureVal = recursiveFutureValue( presentValue, term, interestRate );
		return futureVal;
	}

	public static void setNextAccountNumber( long nextAccountNumber )
	{
		nextAccNumber = nextAccountNumber;
	}

	/*
	 * public static boolean readFromFile(String fileName) { try { BufferedReader rd
	 * = new BufferedReader(new FileReader(fileName)); String line = rd.readLine();
	 * //reads the first line setNextAccountNumber(Long.parseLong(line)); line =
	 * rd.readLine(); //reads the 2nd line int cdLength = Integer.parseInt(line);
	 * CDOffering[] cd = new CDOffering[cdLength]; //makes an array for CDOffering
	 * for(int i=0; i<cdLength;i++) { //length of array was read from 2nd line
	 * line=rd.readLine(); cd[i]=CDOffering.readFromString(line); //stores the
	 * CDOffering with (term and rate) in each index } setCDOfferings(cd); //
	 * requirement:"when reading from file, the data should overwrite the MeritBank data such that previous data no longer exists, only the data read from the file should exist"
	 * line=rd.readLine(); int accHolderLength=Integer.parseInt(line); accHolderList
	 * = new ArrayList<AccountHolder>();//the accHolderList now points to the newly
	 * created arrayList of accounts which is read from the file.//automatically the
	 * pointer has now changed from previous lists to the new arrayList.
	 * 
	 * for (int i = 0; i < accHolderLength; i++) { line = rd.readLine();
	 * AccountHolder a = AccountHolder.readFromString(line); accHolderList.add(a);
	 * line = rd.readLine(); ArrayList<String> unprocessedTransactions = new
	 * ArrayList<String>(); int numOfChecking = Integer.parseInt(line);
	 * 
	 * for (int j = 0; j < numOfChecking; j++) { line = rd.readLine();
	 * CheckingAccount ch =CheckingAccount.readFromString(line);
	 * a.addCheckingAccount(ch); line=rd.readLine(); int numOfTransactions =
	 * Integer.parseInt(line);
	 * 
	 * for(int k=0;k<numOfTransactions;k++){ line=rd.readLine();
	 * unprocessedTransactions.add(line); } }
	 * 
	 * line = rd.readLine(); int numOfSavings = Integer.parseInt(line);
	 * 
	 * for (int j = 0; j < numOfSavings; j++) { line = rd.readLine(); SavingsAccount
	 * sv = SavingsAccount.readFromString(line); a.addSavingsAccount(sv);
	 * line=rd.readLine(); int numOfTransactions = Integer.parseInt(line);
	 * 
	 * for(int k=0;k<numOfTransactions;k++){ line=rd.readLine();
	 * unprocessedTransactions.add(line); } }
	 * 
	 * line = rd.readLine(); int numOfCD = Integer.parseInt(line); if (numOfCD != 0)
	 * { for (int j = 0; j < numOfCD; j++) { line = rd.readLine(); CDAccount cdAcc =
	 * CDAccount.readFromString(line); a.addCDAccount(cdAcc); line = rd.readLine();
	 * int numOfTransactions = Integer.parseInt(line);
	 * 
	 * for (int k = 0; k < numOfTransactions; k++) { line = rd.readLine();
	 * unprocessedTransactions.add(line); } }
	 * 
	 * } }
	 * 
	 * }
	 */
	public static boolean writeToFile( String fileName )
	{
		try
		{
			PrintWriter wr = new PrintWriter( new FileWriter( fileName ) );
			// wr.print("");
			wr.println( nextAccNumber );
			wr.println( offerings.length );
			for( int i = 0; i < offerings.length; i++ )
			{
				wr.println( offerings[i].writeToString() );
			}
			wr.println( accHolderList.size() );
			for( int i = 0; i < accHolderList.size(); i++ )
			{
				AccountHolder accInfo = accHolderList.get( i );
				wr.println( accInfo.writeToString() );
				int numOfCheckings = accInfo.getNumberOfCheckingAccounts();

				wr.println( numOfCheckings );
				if( numOfCheckings != 0 )
				{
					CheckingAccount[] checking = accInfo.getCheckingAccounts();
					for( int j = 0; j < numOfCheckings; j++ )
					{
						wr.println( checking[j].writeToString() );

					}
				}
				int numOfSavings = accInfo.getNumberOfSavingsAccounts();
				wr.println( numOfSavings );
				if( numOfSavings != 0 )
				{
					SavingsAccount[] savings = accInfo.getSavingsAccounts();
					for( int j = 0; j < savings.length; j++ )
					{
						wr.println( savings[j].writeToString() );
					}
				}
				int numOfCD = accInfo.getNumberOfCDAccounts();
				wr.println( numOfCD );
				if( numOfCD != 0 )
				{
					CDAccount[] cd = accInfo.getCDAccounts();
					for( int j = 0; j < cd.length; j++ )
					{
						wr.println( cd[j].writeToString() );
					}
				}
			}
			wr.close();
			return true;
		}
		catch (IOException e)
		{
			return false;
		}
	}

	public static AccountHolder[] sortAccountHolders()
	{
		Collections.sort( accHolderList ); // Collections.sort(list) returns a void,and cannot be stored to any
											// variable,the list itself is sorted
		return accHolderList.toArray( new AccountHolder[0] ); // then to convert to array, separately use, list.toArray,
																// *Collections.sort(list).toArray()* will not work,
																// it's an error.
	}

	public static double recursiveFutureValue( double amount, int years, double interestRate )
	{
		double futureVal;
		futureVal = amount * power( amount, years, interestRate );
		return futureVal;
	}

	public static double power( double amount, int years, double interestRate )
	{
		double recurTerm;
		if( years == 0 )
		{
			return 1;
		}
		else
		{
			recurTerm = ( 1 + interestRate ) * power( amount, years - 1, interestRate );
			return ( recurTerm );
		}
	}

	public static BankAccount getBankAccount( long accountId )
	{
		for( int i = 0; i < accHolderList.size(); i++ )
		{
			AccountHolder accInfo = accHolderList.get( i );
			int numChecking = accInfo.getNumberOfCheckingAccounts();
			int numSavings = accInfo.getNumberOfSavingsAccounts();
			int numCD = accInfo.getNumberOfCDAccounts();

			CheckingAccount[] chkAccts = accInfo.getCheckingAccounts();
			for( int j = 0; j < numChecking; j++ )
			{
				if( chkAccts[j].getAccountNumber() == accountId )
				{
					return chkAccts[j];
				}
			}

			SavingsAccount[] savAccts = accInfo.getSavingsAccounts();
			for( int j = 0; j < numSavings; j++ )
			{
				if( savAccts[j].getAccountNumber() == accountId )
				{
					return savAccts[j];
				}
			}

			CDAccount[] cdAccts = accInfo.getCDAccounts();
			for( int j = 0; j < numCD; j++ )
			{
				if( cdAccts[j].getAccountNumber() == accountId )
				{
					return cdAccts[j];
				}
			}
		}
		return null;
	}

	public static CDOffering getCDOfferingById( int id ) throws NoSuchResourceFoundException
	{
		for( CDOffering cdo : MeritBank.getCDOfferings() )
			if( cdo.getId() == id ) return cdo;

		throw new NoSuchResourceFoundException( "No Such Resource Found" );
	}

	private static ArrayList< AccountHolder > accHolderList = new ArrayList< AccountHolder >();
	private static long nextAccNumber;
	private static CDOffering[] offerings;

}
