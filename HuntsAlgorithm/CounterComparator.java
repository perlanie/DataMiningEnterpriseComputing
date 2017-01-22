import java.util.Comparator;

public class CounterComparator implements Comparator<Counter>{
	@Override
	public int compare(Counter one, Counter two){
		if((one.getAttrValue()==two.getAttrValue)&&(one.getClassValue()==two.getClassValue)){
			return 0; //returns they are equal
		}
		return 1; 
	}
}