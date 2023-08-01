package Models;

import java.util.LinkedHashMap;


/**
 * The class RecipeChecker holds predetermined cookbooks for each Special Item.
 * Currently only supports KangkongChips.
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public class RecipeChecker
{
	/**
	 * Instantiates a cookbook and
	 * lists down the available stocks of both required and optional ingredients.
	 *
	 * @param slots the regular slots of the parent vending machine
	 * @param specialSlots the specialSlots of the parent vending machine
	 */   	
	public RecipeChecker(VM_Slot[] slots, VM_Slot[] specialSlots)
	{
		int i;
		
		/* lists all base ingredients */
		absoluteBaseIngredients = new LinkedHashMap<Integer, String>();
		absoluteBaseIngredients.put(1, "KANGKONG");
		absoluteBaseIngredients.put(2, "FLOUR");
		absoluteBaseIngredients.put(3, "CORNSTARCH");
		absoluteBaseIngredients.put(4, "SALT");
		
		
		/* Lists the required stock for each absolute base ingredient. */
		requiredStock = new LinkedHashMap<Integer, Integer>();
		for( int k : absoluteBaseIngredients.keySet() )
			requiredStock.put( k , 1 );
		
		/* lists all kangkong chips flavors, whether they are available or not */
		flavors = new LinkedHashMap<Integer, String>();
		flavors.put(1, "PLAIN");
		flavors.put(2, "CHEESE");
		flavors.put(3, "EGG");
		flavors.put(4, "COCOA");
		flavors.put(5, "BBQ");
		
		/* the list of absolute base ingredients but with the keys and values REVERSED */
		reversedAbsoluteBaseIngredients = new LinkedHashMap<String, Integer>();
		for( int k : absoluteBaseIngredients.keySet() )
			reversedAbsoluteBaseIngredients.put( absoluteBaseIngredients.get(k), k );

		
		
		/* Lists down how much stock is available for each absolute base ingredient. */
		absoluteBaseIngredientStock = new LinkedHashMap<Integer, Integer>();
		/* First sets all records of absolute base ingredients to 0. */
		for( int k : absoluteBaseIngredients.keySet() )
			absoluteBaseIngredientStock.put( k , 0 );
		for(i = 0; i < slots.length; i++)
			if( slots[i].getSlotItemName() != null &&
				reversedAbsoluteBaseIngredients.get( slots[i].getSlotItemName().toUpperCase() ) != null )
				absoluteBaseIngredientStock.put( reversedAbsoluteBaseIngredients.get( slots[i].getSlotItemName().toUpperCase() ), slots[i].getSlotItemStock() );
		for(i = 0; i < specialSlots.length; i++)
			if( specialSlots[i].getSlotItemName() != null &&
				reversedAbsoluteBaseIngredients.get( specialSlots[i].getSlotItemName().toUpperCase() ) != null )
				absoluteBaseIngredientStock.put( reversedAbsoluteBaseIngredients.get( specialSlots[i].getSlotItemName().toUpperCase() ), specialSlots[i].getSlotItemStock() );
		
		
		
		/* the flavors list but with the keys and values REVERSED */
		reversedFlavors = new LinkedHashMap<String, Integer>();
		for( int k : flavors.keySet() )
			reversedFlavors.put( flavors.get(k), k );
		
		/* lists down how much stock is available for each flavor */
		flavorStock = new LinkedHashMap<Integer, Integer>();
		for( int k : flavors.keySet() )
			flavorStock.put( k, 0 );
		flavorStock.put( 1, 99999999 ); // sets "stock" of Plain flavor (no flavor!)
		for(i = 0; i < slots.length; i++)
			if( slots[i].getSlotItemName() != null &&
				reversedFlavors.get( slots[i].getSlotItemName().toUpperCase() ) != null )
				flavorStock.put( reversedFlavors.get( slots[i].getSlotItemName().toUpperCase() ), slots[i].getSlotItemStock() );
		for(i = 0; i < specialSlots.length; i++)
			if( specialSlots[i].getSlotItemName() != null &&
				reversedFlavors.get( specialSlots[i].getSlotItemName().toUpperCase() ) != null )
				flavorStock.put( reversedFlavors.get( specialSlots[i].getSlotItemName().toUpperCase() ), specialSlots[i].getSlotItemStock() );
	}
	
	
	
	
	
	/**
	 * Checks whether all base ingredients are present in the required amounts.
	 *
	 * @return true if all base ingredients are present in the required amounts, false otherwise
	 */
	public boolean allAbsoluteBaseIngredientsAreInStock()
	{
		boolean allAreInStock = true; // initially true
		
		for( int k : absoluteBaseIngredientStock.keySet() )
		System.out.print( k + " : " + absoluteBaseIngredientStock.get(k) + "\n");


		for( int k : absoluteBaseIngredientStock.keySet() )
			if( absoluteBaseIngredientStock.get(k) < requiredStock.get(k) )
				allAreInStock = false;
			
	
		return allAreInStock;
	}
	

	/**
	 * Returns the flavor list the special item.
	 *
	 * @return the flavors of the special item
	 */
	public LinkedHashMap<Integer, String> getFlavors() { return flavors; }
	
	/**
	 * Returns the inverse flavor list of the special item.
	 * 
	 * @return the inverse flavor map
	 */
	public LinkedHashMap<String, Integer> getReversedFlavors() { return reversedFlavors; }
	
	/**
	 * Returns the available stock counts of each flavor.
	 * 
	 * @return the stock counts of each flavor.
	 */
	public LinkedHashMap<Integer, Integer> getFlavorStock() { return flavorStock; }
	
	/**
	 * Returns the list of base ingredients.
	 *
	 * @return the list of the base ingredients for the special item.
	 */
	public LinkedHashMap<Integer, String> getAbsoluteBaseIngredients() { return absoluteBaseIngredients; }
	
	/**
	 * Returns the stock count of each base ingredient.
	 *
	 * @return the stock counts of the base ingredients for the special item.
	 */
	public LinkedHashMap<Integer, Integer> getAbsoluteBaseIngredientStock() { return absoluteBaseIngredientStock; }
	
	/**
	 * Returns the inverse base ingredients map.
	 *
	 * @return the inverse base ingredents map for the special item.
	 */
	public LinkedHashMap<String, Integer> getReversedAbsoluteBaseIngredients() { return reversedAbsoluteBaseIngredients; }
	
	
	/**
	 * Returns the required stock count for each base ingredient.
	 *
	 * @return the required stock count for each base ingredient in the special list.
	 */
	public LinkedHashMap<Integer, Integer> getRequiredStock() { return requiredStock; }
	
	/** the list of ingredients absolutely required for the special item */
	private LinkedHashMap<Integer, String> absoluteBaseIngredients;
	/** the inverse list of base ingredients */
	private LinkedHashMap<String, Integer> reversedAbsoluteBaseIngredients;
	/** the stock count of each base ingredient */
	private LinkedHashMap<Integer, Integer> absoluteBaseIngredientStock;
	/** the required stock count for each ingredient */	
	private LinkedHashMap<Integer, Integer> requiredStock;
	
	/** the list of flavors for the special item */
	private LinkedHashMap<Integer, String> flavors;
	/** the inverse list of flavors for the special item */
	private LinkedHashMap<String, Integer> reversedFlavors;
	/** the stock count of each flavor */
	private LinkedHashMap<Integer, Integer> flavorStock;
}
