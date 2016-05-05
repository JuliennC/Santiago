package Classes.Marqueurs;

import java.io.Serializable;

import Classes.Joueur;

public abstract class MarqueurRendement implements Serializable{

	private Joueur joueurProprietaire;
	
	protected String path;
	/*
	 * ATTENTION devra être remplacé par le lien de l'image du marqueur
	 */
	private String couleur;

	
	public MarqueurRendement(String c, String p){
		
		couleur = c;
		path = p;
	}
	
	
	
	// --------------- GETTER et SETTER ---------------

	
	
	/*
	 * Fonction qui met le joueur
	 * 
	 * Params : Joueur 
	 * Return : void
	 * 			
	 */
	public void setJoueur(Joueur joueur){
			
		joueurProprietaire = joueur;
	}
	
	
	/**
	 * Fonction qui retourn la couleur d'un marqueur de rendement
	 * 
	 * @return String : la couleur
	 */
	public String getCouleur(){
		return couleur;
	}
}
