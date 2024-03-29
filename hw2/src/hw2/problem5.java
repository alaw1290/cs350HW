package hw2;
import java.util.Arrays;
import java.util.Random;

public class problem5 {
	
	public static double[] Zrand(double mean, double stdev, int n){
		mean = mean / 30;
		double var = (stdev * stdev) / 30;
		double bound = var * 12;
		bound = Math.pow(bound, 0.5);
		double lowerBound = mean - (bound / 2);
		double upperBound = mean + (bound / 2);
		double[] uniformList = new double[200];
		for(int i = 0; i < 200; i++){
			uniformList[i] = lowerBound + (Math.random() * (upperBound - lowerBound));
		}
		
		double[] normList = new double[n];
		for(int i = 0; i < n; i++){
			double sum = 0;
			for(int j = 0; j < 30; j++){
				sum  = sum + uniformList[new Random().nextInt(uniformList.length)];
			}
			normList[i] = sum;
		}
		
		return normList;
	}
	
	public static void main(String[] args) {
		System.out.println("Standard Normal Distribution: \n");
		double[] standard = Zrand(0,1,100);
		Arrays.sort(standard);
		for(int i = 0; i < 100; i++){
			System.out.println(standard[i]);
		};

		System.out.println("\nNormal Distribution w/ Mean: 72 Std Dev: 16:\n");
		
		double[] partB = Zrand(72,16,100);
		Arrays.sort(partB);
		for(int i = 0; i < 100; i++){
			System.out.println(partB[i]);
		};
		
	}

}
