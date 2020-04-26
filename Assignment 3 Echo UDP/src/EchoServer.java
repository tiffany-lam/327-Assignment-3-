import java.io.IOException;
import java.net.*; 
import java.util.*;
//In this class we will receive the data from the client and then
//echo it back to the client by using the same socket
// when the server receives a message from the client, the server echoes the message to the same client. 
//This is typically used to check if the system is well connected in the network. 
public class EchoServer {
	
	public static void main(String[] args) {
		//check to make sure the user passes in a port 
		if (args.length != 1) {
			System.out.println("ERROR: missing the port number.");
			System.out.println("Please enter: java EchoServer <port> ");
			return;
		}
		//initialize server socket
		DatagramSocket socket = null;
		//now send the message to our server
		try {
			//get the port from the Client
			int port = Integer.parseInt(args[0]); //convert the argument to integer
			socket = new DatagramSocket(port);
			
			
			while(true) {
				
				byte[] buffer = new byte[1000]; //byte to hold message
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				// .receive - receives a datagram packet from this socket
				// returns - the DatagramPacket's buffer is filled w/ data received. 
				// Datagram contains sender's IP address and port from sender's machine
				socket.receive(packet); //blocks until a datagram is received 
				//======================= NOW we must get the data fromt eh packet and the ip address and port ================= 
				//returns the data or message from the client
				buffer = packet.getData(); 
				String message = new String(buffer);
				System.out.println("Data recieved! Your message from client is: " + message);
				//returns the IP address of the machine to which the datagram was received from 
				InetAddress clientIP = packet.getAddress();
				//returns the port to which the datagram was received from 
				int clientPort = packet.getPort();
				
				DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientIP, clientPort);
				//sends a datagram package using the information we got
				socket.send(response);
			}
			
			
			
		}catch(SocketException exception) { //catch any socket exceptions when creating our server
			System.out.println("SOCKET ERROR: " + exception.getMessage());
		}catch(IOException exception) {
			System.out.println("IOException: " + exception.getMessage());
		}finally {
			//close the socket
			if(socket != null) {
				socket.close();
			}
			
		}

	}

}
