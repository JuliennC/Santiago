package Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import Classes.Marqueurs.MarqueurOrange;
import Classes.Marqueurs.MarqueurRendement;
import Classes.Marqueurs.MarqueurRose;
import Classes.Marqueurs.MarqueurRouge;
import Classes.Marqueurs.MarqueurVert;
import Classes.Marqueurs.MarqueurViolet;
import Exception.JoueurException;


public class Joueur implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pseudo;	
	private String motDePasse;	
	
	private int solde;
	private String couleur;
	private ArrayList<MarqueurRendement> marqueurRestant;
		
	public Joueur() {}
	
	/**
	 * Fonction qui crée un joueur SANS MOT DE PASSE --> Utilisé pour les tests
	 */
	public Joueur(String pseudo, int solde) {
		this.pseudo = pseudo;
		this.solde = solde;
		this.marqueurRestant = new ArrayList<MarqueurRendement>();
	}
	
	public Joueur(String pseudo, String motDePasse, int solde) {
		this.pseudo = pseudo;
		this.motDePasse = motDePasse;
		this.solde = solde;
		this.marqueurRestant = new ArrayList<MarqueurRendement>();
	}
	
	public Joueur(String pseudo, int solde,String couleur) {
		this.pseudo = pseudo;
		this.solde = solde;
		this.couleur = couleur;
		this.marqueurRestant = new ArrayList<MarqueurRendement>();
		for(int i =0 ; i<22; i++){
			switch(this.couleur){
				case "Vert" :
					this.marqueurRestant.add(new MarqueurVert());
					break;
				case "Rose" :
					this.marqueurRestant.add(new MarqueurRose());
					break;
				case "Violet" :
					this.marqueurRestant.add(new MarqueurViolet());
					break;
				case "Orange" :
					this.marqueurRestant.add(new MarqueurOrange());
					break;
				case "Rouge" :
					this.marqueurRestant.add(new MarqueurRouge());
					break;
				default:
					break;
			}
		}
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

	public int[] joueurChoisitPlacement(){
		int[] coord = new int[2];
		Scanner scInt = new Scanner(System.in);
		boolean placementOk = false;
		
		while(!placementOk){
			System.out.println("["+pseudo+"] : Où voulez vous placez votre tuile ? ");
			System.out.println("["+pseudo+"] : Position en Y : ");
			String str = scInt.nextLine();
			coord[0] = (int) Integer.parseInt(str);
			System.out.println("["+pseudo+"] : Position en X : ");
			str = scInt.nextLine();
			coord[1] = (int) Integer.parseInt(str);
			if(coord[0]<6 && coord[1]<8){
				placementOk = true;
			}
			else{
				System.out.println("Les positions que vous avez choisit ne font pas partie du plateau");
			}
		}
		return coord;
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


	
	
	public void setCouleur(String couleur){
		this.couleur = couleur;
		for(int i =0 ; i<22; i++){
			switch(this.couleur){
			case "Vert" :
				this.marqueurRestant.add(new MarqueurVert());
				break;
			case "Rose" :
				this.marqueurRestant.add(new MarqueurRose());
				break;
			case "Violet" :
				this.marqueurRestant.add(new MarqueurViolet());
				break;
			case "Orange": 
				this.marqueurRestant.add(new MarqueurOrange());
				break;
			case "Rouge" :
				this.marqueurRestant.add(new MarqueurRouge());
				break;
			default:
				break;
			}
		}
	}
	
	
	public String getCouleur() {
		return couleur;
	}
	
	
	public ArrayList<MarqueurRendement> getListeMarqueurs(){
		return this.marqueurRestant;
	}
	
	public MarqueurRendement getMarqueur(){
		MarqueurRendement marqueur = this.marqueurRestant.get(0);
		this.marqueurRestant.remove(marqueur);
		return marqueur;
	}

	public ArrayList<MarqueurRendement> getMarqueurRestant() {
		return marqueurRestant;
	}

	public void setMarqueurRestant(ArrayList<MarqueurRendement> marqueurRestant) {
		this.marqueurRestant = marqueurRestant;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	public String getCodeCouleur(){
		switch(this.couleur){
			case "Rouge":
				return "#ff0000";
			case "Orange":
				return "#ff8000";
			case "Rose":
				return "#ff46d1";
			case "Vert":
				return "#13c300";
			case "Violet":
				return "#780274";
			default:
				return null;
		}
	}

}
