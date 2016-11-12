package pse.adp.bluetooth;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;

public class MyMain {

	public static void main(String[] args) throws BluetoothStateException {
		LocalDevice localDevice = LocalDevice.getLocalDevice();
		System.out.println("Address: "+localDevice.getBluetoothAddress());
		System.out.println("Name: "+localDevice.getFriendlyName());

		MyMasterThread master=new MyMasterThread();
		master.start();

	}

}
