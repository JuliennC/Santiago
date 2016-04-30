package Classes.Plateau;

import java.io.Serializable;

/**
 * Classe Source
 */
public class Source implements Serializable{
	/**
	 * La source est un couple de coordonées
	 * Ex : elle peut etre en X[1,2] Y[1,2] elle sera en entre la colonne 1,2 et la ligne 1,2
	 * liste des points possibles pour x : x[1,2] x[3,4] x[5,6] 
	 * liste des points possibles pour y : y[1,2] y[3,4]
	 * Les coordonnées commencent à 0
	 */

	private int coordX[] = new int[2];
	private int coordY[] = new int[2];
	
	public int[] getCoordX() {
		return coordX;
	}
	public void setCoordX(int[] coordX) {
		this.coordX = coordX;
	}
	public int[] getCoordY() {
		return coordY;
	}
	public void setCoordY(int[] coordY) {
		this.coordY = coordY;
	}
	
	/**
	 * Constructeur de Source
	 * placement aléatoire en X @see placerSourceX()
	 * placement aléatoire en Y @see placerSourceY()
	 * @return Affichage des coordonnées de la source
	 */
	public Source() {
		
		super();
		coordX = placerSourceX();
		coordY = placerSourceY();
		/*System.out.println("La source a pour coordonnees X :");
		for (int i = 0; i<coordX.length; i++){
			System.out.println("x["+ i +"]: "+coordX[i]);
		}
		System.out.println("La source a pour coordonnees Y :");
		for (int i = 0; i<coordX.length; i++){
			System.out.println("y["+ i +"]: "+coordY[i]);
		}*/
		
	}
	
	/**
	 * Méthode de placement aléatoire de la source en X
	 * 
	 * @return Un tableau de coordonnées en X
	 */
	public int[] placerSourceX(){
		int l = 0;
		int h = 3;
		int coordXrandom = (int)(Math.random() * (h-l)) + l;
		//System.out.println("coordXrandom = "+coordXrandom);
		switch(coordXrandom){
		case 0:
			coordX[0]= 1;
			coordX[1]= 2;
			break;
			
		case 1:
			coordX[0]= 3;
			coordX[1]= 4;
			break;
			
		case 2:
			coordX[0]= 5;
			coordX[1]= 6;
			break;
		
		default:
			System.out.println("Probleme de placement de la source");
		}
		return coordX;
	}
	
	/**
	 * Méthode de placement aléatoire de la source en Y
	 * 
	 * @return Un tableau de coordonnées en Y
	 */
	public int[] placerSourceY(){
		int l = 0;
		int h2 = 2;
		int coordYrandom = (int)(Math.random() * (h2-l)) + l;
		//System.out.println("coordYrandom = "+coordYrandom);
		switch(coordYrandom){
		
		case 0:
			coordY[0]= 1;
			coordY[1]= 2;
			break;
			
		case 1:
			coordY[0]= 3;
			coordY[1]= 4;
			break;
		
		default:
			System.out.println("Probleme de placement de la source");
		}
		return coordY;
	}
	
}
