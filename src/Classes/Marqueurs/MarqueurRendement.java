package Classes.Marqueurs;

import Classes.Joueur;

public abstract class MarqueurRendement {

	private Joueur joueurProprietaire;
	
	/*
	 * ATTENTION devra être remplacé par le lien de l'image du marqueur
	 */
	private String couleur;

	
	public MarqueurRendement(String c){
		
		couleur = c;
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
