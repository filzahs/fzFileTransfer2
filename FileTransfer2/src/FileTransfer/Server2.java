package FileTransfer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class Server2 {
	// Vector to store active clients
    static Vector<ClientHandler> ar = new Vector<>();
     
    // counter for clients
    static int clientCount = 0;

	public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        DataInputStream in = null;
		DataOutputStream out = null;
        BufferedReader br = null;
        
		try {
			serverSocket = new ServerSocket(9090);
			System.out.println("Waiting for the client to connect on port 9090");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
        // running infinite loop for getting client request
		//how to exit out of this loop?
        while (true)
        {
            // Accept the incoming request
            try {
				socket = serverSocket.accept();
				System.out.println("New client connected: " + socket);
				
				// obtain input and output streams of this socket
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				
				// Create a new handler object for handling this request.
				ClientHandler mtch = new ClientHandler(socket, "client " + clientCount, in, out);
				
				// Create a new Thread with this object.
				Thread t = new Thread(mtch);
				
				// add this client to active clients list
				ar.add(mtch);
				
				// start the thread for this client
				t.start();
				
				clientCount++;
				System.out.println("Number of clients connected: " + ar.size());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
 
        }
              
    }
}

//ClientHandler class
class ClientHandler implements Runnable {
	Scanner scn = new Scanner(System.in);
	private String name;
	final DataInputStream dis;
	final DataOutputStream dos;
	Socket s;

	// constructor
	public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) {
		this.dis = dis;
		this.dos = dos;
		this.name = name;
		this.s = s;
	}

	@Override
	public void run() {

		String received;
		while (true) {
			try {
				// receive the string
				received = dis.readUTF();
				System.out.println(received);

				if (received.equals("exit")) {
					this.s.close();
					this.dis.close();
					this.dos.close();
					removeClient(s);
					break;
				}

			} catch (IOException e) {
				
				e.printStackTrace();
			}

		}
		try {
			// closing resources
			this.dis.close();
			this.dos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void removeClient(Socket s) {
		if(Server2.ar.contains(s)) {
			Server2.ar.remove(s);
			System.out.println("Number of clients connected: " + Server2.ar.size());
			System.out.println("Client count: " + Server2.clientCount);
		}
	}
	
	//have a stop server function
}