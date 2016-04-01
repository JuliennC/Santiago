package Classes.Plateau;

public class Plateau {
	//plateau de 8 cases de largeur et 6 cases de hauteur
	
	Case tabPlateau[][] = new Case[7][5] ;
	
	
	public Plateau() {
		super();
		
		for (int i = 0; i<tabPlateau.length; i++){
			for (int j = 0; j<tabPlateau.length-1; j++){
				tabPlateau[i][j]=new Case(i, j, false, null);
				System.out.println(tabPlateau[i][j].toString());
			}
		}
			
		Source source = new Source();
		//on irrigue les cases où la source est placée
		/*int coordX[] = new int[2];
		int coordY[] = new int[2];
		coordX = source.getCoordX();
		coordY = source.getCoordY();
		Case.get*/
	}

}
