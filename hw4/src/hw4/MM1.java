package hw4;

import java.util.*;

public class MM1 {
	public LinkedList<Request> queue;
	public Request server;
	
	public MM1(){
		//Initializes a new MM1 Server
		queue = new LinkedList<Request>();
		server = null;
	}
	
	public int birth(Request r){
		//Birth event: add request to queue or server
		
		if(server == null){
			//server is empty
			server = r;
			return 1;
		}
		else{
			//server is occupied
			queue.add(r);
			return 0;
		}
	}
	public Request death(){
		//Death event: remove event from server, serve next request
		
		Request dead = server;
		
		if(queue.isEmpty()){
			//queue is empty, nothing to service
			server = null;
		}
		else{
			//start next request
			server = queue.remove();
		}
		
		return dead;
	}
}
