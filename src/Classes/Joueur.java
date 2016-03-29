package Classes;

import Exception.JoueurException;

public class Joueur {

	private String pseudo;
	private int solde;

	public Joueur(String pseudo, int solde) {
		this.pseudo = pseudo;
		this.solde = solde;
	}

	public Joueur() {

	}

	/*
	 * Fonction Static qui vÃ©rifie la validitÃ© d'un pseudo du joueur Params :
	 * String -> le pseudo Return : void
	 * 
	 * On lÃ¨ve une exception s'il y a une erreur dans le pseudo
	 */
	public static void pseudoEstValide(String pseudo) throws JoueurException {

		if (pseudo.length() < 3) {

			throw new JoueurException(
					"Votre pseudo doit contenir au moins 3 pseudo.");
		}

	}

	/*
	 * Fonction Static qui vérifie que le solde du joueur est bien positif.
	 * Params : Int -> le pseudo Return : void
	 * 
	 * On lève une exception s'il y a une erreur dans le solde
	 */
	public static void soldeEstPositif(int solde) throws JoueurException {
		if (solde < 0) {
			throw new JoueurException("Votre solde ne peut pas etre négatif");
		}
	}

	public void AugmenterSoldeParTour() {
		solde = solde + 3;
	}

	// --------------- GETTER et SETTER ---------------

	/*
	 * Fonction qui met le pseudo du joueur
	 * 
	 * Params : String -> pseudo Return : void
	 */
	public void setPseudo(String pseudo) throws JoueurException {

		// On test le pseudo
		Joueur.pseudoEstValide(pseudo);

		// On met le pseudo
		this.pseudo = pseudo;
	}

	/*
	 * Fonction qui retourne le pseudo du joueur
	 * 
	 * Params : void Return : String -> pseudo
	 */
	public String getPseudo() {

		return pseudo;
	}

	/*
	 * Fonction qui met le pseudo du joueur
	 * 
	 * Params : String -> pseudo Return : void
	 */
	public void setSolde(int solde) throws JoueurException {

		// On test le pseudo
		Joueur.soldeEstPositif(solde);

		// On met le pseudo
		this.solde = solde;
	}

	/*
	 * Fonction qui retourne le montant du portefeuille du joueur
	 * 
	 * Params : void Return : String -> solde
	 */
	public int getSolde() {

		return solde;
	}
}
