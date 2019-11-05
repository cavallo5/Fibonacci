package fibonacci;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class FibonacciServer {
	public static final int FIBONACCI_PORT = 4949;

	private ServerSocket serverSocket;

	public FibonacciServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	public void serve() throws IOException {
		while (true) {
			// rimane in attesa fino a quando un client si connette
			Socket socket = serverSocket.accept();
			try {
				handle(socket);
			} catch (IOException ioe) {
				ioe.printStackTrace(); 
			} finally {
				socket.close();
			}
		}
	}

	public BigInteger fibonacci(int n) {
		if (n <= 0)
			return BigInteger.valueOf(0);
		if (n == 1)
			return BigInteger.valueOf(1);

		// if n >= 2, do the following

		BigInteger a = BigInteger.valueOf(0);
		BigInteger b = BigInteger.valueOf(1);
		BigInteger c;

		while (n > 1) {
			c = b;
			b = a.add(b);
			a = c;
			n--;
		}
		return b;
	}

	private void handle(Socket socket) throws IOException {
		System.err.println("client connected");

		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		PrintWriter out = new PrintWriter(new OutputStreamWriter(
				socket.getOutputStream()));

		try {
			// ogni richiesta è una linea contenente un numero
			for (String line = in.readLine(); line != null; line = in
					.readLine()) {
				System.err.println("request: " + line);
				try {
					int x = Integer.valueOf(line);
					// calcolo risposta e invia al client
					BigInteger y = fibonacci(x);
					System.err.println("reply: " + y);
					out.print(y + "\n");
				} catch (NumberFormatException e) {
					System.err.println("reply: err");
					out.println("err");
				}
				out.flush();
			}
		} finally {
			out.close();
			in.close();
		}
	}

	/**
	 * Avvio del server Fibonacci sulla porta FIBONACCI_PORT
	 */
	public static void main(String[] args) {
		try {
			FibonacciServer server = new FibonacciServer(FIBONACCI_PORT);
			server.serve();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
