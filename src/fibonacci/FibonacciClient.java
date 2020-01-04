package fibonacci;

/**
* Client che richiede il calcolo del numero di Fibonacci
* @author Vincenzo Cavallo, Malamine Liviano D’Arcangelo Koumare
*
*/

//Librerie importate
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import fibonacci.Manager;

public class FibonacciClient {
	/**
	*  Socket per la comunicazione
	*/
    private Socket socket;
    /**
   	*  BufferedReader per la comunicazione
   	*/
    private BufferedReader in;
    /**
 	*  PrintWriter per la comunicazione
 	*/
    private PrintWriter out;
    /**
   	*  Istanza della classe Manager (relazione di composizione)
   	*/
    private static Manager manager;
    /**
     * Estremo superiore dell'intervallo dei numeri scelto
     */
    private static int M=100;
    
    
    /**
     *  Costruttore
     *  @param hostname String
     *  @param port int
     *  @throws IOException eccezione in caso di errore nella comunicazione
     */
    public FibonacciClient(String hostname, int port) throws IOException {
        socket = new Socket(hostname, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
    
    /**
     *  Funzione per inviare la richiesta al server
     *  @param x variabile intera da inviare
     *  @throws IOException
     */
    public void sendRequest(int x) throws IOException {
        out.print(x + "\n");
        out.flush();
    }
    
    /**
     *  Funzione per la risposta del server
     *  @return il risultato del calcolo del numero di Fibonacci
     */
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

    /**
     *  Funzione per la chiusura della comunicazione
     *  @throws IOException eccezione in caso di errore nella chiusura della comunicazione
     */
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
    
    /**
     *  Main che simula il comportamento di 10 client 
     */
    public static void main(String[] args) throws IOException {
    	String s; //Variabile d'appoggio 
    	int numeroclient=10; //Numero client
    	manager=new Manager(numeroclient); //Creazione manager
    	String[] numeri = new String[numeroclient]; //Array d'appoggio in cui salvo i numeri scelti
    	String[] risultati = new String[numeroclient]; //Array d'appoggio in cui salvo i risultati ottenuti
    	String[] elapsedtime = new String[numeroclient]; //Array d'appoggio in cui salvo gli elapsed time
        long start_time, end_time, time_used, total_time=0; //variabili per il calcolo dell'elapsed time
  
    	for(int z=1; z <= numeroclient; z++) { //10 client
    		int x= (int)(Math.random()*M -1);	//numero casuale tra 0 e M
    		numeri[z-1]=String.valueOf(x); //salvo nell'array di stringhe numeri il numero scelto
    		
    		try {
    			FibonacciClient client = new FibonacciClient("localhost", FibonacciServer.FIBONACCI_PORT); //Creo un client
    			
    			start_time=System.nanoTime(); //tempo in ns prima di inviare la richiesta al server
            	client.sendRequest(x); //Invio numero da calcolare
                System.out.println("fibonacci("+x+") = ?");
             
                BigInteger y = client.getReply(); //il risultato viene restituito dal server in una variabile BigInteger
                System.out.println("fibonacci("+x+") = "+y);
                end_time= System.nanoTime(); //tempo in ns dopo aver ricevuto la risposta del server
                
                time_used= (long) ((end_time - start_time)/1000F); //tempo impiegato in 􏱇µs tra invio e risposta del server
                total_time=total_time+time_used; //aggiorno il tempo totale
                
                s=y.toString(); //conversione y in string
                risultati[z-1]=s; //salvo nell'array di stringhe risultati il risultato ottenuto 
                
                s=String.valueOf(time_used); //conversione time_used in string
                elapsedtime[z-1]=s; //salvo nell'array di stringhe elapsedtime l'elapsed time calcolato
                
                if(z==numeroclient) { //Se tutti i client hanno terminato
                	manager.salvadati(numeri,risultati,elapsedtime, total_time); //salvo i dati nel manager
                	manager.creafile(); //creo file di output
                }
               
                client.close(); //chiusura comunicazione 
                
        	} catch (IOException ioe) {
        		ioe.printStackTrace();
        	}
    	} 
    }
}
