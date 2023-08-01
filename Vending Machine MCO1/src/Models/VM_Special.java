package Models;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Map;



import java.text.DecimalFormat;


/** The class VM_Special represents a Special Vending Machine.
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  */
public class VM_Special extends VM_Regular
{
	/**
 	 * Instantiates a Special vending machine and sets its basic characteristics.
   	 *
     	 * @param nOfSlots the number of slots in this vending machine
       	 * @param item_max the maximum number of items per slot
	 * @change the change tray
  	 */
	public VM_Special(String name, 
					  int nOfSlots, 
					  int item_max,
					  Money change)
	{
		super(name, nOfSlots, item_max, change);
		
		specialSlots = new VM_Slot[10];
		
		if(item_max < 10)
			item_max = super.getMaxITEMS();
			
		for (int i = 0; i < 10; i++)
			specialSlots[i] = new VM_Slot(item_max);
		
		
		setSpecialOperator(
			new SpecialSellingOperator(
				getSlots(),
				getCurrentMoney(),
				getOrderHistory(),
				getChange(),
				specialSlots, 
				getCustomItems() ));
		}
	

	/**
 	 * Sets a new Special operator brain in the vending machine.
   	 *
     	 * @param the new Special operator brain
       	 */
	public void setSpecialOperator(SpecialSellingOperator specialSellingOperator)
	{
		super.setOperator( specialSellingOperator );
	}
	
	
	/**
 	 * Returns the special slots of this vending machine.
   	 *
     	 * @return the special slots of this vending machine
   	 */
	public VM_Slot[] getSpecialSlots() { return specialSlots; }


	/**
 	 * Returns the recipe checker of this operator.
   	 *
     	 * @return the recipe checker
     	 */
	public RecipeChecker getRecipeChecker() { return recipeChecker; }

	/**
 	 * Sets the recipe checker of this vending machine.
   	 *
     	 * @param the new recipe checker
     	 */
	public void setRecipeChecker(RecipeChecker recipeChecker) { this.recipeChecker = recipeChecker; }
	
	
	
	
	
	/** the Special operators's recipe-checking component */
	private RecipeChecker recipeChecker;
	/** slots for items that cannot be sold on their own */
	private VM_Slot[] specialSlots;
}
