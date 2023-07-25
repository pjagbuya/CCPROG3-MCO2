
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
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
    public SpecialSellingOperator()
    {		
		
    }

    
	/**
	 * Allows user to choose between regular vending features and special vending features
	 *
	 * @param duplicate		a duplicate set of the VM's current denominations	 
	 * @param payment		the types of denominations inserted into the VM, and their corresponding quantities greater than or equal to 0
	 * @param change		the types of denominations returned by the VM as change, and their corresponding quantities greater than or equal to 0
     * @param order			the order object, contains the user's order
	 */
	public void sellingOperation(
		VM_Regular vm,
		Money duplicate,
		Money payment,
		Money change,
		Order order )
	{
		recipeChecker = new RecipeChecker(vm);
		Scanner sc = new Scanner(System.in);
		String input;
		
		System.out.print("Choose:\n [R] Regular Vending Feautures\n [S] Special Vending Features\n >> ");
		input = sc.next();
		if(input.equalsIgnoreCase("R"))
			super.sellingOperation( vm, duplicate, payment, change, order );
		else if(input.equalsIgnoreCase("S"))
			sellSpecialItems( vm, duplicate, payment, change, order );
	}
	
	/**
	 * Sells a special item.
	 *
	 * @param duplicate		a duplicate set of the VM's current denominations	 
	 * @param payment		the types of denominations inserted into the VM, and their corresponding quantities greater than or equal to 0
	 * @param change		the types of denominations returned by the VM as change, and their corresponding quantities greater than or equal to 0
     * @param order			the order object, contains the user's order
	 */
	public void sellSpecialItems(
		VM_Regular vm,
		Money duplicate,
		Money payment,
		Money change,
		Order order )
	{	
		recipeChecker = new RecipeChecker(vm);
		/* Choosing a kankong chips flavor */
		if( recipeChecker.allAbsoluteBaseIngredientsAreInStock() )
		{
			promptFlavor(vm, order);
			promptIngredients( vm, order );
		}	
		else
			System.out.println("\033[1;38;5;202m-NOT ALL ABSOLUTE BASE INGREDIENTS ARE IN STOCK!\033[0m");
	}
	
	private void promptIngredients(VM_Regular vm, Order order)
	{
		String ingredient;
		String inputQty;
		int slotNum;
		int qty;
		int i;
		VM_Slot[] slots = null;
		Scanner sc;
		boolean ingredientExists = true; // assumed true
		boolean ingredientIsAnotherFlavor = false; // assumed false
		boolean ingredientHasSlot = false; // assumed false
		boolean ingredientHasEnoughStock = false; // assumed false
		
		sc = new Scanner(System.in);

		/* order request while loop prompting*/
		while(true)
		try
		{
			System.out.print("What would you like to add to the Special Item? (\033[1;33mEnter 'Y' to confirm/finish\033[0m)? \033[1;32m<item name> <qty>\033[0m\n>> ");
			ingredient = sc.next();
			if( ingredient.equalsIgnoreCase("Y") )
				break;
			inputQty = sc.next();
			
			
		
			/* INPUT VALIDATION */
			
			if( Main.getPossibleItems().get( ingredient.toUpperCase() ) == null )
			{
				System.out.println("\033[1;38;5;202m-ERROR: ITEM DOES NOT EXIST IN UNIVERSE\033[0m");
				ingredientExists = false;
			}
			
			
			if( ingredientExists &&
				order.getPendingOrder().get( ingredient.toUpperCase() ) == null &&
				recipeChecker.getReversedFlavors().get( ingredient.toUpperCase() ) != null )
			{
				System.out.println("\033[1;38;5;202m-ERROR: CANNOT ADD OTHER FLAVORS\033[0m");
				ingredientIsAnotherFlavor = true;
			}
			
			
			qty = Integer.parseInt(inputQty);
			
			/* Checks whether a slot holds enough of this item. */
			if( ingredientExists )
			{
				if( Main.getPossibleItems().get( ingredient.toUpperCase() ) == 1 )
					slots = vm.getSlots();
				else
					slots = ((VM_Special)vm).getSpecialSlots();
				for(i = 0; i < slots.length; i++)
					if( slots[i].getSlotItemName() != null &&
						slots[i].getSlotItemName().equalsIgnoreCase( ingredient ) )
					{
						ingredientHasSlot = true;
						if( qty <= slots[i].getSlotItemStock() )
							ingredientHasEnoughStock = true;
					}
			}
			
			/* display messages */
			if( ingredientExists && !ingredientHasSlot )
			{
				System.out.println("\033[1;38;5;202m-ERROR: NO SLOT ACCOMODATES THIS INGREDIENT\033[0m");
			}
			if( ingredientExists && ingredientHasSlot && !ingredientHasEnoughStock )
			{
				System.out.println("\033[1;38;5;202m-ERROR: INSUFFICIENT STOCK\033[0m");
			}
			
			
			/* Decision */
			if( ingredientExists && !ingredientIsAnotherFlavor && ingredientHasSlot && ingredientHasEnoughStock )
			{
				for(i = 0; i < slots.length; i++)
					if( slots[i].getSlotItemName() != null &&
						slots[i].getSlotItemName().equalsIgnoreCase( ingredient ) )
						order.addOrder(slots[i], qty);
				System.out.println("\033[1;32m-ADDED TO ORDER\033[0m");
			}
		}
		catch(NumberFormatException e)
		{
			System.out.println("\033[1;38;5;202m-ERROR: NOT PARSABLE TO INT, Please enter slot number\033[0m");
		}
		
		
		
		

		sc = null;
	}
	
	
	/**
	 * Ensures that user picks a flavor with a stock of at least 1
	 *
	 */
	private void promptFlavor(VM_Regular vm, Order order)
	{
		String input;
		int chosenFlavor = 0;
		int i;
		int totalOfAllFlavorStock = 0;
		VM_Slot[] slots;
		Scanner sc;
		
		sc = new Scanner(System.in);
		
		LinkedHashMap<Integer, String> flavors = recipeChecker.getFlavors();
		LinkedHashMap<Integer, Integer> flavorStock = recipeChecker.getFlavorStock();
		
		for( int k : flavorStock.keySet() )
			totalOfAllFlavorStock += flavorStock.get(k);
		
		if( totalOfAllFlavorStock > 0 )
			while(true)
			try
			{
				System.out.print("Choose a flavor:\n");
				/* displaying AVAILABLE flavors */
				for( int k : flavors.keySet() )
					if( flavorStock.get(k) > 0 )
						System.out.print( " [" + k + "] " + flavors.get(k) + "\n" );
					
				System.out.print(" >> ");
				
				input = sc.next();
				
				chosenFlavor = Integer.parseInt(input);
				
				
				// only when selected slot num is within range, this will trigger to add that order
				if( flavors.get(chosenFlavor) != null &&  flavorStock.get(chosenFlavor) > 0 )
					break;
				else
					System.out.println("\033[1;38;5;202m-ERROR: FLAVOR NOT AVAILABLE\033[0m");
			}
			catch(NumberFormatException e)
			{
				System.out.println("\033[1;38;5;202m-ERROR: NOT PARSABLE TO INT, Please enter slot number\033[0m");
			}
		else
			System.out.println("\033[1;38;5;202m-NO FLAVOR AVAILABLE! PLAIN FLAVOR CHOSEN\033[0m");
		
		/* adds flavor to Order */
		if( !flavors.get(chosenFlavor).equalsIgnoreCase("PLAIN") )
		{
			if( Main.getPossibleItems().get( flavors.get(chosenFlavor) ) == 1 )
				slots = vm.getSlots();
			else
				slots = ((VM_Special)vm).getSpecialSlots();
			for(i = 0; i < slots.length; i++)
				if( slots[i].getSlotItemName() != null &&
					slots[i].getSlotItemName().equalsIgnoreCase( flavors.get(chosenFlavor) ) )
					order.addOrder( slots[i], 1 );
		}
		
		sc = null;
	}
	
	
	
	
	
	
	
	
	
	public RecipeChecker getRecipeChecker() { return recipeChecker; }
	
	private RecipeChecker recipeChecker;
}