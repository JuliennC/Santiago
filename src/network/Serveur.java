package network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Classes.Joueur;
import Classes.Partie;

public class Serveur extends UnicastRemoteObject {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public Serveur() throws RemoteException {
		super();

	}


	public static void test(){
		
		System.out.println("Msg recu serveur");
	}
	
	
	public static void rejoindrePartie(Joueur joueur, Partie partie){
		
		System.out.println("Msg recu serveur");
	}

}
