package pse.adp.agent;

import javax.json.JsonObject;

import com.pi4j.io.gpio.GpioController;

import pse.adp.abstraction.Event;
import pse.adp.abstraction.ExternalEvent;
import pse.adp.abstraction.JsonMsg;
import pse.adp.abstraction.Msg;
import pse.adp.abstraction.MsgEvent;
import pse.adp.abstraction.ReactiveAgent;
import pse.adp.abstraction.SpecializedAgent;
import pse.adp.common.EventType;
import pse.adp.common.Text;
import pse.adp.deviceImpl.Lcd1602;
import pse.adp.deviceImpl.LcdPi4J;
import pse.adp.deviceInterface.Console;
import pse.adp.messageService.JsonService;
import pse.adp.messageService.Outbox;

public class Logger extends SpecializedAgent {
	
	private Console console;
	private Msg msg;
	private JsonObject jObj;
	private JsonService jService;
	private ReactiveAgent from;
	
	private static final  String NEWVALUE = "NEWVALUE" ;
	private static final  String TURN = "TURN" ;
	private enum State {ALARM,SAFETY};
	private State state = State.SAFETY;
	
	
	public Logger(GpioController gpio){
		super();
		console = new LcdPi4J(gpio);
		jService = new JsonService();
		//eventType.add(EventType.INFO);
		//eventType.add(EventType.STEER);
		eventType.add(EventType.LIGHT);
		eventType.add(EventType.DISTANCE);
		//eventType.add(EventType.TEMPERATURE);
		eventType.add(EventType.ARDUINO_MEMORY);
		//eventType.add(EventType.TEMPERATURE);
		
	}

	@Override
	protected void processEvent(Event ev){
		if(ev instanceof MsgEvent){
			msg = ((MsgEvent) ev).getMsg();
			jObj = ((JsonMsg) msg).getjObj();
			from = ((MsgEvent) ev).getFrom();


			switch(state){
			case SAFETY:
				if(from instanceof Driving){					
					if(jService.getType(jObj).equals(EventType.ALARM)){
						System.out.println(Text.ALARM_INFO + " " + jService.getContent(jObj) +" "+ jService.getValue(jObj));
						console.writeMsg(
								Text.ALARM_INFO,
								jService.getContent(jObj) +" "+ jService.getValue(jObj));
						state = State.ALARM;
					}
				}
				else{
					if(from instanceof Temperature){
						System.out.println( "TEMP: " + jService.getValue(jObj));
						console.writeMsg(
								Text.SAFETY_INFO,
								jService.getType(jObj) +" "+ jService.getValue(jObj));
					}
				}
				break;
			case ALARM:
				if(from instanceof Driving){					
					if(jService.getType(jObj).equals(EventType.ALARM)){
						System.out.println(Text.ALARM_INFO + " " + jService.getContent(jObj) +" "+ jService.getValue(jObj));
						console.writeMsg(
								Text.ALARM_INFO,
								jService.getContent(jObj) +" "+ jService.getValue(jObj));
					}else{
						state = State.SAFETY;
					}
				
				}
				break;


			}
		}
		else			
			if(ev instanceof  ExternalEvent){
//				console.writeMsg(((ExternalEvent) ev).getContent() + " : " + ((ExternalEvent) ev).getValue());
				System.out.println(((ExternalEvent) ev).getContent() + " : " + ((ExternalEvent) ev).getValue());
			}
	}
//			
//			//OLD BUT GOLD
//			if(from instanceof Driving){
//				if(jService.getType(jObj).equals(EventType.ALARM)){
//					System.out.println(Text.ALARM_INFO + " " + jService.getContent(jObj) +" "+ jService.getValue(jObj));
//					console.writeMsg(
//							Text.ALARM_INFO,
//							jService.getContent(jObj) +" "+ jService.getValue(jObj));
//					
//				}
//				else{
//					System.out.println(Text.SAFETY_INFO);
//					console.writeMsg(
//							Text.SAFETY_INFO,"");
//					
//				}
//			}else{
//				if(from instanceof Temperature){
//					System.out.println( "TEMP: " + jService.getValue(jObj));
//					console.writeMsg("", "TEMP: " + jService.getValue(jObj));
//				}
//			}
//		}else			
//		if(ev instanceof  ExternalEvent){
//			//console.writeMsg(((ExternalEvent) ev).getContent() + " : " + ((ExternalEvent) ev).getValue());
//			System.out.println(((ExternalEvent) ev).getContent() + " : " + ((ExternalEvent) ev).getValue());
//		}		
//	}

}
