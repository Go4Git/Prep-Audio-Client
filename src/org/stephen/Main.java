package org.stephen;

import org.stephen.net.ClientConnector;

/**
 * The main class of the client. Disposes the GUI
 * and establishes the connection to the server.
 * @author Stephen Andrews
 */
public class Main {

	/**
	 * The main method.
	 * @param args The arguments, if any.
	 */
	public static void main(String[] args) {
		try {
			new ClientConnector().connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
