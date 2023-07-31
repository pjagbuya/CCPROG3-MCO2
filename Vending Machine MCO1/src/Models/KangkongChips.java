package Models;

import java.util.ArrayList;


/**
 * The class KangkongChips is a subclass of VM_Item.
 * For this project, it is the so-called "special item."
 *
 */
public class KangkongChips extends VM_Item
{
	/**
	 * Instantiates an empty KangkongChips item (no ingredients).
	 *
	 * @param name the name of this item
	 * @param price price or cost of this item
     * @param calories amount of calories of this item
	 */
	public KangkongChips(String name, 
                   double price, 
                   int calories)
	{ 
		super(name, price, calories);
		ingredients = new ArrayList<VM_Item>();
	}
	
	
	/**
	 * Adds one ingredient to the list of ingredients comprising these kangkong chips.
	 *
	 * @param ingredient the ingredients that went into these Kangkong Chips 
	 */
	public void acceptIngredient(VM_Item ingredient) { ingredients.add(ingredient); }
	
	
	/**
	 * Returns the ingredients used in the making of these kangkong chips.
	 *
	 * @return the ingredients that went int these Kangkong Chips.
	 */
	public ArrayList<VM_Item> getIngredients() { return ingredients; }
	
	
	
	
	/** all ingredients used to make these Kangkong Chips */
	private ArrayList<VM_Item> ingredients;
}
