package Models;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/** This class represents a Special Selling Operator
  * which provides for selling of both regular and special items
  * through the use of text based prompt
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  */
public class SpecialSellingOperator extends SellingOperator
{

	/**
	 * This constructor represent a Selling Operator that manages how
	 * the flow of prompts and how the Vending machine would get affected
	 * by such prompts
	 *
	 * Also sets up the ingredient hashmaps, detailing the stocks of each
	 * 
	 * @param vm the target vending machine for results and effects of this class
	 */
    public SpecialSellingOperator(
		VM_Slot[] slots,
		Money vmCashReserves,
		ArrayList<Order> orderHistory,
		Money change,
		VM_Slot[] specialSlots,
		LinkedHashMap<String, Integer> customItems)
    {	
		super(slots, vmCashReserves, orderHistory, change, customItems);
		this.specialSlots = specialSlots;
		recipeChecker = new RecipeChecker(getSlots(), specialSlots);
    }

    

	public VM_Item createSpecialItem()
	{
		VM_Item specialItem;
		int i;
			
		specialItem =
			new KangkongChips(
			"Kangkong Chips",
			getOrder().getTotalCost() ,
			getOrder().getTotalCalories() );
					
		for(i = 0; i < ingredients.size(); i++)
			((KangkongChips)specialItem).acceptIngredient( ingredients.get(i) );
					
		ingredients.clear();
		
		return specialItem;
	}

	
	
	
	public String validateTransaction()
	{
		String msg = super.validateTransaction();
		
		if( msg.equals("") )
			if( !hasEnoughStock(specialSlots) )
				msg = msg + "ERROR: SPECIAL STOCK INSUFFICIENT.\n";
		else
			msg = msg + "ERROR: REGULAR STOCK INSUFFICIENT.\n";
		
		return msg;
	}
	
	public void proceedTrasaction()
	{
		int i;
		ArrayList<VM_Item> soldItems = dispenseItems( getSlots() );
		for(i = 0; i < soldItems.size(); i++)
			ingredients.add( soldItems.get(i) );
		soldItems.clear();
		soldItems = dispenseItems( specialSlots );
		for(i = 0; i < soldItems.size(); i++)
			ingredients.add( soldItems.get(i) );
		soldItems.clear();
		dispenseItems( getSlots() );
		dispenseItems( specialSlots );
		updateCashTrays();
		addOrderHistory(getOrder());

	}
	
	public String addIngredient(String ingredient, int qty)
	{
		String msg;
		int i;
		VM_Slot[] slots = null;
		boolean ingredientExists = true; // assumed true
		boolean ingredientIsAnotherFlavor = false; // assumed false
		boolean ingredientHasSlot = false; // assumed false
		boolean ingredientHasEnoughStock = false; // assumed false
			
		msg = null;
		if( getPresetItems().get( ingredient.toUpperCase() ) == null &&
			getCustomItems().get( ingredient.toUpperCase() ) == null ) {
			msg = new String("ERROR: ITEM DOES NOT EXIST IN UNIVERSE.\n");
			ingredientExists = false;
		}
		
		/* Prevents user from adding other flavors */
		if( ingredientExists &&
			getOrder().getPendingOrder().get( ingredient.toUpperCase() ) == null &&
			recipeChecker.getReversedFlavors().get( ingredient.toUpperCase() ) != null )
		{
			msg = new String("ERROR: MULTIPLE FLAVORS NOT ALLOWED.\n");
			ingredientIsAnotherFlavor = true;
		}
			
		/* Checks whether a slot holds enough of this item. */
		if( ingredientExists && !ingredientIsAnotherFlavor ) {
			if( getPresetItems().get( ingredient.toUpperCase() ) == 1 )
				slots = getSlots();
			else if( getPresetItems().get( ingredient.toUpperCase() ) == 0 )
				slots = specialSlots;
			else if( getCustomItems().get( ingredient.toUpperCase() ) != null )
				slots = getSlots();
			for(i = 0; i < slots.length; i++)
				if( slots[i].getSlotItemName() != null &&
					slots[i].getSlotItemName().equalsIgnoreCase( ingredient ) )
				{
					ingredientHasSlot = true;
					if( qty <= slots[i].getSlotItemStock() )
						ingredientHasEnoughStock = true;
				}
		}
		
		if( !ingredientHasSlot )
			msg = new String("ERROR: NO SLOT HAS THIS .\n");
		if( ingredientHasSlot && !ingredientHasEnoughStock )
			msg = new String("ERROR: NOT ENOUGH STOCK OF INGREDIENT.\n");
		
		/* Decision */
		if( ingredientExists &&
			!ingredientIsAnotherFlavor &&
			ingredientHasSlot &&
			ingredientHasEnoughStock ) {
			for(i = 0; i < slots.length; i++)
				if( slots[i].getSlotItemName() != null &&
					slots[i].getSlotItemName().equalsIgnoreCase( ingredient ) )
					addToPendingMap( getOrder().getPendingOrder(), slots[i], qty);
		}

		return msg;
	}
	
