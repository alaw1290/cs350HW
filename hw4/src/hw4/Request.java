package hw4;

public class Request {
	public double SystemArrivalTime;
	public double SystemDepartureTime;
	
	public Request(double currentTime){
		//Constructor: creates a Request to record stats, logs arrival time to initalize
		SystemArrivalTime = currentTime;
	}
	
	public void SystemExit(double currentTime){
		//logs the completion time of the request
		SystemDepartureTime = currentTime;
	}
	
	public double queueLog(){
		//Returns the System Tq of the request
		return SystemDepartureTime - SystemArrivalTime;
	}
	
}
