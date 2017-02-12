import java.util.*;
import java.io.*;
class Apriori{
	public static HashSet<String> candidates = new HashSet<String>();
	public static HashMap<Integer,HashMap<HashSet<String>,Double>> frequentItemSets=new HashMap<Integer,HashMap<HashSet<String>,Double>>();
	public static int numTransactions=0;
	public static LinkedList<HashSet<String>> transactions= new LinkedList<HashSet<String>>();

	/*========================================================
	getCandidates: Gets all the possible candidates.
	----------------------------------------------------------
	String filePath: path of the file with the dataset
	double threshold: mins of support needed to pass.
	==========================================================*/
	public static void getCandidates(String filePath,double threshold){
		HashMap<HashSet<String>,Double> l1=new HashMap<HashSet<String>,Double>();
		BufferedReader reader=null;
		String line=null;
		try{
			//opens file with dataset
			FileReader file = new FileReader(filePath);
			reader= new BufferedReader(file);
			while((line = reader.readLine()) != null){
				String[] items=line.split(",");
				//reads dataset and adds values to candidate set and L1 itemset
				for(String item:items){
					if(candidates.contains(item)){
						double count=l1.get(new HashSet<String>(Arrays.asList(item))) + 1.0;
						l1.put(new HashSet<String>(Arrays.asList(item)),count);
					}
					else{
						candidates.add(item);
						l1.put(new HashSet<String>(Arrays.asList(item)),1.0);
					}
				}
				numTransactions++;
			}
			reader.close();

			HashSet<HashSet<String>> itemsNotSupported=new HashSet<HashSet<String>>();
			for(HashSet<String> key:l1.keySet()){
				double support=l1.get(key)/numTransactions;
				if(support<threshold){
					itemsNotSupported.add(key);
				}
				else{
					l1.put(key,support);
				}
				
			}

			for(HashSet<String> item:itemsNotSupported){
				l1.remove(item);
				for (String i : item) {
		        	candidates.remove(i);
		      	} 
			}

			System.out.println("L1 = "+l1.keySet());

			frequentItemSets.put(1,l1);

		}
		catch (IOException e){
			System.out.println("Error: Cannot readfile");
		}
	}

	/*========================================================
	getCombinations: Gets the different combinations of the 
	candidates.
	----------------------------------------------------------
	List<String> items: items that are candidates
	int itemSetSize: size of the item sets needed.
	==========================================================*/

	public static List<List<String>> getCombinations(List<String> items, int itemSetSize) {

	    if (0 == itemSetSize || items.isEmpty()) {
	        return Collections.singletonList(Collections.<String> emptyList());
	    }

	    //holds the all te combinations
	    List<List<String>> combinations = new LinkedList<List<String>>(); 

	    String item = items.iterator().next();

	    List<String> subSet = new LinkedList<String>(items);
	    subSet.remove(item);

	    List<List<String>> subSetCombination = getCombinations(subSet, itemSetSize - 1);

	    for (List<String> set : subSetCombination) {
	        List<String> newSet = new LinkedList<String>(set);
	        newSet.add(0, item);
	       	if(newSet.size()==itemSetSize){
	       		combinations.add(newSet);
	       	}
	       		
	    }

	    combinations.addAll(getCombinations(subSet, itemSetSize));

    return combinations;
}


	
	public static HashMap<HashSet<String>,Double> getFrequentItemsets(String filePath,double threshold,int itemSetSize){
		HashMap<HashSet<String>,Double> lk=new HashMap<HashSet<String>,Double>();
		BufferedReader reader=null;
		String line=null;

		List<String> itemList=new LinkedList<String>(candidates);
		List<List<String>> itemCombinations=getCombinations(itemList,itemSetSize);
		

		for(int i=0;i<itemCombinations.size();i++){
			List<String>comb=itemCombinations.get(i);
			if(comb.size()!=itemSetSize){
				itemCombinations.remove(i);
			}
		}
		
		try{
			//opens file with dataset
			FileReader file = new FileReader(filePath);
			reader= new BufferedReader(file);
			while((line = reader.readLine()) != null){
				String[] items=line.split(",");
				//reads dataset and adds values to candidate set and L1 itemset
				for(List<String> combination:itemCombinations){
					boolean inItemSet=true;
					for(String comb: combination){
						if(!Arrays.asList(items).contains(comb)){
							inItemSet=false;
						}
					}

					if(inItemSet){
						if(lk.containsKey(new HashSet<String>(combination))){
							double count=lk.get(new HashSet<String>(combination)) + 1.0;
							lk.put(new HashSet<String>(combination),count);
						}
						else{
							lk.put(new HashSet<String>(combination),1.0);
						}

					}
					
				}
			}
			reader.close();

			HashSet<HashSet<String>> itemsNotSupported=new HashSet<HashSet<String>>();
			candidates=new HashSet<String>();
			for(HashSet<String> key:lk.keySet()){
				double support=lk.get(key)/numTransactions;
				if(support<threshold){
					itemsNotSupported.add(key);
					for(String k: key){
						if(!(candidates.contains(k))){
							candidates.add(k);
						}
					}

				}
				else{
					lk.put(key,support);
				}
				
			}

			for(HashSet<String> item:itemsNotSupported){
				lk.remove(item);
			}

			if(lk.size()!=0){
				System.out.println("L"+itemSetSize+ "= "+lk.keySet());
				frequentItemSets.put(itemSetSize,lk);
			}
			

		}
		catch (IOException e){
			System.out.println("Error: Cannot readfile");
		}

		return lk;
	}

	
	public static void aprioriIterations(String filePath,double threshold){
		getCandidates("dataset.txt",0.5);    
		int k=2;
		boolean next=true;
		while(next){
			HashMap<HashSet<String>,Double>lk = getFrequentItemsets(filePath,threshold,k);
			if(lk.size()==0){
				next=false;
			}
			k++;
		}
	}

	public static void main(String args[]){
		aprioriIterations("dataset.txt",0.5);
	}
}