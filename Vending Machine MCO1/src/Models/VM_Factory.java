package Models;
import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.InputMismatchException;

public class VM_Factory
{
	public VM_Factory()
	{
	
	}
	
	
	public void createVM (String vmType, String name, int nOfSlots, int maxItemsPerSlot)
	{
		if( vmType.equalsIgnoreCase("R") )
			vm = VM_Regular(name, nOfSlots, maxItemsPerSlot);
		else
			vm = VM_Special(name, nOfSlots, maxItemsPerSlot);
		
		this.vmMoney = vm.getCurrentMoney;
		this.slots = vm.getSlots();
		if( vm instanceof VM_Special )
			this.specialSlots = ((VM_Special)vm).getSpecialSlots();
	}
	
	
	public String specifyInitialStocks(String itemName, int qty)
	{
		String msg;
		VM_Slot[] slots;
		int i;
		int j;
		
		/* Switching between special and regular slots. */
		if( Main.getPossibleItems().get( itemName ) == 1 )
			slots = this.slots;
		else
			slots = specialSlots;
		
		if(qty < 0)
			for(i = 0; i < slots.length; i++)
				if( slots[i].getSlotItemName() == null ||
					slots[i].getSlotItemName().equalsIgnoreCase(itemName) )
				{
					for(j = 0; j < qty; j++)
						slots[i].addItemStock( generateItem( itemName ) );
					break;
				}
		else
			msg = new String("ERROR: NEGATIVE QUANTITIES NOT ALLOWED.\n");
		
		
		return msg;
	}
	
	
	public String specifyInitialCashReserves(String denom, int qty)
	{
		String msg;
		VM_Slot[] slots;
		int i;
		
		if(qty >= 0)
			if( Money.getStrToVal().get(denom) != null )
				from(i = 0; i < qty; i++)
					vmMoney.add( createDenomination( denom ) );
			else
				msg = new String("ERROR: INVALID DENOMINATION.\n");		
		else
			msg = new String("\ERROR: NEGATIVE QUANTITIES NOT ALLOWED.\n");	
		
		return msg;
	}
	
	
	
	private Denomination createDenomination(String denom)
	{
		return Denomination( denom , Money.getStrToVal() );
	}	
	
	
	private VM_Item generateItem( String s )
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
	
	
	
	public getVM()
	{
		return vm;
	}

	
	private VM_Regular vm;
	private Money vmMoney;
	private VM_Slot[] slots;
	private VM_Slot[] specialSlots;
}