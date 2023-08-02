package Models;
import java.util.Scanner;
import java.util.ArrayList;



/** The class VM_Slot represents an abstract slot of the VM
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  * @version 1.0
  */
public class VM_Slot {


    /**
     * This instructor Initializes a slot's item and capacity based on the given parameters.
     * Every slot can contain only one actual copy of the item it is set to hold
     * 
     * @param capacity the maximum no. of items that this slot can hold
     */
    public VM_Slot(int capacity){

        storedProfit = 0.0;
        slotItemName = null;
        items = null;
		copy_stockCount = 0;
		price = 0.0;
		
		slotItemSold = 0;
		
        if(capacity >= 10)
            MAX = capacity;
        else
            MAX = 10;
    }
	
	
	/**
     * Instructs slot to hold a different item, or even another of the same item
     * 
     * @param givenItem the new item replacing the slot's original item
     * @param qty the initial stock count of the new item
     */
    public void replaceStock(VM_Item givenItem)
    {	
        if(givenItem != null)
        {
			items = new ArrayList<VM_Item>();
			items.add( givenItem );
			slotItemName = new String(givenItem.getItemName());
		}
		
		repriceItem( givenItem.getItemPrice() );
    }
	
	
	/**
     * Checks whether this slot contains the desired number of its item or more
     * 
     * @param qty the desired number of pieces of the item
	 * @return true if slot contain the desired quantity of items, false otherwise
     */
	public boolean hasEnoughStock(int qty) {
		if( items != null &&
			getSlotItemStock() >= 0 &&
			qty >= 0 &&
			qty <= getSlotItemStock() )
			return true;
		return false;
	}
	
	
	/**
     * Computes for that fraction of the total cost
     * contributed by the desired number of items from this slot
     * 
     * @param qty the desired quantity of items
	 * @return the total cost of desired number of items from this slot
     */
	public double computePartialCost(int qty) 
    {
        double sum = 0.0;
		sum = getPrice() * qty;
        return sum;
	}

	
	 /**
     * Tells slot to "release" a certain quantity of its item,
     * which in the current design of this program
     * simply means increasing or decreasing the stock counter
     * 
     * @param qty the number of items to be released from this slot
     */
    public VM_Item releaseStock()
    {
		int i;
		VM_Item releasedItem = null;
		/* Only release stock if there is enough of it to release */
        if( items != null && hasEnoughStock(1) )
        {
			releasedItem = items.remove(0);
            storedProfit += getPrice();
            slotItemSold++;
        }
		return releasedItem;
    }
	
	
    /**
     * Gets the name of this slot
     * 
     * @return the String name of the slot
     */
    public String getSlotItemName() 
    {
        return slotItemName;
    }


    /**
     * Gets the stock count of items in the slot
     * 
     * @return the number of items currently in this slot
     */
    public int getSlotItemStock()
	{
		if( items != null )
			return items.size();
		return 0;
    }

    
	/**
     * Gets the maximum number of items that this slot can hold
     * 
     * @return the maximum capacity of this slot
     */
    public int getMAX()
	{
        return MAX;
    }

    


    
	/**
     * Adds to the stock count of this slot if givenItem
     * has the the same name as the currently held item,
     * but sets the stock count and replaces the currently held item
     * with givenItem if the two items have different names.
     * 
	 * @param givenItem the given type of item to be added
     * @param qty the quantity of objects
     */
    public void addItemStock( VM_Item givenItem )
    {

        
        // Error that there was no stock added
        if( givenItem == null )

        // State and excess and return it
        if( 1 + getSlotItemStock() > MAX && givenItem.getItemName().equalsIgnoreCase(slotItemName) ) {
            return;
        }

        // If slot was initialized empty, proceed to put in stock
		if( items == null || getSlotItemStock() == 0 )
            replaceStock( givenItem );
		// Skips conditional construct if restocker is empty
		else if( givenItem == null );
        // If the slot is not empty, then proceed to add the stock
        else if( givenItem.getItemName().equalsIgnoreCase(slotItemName) && (getSlotItemStock()+1) <= MAX )
		{
			givenItem.setPrice( getPrice() );
			items.add( givenItem );
		}
        // if this slot already has an item, but has a different name
        else
            return;


    }

	
	/**
     * This method Sets the price of the item object held by this slot
     * 
     * @param amt the new price of the item

     */
	public void repriceItem(double amt)
	{
		int i;
		if(amt < 0.5) // minimum price of an item is 50 Cents
			amt = 0.5;
		for(i = 0; i < items.size(); i++)
			items.get(i).setPrice(amt);
		price = amt;
	}
	
	 
	/**
     * Gets how many items have been sold
	 *	by this slot since last restocking
     * 
     * @return the number of items sold by this slot since last restocking
     */
    public int getSlotItemSold() {
        return slotItemSold;
    }

	
	/**
     * Sets the number of items sold
	 * by this slot since the last restocking
     * 
     * @param slotItemSold the new number of items sold by this slot since last restocking
     */
	public void setSlotItemSold(int slotItemSold) 
    {
			this.slotItemSold = slotItemSold;
	}
	
	
	/**
	 * Gets the amount of profit generated
	 * by this slot since last restocking
	 *
	 * @return the profit made by the slot thus far
	 */
    public double getStoredProfit()
    {
        return storedProfit;
    }
	
	
	/**
	 * This method resets the stored profit back to 0.0
	 *
	 *
	 */
    public void clearStoredProfit()
    {
        storedProfit = 0.0;
    }
	
	

	
	public double getPrice() { return price; }
	
	public ArrayList<VM_Item> getItems() { return items; }
	
    /** the item held by this slot */
    private VM_Item item;
    /** the number of items sold by this slot since last restocking or repricing */
	private int slotItemSold;
	/** the slot's item name */
    private String slotItemName;
    /** the stock count of the slot from which this slot was copied */
    private int copy_stockCount;
    /** profit that this slot has collected */
    private double storedProfit;
    /** max capacity of this slot */
    private final int MAX;
	/** the per piece price of the items */
	private double price;
	/** the items in this slot */
    private ArrayList<VM_Item> items;
}