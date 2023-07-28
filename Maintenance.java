import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.InputMismatchException;
/**
 * The class Maintenance represents a Maintenance feature that allows the user to restock,
 * replenish, reprice, add/replace items, and update stock information of a Vending Machine
 *
 * 
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 * @version 1.0
 */
public class Maintenance
{
	/**
	 * Takes note of the list of possible items in the program
	 *
	 * @param possibleItems list of names of all item classes in the program, excluding VM_Item
	
	 */
    public Maintenance(Money vmMoney, VM_Slot[] slots, VM_Slot[] specialSlots)
    {
		this vmMoney = vmMoney;
		this.slots = slots; 
		this.specialSlots = specialSlots;
    }

    /**
	 * Provides for manual restocking of the VM's sellable items. Keeps looping
     * and prompting the user of which item slot and how much of such would be restocked
	 * 
	 * @param vm the VM to restock
	 * @return true when a restocking occurred, false otherwise
	 */
    public String restockItems(String itemName, int qty)
    {
		String msg = null;
		int i;
		int slotIndex;
        boolean anItemIsRestocked = false; // initially false
		boolean slotFound = false; // intially false
		VM_Slot[] slots;
		
		/* Switching between special and regular slots. */
		if( Main.getPossibleItems().get( itemName ) == 1 )
			slots = this.slots;
		else
			slots = specialSlots;
		
		for(i = 0; i < slots.length; i++)
			if( slots.getSlotItemName() != null &&
				slots.getSlotItemName().equalsIgnoreCase(itemName) ) {
				slotIndex = i;
				slotFound = true;
			}

		if( slotFound )
			msg = new String("ERROR: SLOT NOT FOUND.\n");
        
        // Only proceed updating and allow adding if the slot
		if( slotFound && slots[slotIndex].getSlotItemName() != null )
			if ( !anItemIsRestocked ) {
				anItemIsRestocked = true;
				for( i = 0; i < qty; i++ )
					slots[slotIndex].addItemStock( generateItem( slots[slotIndex].getSlotItemName() ) );
			}
			else
				msg = new String("ERROR: SLOT HAS NO ASSIGNED ITEM. ENTER A DIFF. SLOT NUM.\n");		

		return msg;
    }


    /**
	 * Provides for repricing of the VM's items, via console prompting
	 *
	 * @param vm the VM whose items are to be replaced
	 */
	public String repriceItems(String itemName, double amt) 
	{
		String msg = null;
		int i;
		int slotIndex;
		boolean slotFound = false; // assumed false
		boolean slotHasName = true; // assumed true
		boolean slotIsEmpty = false; // assumed false
		VM_Slot[] slots;
		
		/* INPUT VALIDATION */
			
		/* Switching between special and regular slots. */
		if( Main.getPossibleItems().get( itemName ) == 1 )
			slots = this.slots;
		else
			slots = specialSlots;
		
		for(i = 0; i < slots.length; i++)
			if( slots.getSlotItemName() != null &&
				slots.getSlotItemName().equalsIgnoreCase(itemName) ) {
				slotIndex = i;
				slotFound = true;
			}
			
		if( !slotFound )
			msg = new String("ERROR: SLOT WITH ITEM NAME NOT FOUND.\n");
			
		/* Checks whether the slot is already associated with an item. */
		if( slotFound && slots[slotFound].getSlotItemName() == null )
		{
			msg = new String("ERROR: SLOT HAS NO NAME. ENTER A DIFF. SLOT NUM.\n");
			slotHasName = false;
		}
			
		/* Checks whether slot is empty */
		if( slotFound && slotHasName && slots[].getSlotItemStock() == 0 )
		{	
			msg = new String("ERROR: SLOT IS EMPTY. NO ITEM TO REPRICE.\n");
			slotIsEmpty = true;
		}
			
		/* Decision */
		if( slotFound && slotHasName && !slotIsEmpty )
			slots[slotIndex].repriceItem(amt);
		
		return msg;
	}


    /**
	 * Provides for manual replenishing of the VM's cash reserves
	 *
	 * @param vm is the VM that will receive more cash reserves
	 */
	public String replenishDenominations(String denom, int qty)
	{
		String msg;
        Money vmMoney;
		int i;
						
		if( Money.getStrToVal().get(denom) != null )
			for(i = 0; i < qty; i++)
				vmMoney.add( createDenomination(denom) );
		else
			msg = new String("ERROR: DENOMINATION DOES NOT EXIST.\n");
		
		return msg;
	}
	
	
	public Denomination createDenomination(String denom)
	{
		return Denomination( denom , Money.getStrToVal() );
	}
	
	
	
	
    
