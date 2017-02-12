import java.util.ArrayList;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;	
import java.util.Random;

class KMeans{
	
	public static ArrayList<Vector<Double>> centroids=new ArrayList<Vector<Double>>();
	public static ArrayList<ArrayList<Vector<Double>>> clusters= new ArrayList<ArrayList<Vector<Double>>>();
	public static ArrayList<Vector<Double>> dataPoints=new ArrayList<Vector<Double>>();
	public static int checkCount=0;
 
	/*========================================================
	getDistance: gets the distance of two points
	----------------------------------------------------------
	Vector<Double> point1: datapoint 1
	Vector<Double> point2: datapoint 2
	==========================================================*/
	public static double getDistance(Vector<Double> point1,Vector<Double> point2){
		double distance=0;
		double sum=0;
		if(point1.size()==point2.size()){
			for(int i=0;i<point1.size();i++){
				sum=sum+Math.pow((point1.get(i)-point2.get(i)),2);
			}
			distance=Math.sqrt(sum);
		}
		return distance;
	}

	/*========================================================
	getDistance: gets the distance of two points
	----------------------------------------------------------
	Vector<Double> point1: datapoint 1
	Vector<Double> point2: datapoint 2
	==========================================================*/
	public static Vector<Double> getCentroid(ArrayList<Vector<Double>> cluster){
		Vector<Double> vectorAverage=new Vector<Double>();
		int vectorSize=cluster.get(0).size();

		for(int p=0;p<vectorSize;p++){
			double sum=0;
			for(int i=0; i<cluster.size();i++){
				Vector<Double> point=cluster.get(i);
				sum=sum+point.get(p);
			}
			vectorAverage.add(sum/cluster.size());
		}

		return vectorAverage;

	}

	/*========================================================
	getDistance: calculates new centroids
	==========================================================*/
	public static void calculateCentroids(){
		for(int i=0;i<centroids.size();i++){
			ArrayList<Vector<Double>> cluster=clusters.get(i);
			Vector<Double> newCentroid = getCentroid(cluster);
			centroids.set(i,newCentroid);
		}
	}

	/*========================================================
	getDataPoints: opens the dataset file to get the datset points
	----------------------------------------------------------
	String filePath: file path of the dataset file
	==========================================================*/
	public static void getDataPoints(String filePath){
		BufferedReader reader=null;
		String line=null;
		try{
			//opens file with dataset
			FileReader file = new FileReader(filePath);
			reader= new BufferedReader(file);
			while((line = reader.readLine()) != null){
				String[] point=line.split(",");
				Vector<Double> dataPoint= new Vector<Double>();
				for(String p: point){
					dataPoint.add(Double.parseDouble(p));
				}
				dataPoints.add(dataPoint);
			}
			reader.close();

		}
		catch (IOException e){
			System.out.println("Error: Cannot readfile");
		}
	}

	/*================================================================
	determineCluster: determines which cluster a datapoint belongs to
	------------------------------------------------------------------
	Vector<Double> dataPoint: datapoint you would like to add to a 
	cluster.
	==================================================================*/
	public static int determineCluster(Vector<Double> dataPoint){
		int clusterNum=0;
		
		double minDistance=getDistance(dataPoint,centroids.get(0));
		
		//compares point to every centroid to find its corresponding cluster
		for(int i=1;i<centroids.size();i++){
			double curDistance=getDistance(dataPoint,centroids.get(i));
			//System.out.println("Next Vect Distance: "+curVectorDistance+", i="+i );
			if(curDistance<minDistance){
				minDistance=curDistance;
				clusterNum=i;
			}
		}
		return clusterNum;
	}

	/*================================================================
	checkConverged:check if the centroids have converged
	------------------------------------------------------------------
	ArrayList<Vector<Double>> newCentroids: new centroids
	ArrayList<Vector<Double>>oldCentroids: previous centroids
	==================================================================*/
	public static boolean checkConverged(ArrayList<Vector<Double>> newCentroids, ArrayList<Vector<Double>>oldCentroids){
		double distance=0;
		for(int i=0;i<newCentroids.size();i++){
			distance=distance+getDistance(newCentroids.get(i),oldCentroids.get(i));
		}
		distance=distance/newCentroids.size();
		
		boolean converged=false;

		if(distance<=0.01){
			checkCount++;
		}
		else{
			checkCount=0;
		}

		if(checkCount==5){
			converged=true;
		}

		return converged;
		
		
	}

	/*================================================================
	kMeansClustering: clusters the datapoints into k clusters
	------------------------------------------------------------------
	int k: number of clusters
	==================================================================*/
	public static void kMeansClustering(int k){
		//create arraylists for the clusters
		for(int c=0;c<k;c++){
			clusters.add(new ArrayList<Vector<Double>>());
		}
		//gets random points for beginning centroids
		
		while(centroids.size()<k){
			Random rand = new Random();
			int index = rand.nextInt(dataPoints.size()-1);
			if(!centroids.contains(dataPoints.get(index))){
				centroids.add(dataPoints.get(index));
			}
		}

		int numIterations=0;
		boolean converged=false;

		while(!converged){
			ArrayList<Vector<Double>> oldCentroids= new ArrayList<Vector<Double>>(centroids);
			System.out.println("Old centroids = "+centroids);
			//goes through each point and determines its corresponding cluster
			for(int index=0;index<dataPoints.size();index++){
				int clusterNum=determineCluster(dataPoints.get(index));
				clusters.get(clusterNum).add(dataPoints.get(index));
			}
			//determines new centroids
			calculateCentroids();
			System.out.println("New centroids = "+centroids);

			System.out.println("\n===================================");
			converged=checkConverged(centroids,oldCentroids);
			numIterations++;
		}
		System.out.println("numIterations = "+numIterations);
	}


	public static void  main(String[]args){
		String filePath="dataset.txt";
		getDataPoints(filePath);
		kMeansClustering(4);



	}

}







