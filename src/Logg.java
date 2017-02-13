package no.uib.inf142.server;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;


/***
 * Logg
 * @author Stian
 *
 */
public class Logg {
	private SimpleDateFormat format;
	private Date dato;
	private InetAddress ip;
	private String kommando;
	private int nyVerdi;
	
	public Logg(Date dato, InetAddress ip, String kommando, int nyVerdi) {
		format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		this.dato = dato;
		this.ip = ip;
		this.kommando = kommando;
		this.nyVerdi = nyVerdi;
	}


	public Date getDato() {
		return dato;
	}


	public void setDato(Date dato) {
		this.dato = dato;
	}


	public InetAddress getIp() {
		return ip;
	}


	public void setIp(InetAddress ip) {
		this.ip = ip;
	}


	public String getKommando() {
		return kommando;
	}


	public void setKommando(String kommando) {
		this.kommando = kommando;
	}


	public int getNyVerdi() {
		return nyVerdi;
	}


	public void setNyVerdi(int nyVerdi) {
		this.nyVerdi = nyVerdi;
	}


	public String toString() {
		return String.format("%s\t%s\t\t%s\t\t%d", format.format(dato), ip, kommando, nyVerdi);
	}

	
}
