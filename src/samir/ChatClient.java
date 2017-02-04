package samir;
/**
 * 
 */


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author samir
 *
 */
public class ChatClient implements Runnable{
	 private Socket socket;
	   private Thread thread;
	   private DataInputStream  console;
	   private DataOutputStream streamOut;
	   private ChatClientChild client;
	   public ChatClient(String serverName, int serverPort)
	   {  System.out.println("Establishing connection. Please wait ...");
	      try
	      {  
	    	 socket = new Socket(serverName, serverPort);
	         System.out.println("Connected: " + socket);
	         start();
	      }
	      catch(IOException e)
	      {  
	    	  System.out.println("Unexpected exception: " + e.getMessage()); 
	      }
	   }
	   private void start() throws IOException {
		console   = new DataInputStream(System.in);
	    streamOut = new DataOutputStream(socket.getOutputStream());
	    if (thread == null)
	    {  
	    	client = new ChatClientChild(this, socket);
	    	thread = new Thread(this);                   
		    thread.start();
	    }
		
	}
	@Override
	public void run() {
		while (thread != null)
	      {  try
	         {  
	    	  @SuppressWarnings("deprecation")
	    	  String str = console.readLine();
	    	  if(!str.contains(Integer.toString(socket.getLocalPort())))
	    	  {
	         		streamOut.writeUTF(str); streamOut.flush();
	    	  }
	         	streamOut.flush();
	         }
	         catch(IOException ioe)
	         {  System.out.println("Sending error: " + ioe.getMessage());
	         if (thread != null)
	         {  thread.interrupt(); 
	            thread = null;
	         }
	         if (console!= null){
				try {
					console.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         }
	         if (streamOut!= null){
				try {
					streamOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         }
	      if (socket!= null){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      }
	      client.close();  
	      }
      }
		
	}
	public static void main(String args[])
	   {  	 
			ChatClient client1 = new ChatClient("localhost",1111);
	   }
}
