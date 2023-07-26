import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.InputMismatchException;

public class VM_Factory
{
	public VM_Regular createVM(Maintenance maintenance)
	{
	Scanner sc = new Scanner(System.in);
	String input;
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

	userHelp = "(\033[1;33mEnter 'Y' to confirm prompt\033[0m)";
	
	
	
	while(true) 
	{
		System.out.print(	"\t\033[1;36m[C]\033[0m Create a New Vending Machine\n" +
							"\t\033[1;36m[E]\033[0m Exit Factory\n" +
							">> ");
		input = sc.next();
			
		if(input.equalsIgnoreCase("C"))
		/* Create a Vending Machine */
		while(true) 
		{
			System.out.print("\t\033[1;36m[R]\033[0m Regular Vending Machine\n\t\033[1;36m[S]\033[0m Special Vending Machine\n>> ");
			input = sc.next();
			if( input.equalsIgnoreCase("R") || input.equalsIgnoreCase("S") )
			{
				Main.displayPossibleItems();
				
				/* clearing away old vending machine, if any */
				vm = null;
				
				/* blanks factory inventory */
				initialStock = new LinkedHashMap<String, Integer>();
						
				/* blanks factory cash reserves */
				initialCash = new LinkedHashMap<Double, Integer>();
				
				while(true)
				{	
					/* Setting VM's No. of Slots and Max No. of Items per Slot */
					try
					{
						System.out.println();
						System.out.println("\033[1;33mPlease indicate the details below: \033[0m" );
						
						System.out.print("Name of this vending machine: \n>> ");
						vmName = sc.next();
						
						System.out.print("No. of slots (\033[1;36mmin. of 8\033[0m)\n>> ");
						noOfSlots = sc.nextInt();
						
						System.out.print("Max. items per slot (\033[1;36mmin. of 10\033[0m)\n>> ");
						noOfItems = sc.nextInt();
						
						if(noOfSlots < VM_Regular.getMinSLOTS() || noOfItems < VM_Regular.getMaxITEMS())
							System.out.println("\033[1;38;5;202m-ERROR: PARAMETER(S) TOO SMALL\033[0m");
						else
						{
							if(input.equalsIgnoreCase("R"))
								vm = new VM_Regular(vmName, noOfSlots, noOfItems);
							else if(input.equalsIgnoreCase("S"))
								vm = new VM_Special(vmName, noOfSlots, noOfItems);
							break;
						}
					}
					catch (InputMismatchException e)
					{
							System.out.println("\033[1;38;5;202m-ERROR: NON-INTEGER INPUT\033[0m");
					}
				}
						
						
				
				/* Requesting Initial Stocks */
				while(true)
				try
				{
					nOfInitialStandaloneItems = 0;
					for( String s : initialStock.keySet() )
						if( Main.getPossibleItems().get(s) != null &&  Main.getPossibleItems().get(s) == 1 )
							nOfInitialStandaloneItems++;
					
					/* Prevents factory from adding more STANDALONE item types than regular slots (which are reserved for standalone items) */
					if(vm.getSlots().length == nOfInitialStandaloneItems)
						break;

					System.out.print("Specify initial stocks, \033[1;32m<name> <number>\033[0m "+ userHelp + "\n>> ");
					input = sc.next();
					if( input.equalsIgnoreCase("Y" )) 
						break;
					inputQty = sc.next();
					
					qty = Integer.parseInt(inputQty);
								
					if(qty >= 0)
						if( possibleItems.get( input.toUpperCase() ) != null )
							initialStock.put( input.toUpperCase(), qty );
						else
							System.out.println("\033[1;38;5;202m-ERROR: UNKNOWN ITEM CLASS\033[0m");		
					else
						System.out.println("\033[1;38;5;202m-ERROR: NEGATIVE QUANTITIES NOT ALLOWED\033[0m");		
				}
				catch(NumberFormatException e)
				{
					System.out.println("\033[1;38;5;202m-ERROR: ITEM CLASS DOES NOT EXIST\033[0m");
				}
					
					
				/* Requesting Initial Cash Reserves */
				while(true)
				try
				{	
					System.out.print("Specify initial cash reserves \033[1;32m<cash> <number>\033[0m"+ userHelp + "\n>> ");
					input = sc.next();
					if( input.equalsIgnoreCase("Y"))
						break;
					inputQty = sc.next();	
								
					amt = Double.parseDouble(input);
					qty = Integer.parseInt(inputQty);
								
					if(qty >= 0)
						if( Money.getValToStr().get(amt) != null )
							initialCash.put(amt, qty);		
						else
							System.out.println("\033[1;38;5;202m-ERROR: INVALID DENOMINATION\033[0m");		
					else
						System.out.println("\033[1;38;5;202m-ERROR: NEGATIVE QUANTITIES NOT ALLOWED\033[0m");		
				}
				catch(NumberFormatException e)
				{
					System.out.println("\033[1;38;5;202m-ERROR: INPUT MUST BE <DOUBLE> <INTEGER>\033[0m");
				}
				
				
				
				/* Initializing Vending Machine Stocks */
				i = 0;
				j = 0;
				if( initialStock.size() > 0 )
				for( String s : initialStock.keySet() )
				{	
					if( Main.getPossibleItems().get(s) == 1 )
					{
						for(z = 0; z < initialStock.get(s); z++)
							vm.addItemStock( maintenance.generateItem( s ) , i );
						i++;
					}
					else
						if( vm instanceof VM_Special )
						{
							for(z = 0; z < initialStock.get(s); z++)
								vm.addItemStock( maintenance.generateItem( s ) , j );
							j++;
						}
						else
							System.out.println("\033[1;38;5;202m-NOTE: A NON-STANDALONE ITEM WAS NOT ADDED TO VM_Regular!\033[0m");
				}
					
				/* Initializing Vending Machine Cash Reserves */
				if(vm != null)
				for( Double d : initialCash.keySet() )
					vm.getCurrentMoney().addBillsOrCoins(d, initialCash.get(d)); 
					
				sc = null;
				return vm;
			}
			else
				System.out.println("\033[1;38;5;202m-ERROR: CHOOSE BETWEEN 'R' AND 'S' ONLY\033[0m");
		}
		/* END OF CREATING A NEW VENDING MACHINE */
		
		
		else if(input.equalsIgnoreCase("E"))
			break;
	}
	
	sc = null;
	return null;
	}
}