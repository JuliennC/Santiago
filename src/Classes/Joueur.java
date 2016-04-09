package Classes;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Exception.JoueurException;
import network.SantiagoInterface;

public class Joueur implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pseudo;	
	private int solde;
	private String couleur;
		
	public Joueur(String pseudo, int solde) {
		this.pseudo = pseudo;
		this.solde = solde;
		this.couleur = "blue";
	}


	/**
	 * Fonction Static qui vérifie la validité d'un pseudo du joueur
	 * 
	 * @param pseudo: le pseudo a verifier
	 * @return void
	 * @throws JoueurException
	 * On lève une exception s'il y a une erreur dans le pseudo
	 */
	public static void pseudoEstValide(String pseudo) throws JoueurException {

		if (pseudo.length() < 3) {

			throw new JoueurException(
					"Votre pseudo doit contenir au moins 3 pseudo.");
		}

	}

	/**
	 * Fonction Static qui vérifie que le solde du joueur est bien positif.
	 *
	 * @param solde: le solde a vérifier
	 * @return void
	 * @throws JoueurException
	 */
	public static void EstPositif(int solde) throws JoueurException {
		if (solde <= 0) {
			throw new JoueurException("Votre solde ne peut pas etre négatif");
		}
	}

	public void AugmenterSoldeParTour() {
		solde = solde + 3;
	}
	
	// --------------- GETTER et SETTER ---------------

	/**
	 * Fonction qui met le pseudo du joueur
	 * 
	 * @param : String -> pseudo 
	 * @return : void
	 */
	public void setPseudo(String pseudo) throws JoueurException {

		// On test le pseudo
		Joueur.pseudoEstValide(pseudo);

		// On met le pseudo
		this.pseudo = pseudo;
	}

	/**
	 * Fonction qui retourne le pseudo du joueur
	 * 
	 * @param : void 
	 * @return : String -> pseudo
	 */
	public String getPseudo() {

		return pseudo;
	}

	/**
	 * Fonction qui met le pseudo du joueur
	 * 
	 * @param : String -> pseudo
	 * @return : void
	 */
	public void setSolde(int solde) throws JoueurException {

		// On test le solde
		Joueur.EstPositif(solde);

		// On met le solde
		this.solde = solde;
	}

	/**
	 * Fonction qui retourne le montant du portefeuille du joueur
	 * 
	 * @param : void 
	 * @return : String -> solde
	 */
	public int getSolde() {

		return solde;
	}


	public String getCouleur() {
		return couleur;
	}
	
	

}
