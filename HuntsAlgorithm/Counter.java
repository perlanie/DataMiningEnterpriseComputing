public class Counter{

	private int attrIndex;
	private String attrValue;
	private int classIndex;
	private String classValue;
	private int count; 

	public Counter(int attrIndex, String attrValue, int classIndex, String classValue){
		this.attrIndex=attrIndex;
		this.attrValue=attrValue;
		this.classIndex=classIndex;
		this.classValue=classValue;
		this.count=0;
	}

	public int getAttrIndex(){
		return this.attrIndex;
	}
	public String getAttrValue(){
		return this.attrValue;
	}
	public int getClassIndex(){
		return this.classIndex;
	}
	public String getClassValue(){
		return this.classValue;
	}

	public int getCount(){
		return this.count;
	}

	public void setCount(int count){
		this.count=count;
	}

	public void incrementCount(){
		this.count++;
	}

	public void printCounterValues(){
		System.out.println("("+this.attrValue+","+this.classValue+")=>"+this.count);
		// System.out.println("Attribute Index: " + this.attrIndex);
		// System.out.println("Attribute Value: " + this.attrValue);
		// System.out.println("Class Index: " + this.classIndex);
		// System.out.println("Class Value: " + this.classValue);
	}


}