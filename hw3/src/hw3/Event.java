package hw3;

public class Event implements Comparable<Event>{
	public int type;
	public double time;
	
	public Event(int Type, double Time){
		type = Type;
		time = Time;
	}
	
	@Override
	public int compareTo(Event e) {
		double comparedTime = e.time;
		if (this.time > comparedTime) {
			return 1;
		} else if (this.time == comparedTime) {
			return 0;
		} else {
			return -1;
		}
	}
	
	public String toString() {
		return "[" + Integer.toString(this.type) + "," + Double.toString(this.time) + "]";
	}
	
}
