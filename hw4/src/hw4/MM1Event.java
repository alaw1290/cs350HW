package hw4;

import java.util.Random;

public class MM1Event {
	private double Systemlambda;
	private double loggingRate;
	private double CPUserviceRate;
	private double NetserviceRate;
	private double DiskserviceRate;
	private Random rand;
	
	public MM1Event(double lamb, double lr, double CPU, double Net, double Disk){
		// Constructor: sets arrival, service, and logging rate of each system component
		Systemlambda = lamb;
		loggingRate = lr;
		CPUserviceRate = CPU;
		NetserviceRate = Net;
		DiskserviceRate = Disk;
		rand = new Random();
	}
	
	private double expDist(double lambda){
		// Input lambda (interarrival time) to return exponentially distributed values
		double result = -(Math.log(1-rand.nextDouble())) / lambda;
		return result;
	}
	
	public static double Zrand(double mean, double stdev){
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
		double sum = 0;
		for(int j = 0; j < 30; j++){
			sum  = sum + uniformList[new Random().nextInt(uniformList.length)];
		}
		return sum;
	}
	
	public Event nextSystemLog(double currentTime){
		// Returns time of next system log event
		Event c = new Event(0,currentTime + expDist(loggingRate));
		return c;
	}
	
	public Event nextSystemBirth(double currentTime){
		// Returns time of next system birth event (System -> CPU)
		Event a = new Event(1,currentTime + expDist(Systemlambda));
		return a;
	}
	
	
	public Event nextCPU1Death(double currentTime, int flag){
		// Returns time of next CPU A death event (CPU -> System/Net/Disk)
		if(flag == 0){
			Event b = new Event(21,currentTime + expDist(CPUserviceRate));
			return b;
		}
		else{
			//CPU service time is uniformly distributed between 1 and 39 msec
			Event b = new Event(21,currentTime + (0.001 + Math.random()*0.038));
			return b;
		}
	}
	
	public Event nextCPU2Death(double currentTime, int flag){
		// Returns time of next CPU B death event (CPU -> System/Net/Disk)
		if(flag == 0){
			Event b = new Event(22,currentTime + expDist(CPUserviceRate));
			return b;
		}
		else{
			//CPU service time is uniformly distributed between 1 and 39 msec
			Event b = new Event(22,currentTime + (0.001 + Math.random()*0.038));
			return b;
		}
	}

	public Event nextNetDeath(double currentTime, int flag){
		// Returns time of next CPU death event (Net -> CPU)
		if(flag == 0){
			Event b = new Event(3,currentTime + expDist(NetserviceRate));
			return b;
		}
		else{
			//Disk I/O service time is normally distributed with mean of 100 msec and standard deviation of 30 msec (but never negative)
			
			double normval = Zrand(0.1,0.03);
			while(normval < 0){
				normval = Zrand(0.1,0.03);
			}
			Event b = new Event(3,currentTime + normval);
			return b;
		}
	}

	public Event nextDiskDeath(double currentTime, int flag){
		// Returns time of next CPU death event (Disk -> Net/CPU)
		if(flag == 0){
			Event b = new Event(4,currentTime + expDist(DiskserviceRate));
			return b;
		}
		else{
			//Network service time is constant and equal to 25 msec
			Event b = new Event(4,currentTime + 0.025);
			return b;
		}
	}
}
