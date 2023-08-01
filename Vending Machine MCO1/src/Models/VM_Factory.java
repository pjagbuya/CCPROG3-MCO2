package Models;

import DenomLib.Denomination;
import ItemSelectLib.PresetItem;
import java.util.LinkedHashMap;


/**
 * The class VM_Factory represents a factory where vending machines are made.
 *
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public class VM_Factory
{
	/**
 	 * Instantiates a vending machine factory.
	 */
	public VM_Factory()
	{
		presetItems = new LinkedHashMap<String, Integer>();
		customItems = new LinkedHashMap<String, Integer>();
		defaultItems();
	}
	
	/**
	 * Instantiates a blank vending machine.
  	 *
    	 * @param vmType the type of vending maching to be manufactured
	 * @param name the name of the vending machine
      	 * @param nOfSlots the number of slots that the vending machine will contain
	 * @param maxItemsPerSlot the maximum number of items per slot
  	 * @return the vending machine object
	 */
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

		if( customItems.size() > 0 )
		for( String k : customItems.keySet() )
			vm.getCustomItems().put( k , customItems.get(k) );

		return vm;
	}

	/**
 	 * Sends out the vending machine from the manufacturing facility.
   	 *
   	 * @return the readied vending machine
     	 */
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

	/**
 	 * Allows user to add stocks to the vending machine.
	 * 
  	 * @param itemName the name of the item to be added
    	 * @param qty the amount of the item to be added
      	 * @return null if adding was successful, error message if not
    	 */
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
	
	/**
 	 * Allows user to add cash to the vending machine.
	 *
  	 * @param denom the worded value of the denomination to be added
    	 * @param qty the number of pieces of the denomination to be added
      	 * @return null if adding was successful, error message if not
    	 */
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

	/**
 	 * Adds a custom (user-named) item to the list of items in this program.
   	 *
     	 * @param name the name of the item to be added
       	 * @param calories the calorific value of the item
     	 */
	public void addAsPossibleItem(String name, int calories)
	{
		customItems.put(name, calories);

		for( String k : customItems.keySet() )
			vm.getCustomItems().put( k , customItems.get(k) );
	}

	/**
	 * Resets the possible item types back to the preset items only. 
    	 */
	public void defaultItems()
	{
		presetItems.clear();
		customItems.clear();
		for(PresetItem item : PresetItem.values()) {
			presetItems.put(item.name(), item.getIsIndependent());
		}
	}	

	/**
  	 * Instantiates a coin or banknote.
    	 *
      	 * @param denom the worded value of the denomination to be instantiated
	 * @return the new coin or banknote instance
     	 */
	private DenominationItem createDenomination(String denom)
	{
		return new DenominationItem( denom , Money.getStrToVal().get(denom) );
	}	
	
	/**
 	 * Instantiates an item.
   	 *
     	 * @param s the name of the item to be instantiated
       	 * @return the instantiated item, null if nothing was instantiated
     	 */
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
			item = generateCustomItem( s );
		
		return item;
	}
	
	
	/**
	 * Instatiates items, based on the user-named (custom) food items list.
	 *
	 * @param s the String name of the item to be instantiatd
	 * @return the generated item, null otherwise
	 */
	private VM_Item generateCustomItem( String s )
	{
		VM_Item item = null;
		
		if( customItems.keySet().contains(s) )
			item = new VM_Item( new String(s) , 20.00, customItems.get(s) );
		
		return item;
	}

	/** the vending machine */
	private VM_Regular vm;
	/** the list of preset items in the program */
	private LinkedHashMap<String, Integer> presetItems;
	/** the list of custom items noted by this factory */
	private LinkedHashMap<String, Integer> customItems;
	/** the vending machine's cash reserves */
	private Money vmMoney;
	/** the vending machine's regular slots */
	private VM_Slot[] slots;
	/** the vending machine's special slots, if any */
	private VM_Slot[] specialSlots;
}
