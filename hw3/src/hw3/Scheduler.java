package hw3;

import java.util.*;


public class Scheduler {
	private PriorityQueue<Event> Schedule;
	private MM1Event emitter;
	
	public Scheduler(double lamb, double sr, double lr, double simulationLength){
		//Constructor: sets arrival, service, and logging rates 
		emitter = new MM1Event(lamb, sr, lr);
		Schedule = new PriorityQueue<Event>();
		
		//populate Events list till simulation time is reached
		double eventTime = 0;
		while(eventTime < simulationLength){
			//add Births 
			Event birth = emitter.nextBirth(eventTime);
			Schedule.add(birth);
			eventTime = birth.time;
		}
		
		eventTime = simulationLength/2;
		while(eventTime < simulationLength){
			//add Logs
			Event log = emitter.nextLog(eventTime);
			Schedule.add(log);
			eventTime = log.time;
		}
		
	}
	
	public void insertDeathEvent(double serviceStart){
		//inserts a death event for a request in the schedule 
		Event death = emitter.nextDeath(serviceStart);
		Schedule.add(death);
	}
	
	public Event nextEvent(){
		//Returns the next event 
		return Schedule.remove();
	}
}
