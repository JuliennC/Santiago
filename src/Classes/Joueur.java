package Classes;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

import Exception.JoueurException;
import network.SantiagoInterface;

public class Joueur implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pseudo;	
	private boolean pret;
	private int solde;
	
	public Joueur(String pseudo, boolean p) {
		this.pseudo = pseudo;
		this.pret = p;
		solde = 3;
	}
		
	public Joueur(String pseudo, int solde) {
		this.pseudo = pseudo;
		this.solde = solde;
	}


	/**
	 * Fonction Static qui vérifie la validité d'un pseudo du joueur
	 * 
	 * @param pseudo: le pseudo a verifier
	 * @return void
	 * @throws JoueurException
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
	
	/**
	 * Fonction qui gère l'enchère du joueur
	 * 
	 * @param : void
	 * @return : int : le montant de l'enchère
	 * @throws JoueurException 
	 */
	public int joueurFaitUneOffre()  {

		Scanner scInt = new Scanner(System.in);

		
		int offre = 0;

		boolean offreOk = false;
		
		while(! offreOk) {

			//On récupère l'offre
			System.out.println("["+pseudo+"] : Vous devez faire une enchère : ");
			String str = scInt.nextLine();
			offre = Integer.parseInt(str);
			
			//On regarde la conformité de l'offre
			if (offre < 0) {
				System.out.println("Vous ne pouvez pas faire une offre inférieur à 0");
		
			} else if (offre > solde) {

				System.out.println("Vous ne pouvez pas faire une offre supérieur à votre solde : "+solde+".");
			
			} else {
			
				offreOk = true;
			}
			
		}
		
		return offre;
	}
	
	public int joueurChoisitTuile(int nbTuile){
		
		Scanner scInt = new Scanner(System.in);
		int tuile = 0;
		boolean tuileOk = false;
		
		while(! tuileOk) {
			//On récupère l'offre
			System.out.println("["+pseudo+"] : Vous devez choisir une tuile : ");
			String str = scInt.nextLine();
			tuile = Integer.parseInt(str);
			
			//On regarde la conformité de la demande de tuile
			if (tuile < 0 || tuile >= nbTuile) {
				System.out.println("Le chiffre que vous donnez ne correspond a aucune tuile.");
			} else {
				tuileOk = true;
			}
			
		}
		
		return tuile;
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

}
