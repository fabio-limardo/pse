package pse.adp.agent;

import pse.adp.abstraction.Event;
import pse.adp.abstraction.ExternalEvent;
import pse.adp.abstraction.JsonMsg;
import pse.adp.abstraction.SpecializedAgent;
import pse.adp.common.Context;
import pse.adp.common.EventType;
import pse.adp.messageService.Outbox;

public class Temperature extends SpecializedAgent {

	public static final  String NEWVALUE = "NEWVALUE" ;
	Logger logger;
	private float currentTemperature = 10;
	private Sender sender;
	
	public Temperature(Sender sender, Logger logger) {
		super();
		this.logger = logger;
		this.sender = sender;
		eventType.add(EventType.TEMPERATURE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void processEvent(Event ev) {
		if(ev instanceof  ExternalEvent){
			if(((ExternalEvent) ev).getContent().equals(NEWVALUE)){
				if(((ExternalEvent) ev).getValue() != currentTemperature)
				sendMsgTo(logger, new JsonMsg(((ExternalEvent) ev).getjObj()));
				sendMsgTo(sender, new JsonMsg(((ExternalEvent) ev).getjObj()));
				
				//outbox.getBuffer().add(jService.createMessage(Context.ARDUINO,EventType.TEMPERATURE,NEWVALUE,((ExternalEvent) ev).getValue()));
			}
			//outbox.getBuffer().add(jService.createMessage(Context.ARDUINO,EventType.LIGHT,"ON",1));
		}		
	}
}
