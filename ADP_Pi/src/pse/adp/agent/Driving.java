package pse.adp.agent;

import pse.adp.abstraction.Event;
import pse.adp.abstraction.ExternalEvent;
import pse.adp.abstraction.JsonMsg;
import pse.adp.abstraction.SpecializedAgent;
import pse.adp.common.Context;
import pse.adp.common.EventType;
import pse.adp.common.Text;
import pse.adp.messageService.JsonService;
import pse.adp.messageService.Outbox;

public class Driving extends SpecializedAgent {
	/**
	 * Le distanze minime sono da considerarsi puramente funzionali al test del corretto funzionamento
	 * del prototipo
	 */
	private static final  int MIN_DISTANCE_LEFT = 1;
	private static final  int MIN_DISTANCE_RIGHT = 1 ;
	private static final  int MIN_DISTANCE_FRONT = 1 ;
	private boolean safetyL;
	private boolean safetyR;
	private boolean safetyF;
	private JsonService jService;
	private Logger logger;
	private Sender sender;
	
	

	public Driving(Sender sender,Logger logger) {
		super();
		eventType.add(EventType.DISTANCE);
		this.logger = logger;
		this.sender = sender;
		jService = new JsonService();
		
	}

	@Override
	protected void processEvent(Event ev) {
		if(ev instanceof  ExternalEvent){
			switch(((ExternalEvent) ev).getContent()){
			case "L":
				if(((ExternalEvent) ev).getValue()<MIN_DISTANCE_LEFT){
					if(safetyL){ //new
						safetyL = false;
						System.out.println("Turn Right: " + ((ExternalEvent) ev).getValue());
						sendMsgTo(logger, 
								new JsonMsg(jService.createMessage(
										Context.RASPBERY, EventType.ALARM, Text.TURN_RIGHT,Text.ANGLE)));
						sendMsgTo(sender, 
								new JsonMsg(jService.createMessage(
										Context.ANDROID, EventType.ALARM, Text.TURN_RIGHT,Text.ANGLE)));
						//outbox.getBuffer().add(jService.createMessage(Context.ARDUINO,EventType.ALARM,RIGHT_ALARM,30));
					}
				}
				else
				{
					safetyL = true;
				}
				break;
			case "R":
				if(((ExternalEvent) ev).getValue()<MIN_DISTANCE_RIGHT){
					if(safetyR){
					safetyR = false;
					System.out.println("Turn Left "  + ((ExternalEvent) ev).getValue());
					sendMsgTo(logger, 
							new JsonMsg(jService.createMessage(
									Context.RASPBERY, EventType.ALARM, Text.TURN_LEFT,Text.ANGLE)));
					sendMsgTo(sender, 
							new JsonMsg(jService.createMessage(
									Context.ANDROID, EventType.ALARM, Text.TURN_LEFT,Text.ANGLE)));
					//outbox.getBuffer().add(jService.createMessage(Context.ARDUINO,EventType.ALARM,RIGHT_ALARM,30));
					}
				}
				else
				{
					safetyR = true;
				}
				break;
			case "F":
				if(((ExternalEvent) ev).getValue()<MIN_DISTANCE_FRONT){
					if(safetyF){
					safetyF = false;
					System.out.println("Brake " + ((ExternalEvent) ev).getValue());
					sendMsgTo(logger, 
							new JsonMsg(jService.createMessage(
									Context.RASPBERY, EventType.ALARM, Text.BRAKE,Text.GFORCE)));
					sendMsgTo(sender, 
							new JsonMsg(jService.createMessage(
									Context.ANDROID, EventType.ALARM, Text.BRAKE,Text.GFORCE)));	
					//outbox.getBuffer().add(jService.createMessage(Context.ARDUINO,EventType.ALARM,FRONT_ALARM,30));
					}
				}
				else
				{
					safetyF = true;
				}
				break;
				
			}
			if(safetyL && safetyR && safetyF){
//				System.out.println("Enjoy your trip!");
				sendMsgTo(logger, 
						new JsonMsg(jService.createMessage(
								Context.RASPBERY, EventType.INFO, Text.SAFETY_INFO, 1)));
				sendMsgTo(sender, 
						new JsonMsg(jService.createMessage(
								Context.ANDROID, EventType.INFO, Text.SAFETY_INFO, 1)));
				//outbox.getBuffer().add(jService.createMessage(Context.ARDUINO,EventType.ALARM,FRONT_ALARM,30));
				
			}
		}

	
		
	}

}
