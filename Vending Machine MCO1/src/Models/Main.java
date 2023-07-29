package Models;

import java.util.Scanner;
import java.util.LinkedHashMap;

import java.util.InputMismatchException;

/** This class is a driver that simulates running the MCO1 project
 *  which involves creating a vending machine, ordering from said vending machine,
 *  restocking the vending machine, and purchasing items in the vending machine. There is
 *  also a seperate maintenance feature as such to restock/reprice/replace
  *
  * @author Paul Josef P. Agbuya
  * @author Vince Kenneth D. Rojo
*/



public class Main
{
	/**
	 * Sets all items within the universe of the program
	 *
	 */
	public Main()
	{
		/* a value of 1 means the item is STANDALONE */
		possibleItems.put("CHEESE", 1);
		possibleItems.put("COCOA", 0);
		possibleItems.put("CREAM", 0);
		possibleItems.put("EGG", 1);
		possibleItems.put("KANGKONG", 1);
		possibleItems.put("MILK", 1);
		possibleItems.put("SALT", 0);
		possibleItems.put("SUGAR", 0);
		possibleItems.put("CORNSTARCH", 0);
		possibleItems.put("TOFU", 1);
		possibleItems.put("CHICKEN", 1);
		possibleItems.put("BBQ", 0);
		possibleItems.put("FLOUR", 0);
	}
	
	
	/**
	 * Main method
	 *
	 * @param args ...
	 */
    public static void main(String[] args)
	{
		
		Main mainHelp = new Main();
		Maintenance maintenance = new Maintenance(Main.getPossibleItems());
		SellingOperator sellingOperator = null;
		Scanner sc = new Scanner(System.in);
		String input;
		String userHelp;
		String inputQty;
		String vmName;
		VM_Regular vm;
		VM_Draw vmDraw = null;
		VM_Factory vmFactory;
		int noOfSlots;
		int noOfItems; // no. of items PER SLOT
		int qty;
		double amt;
		int i;
		
		/* Money objects for the SellingOperator */
		Money duplicate = new Money();
		Money payment = new Money();
		Money change = new Money();
		
		Order order = new Order();

		userHelp = "(\033[1;33m" + "Enter 'Y' to confirm prompt" + "\033[0m)";
		mainHelp.displayWelcome();
		
		vmFactory = new VM_Factory();
		
		
		while(true)
		{
			vm = vmFactory.createVM(maintenance);
			
			if(vm == null)
				break;
	
			
			vmDraw = new VM_Draw(vm);
			vmDraw.drawAndSetVM();
			maintenance.updateStockedInfos(vm);
			System.out.println("\033[1;32mVENDING MACHINE CREATION SUCCESSFUL!\033[0m\n");

			while(true) 
			{	
				System.out.print("\t\033[1;36m[V]\033[0m Vending Features\n\t\033[1;36m[M]\033[0m Maintenance Features\n\t\033[1;36m[C]\033[0m Create a New Vending Machine\n>> ");
				input = sc.next();
					
				/* Vending Features */
				if(input.equalsIgnoreCase("V"))
				{
					if(vmDraw != null)
					{
						vmDraw.updateVM(vm);
						vmDraw.drawAndSetVM();
					}
					if( vm instanceof VM_Special ) {
						sellingOperator = new SpecialSellingOperator();
						((SpecialSellingOperator)sellingOperator).sellingOperation( vm, duplicate, payment, change, order);
					}
					else if( vm instanceof VM_Regular ) {
						sellingOperator = new SellingOperator();
						sellingOperator.sellingOperation( vm, duplicate, payment, change, order);
					}
					
				}
				/* Maintenance Features */
				else if(input.equalsIgnoreCase("M"))
				{
					while(true)
					{

						System.out.print(	"\t\033[1;36m[1]\033[0m Restock Items\n" +
											"\t\033[1;36m[2]\033[0m Replace/Fill with Items\n" +
											"\t\033[1;36m[3]\033[0m Replenish Money\n" +
											"\t\033[1;36m[4]\033[0m Set Price\n" +
											"\t\033[1;36m[5]\033[0m Collect Money\n" +
											"\t\033[1;36m[6]\033[0m Order History\n" +
											"\t\033[1;36m[7]\033[0m Stocked Information\n" +
											"\t\033[1;36m[8]\033[0m Exit\n" +
											">> ");
						input = sc.next();
						if(input.equalsIgnoreCase("1"))
						{
							vmDraw.updateVM(vm);
							vmDraw.drawAndSetVM();
							vm.emptyOrderHistory();
							maintenance.restockItems(vm);
							vmDraw.updateVM(vm);
							vmDraw.drawAndSetVM();
						}
						else if(input.equalsIgnoreCase("2"))
						{
							vmDraw.updateVM(vm);
							vmDraw.drawAndSetVM();
							maintenance.replaceItemStock(vm);
							vmDraw.updateVM(vm);
							vmDraw.drawAndSetVM();
						}
								
						else if(input.equalsIgnoreCase("3"))
							maintenance.replenishDenominations(vm);
						else if(input.equalsIgnoreCase("4"))
						{
							vmDraw.updateVM(vm);
							vmDraw.drawAndSetVM();
							maintenance.repriceItems(vm);
							vmDraw.updateVM(vm);
							vmDraw.drawAndSetVM();
						}
								
						else if(input.equalsIgnoreCase("5"))
							maintenance.collectCashReserves(vm);
						else if(input.equalsIgnoreCase("6"))
							vm.displayOrderHistory();
						else if(input.equalsIgnoreCase("7"))
							vm.displayAllStockInfo();
						else if(input.equalsIgnoreCase("8"))
							break;
						else
							System.out.println("\033[1;38;5;202mNOT IN OPTIONS!\033[0m");
						}
					}
					
					/* triggers creation of a new Vending Machine */
					else if(input.equalsIgnoreCase("C"))
						break;
					/* catcher for non-options */
					else
						System.out.println("\033[1;38;5;202mNOT IN OPTIONS!\033[0m");
			}
		}
		
		sc.close();
    }
	
	
	/**
	 * Lists all items available in the program
	 *
	 */
	public static void displayPossibleItems()
	{
		System.out.println("Here are your available options to set an item to your vending Machine");
		for(String stringTemp : possibleItems.keySet())
		{
			System.out.println("\033[1;33m" + stringTemp + "\033[0m");
		}

	}

