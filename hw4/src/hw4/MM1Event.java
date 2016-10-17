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
	
	
	public Event nextCPU1Death(double currentTime){
		// Returns time of next CPU A death event (CPU -> System/Net/Disk)
		Event b = new Event(21,currentTime + expDist(CPUserviceRate));
		return b;
	}
	
	public Event nextCPU2Death(double currentTime){
		// Returns time of next CPU B death event (CPU -> System/Net/Disk)
		Event b = new Event(22,currentTime + expDist(CPUserviceRate));
		return b;
	}

	public Event nextNetDeath(double currentTime){
		// Returns time of next CPU death event (Net -> CPU)
		Event b = new Event(3,currentTime + expDist(NetserviceRate));
		return b;
	}

	public Event nextDiskDeath(double currentTime){
		// Returns time of next CPU death event (Disk -> Net/CPU)
		Event b = new Event(4,currentTime + expDist(DiskserviceRate));
		return b;
	}
}
