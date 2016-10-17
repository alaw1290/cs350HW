package hw4;

import java.util.*;

public class Scheduler {
	private PriorityQueue<Event> Schedule;
	private MM1Event emitter;
	
	public Scheduler(double lamb, double lr, double CPU, double Net, double Disk, double simulationLength){
		//Constructor: sets arrival, logging, and respective service rates 
		emitter = new MM1Event(lamb, lr, CPU, Net, Disk);
		Schedule = new PriorityQueue<Event>();
		
		//populate Events list till simulation time is reached
		double eventTime = 0;
		while(eventTime < simulationLength){
			//add Births 
			Event birth = emitter.nextSystemBirth(eventTime);
			Schedule.add(birth);
			eventTime = birth.time;
		}
		
		eventTime = 0;
		while(eventTime < simulationLength){
			//add Logs
			Event log = emitter.nextSystemLog(eventTime);
			Schedule.add(log);
			eventTime = log.time;
		}
		
	}
	
	public void insertDeathEvent(double serviceStart, int type){
		//inserts a death event for a request in the schedule 
		
		//CPU A type 21
		if(type == 21){
			Event death = emitter.nextCPU1Death(serviceStart);
			Schedule.add(death);
		}
		
		//CPU B type 22
		if(type == 22){
			Event death = emitter.nextCPU2Death(serviceStart);
			Schedule.add(death);
		}
		
		//Net type 3
		if(type == 3){
			Event death = emitter.nextNetDeath(serviceStart);
			Schedule.add(death);
		}
		
		//Disk type 4
		if(type == 4){
			Event death = emitter.nextDiskDeath(serviceStart);
			Schedule.add(death);

		}
		
		//
	}
	
	public Event nextEvent(){
		//Returns the next event 
		Event x = Schedule.remove();
		return x;
	}
}