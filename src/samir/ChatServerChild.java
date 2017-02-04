/**
 * 
 */
package samir;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author samir
 *
 */
public class ChatServerChild extends Thread {
	   private ChatServer server;
	   private Socket socket;
	   private int ID;
	   private String Name;
	   private DataInputStream streamIn;
	   private DataOutputStream streamOut;
	   
	   public ChatServerChild(ChatServer paramServer, Socket paramSocket)
	   {  super();
	      server = paramServer;
	      socket = paramSocket;
	      ID     = socket.getPort();
	      Name = "Client" + ID;
	   }
	   public int getID()
	   {  return ID;
	   }
	   public String getNames(){
		   return Name;
	   }
	   public void open() throws IOException
	   {  streamIn = new DataInputStream(new 
	                        BufferedInputStream(socket.getInputStream()));
	      streamOut = new DataOutputStream(new
	                        BufferedOutputStream(socket.getOutputStream()));
	   }
	   
	   public void run()
	   {  
		  System.out.println("Server Thread " + ID + " running.");
	      while (true)
	      {  try
	         {  server.handle(ID, streamIn.readUTF());
	         }
	         catch(IOException ioe)
	         {  System.out.println(ID + " ERROR reading: " + ioe.getMessage());
	            interrupt();
	         }
	      }
	   }
	public void send(String string) {
		// TODO Auto-generated method stub
		 try
	       {  streamOut.writeUTF(string);
	          streamOut.flush();
	       }
	       catch(IOException ioe)
	       {  System.out.println(ID + " ERROR sending: " + ioe.getMessage());
	          interrupt();
	       }
		
	}
	public void close() {
		if (socket != null){
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	    if (streamIn != null){
	    	try {
				streamIn.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    if (streamOut != null){
	    	try {
				streamOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		
	}
	
}
