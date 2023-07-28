public class Denomination
{
	public Denomination(String name, double value)
	{
		this.value = value;
		this.name = name;
	}
	
	public double getValue()
	{
		return value;
	}
	
	public double getName()
	{
		return name;
	}
	
	
	private double value;
	private String name;
}