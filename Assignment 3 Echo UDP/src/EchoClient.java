import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.*;


public class EchoClient {
	public static void main(String[] args) throws IOException{
		//System.out.println(IPAddressValidator("1.1.1"));
		Scanner scanner = new Scanner(System.in);
		DatagramSocket socket = new DatagramSocket();
		// TODO Auto-generated method stub
		Boolean run = true;
		
		System.out.println("Please enter IP address of the server: ");
		String IPAddress = scanner.nextLine();
		while (!IPAddressValidator(IPAddress) & run) {
			System.out.println("ERROR: IP Address does not entered correctly");
			System.out.println("Would you like to enter it again or quit?");
			System.out.println("1. Enter again\n2. Quit");
			int option = Integer.parseInt(scanner.nextLine().toString());
			if (option == 1) {
				System.out.println("Please enter IP address of the server again: ");
				IPAddress = scanner.nextLine();
			}
			else if (option == 2) {
				run = false;
				System.out.println("Thank you for using our UDP program");
			}
		}
		
		System.out.println("Please enter port number of the server:");
		String portnumber = scanner.nextLine();
		int invalidPort = -999;
		int port = 0;
//		if (PortNumberValidator(portnumber) != invalidPort) {
//			port = PortNumberValidator(portnumber);
//		}
		while ((PortNumberValidator(portnumber) == invalidPort) & run) {
			System.out.println("ERROR: Port Number does not entered correctly");
			System.out.println("Would you like to enter it again or quit?");
			System.out.println("1. Enter again\n2. Quit");
			int option = Integer.parseInt(scanner.nextLine().toString());
			if (option == 1) {
				System.out.println("Please enter port number of the server again:");
				portnumber = scanner.nextLine();
			}
			else if (option == 2) {
				run = false;
				System.out.println("Thank you for using our UDP program");
			}
			
		}
		if (PortNumberValidator(portnumber) != invalidPort) {
			port = PortNumberValidator(portnumber);
		}
		System.out.println("Please enter the message you would like to send to that server:");
		String message = scanner.nextLine();
		byte buf[] = message.getBytes();
		InetAddress ipaddress = InetAddress.getByName(IPAddress);
		DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ipaddress, port);
		socket.send(DpSend);
		byte reciever[] = new byte[1024];
		DatagramPacket Dpreciever = new DatagramPacket(reciever, reciever.length);
		socket.receive(Dpreciever);
		String Servermessage = new String(Dpreciever.getData());
		System.out.println("From Server side:  " + Servermessage);
		socket.close();
		
		if (run) {
			run = false;
		}
		
	}
	//}
	
	public static boolean IPAddressValidator(String IPAddress) {
		String IPv4Regex = "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";
		Pattern regexpattern = Pattern.compile("^(" + IPv4Regex + "\\.){3}" + IPv4Regex + "$");
		if (regexpattern.matcher(IPAddress).matches()) {
			return true;
		}
		else {
		return false; }
		}
	
	public static int PortNumberValidator(String portnumber) {
	
	int invalid = -999;
	if (portnumber == null) {
		return invalid;
	}
	else {
		int Port;
	
		try {
			 Port = Integer.parseInt(portnumber);
		}
		catch (NumberFormatException e) {
			return invalid;
		}
		if (Port >= 1 && Port <= 65535) {
			return Port;
		}
		else {
			return invalid;
		}
	}
		
	}
	}
		
	
		
	