	public void addAbsoluteBaseIngredients(Order order)
	{
		VM_Slot[] slots = null;
		int i;
		
		/* Add absolute base ingredients to order. */
		for( int k : recipeChecker.getAbsoluteBaseIngredients().keySet() )
		{
			if( getPresetItems().get( recipeChecker.getAbsoluteBaseIngredients().get(k).toUpperCase() ) == 1 )
				slots = getSlots();
			else if( getPresetItems().get( recipeChecker.getAbsoluteBaseIngredients().get(k).toUpperCase() ) == 0 )
				slots = specialSlots;
			else if( getCustomItems().get( recipeChecker.getAbsoluteBaseIngredients().get(k).toUpperCase() ) != null )
				slots = getSlots();
			for(i = 0; i < slots.length; i++)
				if( slots[i].getSlotItemName() != null &&
					slots[i].getSlotItemName().equalsIgnoreCase( recipeChecker.getAbsoluteBaseIngredients().get(k) ) ) {
					order.getPendingOrder().put(
						recipeChecker.getAbsoluteBaseIngredients().get(k) ,
						recipeChecker.getRequiredStock().get(k) );
				}
		}
	}
	
	
	public String chooseFlavor(int chosenFlavor)
	{
		String msg = null;
		int i;
		VM_Slot[] slots = null;
		LinkedHashMap<Integer, String> flavors = recipeChecker.getFlavors();
		LinkedHashMap<Integer, Integer> flavorStock = recipeChecker.getFlavorStock();
		
		// only when selected slot num is within range, this will trigger to add that order
		if( flavors.get(chosenFlavor) != null &&
			flavorStock.get(chosenFlavor) > 0 )
			msg = null;
		else
			msg = new String("ERROR: FLAVOR NOT AVAILABLE.\n");
		
		/* adds flavor to Order, if it is not PLAIN */
		if( msg == null && !flavors.get(chosenFlavor).equalsIgnoreCase("PLAIN") )
		{
			if( getPresetItems().get( flavors.get(chosenFlavor) ) == 1 )
				slots = getSlots();
			else if( getPresetItems().get( flavors.get(chosenFlavor) ) == 0 )
				slots = specialSlots;
			else if( getCustomItems().get( flavors.get(chosenFlavor) ) != null )
				slots = getSlots();
			for(i = 0; i < slots.length; i++)
				if( slots[i].getSlotItemName() != null &&
					slots[i].getSlotItemName().equalsIgnoreCase( flavors.get(chosenFlavor) ) )
					addToPendingMap( getOrder().getPendingOrder() , slots[i] , 1 );
		}
		return msg;
	}
	
	
	public void renewRecipeChecker()
	{
		recipeChecker = new RecipeChecker( getSlots() , specialSlots );
	}
	
	
	ArrayList<VM_Item> ingredients;
	RecipeChecker recipeChecker;
	VM_Slot[] specialSlots;
	
}