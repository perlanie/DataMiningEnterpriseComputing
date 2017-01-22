import java.util.*;

public class Dataset{


	private ArrayList<HashMap<String,HashMap<String,Counter>>> totalAttrCounts;
	private String[][]dataset;
	private String[][]datasetSchema;
	private int classIndex;

	public Dataset(String[][] datasetSchema, String[][]dataset, int classIndex){
		this.datasetSchema=datasetSchema;
		this.dataset=dataset;
		this.totalAttrCounts=new ArrayList<HashMap<String,HashMap<String,Counter>>>();
		this.classIndex=classIndex;
		
	}

	public void countAttrValues(){
		HashMap<String,HashMap<String,Counter>> attrValues = new HashMap<String,HashMap<String,Counter>> ();

		for(int i=0;i<datasetSchema.length;i++){ //goes through each attribute
			//NOTE: CURRENTLY DOES NOT HANDLE CONTINOUS VALUES
			if(i!=this.classIndex){
				for(String[] dataRow : this.dataset){ //goes through each row of the dataset
					
					//checks if the attribute value is in the HashMap
					if(!attrValues.containsKey(dataRow[i])){
						HashMap<String, Counter> counterHashMap= new HashMap<String,Counter>();
						Counter counter=new Counter(i,dataRow[i],this.classIndex,dataRow[this.classIndex]);
						counter.incrementCount();
						counterHashMap.put(dataRow[this.classIndex],counter);
						attrValues.put(dataRow[i],counterHashMap);
					}
					else{
						HashMap<String, Counter> counterHashMap=attrValues.get(dataRow[i]);
						if(!counterHashMap.containsKey(dataRow[this.classIndex])){
							Counter counter= new Counter(i,dataRow[i],this.classIndex,dataRow[this.classIndex]);
							counter.incrementCount();
							counterHashMap.put(dataRow[this.classIndex],counter);
						}
						else{
							Counter counter=counterHashMap.get(dataRow[this.classIndex]);
							counter.incrementCount();
						}
						
					}
					
				}
				this.totalAttrCounts.add(attrValues);
				attrValues = new HashMap<String,HashMap<String,Counter>> ();
			}
		}

	}

	public ArrayList<HashMap<String,HashMap<String,Counter>>> getTotalAttrCounts(){
		if(this.totalAttrCounts.isEmpty()){
			this.countAttrValues();
		}
		return this.totalAttrCounts;
	}


	public HashMap<String,Integer> getTotalClassCounts(){
		HashMap<String,Integer> classCounts=new HashMap<String,Integer>();
		for(String[] dataRow:this.dataset){
			if(classCounts.containsKey(dataRow[this.classIndex])){
				int count=classCounts.get(dataRow[this.classIndex]);
				count++;
				classCounts.put(dataRow[this.classIndex],count);
			}
		}

		return classCounts;
	}

	public void printTotalAttributeCounts(){
		if(this.totalAttrCounts.isEmpty()){
			this.countAttrValues();
		}
		for(int i=0; i<this.totalAttrCounts.size();i++){
			System.out.println("\nAttribute: "+this.datasetSchema[i][0]);
		 	HashMap<String,HashMap<String,Counter>> attrCountsHashMap=totalAttrCounts.get(i);
		 	
		 	Collection<HashMap<String,Counter>> attrCountsList=attrCountsHashMap.values();
		 	//gets the hash map of the counters for the specified attribute.
		 	for(HashMap<String,Counter> attrCounts: attrCountsList){
		 		//prints the values of the attribute counters 
		 		for(Counter counter: attrCounts.values()){
		 			counter.printCounterValues();
		 		}
		 	
		 	}
		 	
		}
	}


}