package Models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.LinkedHashMap;


/**
 * The class VM_StockedInfo represents inventory records of VM_Regular,
 * which in this design means storing copies of VM_Slot and
 * their corresponding stock counts
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 * @version 1.0
 */
public class VM_StockedInfo
{
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
	
	
	public ArrayList<String> getNames() { return itemNames; }
	public LinkedList<Double> getPrices() { return itemPrices; }
	public LinkedList<Integer> getStocks() { return itemStocks; }
	public LinkedHashMap<String, Integer> getMoneyRecord() { return moneyRecord; }
	
	
	/** the lists of item names, their prices, and their corresponding stock counts at the time of recording */
	private ArrayList<String> itemNames;
	private LinkedList<Double> itemPrices;
    private LinkedList<Integer> itemStocks;
    /** the record of the VM's cash reserves at the time of recording */
    private LinkedHashMap<String, Integer> moneyRecord;
}
