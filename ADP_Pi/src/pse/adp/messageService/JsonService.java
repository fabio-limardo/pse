package pse.adp.messageService;

import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;

import pse.adp.abstraction.MsgEvent;

public class JsonService {
	
	private final static String CONTEXT = "CONTEXT";
	private final static String TYPE = "TYPE";
	private final static String CONTENT = "CONTENT";
	private final static String VALUE = "VALUE";
	private final static String DEFAULT_CONTEXT = "rasp";
	private final static String DEFAULT_RECEIVER = "event";

		
	public JsonService(){
		
	}
	
	public JsonObject toJson(String msg){
		JsonReader reader = Json.createReader(new java.io.StringReader(msg));
		JsonObject jObj = reader.readObject();
		return jObj;
	}
	
	public String getContext(JsonObject jObj){
		return jObj.getString(CONTEXT);
	}
	
	public String getType(JsonObject jObj){
		return jObj.getString(TYPE);
	}
	
	public String getContent(JsonObject jObj){
		return jObj.getString(CONTENT);
	}
	
	public float getValue(JsonObject jObj){
		return  Float.parseFloat(jObj.get(VALUE).toString());
	}
	
	public JsonObject createMessage(String context,String type,String content,int value){
		if(context.equals("")){
			JsonObject jObj= Json.createObjectBuilder()
					.add(CONTEXT, DEFAULT_CONTEXT)
					.add(TYPE,type)
					.add(CONTENT, content)
					.add(VALUE, value)
					.build();
			return jObj;
		}
		JsonObject jObj= Json.createObjectBuilder()
				.add(CONTEXT, context)
				.add(TYPE,type)
				.add(CONTENT, content)
				.add(VALUE, value)
				.build();
		return jObj;
			
	}
	
	public JsonObject createMessage(String receiver,String content,int value){
		JsonObject jObj= Json.createObjectBuilder()
				.add(CONTEXT, DEFAULT_CONTEXT)
				.add(TYPE,receiver)
				.add(CONTENT, content)
				.add(VALUE, value)
				.build();
		return jObj;
	}

	public void toEvent(JsonObject jObj){

	}
	
}
