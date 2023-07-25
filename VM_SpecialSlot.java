import java.util.Scanner;



/** The class VM_Slot represents a special slot of the VM
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
  * @version 1.0
  */
public class VM_SpecialSlot extends VM_Slot
{
    /**
     * This instructor Initializes a slot's item and capacity based on the given parameters.
     * Every slot can contain only one actual copy of the item it is set to hold
     * 
     * @param capacity the maximum no. of items that this slot can hold
     */
    public VM_SpecialSlot(int capacity)
	{
        super(capacity);
    }
	
	
	/**
     * This copy constructor initializes itself with another VM_Slot and inherit
     * its attributes and data.
     * 
     * @param copy another VM_Slot object
     * 
     */
	public VM_SpecialSlot(VM_Slot copy)
    {
        super(copy);
    }
}