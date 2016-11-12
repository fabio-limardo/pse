package pse.adp.agent;

import java.io.IOException;

import com.pi4j.io.gpio.GpioController;

import pse.adp.abstraction.Event;
import pse.adp.abstraction.ExternalEvent;
import pse.adp.abstraction.SpecializedAgent;
import pse.adp.common.EventType;
import pse.adp.deviceImpl.Led;
import pse.adp.deviceInterface.Light;
import pse.adp.messageService.Outbox;

public class HeadLight extends SpecializedAgent {

	public static final  int THRESHOLD = 120 ;
	private enum State {ON,OFF}
	private State currentState = State.OFF; 
	private Light led; 
	private boolean belowThreshold = false;
	
	public HeadLight(GpioController gpio) {
		super();
		eventType.add(EventType.LIGHT);
		led = new Led(4,gpio);
		
	}
	@Override
	protected void processEvent(Event ev) {
		if(ev instanceof  ExternalEvent){
			if(((ExternalEvent) ev).getValue()<THRESHOLD){
				belowThreshold = true;
				//sendMsgTo(logger, new JsonMsg(new JsonService().createMessage(Context.RASPBERY, content, value)));
				//outbox.getBuffer().add(jService.createMessage(Context.ARDUINO,EventType.LIGHT,"ON",1));
			}else{
				belowThreshold = false;
				//System.out.println("Light ON");
				//System.out.println("barbossa");
			}
			switch (currentState){
			case ON:
				if(!belowThreshold){
					try {
						led.switchOff();
						currentState = State.OFF;
					} catch (IOException ex){}
				}
				break;
			case OFF:
				if(belowThreshold){
					try {
						led.switchOn();
						currentState = State.ON;
					} catch (IOException ex){}
				}
				break;
			}
		}
		
		
	}

}
