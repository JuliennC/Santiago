package Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import Classes.Tuile.*;
import Exception.PartieException;
import Classes.Marqueurs.*;

public class Partie implements Serializable{

	static int nombreDeJoueursMin = 3;
	static int nombreDeJoueursMax = 5;	
	static int nombreDeTuile1Marqueur = 3;	
	static int nombreDeTuile2Marqueur = 6;	
	
	private String nomPartie;
	private ArrayList<Joueur> listeJoueurs;
	private ArrayList<Tuile> listeTuiles;
	private int nombreDeJoueurs;
	private boolean partieACommence = false;
	
	
	public Partie(String aNom, int nbJoueurs) throws PartieException{
		
		this.nomPartie = aNom;
		this.nombreDeJoueurs = nbJoueurs;
		this.partieACommence = false;
		this.listeJoueurs = new ArrayList<Joueur>();
		
		//On lance la fabrication des tuiles
		//fabriqueTuiles();
	}

	/*
	 * Fonction qui ajoute un joueur à la partie
	 * Ne peut être appelée qu'avant le début de la partie
	 * 
	 * Params : un Joueur
	 * Return : void
	 * 	
	 * Si le joueur ne peut pas petre ajouté, on lève une exception
	 */
	public void addJoueur(Joueur joueur) throws PartieException{
		
		if (! listeJoueurs.contains(joueur)) {
			
			listeJoueurs.add(joueur);
		
		} else {
			
			throw new PartieException("Vous êtes déjà dans la partie");
		}
	}
	
	
	
	
	
	/*
	 * Fonction qui fabrique les tuiles nécessaires à la partie
	 * et qui les ajoute dans "listeTuiles"
	 * 
	 * Params : void
	 * Return : void
	 * 	
	 * Si la partie a déjà commencée, on lève une exception
	 */
	public void fabriqueTuiles() throws PartieException{
		
		if(partieACommence){
			throw new PartieException("La partie a déjà commencé.");
		}
		
		//On fabrique les tuiles pomme de terre
		for (int i=0 ; i < nombreDeTuile2Marqueur ; i++){
			TuilePommeDeTerre tuile = new TuilePommeDeTerre(2);
			listeTuiles.add(tuile);
		}
		
		for (int i=0 ; i < nombreDeTuile1Marqueur ; i++){
			TuilePommeDeTerre tuile = new TuilePommeDeTerre(1);
			listeTuiles.add(tuile);
		}
		
		
		//On fabrique les tuiles piment
		for (int i=0 ; i < nombreDeTuile2Marqueur ; i++){
			TuilePiment tuile = new TuilePiment(2);
			listeTuiles.add(tuile);
		}
				
		for (int i=0 ; i < nombreDeTuile1Marqueur ; i++){
			TuilePiment tuile = new TuilePiment(1);
			listeTuiles.add(tuile);
		}
		
		
		//On fabrique les tuiles haricot
		for (int i=0 ; i < nombreDeTuile2Marqueur ; i++){
			TuileHaricot tuile = new TuileHaricot(2);
			listeTuiles.add(tuile);
		}
						
		for (int i=0 ; i < nombreDeTuile1Marqueur ; i++){
			TuileHaricot tuile = new TuileHaricot(1);
			listeTuiles.add(tuile);
		}
		
		
		//On fabrique les tuiles Cannes
		for (int i=0 ; i < nombreDeTuile2Marqueur ; i++){
			TuileCanne tuile = new TuileCanne(2);
			listeTuiles.add(tuile);
		}
								
		for (int i=0 ; i < nombreDeTuile1Marqueur ; i++){
			TuileCanne tuile = new TuileCanne(1);
			listeTuiles.add(tuile);
		}
		
		
		//On fabrique les tuiles Bananes
		for (int i=0 ; i < nombreDeTuile2Marqueur ; i++){
			TuileBanane tuile = new TuileBanane(2);
			listeTuiles.add(tuile);
		}
										
		for (int i=0 ; i < nombreDeTuile1Marqueur ; i++){
			TuileBanane tuile = new TuileBanane(1);
			listeTuiles.add(tuile);
		}
		
		
		// On mélange la liste
		Collections.shuffle(listeTuiles);
	}
	
	
	
	
	/*
	 * Fonction qui tire les tuiles Tuiles (autant que de joueurs)
	 * 
	 * Params : void
	 * Return : ArrayList<Tuile>
	 * 	
	 * S'il n'y a plus assez de tuiles, on renvoit une exception
	 */
	public ArrayList<Tuile> retourneTuiles() {
		
		ArrayList<Tuile> tuiles = new ArrayList<Tuile>();
		
		for(int i = 0; i < nombreDeJoueurs ; i++){
			tuiles.add(listeTuiles.get(i));
			listeTuiles.remove(i);
		}
	
		return tuiles;
	}
	
	
	
	
	
	
	// --------------- GETTER et SETTER ---------------
	
	/*
	 * Fonction qui met le nombde de joueurs pour la partie
	 * Ne peut être appelée qu'avant le début de la partie
	 * 
	 * Params : int
	 * Return : void
	 * 	
	 * Si le nombre de joueur ne convient pas, on lève une exception
	 */
	public void setNombreDeJoueurs(int nb) throws PartieException {
	
		if ( (nb < nombreDeJoueursMax) && (nb > nombreDeJoueursMin)){
			
			nombreDeJoueurs = nb;
		
		} else {
			
			throw new PartieException("Une partie se fait avec un minimum de "+nombreDeJoueursMin+" et "+nombreDeJoueursMax+".");
		}
		
	}
	
	
	
	/*
	 * Fonction qui retourne le nombre de joueurs de la partie
	 * 
	 * Params : void
	 * Return : int
	 * 	
	 */
	public int getNombreJoueurs() {
		return nombreDeJoueurs;
	}
	
	
	
	
	/*
	 * Fonction qui retourne une liste qui contient les joueurs des joueurs
	 * 
	 * Params : void
	 * Return : ArrayList<Joueur>
	 * 	
	 */
	public ArrayList<Joueur> getListeJoueurs() {
	
		//On retourne une "copie" de la liste pour qu'aucun joueur ne puisse être changé après le début de la partie
		 ArrayList<Joueur> liste = new ArrayList<Joueur>(listeJoueurs);
		 
		 return liste;
	}
	

	
	
	/*
	 * Fonction qui indique que la partie a commencé
	 * Ne peut être appelée qu'une seule fois
	 * 
	 * Params : void
	 * Return : void
	 * 	
	 * Si la partie a déjà commencée, on lève une exception
	 */
	public void setPartieCommence() throws PartieException {
	
		if ( partieACommence == false ){
			
			partieACommence = true;
		
		} else {
			
			throw new PartieException("La partie a déjà commencée");
		}
		
	}
	
	
	
	/*
	 * Fonction qui dit si une partie a commencé ou nom
	 * 
	 * Params : void
	 * Return : Boolean
	 * 	
	 */
	public boolean getPartieACommence() {
	
		 return partieACommence;
	}
	
	/**
	 * Fonction qui retourne le nom d'une partie
	 * Le nom est définit par le Leader de la partie lors de la création
	 * @return nomPartie
	 */
	public String getNomPartie() {
		return nomPartie;
	}
	
}
