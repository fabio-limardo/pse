package pse.adp.agent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.bluetooth.RemoteDevice;

import javax.bluetooth.UUID;
import javax.json.JsonObject;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import pse.adp.abstraction.BasicFSMController;
import pse.adp.abstraction.Event;
import pse.adp.abstraction.ExternalEvent;
import pse.adp.abstraction.JsonMsg;
import pse.adp.abstraction.Msg;
import pse.adp.abstraction.MsgEvent;
import pse.adp.abstraction.ReactiveAgent;
import pse.adp.abstraction.SpecializedAgent;
import pse.adp.common.Context;
import pse.adp.common.EventType;
import pse.adp.messageService.JsonService;
import pse.adp.messageService.Outbox;
import pse.adp.messageService.SerialCommChannel;

public class Sender extends SpecializedAgent {
	
	private static final String BT_SERVER_NAME = "RASPBERRY";
	private static final String APP_UUID = "00001101-0000-1000-8000-00805F9B34FB"; //Original 04c6093b00001000800000805f9b34fb
	private static final UUID uuid = new UUID("04c6093b00001000800000805f9b34fb",false);
	private static final String serverURL = "btspp://localhost:" + uuid.toString() + ";name="+BT_SERVER_NAME;
	
	private Msg msg;
	private JsonObject jObj;
	private JsonService jService;
	private ReactiveAgent from;
	private ConnectionThread connThread;
	private boolean opened = false;
	private InputStream inStream;
	private OutputStream outputStream;
	private OutputStream outStream;
	

	public Sender(InputStream inStream, OutputStream outStream) {
		super();
		this.inStream = inStream;
		this.outStream = outStream;
		jService = new JsonService();
		eventType.add(EventType.ARDUINO_MEMORY);
		//connThread = new ConnectionThread();
		//connThread.start();

		
	}

	@Override
	protected void processEvent(Event ev){
		//if(opened){
			//System.out.println("E' arrivato un messaggio! Che bello!");
			if(ev instanceof MsgEvent){
				msg = ((MsgEvent) ev).getMsg();
				jObj = ((JsonMsg) msg).getjObj();
				from = ((MsgEvent) ev).getFrom();
			}else 
				if(ev instanceof  ExternalEvent){
					jObj = ((ExternalEvent) ev).getjObj();
				}

			try{
				PrintWriter pWriter=new PrintWriter(new OutputStreamWriter(outStream));
				pWriter.write(jObj.toString());
				pWriter.flush();
			}catch(Exception e){
				try {
					inStream.close();
					outStream.close();
					//connThread.start();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		//}
	}
	private class ConnectionThread extends Thread{
		@Override
		public void run(){

			StreamConnectionNotifier streamConnNotifier;
			try {
				streamConnNotifier = (StreamConnectionNotifier)Connector.open( serverURL );

				System.out.println("\nServer Started. Waiting for clients to connect…");

				StreamConnection connection = streamConnNotifier.acceptAndOpen();
				RemoteDevice dev = RemoteDevice.getRemoteDevice(connection);
				
				inStream = connection.openInputStream();
				outStream = connection.openOutputStream();
				
				
				
				System.out.println("Remote device address: "+dev.getBluetoothAddress());
				System.out.println("Remote device name: "+dev.getFriendlyName(true));
				opened = true;
				//this.interrupt();
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
