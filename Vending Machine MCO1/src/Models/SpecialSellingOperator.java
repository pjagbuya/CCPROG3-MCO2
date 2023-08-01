package Models;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The class SpecialSellingOperator represents a vending machine brain
 * that can sell both regular and special items.
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public class SpecialSellingOperator extends SellingOperator
{
	/**
	 * Instantiates a brain for Special vending machines.
	 *
	 * @param slots the regular slots of the parent vending machine
	 * @param vmCashReserves the cash reserves of the parent vending machine
	 * @param orderHistory the list of (successful) transactions from the parent vending machine
	 * @param change the change tray
	 * @param specialSlots the special slots of the parent vending machine
	 * @param customItems the list of custom items from the parent vending machine
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


	/**
	 * Actually forms the Special Item.
	 *
	 * @return the special item
	 */
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

		getSoldItems().add( specialItem );
	
		return specialItem;
	}



	/**
	 * Checks whether the Special item transaction is valid.
	 *
	 * @return null if transaction is valid, error message otherwise
	 */
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


	/**
	 * Tells vending machine to proceed with the trasaction.
	 */
	public void proceedTrasaction()
	{
		int i;
		ArrayList<VM_Item> soldItems = dispenseItems( getSlots() );
		
		for(i = 0; i < soldItems.size(); i++) {
			ingredients.add( soldItems.get(i) );
		}
		
		soldItems.clear();
		soldItems = dispenseItems( specialSlots );
		
		for(i = 0; i < soldItems.size(); i++) {
			ingredients.add( soldItems.get(i) );
		}
		
		soldItems.clear();
		dispenseItems( getSlots() );
		dispenseItems( specialSlots );
		updateCashTrays();
		addOrderHistory(getOrder());
	
	}

	/**
	 * Adds an ingredient to the order for the special item.
	 *
	 * @param ingredient the name of the ingredient to be added
	 * @param qty the amount of the ingredient to be added
	 * @return null if ingredient was successfully added, error message otherwise
	 */
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


	/**
	 * Tells the vendine machine to add the required base ingredients to the order for the special item.
	 */
	public void addAbsoluteBaseIngredients()
	{
		VM_Slot[] slots = null;
		int i;

		/* Add absolute base ingredients to order. */
		for( int k : recipeChecker.getAbsoluteBaseIngredients().keySet() )
		{
			/* choosing the appropricate slot set */
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

	/**
	 * Allows customer to choose a flavor for the special item.
	 *
	 * @param chosenFlavor the flavor for the special item
	 * @return null if a flavor was succesfully chosen, error message otherwise
	 */
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
			/* choosing the appropriate slot set */
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

	/**
	 * Resets the recipe checker.
 	*/
	public void renewRecipeChecker()
	{
		recipeChecker = new RecipeChecker( getSlots() , specialSlots );
	}

	/**
	 * Checks whether all base ingredients are in stock in the required amounts. 
  	 *
    	 * @return true if all are in stock in stock in the require amounts, false otherwise
  	 */
	public boolean baseIngredientsAreInStock()
	{
		return recipeChecker.allAbsoluteBaseIngredientsAreInStock();
	}

	/**
 	 * Returns the list of available flavors.
   	 *
     	 * @return the list of available flavors.
     	 */
	public ArrayList<String> getAvailableFlavorsStock()
	{
		ArrayList<String> availableFlavors = new ArrayList<String>();
		for( String k : recipeChecker.getFlavorStock().keySet() ) {
			if( recipeChecker.getFlavorStock().get(k) > 0 )
				availableFlavors.add(
					new String( recipeChecker.getAbsoluteBaseIngredients().get(k) ) );
		}
		return availableFlavors;
	}

	/** the ingredients that will be used in making the special item */
	ArrayList<VM_Item> ingredients;
	/** the cookbook for the special ingredient */
	RecipeChecker recipeChecker;
	/** the special slots of the parent vending machine */
	VM_Slot[] specialSlots;

}