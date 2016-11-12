package pse.adp.abstraction;

import java.util.ArrayList;

import pse.adp.messageService.JsonService;
import pse.adp.messageService.Outbox;
/**
 * La classe SpecializedAgent estende Reactive Agent fornendole una lista di tipi di eventi che permettono di dichiarare al Broker a
 * che tipo di eventi interessati
 * @author fabio
 *
 */
public abstract class SpecializedAgent extends ReactiveAgent{
	protected ArrayList<String> eventType;
	//protected Outbox outbox;
	protected JsonService jService;
	
	public SpecializedAgent(/*Outbox outbox*/) {
		//this.outbox = outbox;
		jService = new JsonService();
		eventType = new ArrayList<String>();
	}

	public ArrayList<String> getEventType() {
		return eventType;
	}

}
