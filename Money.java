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
		denominations = new LinkedHashMap<String, ArrayList<Denomination>>;
		
		denominations.put( "One Thousand Bill" , new ArrayList<Denomination>() );
		denominations.put( "Five Hundred Bill" , new ArrayList<Denomination>() );
		denominations.put( "Two Hundred Bill"  , new ArrayList<Denomination>() );
		denominations.put( "One Hundred Bill"  , new ArrayList<Denomination>() );
		denominations.put( "Fifty Bill"        , new ArrayList<Denomination>() );
		denominations.put( "Twenty Bill"       , new ArrayList<Denomination>() );
		
		denominations.put( "Twenty Coin"       , new ArrayList<Denomination>() );
		denominations.put( "Ten Coin"          , new ArrayList<Denomination>() );
		denominations.put( "Five Coin"         , new ArrayList<Denomination>() );
		denominations.put( "One Coin"          , new ArrayList<Denomination>() );
		denominations.put( "Twenty Five Cents" , new ArrayList<Denomination>() );
		denominations.put( "Five Cents"        , new ArrayList<Denomination>() );
		denominations.put( "One Cent"          , new ArrayList<Denomination>() );
		
		
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
		
		/* SHOULD BE REMOVED BECAUSE IT IS NOT A FUNCTION
		valToStr = new LinkedHashMap<Double, String>();
		valToStr.put(1000.0, "One Thousand Bill");
		valToStr.put(500.0, "Five Hundred Bill");
		valToStr.put(200.0, "Two Hundred Bill");
		valToStr.put(100.0, "One Hundred Bill");
		valToStr.put(50.0, "Fifty Bill");
		valToStr.put(20.0, "Twenty Bill");

		valToStr.put(20.0, "Twenty Coin");
		valToStr.put(10.0, "Ten Coin");
		valToStr.put(5.0, "Five Coin");
		valToStr.put(1.0, "One Coin");
		valToStr.put(0.25, "Twenty Five Cents");
		valToStr.put(0.05, "Five Cents");
		valToStr.put(0.01, "One Cent");
		*/
	}
	
	
	public void add(Denomination denom)
	{
		if( denom != null && denominations.get( denom.getName() ) != null )
			denominations.get( denom.getName() ).add( denom );
	}
	
	
	public Denomination subtract(String denom)
	{
		Denomination dispensedDenom = null;
		if( denom != null &&
			denominations.get( denom.getName() ) != null &&
			denominations.get( denom.getName() ).size() > 0 )
			dispensedDenom = denominations.get( denom.getName() ).remove(0);
		return dispensedDenom;
	}
	
	
	/**
	 * Computes for the total value of all cash reserves
	 * 
	 * @return the current total of all cash reserves
	 **/
	public double getTotalMoney() {
		int i;
		int cashTubeSize;
		double total = 0.0;
		ArrayList<Denomination> cashTube;
		for( String s : denominations.keySet() )
		{
			cashTube = denominations.get(s);
			cashTubeSize = denominations.get(s).size();
			for(i = 0; i < cashTubeSize; i++)
				total += strToVal.get(s) * cashTube.get(i);
		}
		return total;
	}
	
	
	/**
	 * Gets cash reserves
	 *
	 * @return the current set of cash reserves by denomination
	 **/
	public LinkedHashMap<String, ArrayList<Denomination>> getDenominations() {
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
	
	/**
	 * Gets valToStr, converting from a denomination's
	 * double representation to its String representation
	 *
	 * @return the valToStr hashmap of the Money class
	 **/
	/*
	public static LinkedHashMap<Double, String> getValToStr() {
		return valToStr;
	}
	*/
	
	/** represents cash reserves, inluding the currently stored number of pieces of each denomination */
	private LinkedHashMap<String, ArrayList<Denomination>> denominations;
	/** class hashmap for converting from the String to the double representation of a denomination */
	private static LinkedHashMap<String, Double> strToVal;
	/** class hashmap for converting from the double to the String representation of a denomination */
	private static LinkedHashMap<Double, String> valToStr;
}