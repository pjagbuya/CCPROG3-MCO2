import java.util.ArrayList;

public class AppModel
{
	public AppModel() {
		factory = new VM_Factory;
		vendingMachines = ArrayList<VM_Regular>();
	}
	
	public VM_Regular getLatestVM() {
		return vendingMachines.get( vendingMachines.size() - 1 );
	}
	
	public VM_Factory getFactory() {
		return factory;
	}
	
	public void addVM(VM_Regular vm) {
		vendingMachines.add( vm );
	}
	

	VM_Factory factory;
	ArrayList<VM_Regular> vendingMachines;
}