package Classes.Plateau;

import static org.junit.Assert.assertTrue;

public class Plateau {
	//plateau de 8 cases de largeur et 6 cases de hauteur
	
	//Passer par une array list serait peut être plus simple ...(?)
	// ATTENTION : dans un tableau à double dimension, c'est tab[y][x] !
	private Case tabPlateau[][] = new Case[6][8] ;
	
	private Source source;
	
	public Plateau() {
		super();
		
		//On parcours d'abord les lignes
		for (int i = 0; i<tabPlateau.length; i++){
			
			//On parcours ensuite les colonnes
			for (int j = 0; j<tabPlateau[i].length; j++){
				
				tabPlateau[i][j]=new Case(i, j, false, null);
				System.out.println(tabPlateau[i][j].toString());
			}
		}
		
		//On init la source	
		source = new Source();
		
		//on irrigue les cases où la source est placée
		/*int coordX[] = new int[2];
		int coordY[] = new int[2];
		coordX = source.getCoordX();
		coordY = source.getCoordY();
		Case.get*/
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
				
			str += "[ ";
			
			for(int x=0 ; x < getTabPlateau()[y].length ; x++){
						
				Case c = getTabPlateau()[y][x];
				
				//Si la case est irriguée on l'affiche en maj
				if(c.isIrriguee()){
					
					str += "X   ";
					
				} else {
					
					str += "x   ";
				}
				
				
			}
			
			str += "]\n";

			//On test pour afficher la source
			if( source.getCoordY()[0] == y ){
				
				for(int i=0; i< source.getCoordX()[0]; i++){
				str += "   ";
				}
			
				str+=" . ";
			}
			
			str += "\n";
					
		}
		
		return str;
	}

	
	public String suppChar(String str){
		
		return str.substring(0, str.length() - 1);
	}
}

