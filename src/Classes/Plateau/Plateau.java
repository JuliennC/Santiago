package Classes.Plateau;

public class Plateau {
	//plateau de 6 cases de hauteur et 8 cases de largeur
	
	private Case tabPlateau[][];
	
	
	public Plateau() {
		super();
		Case tabPlateau[][]= new Case [7][5];
		
		for (int i = 0; i<tabPlateau.length; i++){
			for (int j = 0; j<tabPlateau.length; i++){
				tabPlateau[i][j]=new Case(i,j);
			}
		}
		Source source = new Source();
	}
}
