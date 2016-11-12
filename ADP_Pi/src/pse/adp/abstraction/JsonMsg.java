package pse.adp.abstraction;

import javax.json.JsonObject;

public class JsonMsg implements Msg {
	
	JsonObject jObj;

	public JsonMsg(JsonObject jObj){
		this.jObj = jObj;
	}

	public JsonObject getjObj() {
		return jObj;
	}

}