	/**
	 * Displays an ASCII art of the Welcome message for initial start of the program
	 */
	private void displayWelcome()
	{
        System.out.print("\033[1;33m");
        System.out.println("\t\t\t\t    :::       ::: :::::::::: :::         ::::::::   ::::::::  ::::    ::::  :::::::::: ");
        System.out.println("\t\t\t\t    :+:       :+: :+:        :+:        :+:    :+: :+:    :+: +:+:+: :+:+:+ :+:        ");
        System.out.println("\t\t\t\t    +:+       +:+ +:+        +:+        +:+        +:+    +:+ +:+ +:+:+ +:+ +:+        ");
        System.out.println("\t\t\t\t    +#+  +:+  +#+ +#++:++#   +#+        +#+        +#+    +:+ +#+  +:+  +#+ +#++:++#   ");
        System.out.println("\t\t\t\t    +#+ +#+#+ +#+ +#+        +#+        +#+        +#+    +#+ +#+       +#+ +#+        ");
        System.out.println("\t\t\t\t     #+#+# #+#+#  #+#        #+#        #+#    #+# #+#    #+# #+#       #+# #+#        ");
        System.out.println("\t\t\t\t      ###   ###   ########## ##########  ########   ########  ###       ### ########## ");

        System.out.print("\033[0m");

        System.out.println();
        System.out.println("\t\t\t\t\t\t\t\t\t ____   __ ");
        System.out.println("\t\t\t\t\t\t\t\t\t(_  _) /  \\ ");
        System.out.println("\t\t\t\t\t\t\t\t\t  )(  ( () )");
        System.out.println("\t\t\t\t\t\t\t\t\t (__)  \\__/ ");

        System.out.println();


     
        System.out.println("\t\t\t\t\033[1;44m\\ \\     /                   | _)                   \\  |               |     _)              \033[0m");
        System.out.println("\t\t\t\t\033[1;44m \\ \\   /    _ \\  __ \\    _` |  |  __ \\    _` |    |\\/ |   _` |   __|  __ \\   |  __ \\    _ \\ \033[0m");
        System.out.println("\t\t\t\t\033[1;44m  \\ \\ /     __/  |   |  (   |  |  |   |  (   |    |   |  (   |  (     | | |  |  |   |   __/ \033[0m");
        System.out.println("\t\t\t\t\033[1;44m   \\_/    \\___| _|  _| \\__,_| _| _|  _| \\__, |   _|  _| \\__,_| \\___| _| |_| _| _|  _| \\___| \033[0m");
        System.out.println("\t\t\t\t\033[1;44m                                        |___/                                               \033[0m");
        System.out.print("\033[0m");
        System.out.println();
        System.out.println();

    }
	
	
	/**
	 * Gets the list of all item subclasses available in the program
	 *
	 * @return the list of all possible items
	 */
	public static LinkedHashMap<String, Integer> getPossibleItems()
	{
		return possibleItems;
	}
	
	
	

	/* a list of all possible items in the universe of the program */
	private static LinkedHashMap<String, Integer> possibleItems = new LinkedHashMap<String, Integer>();
}

