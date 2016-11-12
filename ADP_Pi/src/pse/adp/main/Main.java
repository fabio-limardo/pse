package pse.adp.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

import pse.adp.agent.Broker;
import pse.adp.agent.Driving;
import pse.adp.agent.HeadLight;
import pse.adp.agent.Logger;
import pse.adp.agent.Sender;
import pse.adp.agent.Temperature;
import pse.adp.messageService.Outbox;
import pse.adp.messageService.SerialCommChannel;

public class Main {
	
	private static final String PORT = "/dev/ttyACM0";
	private static final String WINPORT = "COM3";
	private static final int RATE = 115200;
	private static final int PERIOD = 1000;
	
	private static final String BT_SERVER_NAME = "RASPBERRY";
	private static final String APP_UUID = "00001101-0000-1000-8000-00805F9B34FB"; //Original 04c6093b00001000800000805f9b34fb
	private static final UUID uuid = new UUID("04c6093b00001000800000805f9b34fb",false);
	private static final String serverURL = "btspp://localhost:" + uuid.toString() + ";name="+BT_SERVER_NAME;
	
	private static InputStream inStream;
	private static OutputStream outStream;

	public static void main(String[] args) throws Exception {
		System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");
		SerialCommChannel channel = new SerialCommChannel(PORT, RATE);
//		SerialCommChannel channel = new SerialCommChannel(WINPORT,RATE);
		System.out.println("Waiting Arduino for rebooting...");		
		Thread.sleep(4000);
		System.out.println("Ready.");
		
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
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GpioController gpio = GpioFactory.getInstance();
		Sender sender = new Sender(inStream, outStream);
		Outbox outbox = new Outbox();
		Broker broker = Broker.getBroker(channel);
		Logger logger = new Logger(gpio);
		Driving driving = new Driving(sender,logger);
		HeadLight headlight = new HeadLight(gpio);
		Temperature temperature = new Temperature(sender, logger);
		
		broker.addObserver(driving);
		broker.addObserver(headlight);
		broker.addObserver(logger);   
		broker.addObserver(temperature);
		broker.addObserver(sender);
		
		sender.start();
		
//		Thread.sleep(10000);
		driving.start();
		headlight.start();
		logger.start();
		temperature.start();
		(new Thread(broker)).start();
		//sender.start();		
		
	}
}
