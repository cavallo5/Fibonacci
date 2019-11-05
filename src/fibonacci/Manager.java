package fibonacci;

import java.io.*;

public class Manager {
	int numeroclient=10;
	String[] numeri;
	String[] risultati;
	String[] elapsedtime;
	FileWriter w;
	
	public Manager(int a){
		this.numeroclient=a;
		this.numeri=new String[a];
		this.risultati= new String[a];
		this.elapsedtime= new String[a];
	}

	public void inviodati(String[] a, String[] b) {
		this.numeri=a;
		this.risultati=b;
	}
	
	public void creafile() throws IOException{
		w=new FileWriter("output_Fibonacci.txt");
		BufferedWriter b;
        b=new BufferedWriter (w);
        b.write("Fibonacci Java \n");
        for(int i=0;i<numeroclient;i++) {
        	b.write("Il numero di Fibonacci di "+this.numeri[i]+" è: "+this.risultati[i]+ "\n");
        }
        
        
        
        
        
        //b.write("\n");
        //b.write("Il numero di Fibonacci di "+x+" è: "+s+ "\n");

        
        
        
        
        b.flush();


		 
		
		
	}

}
