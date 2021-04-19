package com.meritamerica.assignment5.models;

public class CDOffering
{
	private static int nextId = 0;
	private int id;
	private int term;
	private double interestRate;

	public CDOffering( int term, double interestRate )
	{

		this.term = term;
		this.interestRate = interestRate;
		this.id = ++nextId;
	}

	public int getId()
	{
		return id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public int getTerm()
	{

		return this.term;
	}

	public double getInterestRate()
	{
		return this.interestRate;
	}

	public static CDOffering readFromString( String cdOfferingDataString ) throws java.lang.NumberFormatException
	{
		// expecting like this: 1,0.018
		CDOffering cd = null;

		if( cdOfferingDataString.indexOf( ',' ) != -1 )
		{ // if there's no ',' in the string, the string is considered as
			// NumberFormatException
			int term = Integer.parseInt( cdOfferingDataString.substring( 0, cdOfferingDataString.indexOf( ',' ) ) );
			double rate = Double
					.parseDouble( cdOfferingDataString.substring( cdOfferingDataString.indexOf( ',' ) + 1 ) );
			cd = new CDOffering( term, rate );
		}
		else
		{
			throw new NumberFormatException();
		}

		return cd;
	}

	public String writeToString()
	{
		String cdString = this.getTerm() + "," + this.getInterestRate();
		return cdString;
	}

}
