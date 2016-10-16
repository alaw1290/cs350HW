package hw3;

public class Request {
	public double arrivalTime;
	public double serviceStart;
	public double departureTime;
	
	public Request(double currentTime){
		//Constructor: creates a Request to record stats, logs arrival time to initalize
		arrivalTime = currentTime;
	}
	
	public void serviceStart(double currentTime){
		//logs the service start time
		serviceStart = currentTime;
	}
	
	public void finished(double currentTime){
		//logs the completion time of the request
		departureTime = currentTime;
	}
	
	public double waitingLog(){
		//Returns the Tw of the request
		return serviceStart - arrivalTime;
	}
	
	public double serviceLog(){
		//Returns the Ts of the request
		return departureTime - serviceStart;
	}
	
	public double queueLog(){
		//Returns the Tq of the request
		return departureTime - arrivalTime;
	}
	
	public String toString() {
		return "[" + Double.toString(this.queueLog()) + "," + Double.toString(this.waitingLog()) + "," + Double.toString(this.serviceLog()) + ","+  (this.queueLog() == this.waitingLog() + this.serviceLog())+ "]";
	}
}
