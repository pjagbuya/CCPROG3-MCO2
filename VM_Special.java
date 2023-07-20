import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Map;
import java.text.DecimalFormat;


/** This class represents a Special Vending Machine
  * and its methods and attributes
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  */
public class VM_Special extends VM_Regular
{
	public VM_Special(String name, 
					  int nOfSlots, 
					  int item_max)
	{
		super(name, nOfSlots, item_max);
		
		specialSlots = new VM_Slot[7]; // 7 different food items cannot be sold on their own
		
		for (int i = 0; i < 7; i++)
		{
			if(item_max >= 10)
				specialSlots[i] = new VM_Slot(item_max);
			else
				specialSlots[i] = new VM_Slot( super.getMaxITEMS() );
		}
	}
	
	
	
	/**
	 * Adds more of a certain item to the correct slot specified by index
	 *
	 * @param givenItem the item to be added to the specified slot
	 * @param qty the number indicating how many pieces
				  of the specified item should be added to the specified slot
	 * @param i the index of the specified slot in the slots array
	 */
	@Override
	public void addItemStock(VM_Item givenItem, 
							 int qty, 
							 int i)
	{
		if( Main.getPossibleItems().get( givenItem.getItemName().toUpperCase() ) != null &&  Main.getPossibleItems().get( givenItem.getItemName().toUpperCase() ) == 1 )
			super.addItemStock(givenItem, qty, i);
		else if( Main.getPossibleItems().get( givenItem.getItemName().toUpperCase() ) != null )
			specialSlots[i].addItemStock(givenItem, qty);
	}
	
	
	
	
	
	public VM_Slot[] getSpecialSlots() { return specialSlots; }
	
	/** slots for items that cannot be sold on their own */
	private VM_Slot[] specialSlots;
}