package no.uib.inf142.klient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/***
 * 
 * @author Stian
 *
 */
public class Klient {

	// Bufferedreader mottar beskjeder fra server
	private BufferedReader in;

	// PrintWriter sender beskjeder til server
	private PrintWriter out;

	// Scanner håndterer input fra stdin
	private Scanner sc;

	// Bruker localhost
	private static final String SERVERADRESSE = "127.0.1";
	private static final int PORT = 12000;
	private Socket socket;

	/***
	 * Lager en ny klient. Setter opp nettverksinnstillinger, samt lager en ny
	 * socket, in-og-utstrøm, samt en scanner for å motta kommandoer fra
	 * tastatur
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Klient() throws UnknownHostException, IOException {

		socket = new Socket(SERVERADRESSE, PORT);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		sc = new Scanner(System.in);
		System.out.println("Koblet til server.");

	}

	public void kobleTil() throws Exception {

		String respons;

		try {

			respons = in.readLine();

			// Klient mottar velkomstbeskjeden fra server, med antall linjer som
			// argument
			if (respons.startsWith("HEI")) {

				for (int i = 0; i < Integer.parseInt(respons.substring(4)); i++) {
					System.out.println(in.readLine());
				}

			}

			while (true) {

				String kommando = sc.nextLine();

				out.println(kommando);

				if (kommando.toUpperCase().equals("QUIT"))
					System.exit(0);

				respons = in.readLine();

				// Server har mottatt en ugyldig kommando
				if (respons.startsWith("INVALID_COMMAND")) {
					System.out.println("Ugyldig kommando");
				}
				// Server har sendt en verdi i retur
				else if (respons.startsWith("VAL")) {

					// Skriver ut verdien til v
					System.out.println("V = " + respons.substring(4));
				}
				// Server har oppdatert variabelen
				else if (respons.startsWith("OPPDATERT")) {
					System.out.println("Variabelen ble oppdatert.");
				}
				// Server sender logg; med antall logglinjer som argument
				else if (respons.startsWith("LOG")) {
					for (int i = 0; i < Integer.parseInt(respons.substring(4)); i++) {
						System.out.println(in.readLine());

					}

				}

			}

		} finally {
			socket.close();

		}

	}

	public static void main(String[] args) throws Exception {
		Klient k = new Klient();

		k.kobleTil();
	}

}
