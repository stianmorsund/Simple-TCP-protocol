package no.uib.inf142.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TCP Server.
 * 
 * @author Stian
 */
public class Server {
	// Variabelen V settes opprinnelig 0
	protected static int V = 0;
	// Server har en liste med logg-objekter som lages hver gang noen forandrer på V
	protected static List<Logg> logg = new ArrayList<Logg>();
	// En port som typisk ikke brukes av andre applikasjoner..
	private static final int PORT = 12000;
	
	
	/***
	 * Øker V og lager et nytt logg-objekt
	 * 
	 * @param n : hvor mye v skal økes
	 * @param ip : ip-adressen til den som øker V
	 */
	protected static synchronized void inc(int n, InetAddress ip) {
		V += n;
		logg.add(new Logg(new Date(), ip, "INC " + n, V));
	}

	/**
	 * Minker V og lager et nytt logg-objekt
	 * 
	 * @param n : hvor mye V skal minkes
	 * @param ip : ip-adressen til den som minker V
	 */
	protected static synchronized void dec(int n, InetAddress ip) {
		V -= n;
		logg.add(new Logg(new Date(), ip, "DEC " + n, V));
	}
	
	

	/***
	 * Main-metoden setter opp en ServerSocket som venter på innkommende tilkoblinger gjennom spesifisert port.
	 * Hver nye klient får en egen tråd for å forsikre korrekt oppdatering.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		// Setter opp en listener som venter på at klienter skal koble til
		ServerSocket listener = new ServerSocket(PORT);

		try {
			while (true) {
				new ServerThread(listener.accept()).start();

			}
		} finally {
			listener.close();
		}
	}
}