	public String replaceItemStock(String itemName, int qty, int slotNum)
	{
		String msg = "";
		int i;
		boolean stockIsReplaced = false; // initially false
		boolean sameNameExists = false; // initially false
		boolean itemExists = true; // assumed true
		boolean qtyIsPositive = true; // assumed true
		boolean slotNumOutOfBounds = false; // assumed false
		VM_Slot[] slots;
		
		
		/* Switching between special and regular slots. */
		if( Main.getPossibleItems().get( itemName ) == 1 )
			slots = this.slots;
		else
			slots = specialSlots;
		
		/* START OF INPUT VALIDATION */
			
		/* Ensures that each slot has a unique item. */
		for(i = 0; i < slots.length; i++)
			if(	slots[i].getSlotItemName() != null &&
				slots[i].getSlotItemName().equalsIgnoreCase(itemName) &&
				slots[i].getSlotItemStock() != 0)
			{
				msg = msg + "ERROR: SLOT WITH SAME ITEM EXISTS. RESTOCK INSTEAD.\n";
				sameNameExists = true;
			}
			
		/* Slot Number is Out Of Bounds. */
		if( !sameNameExists && (slotNum < 1 || slotNum > slots.length) )
		{
			msg = msg + "-ERROR: SLOT NUM OUT OF BOUNDS.\n";
			slotNumOutOfBounds = true;
		}
		
		/* Item is Not in the Universe of the Program. */
		if( Main.getPossibleItems().get(input.toUpperCase()) == null )
		{
			msg = msg + "ERROR: ITEM DOES NOT EXIST IN THE UNIVERSE OF THE PROGRAM.\n";
			itemExists = false;
		}
			

		/* Adding a zero or negative quantity of the item is not allowed. */
		if( qty <= 0 )
		{
			msg = msg + "ERROR: NON-POSITIVE QUANTITY.\n";
			qtyIsPositive = false;
		}
			
		/* Decision of whether to continue with stock replacement/filling-in */
		if(	qtyIsPositive && !sameNameExists && itemExists && !slotNumOutOfBounds )
		{
			for(i = 0; i < qty; i++)
				slots[slotNum-1].addItemStock( generateItem( itemName ) );
			stockIsReplaced = true;
		}	
		
		if( stockIsReplaced )
			return null;
		return msg;
	}
	
	
	/**
	 * This method updates the stocked infos by instantiating a new Stocked info
     * It will also reset all slot stored Profit and Items sold
	 *
	 * @param vm the VM that will save a copy of its current inventory (as a VM_StockedInfo object)
	 */
	public void updateStockedInfos()
	{
		int i;
		VM_Slot[] slots;
		vm.addStockInd();
		slots = this.slots();

		for(i = 0; i < slots.length; i++)
			if(slots[i] != null)
			{
				// Clear all storedProfit 
				slots[i].clearStoredProfit();

				// resets no. of sold items per slot back to
				slots[i].setSlotItemSold(0); 
			}
	}


	/**
	 * This method provides a simple collection of cash reserves from VM
     * via console prompts
	 *
	 * @param vm the VM from which to collect (subtract) reserves														  
	 */
	public String collectCashReserves(String denom, int qty)
	{
		String msg;
		int i;
		boolean canSubtract;
		
		
		
		/* input validation */ 	
		if( Money.getStrToVal().get(denom) == null ) {	/* if denomination does not exist in the standard set */
			msg = new String("ERROR: INVALID DENOMINATION.\n");
			canSubtract = false;
		} if( qty <= 0 ) {	/* if no. of pieces to subtract is negative */
			msg = new String("ERROR: NEGATIVE AND ZERO SUBTRACTION NOT ALLOWED.\n");
			canSubtract = false;
		} if( 	Money.getValToStr().get(denom) != null &&
				(vmMoney.getDenominations().get(denom).size() - qty < 0 ) {	/* if VM cannot release that number of pieces of the denomination */
			msg = new String("ERROR: SUBTRACTION RESULTS IN NEGATIVE DENOMINATIONS.\n");
			canSubtract = false;
		} if( canSubtract )
			for(i = 0; i < vmMoney.getDenominations().get(denom).size())
				vmMoney.subtract(denom);
		
		sc = null;
	}
	
	
	protected VM_Item generateItem( String s )
	{
		VM_Item item = null;
		
		if( s.equalsIgnoreCase("Cheese") )
			item = new VM_Item("Cheese", 40.00, 15);
							
		else if( s.equalsIgnoreCase("Cocoa") )
			item = new VM_Item("Cocoa", 20.00, 4);
							
		else if( s.equalsIgnoreCase("Cream") )
			item = new VM_Item("Cream", 18.00, 5);
							
		else if( s.equalsIgnoreCase("Egg") )
			item = new VM_Item("Egg", 12.00, 35);
							
		else if( s.equalsIgnoreCase("Kangkong") )
			item = new VM_Item("Kangkong", 10.00, 2);
							
		else if( s.equalsIgnoreCase("Cornstarch") ) 
			item = new VM_Item("Cornstarch", 13.00, 2);
							
		else if( s.equalsIgnoreCase("Milk") )
			item = new VM_Item("Milk", 99.00, 20);
							
		else if( s.equalsIgnoreCase("Tofu") )
			item = new VM_Item("Tofu", 5.00, 3);
							
		else if( s.equalsIgnoreCase("Salt") )
			item = new VM_Item("Salt", 5.00, 1);
							
		else if( s.equalsIgnoreCase("Sugar") )
			item = new VM_Item("Sugar", 5.00, 30);
							
		else if( s.equalsIgnoreCase("Chicken") )
			item = new VM_Item("Chicken", 150.00, 42);
							
		else if( s.equalsIgnoreCase("BBQ") )
			item = new VM_Item("BBQ", 5.00, 1);
							
		else if( s.equalsIgnoreCase("Flour") )
			item = new VM_Item("Flour", 5.00, 1);
		
		return item;
	}
	
	private Money vmMoney;
	private VM_Slot[] slots;
	private VM_Slot[] specialSlots;
}