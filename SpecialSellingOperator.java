
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
	 * @param vm the target vending machine for results and effects of this class
	 */
    public SpecialSellingOperator(VM_Regular vm)
    {
        super(vm);
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
		System.out.print("TEST: SPECIAL ITEM SOLD!\n");
	}
}