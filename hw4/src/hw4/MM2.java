package hw4;

import java.util.LinkedList;

public class MM2 {
	public LinkedList<Request> queue;
	public Request serverA;
	public Request serverB;
	
	public MM2(){
		//initalizes a new MM1 Server
		queue = new LinkedList<Request>();
		serverA = null;
		serverB = null;
	}
	
	public int birth(Request r){
		//Birth event: add request to queue (0), server A (1), or server B (2), return to generate death
		if(serverA == null){
			serverA = r;
			return 1;
		}
		else if(serverB == null){
			serverB = r;
			return 2;
		}
		else{
			queue.add(r);
			return 0;
		}
	}
	
	public Request death1(){
		//Death event: remove event from server A, serve next request
		
		Request dead = serverA;
		
		if(queue.isEmpty()){
			serverA = null;
		}
		else{
			serverA = queue.remove();
		}
		
		return dead;
	}
	
	public Request death2(){
		//Death event: remove event from server B, serve next request
		
		Request dead = serverB;
		
		if(queue.isEmpty()){
			serverB = null;
		}
		else{
			serverB = queue.remove();
		}
		
		return dead;
	}
}
