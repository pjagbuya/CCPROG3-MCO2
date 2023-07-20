
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
    public SpecialSellingOperator(VM_Regular vm)
    {
        super(vm);
		
		recipeChecker = new RecipeChecker(vm);
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
		Money duplicate,
		Money payment,
		Money change,
		Order order )
	{
		Scanner sc = new Scanner(System.in);
		String input;
		
		System.out.print("Choose:\n [R] Regular Vending Feautures\n [S] Special Vending Features\n >> ");
		input = sc.next();
		if(input.equalsIgnoreCase("R"))
			super.sellingOperation( duplicate, payment, change, order );
		else if(input.equalsIgnoreCase("S"))
			sellSpecialItems( duplicate, payment, change, order );
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
		Money duplicate,
		Money payment,
		Money change,
		Order order )
	{
		int chosenFlavor;
		
		if( recipeChecker.allAbsoluteBaseIngredientsAreInStock() )
		{
			chosenFlavor = promptFlavor();
		}
		else
			System.out.println("\033[1;38;5;202m-NOT ALL ABSOLUTE BASE INGREDIENTS ARE IN STOCK!\033[0m");
	}
	
	
	
	
	/**
	 * Ensures that user picks a flavor with a stock of at least 1
	 *
	 */
	private int promptFlavor()
	{
		String input;
		int chosenFlavor = 0;
		int i;
		int totalOfAllFlavorStock = 0;

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
						System.out.print( " [" + k + "] " + flavors.get(k) );
					
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
		
		sc = null;
		return chosenFlavor;
	}
	
	
	
	
	
	
	
	
	
	public RecipeChecker getRecipeChecker() { return recipeChecker; }
	
	private RecipeChecker recipeChecker;
}