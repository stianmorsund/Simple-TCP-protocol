package no.uib.inf142.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/***
 * Bruker tråder for å hindre kappløp (race conditions) og garantere at
 * variabelen blir riktig oppdatert
 * 
 * @author Stian
 *
 */

public class ServerThread extends Thread {

	// Bufferedreader mottar beskjeder fra klient
	BufferedReader in;

	// PrintWriter sender beskjeder til klient
	PrintWriter out;
	
	private Socket socket;

	public ServerThread(Socket socket) {
		this.socket = socket;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public void run() {

		try {

			skrivMeny(out);

			// Tar kontinuerlig imot kommandoer fra klient
			while (true) {

				String input = in.readLine().toUpperCase();

				// klient spør om verdi, server sender den tilbake
				if (input.startsWith("VAL")) {
					out.println("VAL " + Server.V);
				}

				// klient spør om å få inkrementere V
				// Server svarer OPPDATERT for å indikere at kommandoen ble
				// eksekvert

				else if (input.startsWith("INC")) {
					Server.inc(Integer.parseInt(input.substring(4)), socket.getInetAddress());
					out.println("OPPDATERT");

				}

				// klient spør om å få minke V
				// Server svarer OPPDATERT for å indikere at kommandoen ble
				// eksekvert

				else if (input.startsWith("DEC")) {
					Server.dec(Integer.parseInt(input.substring(4)), socket.getInetAddress());
					out.println("OPPDATERT");

				}

				// klient spør om få se loggen til v
				// server svarer
				else if (input.startsWith("LOG")) {

					// første linje sier hvor mange elementer det er i listen
					out.println("LOG " + (Server.logg.size() + 1));
					out.println("Dato\t\t\tIP\t\t\tKommando\tNy verdi");
					for (Logg l : Server.logg) {
						out.println(l);
					}

				}

				else if (input.startsWith("QUIT")) {
					return;
				} else {
					out.println("INVALID_COMMAND");
				}

			}

		} catch (IOException e) {

		} finally {
			try {
				socket.close();
			} catch (IOException e) {

			}

		}

	}

	private void skrivMeny(PrintWriter out) {
		out.println("HEI 8");
		out.println("******************************************");
		out.println("Lovlige kommandoer:");
		out.println("******************************************");
		out.println("VAL\t\t: Spør om verdien til V");
		out.println("INC <tall>\t: Øker V med <tall>");
		out.println("DEC <tall>\t: Minker V med <tall>");
		out.println("LOG\t\t: Vis logg");
		out.println("QUIT\t\t: Terminer forbindelse");

	}
}
