package Classes.Plateau;

import java.util.ArrayList;
import java.util.Collections;

import Classes.Tuile.Tuile;
import Classes.Tuile.TuileBanane;
import Classes.Tuile.TuileCanne;
import Classes.Tuile.TuileHaricot;
import Classes.Tuile.TuilePiment;
import Classes.Tuile.TuilePommeDeTerre;
import Exception.PartieException;

public class Plateau {
	//plateau de 8 cases de largeur et 6 cases de hauteur
	
	//Passer par une array list serait peut être plus simple ...(?)
	// ATTENTION : dans un tableau à double dimension, c'est tab[y][x] !
	private Case tabPlateau[][] = new Case[6][8] ;
	
	private Source source;
	private ArrayList<Canal> listeCanaux = new ArrayList<>();
	private ArrayList<Tuile> listeTuiles = new ArrayList<>();

	static int nombreDeTuile1Marqueur = 3;	
	static int nombreDeTuile2Marqueur = 6;	
	
	
	public Plateau() throws PartieException {
		super();
		
		//On parcours d'abord les lignes
		for (int i = 0; i<tabPlateau.length; i++){
			
			//On parcours ensuite les colonnes
			for (int j = 0; j<tabPlateau[i].length; j++){
				
				tabPlateau[i][j]=new Case(i, j, false, null);
				//System.out.println(tabPlateau[i][j].toString());
			}
		}
		
				
		// On lance la fabrication des tuiles
		fabriqueTuiles();
		
		
		//On init la source	
		source = new Source();
		
		
		//on irrigue les cases où la source est placée
		
		//Première case
		Case case1 = tabPlateau[source.getCoordY()[0]][source.getCoordX()[0]];
		case1.setIrriguee(true);
	
		//Deuxième case
		Case case2 = tabPlateau[source.getCoordY()[0]][source.getCoordX()[1]];
		case2.setIrriguee(true);
				
		//Troisieme case
		Case case3 = tabPlateau[source.getCoordY()[1]][source.getCoordX()[0]];
		case3.setIrriguee(true);
	
		//Quatrième case
		Case case4 = tabPlateau[source.getCoordY()[1]][source.getCoordX()[1]];
		case4.setIrriguee(true);
	
		
		
		//On fabrique les canaux
		fabriqueCanaux();
				
	}
	
	
	
	
	
	/**
	 * Fonction qui fabrique les cannaux et les mets en relation avec les cases qu'ils touchent
	 * 
	 * @param : void
	 * @return : void
	 */
	public void fabriqueCanaux(){
		
		//On dit qu'il y a 2 cases entre les canaux
		int nombreDeCasesEntreCanaux = 2;
	
		//On compte le nombre de canaux nécéssaires
		int nombreDeColonnes = tabPlateau[0].length;

		int nombreDeLignes = tabPlateau.length;
		
		//On met les canaux verticaux
		for(int i = 0; i < nombreDeColonnes; i+=2){
			
			
			//On parcours le nombre de ligne
			//On va jusque -1 pour ne pas qu'il y ai un canal en dehors du terrin
			for(int j = 0; j < nombreDeLignes; j++) {
			
				//On ajoute un canal tous les X cases
				if(j % nombreDeCasesEntreCanaux == 0){

					//On crée le canal
					Canal canal = new Canal();
					
					//On peut donc les coordonnées de début
					canal.setCoordDebut(i, j);
					
					//Et on met les coordonnées de fin
					canal.setCoordFin(i, j+2);
					
					//on ajoute la canal à la liste
					listeCanaux.add(canal);
				}	
			}	
		}
	

		//On met les canaux Horizontaux
		for(int i = 0; i < nombreDeLignes; i+=2){
					
			//On parcours le nombre de ligne
			//On va jusque -1 pour ne pas qu'il y ai un canal en dehors du terrin
			for(int j = 0; j < nombreDeColonnes; j++) {
						
				//On ajoute un canal tous les X cases
				if(j % nombreDeCasesEntreCanaux == 0){
					
					//On crée le canal
					Canal canal = new Canal();
					
					//On peut donc les coordonnées de début
					canal.setCoordDebut(j, i);
							
					//Et on met les coordonnées de fin
					canal.setCoordFin(j+2, i);

					//on ajoute la canal à la liste
					listeCanaux.add(canal);
				}			
			}					
		}
		
		
	}
	
	
	
	
	
