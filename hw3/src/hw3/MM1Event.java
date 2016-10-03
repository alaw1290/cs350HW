package hw3;

import java.util.Random;

public class MM1Event {
	private double lambda;
	private double serviceRate;
	private double loggingRate;
	private Random rand;
	
	public MM1Event(double lamb, double sr, double lr){
		// Constructor: sets arrival, service, and logging rate
		lambda = lamb;
		serviceRate = sr;
		loggingRate = lr;
		rand = new Random();
	}
	
	private double expDist(double lambda){
		// Input lambda (interarrival time) to return exponentially distributed values
		double result = (-1*Math.log(1-rand.nextDouble())) / lambda;
		return result;
	}
	
	public Event nextBirth(double currentTime){
		// Returns time of next birth event
		Event nextEvent = new Event(0,currentTime + expDist(lambda));
		return nextEvent;
	}
	
	public Event nextDeath(double currentTime){
		// Returns time of next death event
		Event nextEvent = new Event(1,currentTime + expDist(serviceRate));
		return nextEvent;
	}

	public Event nextLog(double currentTime){
		// Returns time of next log event
		Event nextEvent = new Event(2,currentTime + expDist(loggingRate));
		return nextEvent;
	}
	
}


