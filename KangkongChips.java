import java.util.ArrayList;


/**
 * The class KangkongChips is a subclass of VM_Item. For this project, it is the so-called "special item."
 *
 */
public class KangkongChips extends VM_Item
{
	/**
	 * Simply uses superclass VM_Item constructor
	 *
	 * @param name the name of this item
	 * @param price price or cost of this item
     * @param calories amount of calories of this item
	 */
	public KangkongChips(String name, 
                   double price, 
                   int calories)
	{ super(name, price, calories); }
	
	
	/**
	 * Adds one ingredient to the list of ingredients comprising these kangkong chips.
	 *
	 */
	public void acceptIngredient(VM_Item ingredient) { ingredients.add(ingredient); }
	
	
	/**
	 * Returns the ingredients used in the making of these kangkong chips.
	 *
	 */
	public ArrayList<VM_Item> getIngredients() { return ingredients; }
	
	
	
	
	/** all ingredients used to make this */
	private ArrayList<VM_Item> ingredients;
}
