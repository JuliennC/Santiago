package Classes.Tuile;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import Classes.Marqueurs.*;
import Exception.TuileException;

public abstract class Tuile implements Serializable {
	
	private int nombreMarqueursNecessaires;
	private ArrayList<MarqueurRendement> marqueursActuels;
	private boolean tuileEstPosee;
	
	protected String intituleDuChamps;
		
	
	public Tuile(int nbMarqueurs) {
		
		nombreMarqueursNecessaires = nbMarqueurs;
	}

	
	

	
	
	/*
	 * Fonction qui enlève un marqueur de rendement de la tuile
	 * 
	 * Params : void
	 * Return : void
	 * 			
	 * Si la tuile ne contient déjà plus de marqueurs, on lève une exception
	 */
	public void supprimeUnMarqueur() throws TuileException{
		
		if(marqueursActuels.size() == 0) {
			throw new TuileException("La tuile ne contient plus de marqueurs, vous ne pouvez donc pas en enlever");
		}
		
		marqueursActuels.remove(0);
	}
	
	
	

	// --------------- GETTER et SETTER ---------------

	
	
	/*
	 * Fonction qui retourne le nombre de marqueurs de rendement actuellement posés sur la tuile
	 * 
	 * Params : void
	 * Return : int
	 * 			
	 */
	public int getNombreMarqueursActuels(){
		
		return marqueursActuels.size();
	}
	
	
	
	
	/*
	 * Fonction qui retourne le nombre de marqueurs necessaires
	 * 
	 * Params : void
	 * Return : int
	 * 			
	 */
	public int getNombreMarqueursNecessaires(){
		
		return nombreMarqueursNecessaires;
	}
	
	
	
	/*
	 * Fonction qui retourne l'intitulé du champs
	 * 
	 * Params : void
	 * Return : String
	 * 			
	 */
	public String getIntitule(){
		
		return intituleDuChamps;
	}
	
	

	
}
