package pse.adp.bluetooth;

import java.io.IOException;

import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class MyMasterThread extends Thread {

	private static final String BT_SERVER_NAME = "RASPBERRY";
	private static final String APP_UUID = "00001101-0000-1000-8000-00805F9B34FB"; //Original 04c6093b00001000800000805f9b34fb

	private UUID uuid = new UUID("04c6093b00001000800000805f9b34fb",false);
	private String serverURL = "btspp://localhost:" + uuid.toString() + ";name="+BT_SERVER_NAME;

	public void run(){


		try {
			//open server url
			StreamConnectionNotifier streamConnNotifier = (StreamConnectionNotifier)Connector.open( serverURL );
			System.out.println("\nServer Started. Waiting for clients to connect…");

			StreamConnection connection = streamConnNotifier.acceptAndOpen();
			RemoteDevice dev = RemoteDevice.getRemoteDevice(connection);
			System.out.println("Remote device address: "+dev.getBluetoothAddress());
			System.out.println("Remote device name: "+dev.getFriendlyName(true));

			Thread myHandler = new Thread(new MyMsgHandler(connection,streamConnNotifier));
			myHandler.start();



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



}
