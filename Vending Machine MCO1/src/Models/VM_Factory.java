package Models;

import java.util.Scanner;
import java.util.LinkedHashMap;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class VM_Factory
{
	public VM_Regular createVM(Maintenance maintenance)
	{

		return null;
		


	

	}
	public void makeNewVMReg(String vmType,
							 String name,
							 int nSlots,
							 int nItems	)
	{

		Scanner sc = new Scanner(System.in);
		String inputVMType;
		String userHelp;
		String inputQty;
		String vmName;
		VM_Regular vm = null;
		int noOfSlots;
		int noOfItems; // no. of items PER SLOT
		int nOfInitialStandaloneItems;
		int qty;
		double amt;
		int i;
		int j;
		int z;

		/* inventory of VM straight out of the factory */ 
		LinkedHashMap<String, Integer> initialStock = null;
		/* cash reserves of VM straight out of the factory */
		LinkedHashMap<Double, Integer> initialCash = null;
		/* a list of all possible non-special items in the universe of the program */
		LinkedHashMap<String, Integer> possibleItems = Main.getPossibleItems();


		currInd = 0;

		inputVMType = vmType;
		vmName = name;
		noOfSlots = nSlots;
		noOfItems = nItems;

		if (isValidCaps(noOfSlots, noOfItems)) {
			switch (Character.toUpperCase(inputVMType.charAt(0))) {
				case 'R':
					vm = new VM_Regular(vmName, noOfSlots, noOfItems);
					break;
				case 'S':
					vm = new VM_Special(vmName, noOfSlots, noOfItems);
					break;
				default:
					vm = null;
					break;
			}
		} 
		else 
		{
			vm = null;
		}



		initialStock = new LinkedHashMap<String, Integer>();
		initialCash = new LinkedHashMap<Double, Integer>();
		vmName = sc.next();		


		if(vm != null)
		{
			this.currentMachine = vm;
			vmMachines.add(vm);

			currInd += 1;
		}


	}

	private boolean isValidCaps(int noOfSlots, int noOfItems)
	{
		if(noOfSlots < VM_Regular.getMinSLOTS() || noOfItems < VM_Regular.getMaxITEMS())
			return false;

		return true;

	}
	public int getCurrInd() {
		return currInd;
	}
	public VM_Regular getCurrentMachine() {
		return currentMachine;
	}
	public ArrayList<VM_Regular> getVmMachines() {
		return vmMachines;
	}
	private ArrayList<VM_Regular> vmMachines;
	private VM_Regular currentMachine;
	private int currInd;

}
