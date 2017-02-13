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
	// Server har en liste med logg-objekter som lages hver gang noen forandrer p� V
	protected static List<Logg> logg = new ArrayList<Logg>();
	// En port som typisk ikke brukes av andre applikasjoner..
	private static final int PORT = 12000;
	
	
	/***
	 * �ker V og lager et nytt logg-objekt
	 * 
	 * @param n : hvor mye v skal �kes
	 * @param ip : ip-adressen til den som �ker V
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
	 * Main-metoden setter opp en ServerSocket som venter p� innkommende tilkoblinger gjennom spesifisert port.
	 * Hver nye klient f�r en egen tr�d for � forsikre korrekt oppdatering.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		// Setter opp en listener som venter p� at klienter skal koble til
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