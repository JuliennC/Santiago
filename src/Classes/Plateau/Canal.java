package Classes.Plateau;

import java.awt.Point;
import java.util.ArrayList;

public class Canal {

	//Les coordonnées sont les coordonnées des cases AVANT le canal
	/*
	 * Exemple, un canal posé verticalement au dessus de la source :
	 * 
	 * 		1     2     3     4
	 * 1	x	  X  |  X     x
	 * 2	x	  X	 |	X	  x
	 * 			   source		
	 * 3	x	  X		X	  x
	 * 4	x	  x     x     x
	 * 
	 * Aura pour coordonées :
	 * coordDebut = (2,1)
	 * coordFin   = (2,2)
	 * 
	*/
	


	private Point coordDebut;
	private Point coordFin;
	
	private boolean estEnEau;
	
	
	public Canal(){
		
		estEnEau = false;
	}
	
	
	
	
	// ---------------- GETTER / SETTER ---------------
	
	
	public boolean estEnEau(){
		return estEnEau;
	}
	
	
	
	public void metEnEau(){
		estEnEau = true;
	}
	
	
	public void setCoordDebut(int x, int y){
		coordDebut = new Point(x, y);
	}
	
	
	public Point getCoordDebut(){
		return coordDebut;
	}
	
	public void setCoordFin(int x, int y){
		coordFin = new Point(x, y);
	}
	
	public Point getCoordFin(){
		return coordFin;
	}

	
	/**
	 * Fonction qui indique si le canal est vertical (ou horizontal)
	 *	@param : void
	 *	@return : boolean (true si vertical)
	 */
	public boolean canalEstVertical(){
		return (coordDebut.x == coordFin.x);
	}
	
	
	/**
	 * Fonction to String pour afficher les coordonnées du canal
	 *	
	 *	@return : String
	 */
	public String toString() {
		return "Canal [coordDebut=" + coordDebut + ", coordFin=" + coordFin + ", estEnEau=" + estEnEau + "]";
	}
}
