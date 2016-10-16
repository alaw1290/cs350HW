package hw3;

public class Event implements Comparable<Event>{
	public int type;
	public double time;
	
	public Event(int Type, double Time){
		type = Type;
		time = Time;
	}
	
	public int compareTo(Event e) {
		if (this.time > e.time) {
			return 1;
		} else if (this.time == e.time) {
			return 0;
		} else {
			return -1;
		}
	}
	
	public String toString() {
		return "[" + Integer.toString(this.type) + "," + Double.toString(this.time) + "]";
	}
	
}
