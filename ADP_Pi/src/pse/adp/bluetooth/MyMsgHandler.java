package pse.adp.bluetooth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class MyMsgHandler implements Runnable{
	
	private StreamConnection connection;
	private StreamConnectionNotifier streamConnNotifier;
	
	public MyMsgHandler(StreamConnection connection,StreamConnectionNotifier streamConnNotifier) {
		this.connection = connection;
		this.streamConnNotifier = streamConnNotifier;
	}

	@Override
	public void run() {
		//read string from spp client
		InputStream inStream;
		OutputStream outputStream;
		
			try {
				inStream = connection.openInputStream();
				OutputStream outStream=connection.openOutputStream();
			//inStream = connection.openInputStream();
//			OutputStream outStream=connection.openOutputStream();
//			PrintWriter pWriter=new PrintWriter(new OutputStreamWriter(outStream));
//			pWriter.write("Response String from SPP Server\r\n");
//			pWriter.flush();
//
//			pWriter.close();
		while(true){
			
			PrintWriter pWriter1=new PrintWriter(new OutputStreamWriter(outStream));
			pWriter1.write("ciao bello");
			pWriter1.flush();
			Thread.sleep(10000);

		BufferedReader bReader=new BufferedReader(new InputStreamReader(inStream));
		String lineRead=bReader.readLine();
//		byte[] buffer = new byte[1024];
//		DataInputStream dinput = new DataInputStream(inStream);
//		dinput.readFully(buffer,0,buffer.length);
		System.out.println("Z1 say:"+ lineRead);
		//connection.close();
		
		String answer ="";
		if(lineRead.equals("1")){
			answer = "ping";
		}else
			answer = "ping";
		
		System.out.println("SKIPPER say:" + answer);

		//send response to spp client
		
		PrintWriter pWriter=new PrintWriter(new OutputStreamWriter(outStream));
		pWriter.write(answer);
		System.out.println(answer);
		pWriter.flush();
		
		//pWriter.close();
		//connection.close();
//        BufferedWriter bWriter=new BufferedWriter(new OutputStreamWriter(outStream));
//        bWriter.write(answer+"\r\n");
		//streamConnNotifier.close();
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ciao");
			e.printStackTrace();
		}
		//
 catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	

}
