package Models;

/**
 * The class DenominationItem represents
 * coins and paper bills.
 * This program's denomination set is based
 * on the current series of Philippine coins and banknotes.
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public class DenominationItem
{
	/** Creates a new instance of DenominationItem based on a name and money value.
	 *
	 * @param name the type and value of the denomination in word format
	 * @param value the numerical value of the denomination
	 */
	public DenominationItem(String name, double value)
	{
		this.value = value;
		this.name = name;
	}
	
	/** 
	 * Returns the numerical value of this denomination.
	 * 
	 * @return the numerical value of this coin/banknote
	 */
	public double getValue()
	{
		return value;
	}
	
	/**
	 * Returns the name of this coin/banknote.
	 *
	 * @return the type and value of the denomination in word format
	 */
	public String getName()
	{
		return name;
	}
	
	/** the numerical value of this denomination */
	private double value;
	/** the type and value of this denomination in word format */
	private String name;
}