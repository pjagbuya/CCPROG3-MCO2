import java.util.LinkedHashMap;


/**
 * Currently only supports KangkongChips
 *
 */
public class RecipeChecker
{
	public RecipeChecker(VM_Regular vm)
	{
		int i;
		VM_Slot[] slots;
		VM_Slot[] specialSlots;
		
		/* lists all base ingredients */
		absoluteBaseIngredients = new LinkedHashMap<Integer, String>();
		absoluteBaseIngredients.put(1, "KANGKONG");
		absoluteBaseIngredients.put(2, "FLOUR");
		absoluteBaseIngredients.put(3, "CORNSTARCH");
		absoluteBaseIngredients.put(3, "SALT");
		
		/* lists all kangkong chips flavors, whether they are available or not */
		flavors = new LinkedHashMap<Integer, String>();
		flavors.put(1, "PLAIN");
		flavors.put(2, "CHEESE");
		flavors.put(3, "EGG");
		flavors.put(4, "COCOA");
		flavors.put(5, "BBQ");
	
		
		
		slots =  vm.getSlots();
		specialSlots = ((VM_Special)vm).getSpecialSlots();
		
		
		
		
		
		/* the list of absolute base ingredients but with the keys and values REVERSED */
		reversedAbsoluteBaseIngredients = new LinkedHashMap<String, Integer>();
		for( int k : absoluteBaseIngredients.keySet() )
			reversedAbsoluteBaseIngredients.put( absoluteBaseIngredients.get(k), k );
		
		
		/* lists down how much stock is available for each absolute base ingredient */
		absoluteBaseIngredientStock = new LinkedHashMap<Integer, Integer>();
		for(i = 0; i < slots.length; i++)
			if( reversedAbsoluteBaseIngredients.get( slots[i].getSlotItemName() ) != null )
				absoluteBaseIngredientStock.put( reversedAbsoluteBaseIngredients.get( slots[i].getSlotItemName() ), slots[i].getSlotItemStock() );
		for(i = 0; i < specialSlots.length; i++)
			if( reversedAbsoluteBaseIngredients.get( specialSlots[i].getSlotItemName() ) != null )
				absoluteBaseIngredientStock.put( reversedAbsoluteBaseIngredients.get( specialSlots[i].getSlotItemName() ), specialSlots[i].getSlotItemStock() );
		
		
		
		
		
		
		
		
		
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
			if( reversedFlavors.get( slots[i].getSlotItemName() ) != null )
				flavorStock.put( reversedFlavors.get( slots[i].getSlotItemName() ), slots[i].getSlotItemStock() );
		for(i = 0; i < specialSlots.length; i++)
			if( reversedFlavors.get( specialSlots[i].getSlotItemName() ) != null )
				flavorStock.put( reversedFlavors.get( specialSlots[i].getSlotItemName() ), specialSlots[i].getSlotItemStock() );
	}
	
	
	
	
	
	/**
	 * Checks whether there is at least one (1) of every absolute base ingredient
	 *
	 */
	public boolean allAbsoluteBaseIngredientsAreInStock()
	{
		boolean allAreInStock = true; // initially true
		
		for( int k : absoluteBaseIngredientStock.keySet() )
			if( absoluteBaseIngredientStock.get(k) < 1 )
				allAreInStock = false;
			
	
		return allAreInStock;
	}
	


	public LinkedHashMap<Integer, String> getFlavors() { return flavors; }
	public LinkedHashMap<String, Integer> getReversedFlavors() { return reversedFlavors; }
	public LinkedHashMap<Integer, Integer> getFlavorStock() { return flavorStock; }
	public LinkedHashMap<Integer, Integer> getAbsoluteBaseIngredientStock() { return absoluteBaseIngredientStock; }
	
	private LinkedHashMap<Integer, String> absoluteBaseIngredients;
	private LinkedHashMap<String, Integer> reversedAbsoluteBaseIngredients;
	private LinkedHashMap<Integer, Integer> absoluteBaseIngredientStock;
	
	private LinkedHashMap<Integer, String> flavors;
	private LinkedHashMap<String, Integer> reversedFlavors;
	private LinkedHashMap<Integer, Integer> flavorStock;
}