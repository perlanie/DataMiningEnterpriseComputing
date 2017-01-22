import java.lang.Math;
import java.util.*;
public class HuntsAlgorithm{
	public static String[][] cartSchema={
											{"Class","Categorical"},
											{"Size","Categorical"},
											{"Color","Categorical"},
											{"Shape","Class"}
										};
	public static String[][] cartDataSet={
											{"A","Small","Yellow","Round"},
											{"A","Big","Yellow","Round"},
											{"A","Big","Red","Round"},
											{"A","Small","Red","Round"},
											{"B","Small","Black","Round"},	
											{"B","Small","Black","Cube"},
											{"B","Big","Yellow","Cube"},
											{"B","Big","Black","Round"},
											{"B","Small","Yellow","Cube"}
										 };
	public static String[][] id3Schema= {
											{"Shape","Categorical"},
											{"Color","Categorical"},
											{"Odor","Categorical"},
											{"Edible","Class"}
										};
	public static String[][] id3DataSet={
											{"C","B","1","Yes"},
											{"D","B","1","Yes"},
											{"D","W","1","Yes"},
											{"D","W","2","Yes"},
											{"C","B","2","Yes"},	
											{"D","B","2","No"},
											{"D","G","2","No"},
											{"C","U","2","No"},
											{"C","B","3","No"},
											{"C","W","3","No"},
											{"D","W","3","No"}
										};

	public static double calcH(ArrayList<Double>probabilities){
		double sum=0;
		for(Double prob:probabilities){
			sum=sum-(prob*(Math.log(prob)/Math.log(2)));
		}
		return sum;
	}

	public static double calcGain(ArrayList<Double>probabilities){
		double x=0;
		return x;
	}
	public static double getAttrProbabilities(HashMap<String,HashMap<String,Counter>> totalCounts){
		double x=0;
		return x;
	}

	public static double getClassProbabilities(){
		double x=0;
		return x;
	}
	public static void id3(){

	}

	public static void cart(){

	}



	public static void main(String[] args){
		Dataset dataset=new Dataset(id3Schema,id3DataSet,3);
		dataset.getTotalAttrCounts();
		dataset.printTotalAttributeCounts();

	}
}