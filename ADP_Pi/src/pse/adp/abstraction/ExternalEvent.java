package pse.adp.abstraction;

import javax.json.JsonObject;

import pse.adp.messageService.JsonService;

public class ExternalEvent implements Event {

	private String context;
	private String content;
	private float value;
	private JsonObject jObj;
	private JsonService jService = new JsonService();
	
	
	public ExternalEvent(String source, String content, float value){
		this.context = source;
		this.content = content;
		this.value = value;
	}
	
	public ExternalEvent(JsonObject jObj){
		this.jObj = jObj;
		this.context = jService.getContext(jObj);
		this.content = jService.getContent(jObj);
		this.value = jService.getValue(jObj);

	}

	public String getContext() {
		return context;
	}

	public String getContent() {
		return content;
	}

	public float getValue() {
		return value;
	}
	
	public JsonObject getjObj() {
		return jObj;
	}

}
