package fibonacci;

import java.io.*;

public class Manager {
	/**
	 *  Numero dei client
	*/
	private int numeroclient;
	/**
	 *  Array di stringhe in cui vengono salvati i numeri scelti dai client
	*/
	private String[] numeri;
	/**
     *  Array di stringhe in cui vengono salvati i risultati ottenuti dall'algoritmo di Fibonacci
	*/
	private String[] risultati;
	/**
	 *  Array di stringhe in cui vengono salvati gli elapsed time di ogni client
	*/
	private String[] elapsedtime;
	/**
	   *  Variabile usata per calcolare il tempo totale impiegato (somma di tutti gli elapsed time)
	*/
	private long tempototale;
	/**
	   *  Variabile per generare file di output
	*/
	private FileWriter w;
	
	/**
	 *  Costruttore
	 *  @param a numero dei client
	*/
	public Manager(int a){
		this.numeroclient=a;
		this.numeri=new String[numeroclient];
		this.risultati= new String[numeroclient];
		this.elapsedtime= new String[numeroclient];
		this.settempototale(0);
	}
	
	//Metodi get
	/**
	 * Restituisce il numero client
	 * @return il numero di client
	 */
	public int getnumeroclient() {
		return this.numeroclient;
	}
	
	/**
	 * Metodo set della variabile contatoreclient
	 * @param a long con il quale settare il tempo totale
	 */
	 private void settempototale(long a) {
		 this.tempototale=a;
	 }
	
	 /**
	  * Metodo set della variabile contatoreclient
	  * @param a Array contenente le stringhe dei numeri scelti dai client
	  * @param b Array contenente le stringhe dei risultati ottenuti dal server
	  * @param c Array contenente gli elapsed time dei client
	  * @param d long contentente il tempo totale impiegato
	  */
	public void salvadati(String[] a, String[] b, String[] c, long d) {
		this.numeri=a;
		this.risultati=b;
		this.elapsedtime=c;
		this.tempototale=d;
	}
	
	 /**
     * Funzione che crea il file di output output_Fibonacci.txt
     * @throws IOException eccezione in caso di errore della scrittura del file
     */
	public void creafile() throws IOException{
		w=new FileWriter("output_Fibonacci.txt");
		BufferedWriter b;
        b=new BufferedWriter (w);
        b.write("Fibonacci Java \n");
        for(int i=0;i<this.getnumeroclient();i++) {
        	b.write((i+1)+")"+" Il numero di Fibonacci di "+this.numeri[i]+" è "+this.risultati[i]+" impiegando "+this.elapsedtime[i]+" µs \n");
        }
    	b.write("Tempo totale impiegato: "+this.tempototale+" µs \n");
        
        b.flush();			
	}

}
