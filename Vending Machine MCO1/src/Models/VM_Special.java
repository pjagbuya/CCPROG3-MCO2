package Models;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Map;

import ItemSelectLib.PresetItem;

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
					  int item_max,
					  Money change)
	{
		super(name, nOfSlots, item_max, change);
		
		specialSlots = new VM_Slot[7]; // 7 different food items cannot be sold on their own
		
		if(item_max < 10)
			item_max = super.getMaxITEMS();
			
		for (int i = 0; i < 7; i++)
			specialSlots[i] = new VM_SpecialSlot(item_max);
		
		for(PresetItem item : PresetItem.values())
		{
			possibleItems.put(item.name(), item.getIsIndependent());
		}
		setSpecialOperator(
			new SpecialSellingOperator(
				getSlots(),
				getCurrentMoney(),
				getOrderHistory(),
				getChange(),
				specialSlots));
				

	}
	
	
	
	public void setSpecialOperator(SpecialSellingOperator specialSellingOperator)
	{
		super.setOperator( specialSellingOperator );
	}
	
	
	


	
	
	
	/**
	 * Adds more of a certain item to the correct slot specified by index
	 *
	 * @param givenItem the item to be added to the specified slot
	 * @param qty the number indicating how many pieces
				  of the specified item should be added to the specified slot
	 * @param i the index of the specified slot in the slots array
	 */

	public void addItemStock(VM_Item givenItem, int i)
	{
		if( possibleItems.get( givenItem.getItemName().toUpperCase() ) != null && 
			possibleItems.get( givenItem.getItemName().toUpperCase() ) == 1 )
			/* UNSURE abt this */ ;
		else if( possibleItems.get( givenItem.getItemName().toUpperCase() ) != null )
			specialSlots[i].addItemStock( givenItem );
	}
	
	
	

	
	
	private LinkedHashMap<String, Integer> possibleItems;
	public VM_Slot[] getSpecialSlots() { return specialSlots; }
	
	public RecipeChecker getRecipeChecker() { return recipeChecker; }
	
	public void setRecipeChecker(RecipeChecker recipeChecker) { this.recipeChecker = recipeChecker; }
	
	
	private RecipeChecker recipeChecker;
	
	
	
	
	/** slots for items that cannot be sold on their own */
	private VM_Slot[] specialSlots;
}