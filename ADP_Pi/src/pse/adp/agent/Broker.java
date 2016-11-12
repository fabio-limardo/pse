package pse.adp.agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import pse.adp.abstraction.ExternalEvent;
import pse.adp.abstraction.Msg;
import pse.adp.abstraction.MsgEvent;
import pse.adp.abstraction.Observable;
import pse.adp.abstraction.Observer;
import pse.adp.abstraction.ReactiveAgent;
import pse.adp.abstraction.SpecializedAgent;
import pse.adp.common.Context;
import pse.adp.messageService.JsonService;
import pse.adp.messageService.Outbox;
import pse.adp.messageService.SerialCommChannel;
/**
 * Il Broker può essere visto come il "centro smistamento" per i messaggi scambiati con il sistema.
 * Riceve i messaggi dall'esterno e li indirizza in base al contenuto.
 * Conosce gli agenti del sistema che si registrano presso il Broker per essere notificati all'evenienza.
 * Broker inoltre riceve anche i messaggi diretti verso l'esterno (altri contesti) e li spedisce a "MessageSender" che li ridirezionerà correttamente
 * 
 * Formato scambio messaggi := MSG[Context,Type,Content,Value]
 * (teorico di fatto useremo Json per lo scambio messaggi, onde favorire l'interoperabilità
 * 
 * @author fabio_limardo 
 *
 */
public class Broker extends Observable implements Runnable{
		
	private  static Broker broker;
	private SerialCommChannel channel;
	private JsonService jService;
	private boolean checkEvent;

	private Broker(SerialCommChannel channel) throws Exception{
		super();
		this.channel = channel;
		jService = new JsonService();
		
	}

	@Override
	public void run() {
		
		while(true){
//			JsonObject jObj = jService.createMessage("ARDUINO", "GETSTATE", " ", 1);
//			channel.sendMsg(jObj.toString());			
//			System.out.println(channel.isMsgAvailable());
			
			try {
				String msg = channel.receiveMsg();
//				System.out.println(msg);
				try {
					JsonObject jObj = jService.toJson(msg);
					checkEvent = false;
					if(jService.getContext(jObj).equals(Context.RASPBERY)){						
						observers.stream()
						.forEach(obs ->((SpecializedAgent)obs)
								.getEventType().stream()
								.forEach(type ->{ 
									if(type.equals(jService.getType(jObj))) {
										((SpecializedAgent)obs).notifyEvent(
												new ExternalEvent(jObj)
												);
										checkEvent = true;
									}}));
						if(checkEvent == false)
							System.out.println("tipo evento non gestito");
					}
				} catch (Exception e) {
					System.out.println("Not a Json message" + e.getMessage());
				}
			}catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static Broker getBroker(SerialCommChannel channel) throws Exception{
		
		if(broker == null){
			synchronized (Broker.class) {
				if (broker == null){
					broker = new Broker(channel);
				}				
			}		
		}
		return broker;
		
	}
}
