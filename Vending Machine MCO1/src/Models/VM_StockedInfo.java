package Models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.LinkedHashMap;


/**
 * The class VM_StockedInfo represents inventory records of a vending machine.
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public class VM_StockedInfo
{
	/**
 	 * Instanstiates an inventory record using the vending's components.
   	 *
     	 * @param slots the regular slots of the vending machine
       	 * @param specialSlots the special slots of the vending machine
	 * @param cashReserves the cash reserves of the vending machine
       	 *
     	 */
	public VM_StockedInfo(
		VM_Slot[] slots,
		VM_Slot[] specialSlots,
		Money cashReserves )
	{
		int i;
		itemNames = new ArrayList<String>();
		itemPrices = new LinkedList<Double>();
		itemStocks = new LinkedList<Integer>();
		moneyRecord = new LinkedHashMap<String, Integer>();
		
		/* Records Regular Slots. */
		for(i = 0; i < slots.length; i++)
			if( slots[i].getSlotItemName() != null )
			{
				itemNames.add( new String( slots[i].getSlotItemName() ) );
				itemPrices.add( slots[i].getPrice() );
				itemStocks.add( slots[i].getSlotItemStock() );
			}
			
		/* Records Special Slots, if any. */
		if( specialSlots != null )
		for(i = 0; i < specialSlots.length; i++)
			if( specialSlots[i].getSlotItemName() != null )
			{
				itemNames.add( specialSlots[i].getSlotItemName() );
				itemPrices.add( specialSlots[i].getPrice() );
				itemStocks.add( specialSlots[i].getSlotItemStock() );
			}
		
		/* Records cash reserves. */
		for( String k : cashReserves.getDenominations().keySet() )
			moneyRecord.put( k , cashReserves.getDenominations().get(k).size() );
	}
	


	/**
 	 * Returns the names of item stocks at the time of recording.
   	 *
     	 * @return the names of item stocks at the time of recording
       	 */
	public ArrayList<String> getNames() { return itemNames; }

	/**
 	 * Returns the prices of item stocks at the time of recording.
   	 *
     	 * @return the prices of item stocks at the time of recording
       	 */
	public LinkedList<Double> getPrices() { return itemPrices; }

	/**
 	 * Returns the stock counts of item stocks at the time of recording.
   	 *
     	 * @return the stock counts of item stocks at the time of recording
       	 */
	public LinkedList<Integer> getStocks() { return itemStocks; }

	/**
 	 * Returns the record of cash reserve denominations at the time of recording.
   	 *
     	 * @return the record of cash reserve denominations at the time of recording
       	 */
	public LinkedHashMap<String, Integer> getMoneyRecord() { return moneyRecord; }
	
	
	/** the lists of item names at the time of recording */
	private ArrayList<String> itemNames;
	/** the lists of item prices at the time of recording */
	private LinkedList<Double> itemPrices;
	/** the lists of item stock counts at the time of recording */
   	private LinkedList<Integer> itemStocks;
    	/** the record of the vending machine's cash reserves at the time of recording */
    	private LinkedHashMap<String, Integer> moneyRecord;
}
