package Models;

/**
 * The interface Generatable gives classes
 * the ability to instantiate
 * VM_Item.
 * 
 * @author Paul Josef P. Agbuya
 * @author Vince Kenneth D. Rojo
 */
public interface Generatable
{
	public VM_Item generateItem(String s);
	public VM_Item generateCustomItem(String s);
}
