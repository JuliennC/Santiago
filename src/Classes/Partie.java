package Classes;

import java.util.ArrayList;
import java.util.Collections;

import Classes.Tuile.*;
import Exception.PartieException;
import Classes.Marqueurs.*;

public class Partie {

	static int nombreDeJoueursMin = 3;
	static int nombreDeJoueursMax = 5;
	static int nombreDeTuile1Marqueur = 3;
	static int nombreDeTuile2Marqueur = 6;

	private Joueur constructeurDeCanal;
	private ArrayList<Joueur> listeJoueurs;
	private ArrayList<Tuile> listeTuiles;
	private int nombreDeJoueurs;
	private boolean partieACommence;

	/**
	 * CTOR
	 * 
	 * @throws PartieException
	 */
	public Partie() throws PartieException {

		this.partieACommence = false;
		this.listeJoueurs = new ArrayList<Joueur>();
		this.listeTuiles = new ArrayList<Tuile>();
		// On lance la fabrication des tuiles
		fabriqueTuiles();
	}

	/**
	 * Fonction qui ajoute un joueur à la partie Ne peut être appelée qu'avant
	 * le début de la partie
	 * 
	 * @param : un Joueur
	 * @return : void
	 * 
	 *         Si le joueur ne peut pas petre ajouté, on lève une exception
	 */
	public void addJoueur(Joueur joueur) throws PartieException {

		if (!listeJoueurs.contains(joueur)) {

			listeJoueurs.add(joueur);

		} else {

			throw new PartieException("Vous êtes déjà dans la partie");
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

		if (partieACommence) {
			throw new PartieException("La partie a déjà commencé.");

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

	/**
	 * Cette méthode permet de donner le nombre de tuile à retourner en fontion
	 * des joueurs lors de la mise aux enchères
	 * 
	 * @return le nombre de tuile à retrouner lors de la mise aux enchères
	 */
	public int nbTuile() {
		if (this.nombreDeJoueurs == 3) {
			return 4;
		} else {
			return this.nombreDeJoueurs;
		}
	}

	/**
	 * Fonction qui tire les tuiles Tuiles (autant que de joueurs)
	 * 
	 * @param : void
	 * @return : ArrayList<Tuile>
	 * 
	 *         S'il n'y a plus assez de tuiles, on renvoit une exception
	 */
	public ArrayList<Tuile> retourneTuiles() {

		ArrayList<Tuile> tuiles = new ArrayList<Tuile>();

		for (int i = 0; i < nbTuile(); i++) {
			tuiles.add(listeTuiles.get(i));
			listeTuiles.remove(i);
		}

		return tuiles;
	}

	/**
	 * Cette méthode permet de tirer un objet random contenu dans une liste
	 * 
	 * @param list
	 * @return un objet random contenu dans la liste
	 */
	public Object randomInList(ArrayList list) {
		int random = (int) Math.random() * list.size();
		return list.get(random);
	}

	/**
	 * Cette méthode permet de donner a chaque joueur une aide au developpement
	 * a chaque tour afin qu'ils puissent augmenter leur solde
	 * 
	 * @param : void
	 * @return : void
	 */
	public void AideAuDeveloppement() {
		if (this.partieACommence == true) {
			for (Joueur j : listeJoueurs) {
				j.AugmenterSoldeParTour();
			}
		}
	}

	// -------- Changement d'état d'une partie --------
	/**
	 * methode de lancement d'une partie
	 */
	public void start() {
		this.partieACommence = true;
		this.constructeurDeCanal = (Joueur) randomInList(this.listeJoueurs);
	}

	// --------------- GETTER et SETTER ---------------

	/**
	 * Fonction qui met le nombde de joueurs pour la partie Ne peut être appelée
	 * qu'avant le début de la partie
	 * 
	 * @param : int
	 * @return : void
	 * 
	 *         Si le nombre de joueur ne convient pas, on lève une exception
	 */
	public void setNombreDeJoueurs(int nb) throws PartieException {

		if ((nb <= nombreDeJoueursMax) && (nb >= nombreDeJoueursMin)) {

			nombreDeJoueurs = nb;

		} else {

			throw new PartieException("Une partie se fait avec un minimum de "
					+ nombreDeJoueursMin + " et " + nombreDeJoueursMax + ".");
		}

	}

	/**
	 * Fonction qui retourne le nombre de joueurs de la partie
	 * 
	 * @param : void
	 * @return : int
	 * 
	 */
	public int getNombreJoueurs() {
		return nombreDeJoueurs;
	}

	/**
	 * Fonction qui retourne une liste qui contient les joueurs des joueurs
	 * 
	 * @param : void
	 * @return : ArrayList<Joueur>
	 * 
	 */
	public ArrayList<Joueur> getListeJoueurs() {

		// On retourne une "copie" de la liste pour qu'aucun joueur ne puisse
		// être changé après le début de la partie
		ArrayList<Joueur> liste = new ArrayList<Joueur>(listeJoueurs);

		return liste;
	}

	/**
	 * Fonction qui indique que la partie a commencé Ne peut être appelée qu'une
	 * seule fois
	 * 
	 * @param : void
	 * @return : void
	 * 
	 *         Si la partie a déjà commencée, on lève une exception
	 */
	public void setPartieCommence() throws PartieException {

		if (partieACommence == false) {

			partieACommence = true;

		} else {

			throw new PartieException("La partie a déjà commencée");
		}

	}

	/**
	 * Fonction qui dit si une partie a commencé ou nom
	 * 
	 * @param : void
	 * @return : Boolean
	 * 
	 */
	public boolean getPartieACommence() {

		return partieACommence;
	}

	/**
	 * Fonction qui donne la liste des tuile en jeux
	 * 
	 * @return une liste de tuile
	 */
	public ArrayList<Tuile> getListTuiles() {
		return this.listeTuiles;
	}

	/**
	 * Fonction qui donne le constructeur de canal
	 */
	public Joueur getConstructeurDeCanal() {
		return this.constructeurDeCanal;
	}
}
