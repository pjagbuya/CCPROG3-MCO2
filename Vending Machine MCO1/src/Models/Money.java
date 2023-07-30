package Models;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/** This class represents a vending machine's cash reserves.
  * Contains methods and attributes for working with the "cash."
  * This program's denomination set is based
  * on the current series of Philippine coins and banknotes.
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  */
public class Money {
	/**
	 * Creates Money object and initializes all denominations to zero pieces.
	 * Also intializes strToVal and valToStr.
	 *
	 */
	public Money() {
		denominations = new LinkedHashMap<String, ArrayList<DenominationItem>>();
		
		denominations.put( "One Thousand Bill" , new ArrayList<DenominationItem>() );
		denominations.put( "Five Hundred Bill" , new ArrayList<DenominationItem>() );
		denominations.put( "Two Hundred Bill"  , new ArrayList<DenominationItem>() );
		denominations.put( "One Hundred Bill"  , new ArrayList<DenominationItem>() );
		denominations.put( "Fifty Bill"        , new ArrayList<DenominationItem>() );
		denominations.put( "Twenty Bill"       , new ArrayList<DenominationItem>() );
		
		denominations.put( "Twenty Coin"       , new ArrayList<DenominationItem>() );
		denominations.put( "Ten Coin"          , new ArrayList<DenominationItem>() );
		denominations.put( "Five Coin"         , new ArrayList<DenominationItem>() );
		denominations.put( "One Coin"          , new ArrayList<DenominationItem>() );
		denominations.put( "Twenty Five Cents" , new ArrayList<DenominationItem>() );
		denominations.put( "Five Cents"        , new ArrayList<DenominationItem>() );
		denominations.put( "One Cent"          , new ArrayList<DenominationItem>() );
		
		
		strToVal = new LinkedHashMap<String, Double>();
		strToVal.put( "One Thousand Bill" , 1000.0 );
		strToVal.put( "Five Hundred Bill" , 500.0  );
		strToVal.put( "Two Hundred Bill"  , 200.0  );
		strToVal.put( "One Hundred Bill"  , 100.0  );
		strToVal.put( "Fifty Bill"        , 50.0   );
		strToVal.put( "Twenty Bill"       , 20.0   );
		
		strToVal.put( "Twenty Coin"       , 20.0   );
		strToVal.put( "Ten Coin"          , 10.0   );
		strToVal.put( "Five Coin"         , 5.0    );
		strToVal.put( "One Coin"          , 1.0    );
		strToVal.put( "Twenty Five Cents" , 0.25   );
		strToVal.put( "Five Cents"        , 0.05   );
		strToVal.put( "One Cent"          , 0.01   );

	}
	
	
	public void add(DenominationItem denom)
	{
		if( denom != null && denominations.get( denom.getName() ) != null )
			denominations.get( denom.getName() ).add( denom );
	}
	
	
	public DenominationItem subtract(String denom)
	{
		DenominationItem dispensedDenom = null;
		if( denom != null &&
			denominations.get( denom ) != null &&
			denominations.get( denom ).size() > 0 )
			dispensedDenom = denominations.get( denom ).remove(0);
		return dispensedDenom;
	}
	
	
	/**
	 * Computes for the total value of all cash reserves
	 * 
	 * @return the current total of all cash reserves
	 **/
	public double getTotalMoney() 
	{
		double total = 0.0;
		ArrayList<DenominationItem> cashTube;
		for( String s : denominations.keySet() )
		{
			cashTube = denominations.get(s);

			for(DenominationItem item: cashTube)
				total += strToVal.get(s);
		}
		return total;
	}
	
	
	/**
	 * Gets cash reserves
	 *
	 * @return the current set of cash reserves by denomination
	 **/
	public LinkedHashMap<String, ArrayList<DenominationItem>> getDenominations() {
		return denominations;
	}
	
	
	/**
	 * Gets strToVal, for converting from a denomination's
	 * String representation to its double representation
	 *
	 * @return the strToVal hashmap of the Money class
	 **/
	public static LinkedHashMap<String, Double> getStrToVal() {
		return strToVal;
	}
	

	/** represents cash reserves, inluding the currently stored number of pieces of each denomination */
	private LinkedHashMap<String, ArrayList<DenominationItem>> denominations;
	/** class hashmap for converting from the String to the double representation of a denomination */
	private static LinkedHashMap<String, Double> strToVal;
	/** class hashmap for converting from the double to the String representation of a denomination */
	private static LinkedHashMap<Double, String> valToStr;
}