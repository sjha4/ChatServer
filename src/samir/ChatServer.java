/**
 * 
 */
package samir;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author samir
 *
 */
public class ChatServer implements Runnable {

	//Every Server thread will correspond to a client. We need only 2 clients
	ChatServerChild client1;
	ChatServerChild client2;
	//Create a net socket
	ServerSocket server;
	Thread thread;
	public ChatServer(int port)
	   {  
		try{
			 System.out.println("Starting on port " + port + "!!");
	         server = new ServerSocket(port);  
	         System.out.println("Server started: " + server);
	         start(); 
	      }
	    catch(IOException ioe){
	         System.out.println("Error starting Server: " + ioe.getMessage()); 
	      }
	   }
	private void start() {
		if (thread == null)
	      {  thread = new Thread(this); 
	         thread.start();
	      }
		
	}
	@Override
	public void run() {
		while(thread!=null && client2==null){
			Socket newSocket = null;
			try {
				
					newSocket = server.accept();
					System.out.println("Connection requested");
					if(client2==null)
						createNewConnection(newSocket);
					else newSocket.close();
				
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}
	private void createNewConnection(Socket newSocket) {
		if(client1==null){
			client1 = new ChatServerChild(this, newSocket);
			try
	         {  
				client1.open(); 
	         	client1.start();
	         	client1.send("Hello Client!");
	         }
			catch(IOException ioe)
	         {  
				System.out.println("Error opening thread: " + ioe); 
			 } 
		}
		else if(client2==null){
			client2 = new ChatServerChild(this, newSocket);
			try
	         {  
				client2.open(); 
	         	client2.start();
	         }
			catch(IOException ioe)
	         {  
				System.out.println("Error opening thread: " + ioe); 
			 } 
		}
		else{
			System.out.println("Resources full");
			try {
				newSocket.close();
				
			} catch (IOException e) {
				System.out.println("Error closing child socket: " + e.getMessage()); 
			}
		}
		
	}
	public synchronized void handle(int ID, String input){ 
		ChatServerChild sender = (client1.getID()==ID)?client1:client2;
		ChatServerChild receiver = (client1.getID()==ID)?client2:client1;
		receiver.send(sender.getNames() + " says : " + input);
	   }
	public static void main(String[] args) {
		//Starting server at port 1111
	      
	        ChatServer server = new ChatServer(1111);
	   }

}
