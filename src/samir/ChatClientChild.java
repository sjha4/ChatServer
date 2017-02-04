package samir;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClientChild extends Thread {
		private Socket socket;
		private ChatClient client;
		private DataInputStream streamIn;
	public ChatClientChild(ChatClient chatClient, Socket socket) {
		// TODO Auto-generated constructor stub
			client   = chatClient;
			this.socket   = socket;
			try {
				streamIn  = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			start();
	}
	
	public void run()
	   {  
		while (true){  
			try
	         {  
				System.out.println(streamIn.readUTF());
	         }
	         catch(IOException e)
	         {  
	        	 System.out.println("Chat client error: " + e.getMessage());
	         }
	      }
	   }

	public void close(){
		// TODO Auto-generated method stub
		if (streamIn != null){
			try {
				streamIn.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
