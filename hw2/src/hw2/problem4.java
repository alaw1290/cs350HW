package hw2;
import java.util.Arrays;

public class problem4 {
	
	public static double expDist(int lambda){
		// Input lambda (interarrival time) to return exponentially 
		// distributed values
		double result = -1*Math.log(1-Math.random()) / lambda;
		return result;
	}
	
	public static void main(String[] args) {
		int lamb = 4;
		double[] list = new double[100];
		double sum = 0;
		for(int i = 0; i < 100; i++){
			double result = expDist(lamb);
			sum = sum + result;
			list[i] = result;
		};
		Arrays.sort(list);
		int[] histList = new int[ (int) (list[99]*10) + 1];
		System.out.printf("Mean of Dist  : %f\n",  sum/100);
		System.out.printf("Results Output: \n");
		for(int i = 0; i < 100; i++){
			System.out.println(list[i]);
			histList[(int) (list[i]*10)] += 1;
		};
		System.out.printf("Histogram Bins Output: \n");
		for(int i = 0; i < histList.length; i++){
			System.out.println(histList[i]);
		};
	}
}
