import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.*;
//In this class we ask user to enter IP address, port number of the server and the message that 
//we would like to send it to server 
//After sending the message, the packet will also receive the message and prints on the screen

public class EchoClient {
	//main class 
	public static void main(String[] args) throws IOException{
		Scanner scanner = new Scanner(System.in);  //initializing scanner for input outputs in the program 
		//Initialize the socket using datagramsocket 
		DatagramSocket socket = new DatagramSocket();
		//Prompts the user to enter the IP address to proceed with it 
		//After user enters the IP address it will check if its in correct IPV4 form or not using the helper method
		System.out.println("Please enter IP address: ");
		//takes user input 
		String IPAddress = scanner.nextLine();
		//IPAddressValidator helper method - checks if entered IP address is in correct format
		//if it is, returns true
		//if not, returns false 
		boolean IPAddresscheck = IPAddressValidator(IPAddress);
		//prompts user to enter the port number of the server to send and receive the message
		//after user enters the port number it will check if its in correct version using helper method 
		System.out.println("Please enter port number of the server:");
		//takes user input 
		String portnumber = scanner.nextLine();
		//PortNumberValidator is a helper method that checks if entered input includes only numbers or it has other types too
		//if it is only numbers -> it checks if it is in correct format 
		//if it is -> converts string to int and returns
		//if it is invalid entry -> returns -999 
		int Port  = PortNumberValidator(portnumber);
		boolean PortNumbercheck = true; //currently it is set to true (i.e. it is in correct form
		int invalidOutput = -999; 
		if (Port == invalidOutput) { //if helper method returns -999 (i.e. invalid/ incorrect form entry)
			//it changes the portnumbercheck to false which basically means that port number was not entered correctly 
			PortNumbercheck = false;
		}
		boolean notDone = true;
		while(notDone == true) {
			//prompts the user to enter the message that they would like to send to the server 
			System.out.println("Please enter the message you would like to send to that server (Enter ^C (ctrl-C) to stop program):");
			//saves user input in string format 
			String message = scanner.nextLine();
			//converts string to byte array as socket and pocket works with byte arguments 
			byte buf[] = message.getBytes(); //conversion 
			//checks if either or both IPaddress and Port number were entered correctly
			//if any of them was not entered correctly, it will show error message on the screen and would not send the messge to the servr
			if (!IPAddresscheck || !PortNumbercheck) {
				//if both of them were not entered correctly, it will fall under this condition 
				if (!IPAddresscheck && !PortNumbercheck) {
					System.out.println("ERROR: IP Address and Port number were not entered correctly so we CANNOT Proceed!");
				}
				//else if ONLY IP address was not entered correctly it will give an error message 
			    else if (!IPAddresscheck) {
					System.out.println("ERROR: IP Address was not entered correctly so we CANNOT Proceed!");
				}
				//else if ONLY port number was not entered correctly it will give this error 
				else if (!PortNumbercheck) {
					System.out.println("ERROR: Port Number was not entered correctly so we CANNOT Proceed!");
				}
				
			}
			//now if everything was entered correctly it will proceed with send/ receive data 
			else {
			// convertng entered IPAddress to InetAddress to have it pass to packet 
			InetAddress ipaddress = InetAddress.getByName(IPAddress); 
			//pass arguments in this order
			// 1. message, 2. length of message, 3. IPaddress, 4. Port number
			DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ipaddress, Port);
			//socket sends of the dpSend to server
			socket.send(DpSend);
			//initializing server message with 1024 size byte array 
			byte reciever[] = new byte[1024];
			//Packet initializer with 1. receiver message and 2. length of receiver message arguments
			DatagramPacket Dpreciever = new DatagramPacket(reciever, reciever.length);
			//socket receives dpreciever
			socket.receive(Dpreciever);
			//converts data to string type in order to print it to the screen 
			String Servermessage = new String(Dpreciever.getData());
			//prints the received message from the server side 
			System.out.println("Server sent back:  " + Servermessage);
			//socket.close(); //close the socket 
			
			}	
		}
		
	}
	/**
	 * This helper method validates if passed IPAddress was entered correctly (i.e. in correct IPV4 and valid address)
	 * @param IPAddress - IPAddress tha function will check to make sure if it's correct 
	 * @return true if it was entered correctly
	 */
	
	public static boolean IPAddressValidator(String IPAddress) {
		//Regex pattern for xxx.xxx.xxx.xxx (IpV4 address) 
		//took this from Leetcode problem solution 
		//this pattern is only for one xxx 
		String IPv4Regex = "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";
		//it compiles the pattern with three other (as there are four xxx in the IpV4 format)
		Pattern regexpattern = Pattern.compile("^(" + IPv4Regex + "\\.){3}" + IPv4Regex + "$");
		//if it matches the regex pattern then returns true 
		if (regexpattern.matcher(IPAddress).matches()) {
			return true;
		}
		else { //if it doesnt match the regex pattern, return false 
		return false; }
		}
	
	/**
	 * This helper method validates if passed portnumber is valid (in range of 1-65535
	 * @param portnumber - that needs to get validates
	 * @return -999 if it was entered incorrectly and integer port number if it was correctly entered
	 */
	public static int PortNumberValidator(String portnumber) {
	
	int invalid = -999; //invalid return value 
	if (portnumber == null) { //if its null, return invalid value 
		return invalid;
	}
	else { 
		int Port; //initialize integer port 
	
		try {
			 Port = Integer.parseInt(portnumber); //convert string to int 
		}
		//if it includes only numbers, it will go to next if statement
		//if it cannot convert string to int, it will go to catch phase
		catch (NumberFormatException e) {  //since it has characters, it will give numberformatexception 
			return invalid; //returns invalid value
		}
		if (Port >= 1 && Port <= 65535) { //validates if port number is between 1 and 65535 
			return Port; //if it is, return the port number itself
		}
		else {
			return invalid; //if not, returns the invalid value
		}
	}	
	}
	}
		
	
		
	







