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
		
		//On compte le nombre de canaux nécéssaires
		int nombreDeColonnes = tabPlateau[0].length;

		int nombreDeLignes = tabPlateau.length;
		


		
		// Il faudra voir si ce n'est pas possible de les généraux dynamiquement (début plus haut)
		
		//Les canaux horyiontaux
		for (int i=0 ; i<=nombreDeLignes ; i+=2){
			
			for(int j=0 ; j<nombreDeColonnes-2 ; j+=2){
				
				Canal c1 = new Canal();
				c1.setCoordDebut(j, i);
				c1.setCoordFin(j+1, i);
				listeCanaux.add(c1);
				//c1.metEnEau();
			}
		}
		
		
		
		//Les canaux verticaux
		for (int i=0 ; i<nombreDeColonnes ; i+=2){
					
			for(int j=0 ; j< nombreDeLignes ; j+=2){
				
				Canal c1 = new Canal();
				c1.setCoordDebut(i, j);
				c1.setCoordFin(i, j+1);
				listeCanaux.add(c1);
				//c1.metEnEau();
			}
			
		}
		
		
		
		
	}
	
	
	
	
	
	/**
	 * Fonction qui met un canal
	 * 
	 * @param : Canal
	 * @return void
	 */
	public void metCanal(Canal canal){
		
		//On indique que le canal est en eau
		canal.metEnEau();
		
		//On récup les coordonées du canal
		int x = canal.getCoordDebut().x;
		int y = canal.getCoordDebut().y;
		
		if(canal.canalEstVertical()){
			
			//On met les cases irriguées
			try{
				
				Case c = tabPlateau[y][x-1];
				c.setIrriguee(true);
			
				c = tabPlateau[y+1][x-1];
				c.setIrriguee(true);
				
			//Si on rentre dans le catch c'est que le canal est tout à gauche
			} catch(Exception e){}
			
			
			try{
				
				Case c = tabPlateau[y][x];
				c.setIrriguee(true);
			
				c = tabPlateau[y+1][x];
				c.setIrriguee(true);
				
			//Si on rentre dans le catch c'est que le canal est tout à droite
			} catch(Exception e){}
			
			
			
		} else {
			
			//On met les cases irriguées
			try{
				
				Case c = tabPlateau[y-1][x];
				c.setIrriguee(true);
			
				c = tabPlateau[y][x];
				c.setIrriguee(true);
				
			//Si on rentre dans le catch c'est que le canal est tout à gauche
			} catch(Exception e){}
			
			
			try{
				
				Case c = tabPlateau[y-1][x+1];
				c.setIrriguee(true);
			
				c = tabPlateau[y][x+1];
				c.setIrriguee(true);
				
			//Si on rentre dans le catch c'est que le canal est tout à droite
			} catch(Exception e){}
			
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

		String str = "\n\n";
		
		
		//On test que chaque case du tableau contienne bien une CASE
		for(int y=0 ; y <= getTabPlateau().length ; y++){
				
			str += "  ";
			for (int x=0; x < tabPlateau[0].length; x++){
				
				boolean canalTrouve = false;
				
				//on test s'il y a un canal à cet endroit
				for(Canal canal : listeCanaux){

					//Si c'est un canal horizontal
					if( (canal.getCoordDebut().y == canal.getCoordFin().y) && (canal.getCoordFin().y == y) ){
					
						if( (canal.getCoordDebut().x <= x) && (canal.getCoordFin().x >= x)){
											
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
				if( (source.getCoordY()[1] == y) && (source.getCoordX()[0] == x) ){
					
					str+=".";
				}
}

			
			if(y<tabPlateau.length){
				
				str += "\n[   ";
			
			for(int x=0 ; x <= getTabPlateau()[y].length ; x++){
				

				//on test s'il y a un canal à cet endroit
				for(Canal canal : listeCanaux){

					//Si c'est un canal vertical
					if((canal.getCoordDebut().x == canal.getCoordFin().x) && (canal.getCoordFin().x == x)){
					
						if( (canal.getCoordDebut().y <= y) && (canal.getCoordFin().y >= y)){
									
							if(canal.estEnEau()){
								str = suppChar(str);
								str = suppChar(str);
								str+="| ";
							}
						}
						
					}
					
				}
				
			
			if(x < tabPlateau.length){

				Case c = getTabPlateau()[y][x];

				
				//Si la case est irriguée on l'affiche en maj
				if(c.isIrriguee()){
					
					str += "X   ";
					
				} else {
					
					str += "x   ";
				}
				
			}
			}
			
			str += "]  ";
			}
			
			/*for (int x=0; x < tabPlateau[0].length; x++){
				
				boolean canalTrouve = false;
				
				//on test s'il y a un canal à cet endroit
				for(Canal canal : listeCanaux){

					//Si c'est un canal horizontal
					if( (canal.getCoordDebut().y == canal.getCoordFin().y) && (canal.getCoordFin().y-1 == y) ){
					
						if( (canal.getCoordDebut().x <= x) && (canal.getCoordFin().x >= x)){
											
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
				
			}			*/
			
			str += "\n";
					
		}
		
		return str;
	}

	
	public String suppChar(String str){
		
		return str.substring(0, str.length() - 1);
	}

	public void afficheCanaux(){
		
		for(Canal canal : listeCanaux){
			System.out.println("Canal début : ("+canal.getCoordDebut().x+","+canal.getCoordDebut().y+")  Fin : ("+canal.getCoordFin().x+","+canal.getCoordFin().y+")");
		}
	}

}


