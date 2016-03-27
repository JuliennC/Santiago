package Classes;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import Exception.JoueurException;
import network.SantiagoInterface;

public class Joueur implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pseudo;	
	private boolean pret;
	
	public Joueur(String pseudo, boolean p) {
		this.pseudo = pseudo;
		this.pret = p;
	}
	
	
	public Joueur() {
		
	}
	
	
	
	/*
	 * Fonction Static qui vérifie la validité d'un pseudo du joueur
	 * Params : String -> le pseudo
	 * Return : void
	 * 			
	 * On lève une exception s'il y a une erreur dans le pseudo
	 */
	public static void pseudoEstValide(String pseudo) throws JoueurException {
		
		if(pseudo.length() < 3) {

			throw new JoueurException("Votre pseudo doit contenir au moins 3 pseudo.");
		} 
		
	}
	
	
	
	
	// --------------- GETTER et SETTER ---------------

	
	
	
	/*
	 * Fonction qui met le pseudo du joueur
	 * 
	 * Params : String -> pseudo
	 * Return : void
	 * 	
	 */
	public void setPseudo(String pseudo) throws JoueurException{
					
		// On test le pseudo
		Joueur.pseudoEstValide(pseudo);
			
		// On met le pseudo
		this.pseudo = pseudo;
	}
	
	
	
	/*
	 * Fonction qui retourne le pseudo du joueur
	 * 
	 * Params : void
	 * Return : String -> pseudo
	 * 	
	 */
	public String getPseudo(){
		
		return pseudo;
	}
}
