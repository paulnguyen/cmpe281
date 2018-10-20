
package log ;

import java.net.*;
import java.io.*;

public class LogServer {

    private ServerSocket serverSocket;
 
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true)
            new ClientHandler(serverSocket.accept()).start();
    }
 
    public void stop() throws IOException {
        serverSocket.close();
    }
 
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
 
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
 
        public void run() {
        	try {
	            out = new PrintWriter(clientSocket.getOutputStream(), true);
	            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	             
	            String inputLine;
	            while ((inputLine = in.readLine()) != null) {
	            	System.out.println( inputLine ) ;
	                if ("<end>".equals(inputLine)) {
	                    out.println("bye");
	                    break;
	                }
	                out.println("OK");
	            }
	 
	            in.close();
	            out.close();
	            clientSocket.close();
        	} catch ( IOException err ) { }
    	}
	}

   public static void main(String[] args) {
        LogServer server = new LogServer() ;
        try {
        	server.start(8888);
        } catch ( IOException err ) {
        	System.out.println( err ) ;
        }
    }

}