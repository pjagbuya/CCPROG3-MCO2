package Models;
import java.util.Scanner;

import DenomLib.Denomination;
import ItemSelectLib.PresetItem;

import java.util.LinkedHashMap;
import java.util.InputMismatchException;

public class VM_Factory
{
	public VM_Factory()
	{
		presetItems = new LinkedHashMap<String, Integer>();
		customItemInfos = new LinkedHashMap<String, Integer>();
		defaultItems();
	}
	
	
	public VM_Regular createBlankVM (String vmType, String name, int nOfSlots, int maxItemsPerSlot)
	{
		VM_Regular vm;
		if( vmType.equalsIgnoreCase("R") )
			vm = new VM_Regular(name, nOfSlots, maxItemsPerSlot, new Money());
		else
			vm = new VM_Special(name, nOfSlots, maxItemsPerSlot, new Money());
		
		this.vmMoney = vm.getCurrentMoney();
		this.slots = vm.getSlots();
		if( vm instanceof VM_Special )
			this.specialSlots = ((VM_Special)vm).getSpecialSlots();

		return vm;
	}

	
	public VM_Regular sendOutVM()
	{
		VM_Regular sentOutVM;
		vm.getMaintenance().recordCurrentInventory();
		sentOutVM = vm;
		
		/* Clears out factory information. */
		vmMoney = null;
		slots = null;
		specialSlots = null;		
		vm = null;

		/* Adds custom items to VM memory. */
		if( customItems.size() > 0 )
			for( String k : customItems.keySet() )
				vm.getCustomItems().put( k , customItems.get(k) );
		
		return sentOutVM;
	}

	
	public String specifyInitialStocks(String itemName, int qty)
	{
		String msg;
		VM_Slot[] slotsVar = null;
		int i;
		int j;
		boolean itemExists = true; // assumed true
		boolean nonNegativeQty = true; // assumed true
		msg = "";
		
		/* Switching between special and regular slots. */
		if( presetItems.get( itemName ) == 1 )
			slotsVar = this.slots;
		else if( presetItems.get( itemName ) == 0 )
			slotsVar = specialSlots;
		else if( customItems.keySet().contains(itemName) ) {
			slotsVar = this.slots;
		}
		else {
			itemExists = false;
			msg += new String("ERROR: ITEM DOES NOT EXIST.\n"); 
		}
		
		if(qty < 0) {
			nonNegativeQty = false;
			msg += new String("ERROR: NEGATIVE QUANTITIES NOT ALLOWED.\n");
		}
		
		if(itemExists && nonNegativeQty && slotsVar != null) {
			for(i = 0; i < slotsVar.length; i++)
				if( slotsVar[i].getSlotItemName() == null ||
					slotsVar[i].getSlotItemName().equalsIgnoreCase(itemName) ) {
					for(j = 0; j < qty; j++) {
						System.out.println("Adding this many " +itemName+ ", which is " + qty);
						slotsVar[i].addItemStock( generateItem( itemName ) );
					}
					break;
				}
		}
				
		if(msg.equals(""))
			return null;
		return msg;
	}
	
	
	public String specifyInitialCashReserves(String denom, int qty)
	{
		String msg;
		VM_Slot[] slots;
		int i;
		msg = null;
		if(qty >= 0)
			if( Money.getStrToVal().get(denom) != null )
				for(i = 0; i < qty; i++)
					vmMoney.add( createDenomination( denom ) );
			else
				msg = new String("ERROR: INVALID DENOMINATION.\n");		
		else
			msg = new String("\nERROR: NEGATIVE QUANTITIES NOT ALLOWED.\n");	
		
		return msg;
	}
	
	public void addAsPossibleItem(String name, int calories)
	{
		customItems.put(name, calories);
		
		for( String k : customItems.keySet() )
			vm.getCustomItems().put( k , customItems.get(k) );
	}
	
	public void defaultItems()
	{
		presetItems.clear();
		customItemInfos.clear();
		for(PresetItem item : PresetItem.values()) {
			presetItems.put(item.name(), item.getIsIndependent());
		}
	}	

	private DenominationItem createDenomination(String denom)
	{
		return new DenominationItem( denom , Money.getStrToVal().get(denom) );
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
		else
			generateCustomItem( s );
		
		return item;
	}
	
	
	/**
	 * Instatiates new food items, based on the user-named food known by Maintenance.
	 *
	 * @param s the String name of the item type to be generated
	 * @return the generated item, null otherwise
	 */
	private VM_Item generateCustomItem( String s )
	{
		VM_Item item = null;
		
		if( customItems().keySet().contains(s) )
			item = new VM_Item( new String(s) , 20.00, customItems.get(s) );
		
		return item;
	}

	
	private VM_Regular vm;
	private LinkedHashMap<String, Integer> presetItems;
	private LinkedHashMap<String, Integer> customItems;
	private Money vmMoney;
	private VM_Slot[] slots;
	private VM_Slot[] specialSlots;
}
