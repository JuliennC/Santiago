package Classes.Tuile;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import Classes.Marqueurs.*;
import Exception.TuileException;

public abstract class Tuile implements Serializable {
	
	private int nombreMarqueursNecessaires;
	private ArrayList<MarqueurRendement> marqueursActuels = new ArrayList<>();
	private boolean tuileEstPosee;
	
	protected String intituleDuChamps;
	protected boolean estDesert;
		
	
	public Tuile(int nbMarqueurs) {
		
		nombreMarqueursNecessaires = nbMarqueurs;
	}

	
	

	
	
	/**
	 * Fonction qui enlève un marqueur de rendement de la tuile
	 * 
	 * @param : void
	 * @return : void
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

	
	
	/**
	 * Fonction qui ajoute un marqueur de rendement
	 * 
	 * @param : MarqueurRendement
	 */
	public void addMarqueur(MarqueurRendement m){
		marqueursActuels.add(m);
	}
	
	
	
	
	/**
	 * Fonction qui met une tuile en désert
	 * 
	 * @param : void
	 * @return : void
	 */
	public void setDesert(){

		while(marqueursActuels.size() > 0){
			try {
				supprimeUnMarqueur();
			}catch(Exception e){
				
			}
		}
		
		estDesert = true;
	}
	
	
	
	/**
	 * Fonction qui dit si une tuile est désert ou non
	 * 
	 * @param : void
	 * @return : boolean
	 */
	public boolean estDesert(){
		return estDesert;
	}
	
	
	
	/**
	 * Fonction qui retourne le nombre de marqueurs de rendement actuellement posés sur la tuile
	 * 
	 * @param : void
	 * @return : int
	 * 			
	 */
	public int getNombreMarqueursActuels(){
		
		return marqueursActuels.size();
	}
	
	
	
	
	/**
	 * Fonction qui retourne le nombre de marqueurs necessaires
	 * 
	 * @param : void
	 * @return : int
	 * 			
	 */
	public int getNombreMarqueursNecessaires(){
		
		return nombreMarqueursNecessaires;
	}
	
	
	
	/**
	 * Fonction qui retourne l'intitulé du champs
	 * 
	 * @param : void
	 * @return : String
	 * 			
	 */
	public String getIntitule(){
		
		return intituleDuChamps;
	}
	
	
	
	/**
	 * Fonction qui retourne la liste des marqueurs actuellement sur la tuile
	 * 
	 * @return : ArrayList<MarqueurActuel>
	 */
	public ArrayList<MarqueurRendement> getMarqueursActuels(){
		return marqueursActuels;
	}
	
}
