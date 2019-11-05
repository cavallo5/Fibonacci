package fibonacci;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;

public class FibonacciClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    
    public FibonacciClient(String hostname, int port) throws IOException {
        socket = new Socket(hostname, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
    
    public void sendRequest(int x) throws IOException {
        out.print(x + "\n");
        out.flush();
    }
    
    public BigInteger getReply() throws IOException {
        String reply = in.readLine();
        if (reply == null) {
            throw new IOException("connection terminated unexpectedly");
        }
        
        try {
            return new BigInteger(reply);
        } catch (NumberFormatException nfe) {
            throw new IOException("misformatted reply: " + reply);
        }
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
    
    public static void main(String[] args) throws IOException {
    	int numeroclient=10;
    	String s;
    	
    	FileWriter w;
        w=new FileWriter("output.txt");

        BufferedWriter b;
        b=new BufferedWriter (w);
        b.write("Fibonacci Java \n");

    	
        
    	for(int z=1; z <= numeroclient; z++) {
    	//Numero casuale tra 0 e M
    	int M=100;
    	int x= (int)(Math.random()*M -1);
    	
        try {
            FibonacciClient client = new FibonacciClient("localhost", FibonacciServer.FIBONACCI_PORT);
            
            // invio la richiesta
            	client.sendRequest(x);
                System.out.println("fibonacci("+x+") = ?");
            
            
            // Stampo le risposte del server
 
                BigInteger y = client.getReply();
                System.out.println("fibonacci("+x+") = "+y);
                
              
            // Scrivo file con risposte
                s=y.toString();
                b.write("Client: "+z+ "\n");
                b.write("Il numero di Fibonacci di "+x+" è: "+s+ "\n");

                client.close();
                b.write("\n");
        	} catch (IOException ioe) {
        		ioe.printStackTrace();
        	}
    	} //FINE FOR
        b.flush();
    	
    }
}