	/**
	 * Fonction qui fabrique les tuiles nécessaires à la partie et qui les
	 * ajoute dans "listeTuiles"
	 * 
	 * @param : void
	 * @return : void
	 * 
	 *         Si la partie a déjà commencée, on lève une exception
	 */
	public void fabriqueTuiles() throws PartieException {

		if(listeTuiles.size() > 0){
			throw new PartieException("Erreur : La partie semble avoir déjà commencée (il y a déjà des tuiles");
		}
		
		
		// On fabrique les tuiles pomme de terre
		for (int i = 0; i < nombreDeTuile2Marqueur; i++) {
			TuilePommeDeTerre tuile = new TuilePommeDeTerre(2);
			this.listeTuiles.add(tuile);
		}

		for (int i = 0; i < nombreDeTuile1Marqueur; i++) {
			TuilePommeDeTerre tuile = new TuilePommeDeTerre(1);
			this.listeTuiles.add(tuile);
		}

		// On fabrique les tuiles piment
		for (int i = 0; i < nombreDeTuile2Marqueur; i++) {
			TuilePiment tuile = new TuilePiment(2);
			this.listeTuiles.add(tuile);
		}

		for (int i = 0; i < nombreDeTuile1Marqueur; i++) {
			TuilePiment tuile = new TuilePiment(1);
			this.listeTuiles.add(tuile);
		}

		// On fabrique les tuiles haricot
		for (int i = 0; i < nombreDeTuile2Marqueur; i++) {
			TuileHaricot tuile = new TuileHaricot(2);
			this.listeTuiles.add(tuile);
		}

		for (int i = 0; i < nombreDeTuile1Marqueur; i++) {
			TuileHaricot tuile = new TuileHaricot(1);
			this.listeTuiles.add(tuile);
		}

		// On fabrique les tuiles Cannes
		for (int i = 0; i < nombreDeTuile2Marqueur; i++) {
			TuileCanne tuile = new TuileCanne(2);
			this.listeTuiles.add(tuile);
		}

		for (int i = 0; i < nombreDeTuile1Marqueur; i++) {
			TuileCanne tuile = new TuileCanne(1);
			this.listeTuiles.add(tuile);
		}

		// On fabrique les tuiles Bananes
		for (int i = 0; i < nombreDeTuile2Marqueur; i++) {
			TuileBanane tuile = new TuileBanane(2);
			this.listeTuiles.add(tuile);
		}

		for (int i = 0; i < nombreDeTuile1Marqueur; i++) {
			TuileBanane tuile = new TuileBanane(1);
			this.listeTuiles.add(tuile);
		}
		// On mélange la liste
		Collections.shuffle(this.listeTuiles);
		
	}
	
	
	
	
	//--------------- GETTER / SETTER ----------
	
	/**
	 * Fonction qui donne la liste des tuile en jeux
	 * 
	 * @return une liste de tuile
	 */
	public ArrayList<Tuile> getListeTuiles(){
		return listeTuiles;
	}
	
	
	/**
	 * Fonction qui donne la liste des cannaux
	 * 
	 * @return ArrayList<Canal>
	 */
	public ArrayList<Canal> getListeCanaux(){
		return listeCanaux;
	}
	
	
	public Source getSource(){
		return source;
	}
	
	

	public Case[][] getTabPlateau(){
		return tabPlateau;
	}
	
	
	
	public String toString(){

		String str = "";
		
		//On test que chaque case du tableau contienne bien une CASE
		for(int y=0 ; y < getTabPlateau().length ; y++){
				
			str += "[   ";
			
			for(int x=0 ; x < getTabPlateau()[y].length ; x++){
						
				Case c = getTabPlateau()[y][x];

				//on test s'il y a un canal à cet endroit
				for(Canal canal : listeCanaux){

					//Si c'est un canal vertical
					if(canal.getCoordDebut().x == canal.getCoordFin().x){
					
						if( (canal.getCoordDebut().x <= x) && (canal.getCoordFin().x >= x)){
									
							if(canal.estEnEau()){
								str = suppChar(str);
								str = suppChar(str);
								str+="| ";
							}
						}
						
					}
					
				}
				
				//Si la case est irriguée on l'affiche en maj
				if(c.isIrriguee()){
					
					str += "X   ";
					
				} else {
					
					str += "x   ";
				}
				
				
			}
			
			str += "]\n  ";
			
			
			for (int x=0; x < tabPlateau[0].length; x++){
				
				boolean canalTrouve = false;
				
				//on test s'il y a un canal à cet endroit
				for(Canal canal : listeCanaux){

					//Si c'est un canal horizontal
					if(canal.getCoordDebut().y == canal.getCoordFin().y){
					
						if( (canal.getCoordDebut().y <= y) && (canal.getCoordFin().y >= y)){
											
							//On affiche que si le canal est irigué
							if(canal.estEnEau()){
								canalTrouve = true;
							}
						
						} 
						
					}
					
				}
				
				if(canalTrouve){
					str+="----";
					
				} else {
					str+="    ";
				}
				
				//On test pour afficher la source
				if( (source.getCoordY()[0] == y) && (source.getCoordX()[0] == x) ){
					
					str+=".";
				}
				
			}			
			
			str += "\n";
					
		}
		
		return str;
	}

	
	public String suppChar(String str){
		
		return str.substring(0, str.length() - 1);
	}

	public void afficheCanaux(){
		
		for(Canal canal : listeCanaux){
			System.out.println("Canal début : ("+canal.getCoordDebut().x+","+canal.getCoordFin().y+")  Fin : ("+canal.getCoordFin().x+","+canal.getCoordDebut().y+")");
		}
	}

}


