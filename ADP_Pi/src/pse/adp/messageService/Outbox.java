package pse.adp.messageService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.json.JsonObject;

public class Outbox {
	List<JsonObject> buffer;
	
	public Outbox(){
		buffer = Collections.synchronizedList(new ArrayList<JsonObject>());
	}
	 
	
	public List<JsonObject> getBuffer(){
		return buffer;
	}
}
