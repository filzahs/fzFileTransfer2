package FileTransfer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new MainJFrame();
				
			}
			
		});
	}
	
	public static void startClient() {
		Socket socket = null;
		DataInputStream in = null;
		DataOutputStream out = null;
		BufferedReader br = null;
		
		try {
			// start listening to connections
			//socket = new Socket("192.168.50.3", 9090);
			socket = new Socket("localhost", 9090);
			System.out.println("Connected");
			
			// read data from client
			in = new DataInputStream(socket.getInputStream());

			// write data back to client
			out = new DataOutputStream(socket.getOutputStream());

			// read data from console/system.in, take input from console and send it to
			// client
			// use buffered reader to read data as string line
			br = new BufferedReader(new InputStreamReader(System.in));

			String messageReceived = "", messageToSend = "";
			while (true) {
				System.out.println("Type message below:");
				// read data from console on what msg to send
				messageToSend = br.readLine();
				out.writeUTF(messageToSend);
				out.flush();

				if (messageToSend.equalsIgnoreCase("exit")) {
					break;
				}
				// read data received back
//				messageReceived = in.readUTF();
//				System.out.println("Server says: " + messageReceived);

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				out.close();
				in.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void startMessage() {
		
	}

}
