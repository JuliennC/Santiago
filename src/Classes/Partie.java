package Classes;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import Classes.Tuile.*;
import Exception.JoueurException;
import Exception.PartieException;
import main.MainClient;
import network.SantiagoInterface;
import Classes.Marqueurs.*;

public class Partie implements Serializable{

	static int nombreDeJoueursMin = 3;
	static int nombreDeJoueursMax = 5;	
	static int nombreDeTuile1Marqueur = 3;	
	static int nombreDeTuile2Marqueur = 6;	
	
	private String nomPartie;
	//private ArrayList<Joueur> listeJoueurs = new ArrayList<Joueur>();
	private ArrayList<SantiagoInterface> listeClients = new ArrayList<>();
	
	private ArrayList<Tuile> listeTuiles = new ArrayList<>();
	private ArrayList<Tuile> tuilesRetournees;
	private int nombreDeJoueurs;
	private boolean partieACommence = false;
	private SantiagoInterface constructeurDeCanal;
	
	
	public Partie(String aNom, int nbJoueurs) throws PartieException{
		
		this.nomPartie = aNom;
		this.nombreDeJoueurs = nbJoueurs;
		this.partieACommence = false;
		
		//On lance la fabrication des tuiles
		fabriqueTuiles();
	}

	/**
	 * CTOR
	 * 
	 * @throws PartieException
	 */
	public Partie() throws PartieException {

		this.partieACommence = false;
		this.listeTuiles = new ArrayList<Tuile>();
		// On lance la fabrication des tuiles
		fabriqueTuiles();
	}
	
	public ArrayList<Joueur> listeJoueurs() {
		
		ArrayList<Joueur> listeJoueurs = new ArrayList<Joueur>();
		
		for(SantiagoInterface si : listeClients){
			
			try{
				listeJoueurs.add(si.getJoueur());
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
	
		return listeJoueurs;
	}
	
	
	
	
	/**
	 * methode de lancement d'une partie
	 * @throws PartieException 
	 * @throws RemoteException 
	 */
	public void lancePartie() throws PartieException, RemoteException {
		
		System.out.println("On lance la partie");

		setPartieCommence();
		this.constructeurDeCanal = (SantiagoInterface) randomInList(listeClients);

		System.out.println("Constructeur de canal : "+constructeurDeCanal.getJoueur().getPseudo());
		
		//On lance la phase1
		
		HashMap<SantiagoInterface, Integer> offres = phase1();
		
		//Maintenant on peut gérer les offres
		phase2(offres);
	}
	
	 
	
	// --------------- FONCTIONS QUI CORRESPONDENT AUX DIFFERENTES PHASES ---------------

	
		public HashMap<SantiagoInterface, Integer> phase1() throws RemoteException {
			
			//On retourne d'abord les tuiles
			this.tuilesRetournees = retourneTuiles();
			
			//On récupère le joueur à gauche
			SantiagoInterface client = getClientAGauche(constructeurDeCanal);
			
			HashMap<SantiagoInterface, Integer> listeOffres = new HashMap<>();

			//On prend les offres des joueurs
			while(! client.equals(constructeurDeCanal)) {

				// Le joueur doit faire une offre
				int offre = client.joueurFaitUneOffre();
				
				//On stocke les offres
				listeOffres.put(client, offre);	
				
				//On change de joueur
				client = getClientAGauche(client);

			}
			
			int offre = client.joueurFaitUneOffre();
			
			//On stocke les offres
			listeOffres.put(client, offre);	
			
			//On affiche les offres
			String str = "";
			
			for(SantiagoInterface si : listeOffres.keySet()){
				
				str += si.getJoueur().getPseudo()+" --> "+listeOffres.get(si)+"\n";
			}
			
			System.out.println("Les enchères sont terminées : \n"+str);
			
			return listeOffres;
		}
		
		public void phase2(HashMap<SantiagoInterface, Integer> listeOffres) throws RemoteException{
			ArrayList <SantiagoInterface> ordre = ordreDesAiguilles();
			int offreMin = -1;
			for(SantiagoInterface si : ordre){
				if(listeOffres.get(si) == 0){
					this.constructeurDeCanal = si;
					break;
				}
				else if(offreMin < 0 || listeOffres.get(si)<offreMin){
					offreMin = listeOffres.get(si);
					this.constructeurDeCanal = si;
				}
			}
			System.out.println("Le nouveau constructeur de canal est: "+this.constructeurDeCanal.getName());
		}
		
		public void phase3(HashMap<SantiagoInterface, Integer> listeOffres) throws RemoteException{
			ArrayList <SantiagoInterface> listeClients = ordreDecroissantOffre(listeOffres);
			phase3point1(listeClients);
			phase2point2(listeClients);
		}
		
		
		// --------------- FIN PHASES ---------------
	
	/**
	 * Methode qui permet aux joueurs de choisir leurs tuiles
	 * 	
	 * @param listeClients
	 */
	public void phase3point1(ArrayList <SantiagoInterface> listeClients){
		for (SantiagoInterface client : listeClients){
			
			//On presente les tuiles
			presentationTuile();
			
			// le client choisit sa tuile
			int indexTuileChoisie = client.choisirTuile();
			
			// on cree le HashMap qui accueil les couple client tuile
			HashMap<SantiagoInterface, Tuile> listeTuilesChoisies = new HashMap<>();
			
			// on insert le couple dans le hashmap
			listeTuilesChoisies.put(client, this.tuilesRetournees.get(indexTuileChoisie));
		}
	}
	
	/**
	 * Methode qui permet aux joueurs de poser leurs tuiles
	 * 
	 * @param listeClients
	 */
	public void phase3point2(ArrayList <SantiagoInterface> listeClients){
		
	}
	
	/**
	 * Methode qui permet de présenter les tuiles
	 */
	public void presentationTuile(){
		int i = 0;
		for(Tuile t: this.tuilesRetournees){
			System.out.println(i + " : " + t.getIntitule() + " : " + t.getNombreMarqueursNecessaires());
			i++;
		}
	}
	
	/**
	 * Cette méthode permet d'avoir la liste des joueurs en 
	 * partant de la gauche du constructeur de canal
	 * 
	 * @return la liste des joueurs en partant de la gauche du constructeur de canal
	 */
	public ArrayList<SantiagoInterface>	ordreDesAiguilles(){
		ArrayList<SantiagoInterface> dansLOrdre = new ArrayList();
		SantiagoInterface client = getClientAGauche(this.constructeurDeCanal);
		while(! client.equals(constructeurDeCanal)){
			dansLOrdre.add(client);
			client = getClientAGauche(client);
		}
		dansLOrdre.add(client);
		return dansLOrdre;
	}
	
	/**
	 * Cette methode retourne les joueurs (client) dans l'orde décroissant de leurs offres
	 * 
	 * @param listeOffres
	 * @return liste de client dans l'ordre
	 * @throws RemoteException 
	 */
	public ArrayList<SantiagoInterface> ordreDecroissantOffre(HashMap<SantiagoInterface, Integer> listeOffres) throws RemoteException{
		ArrayList<SantiagoInterface> dansLOrdre = new ArrayList();
		while(!listeOffres.isEmpty()){
			SantiagoInterface si = plusGrandeOffre(listeOffres);
			dansLOrdre.add(si);
			listeOffres.remove(si);
		}
		return dansLOrdre;
	}
	
	/**
	 * Cette méthode retourne la plus grande offre contenue dans un Hashmap
	 * 
	 * @param listeOffres
	 * @return la plus grande offre
	 * @throws RemoteException 
	 */
	public SantiagoInterface plusGrandeOffre(HashMap<SantiagoInterface, Integer> listeOffres) throws RemoteException{
		int offreMax = 0;
		SantiagoInterface plusGrandOffre = null;
		for(Entry<SantiagoInterface, Integer> entry : listeOffres.entrySet()){
			if(entry.getValue() >= offreMax){
				plusGrandOffre = entry.getKey();
				offreMax = entry.getValue();
			}
		}
		return plusGrandOffre;
	}
	
		
	/**
	 * Fonction qui ajoute un joueur à la partie 
	 * Ne peut être appelée qu'avant le début de la partie
	 * 
	 * @param : un Joueur
	 * @return : void
	 * 	
	 * Si le joueur ne peut pas petre ajouté, on lève une exception
	 */
	public void addClient(SantiagoInterface client) throws PartieException {

		if (!listeClients.contains(client)) {

			listeClients.add(client);

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
	public int nbTuilesNecessaires() {
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
	 * S'il n'y a plus assez de tuiles, on renvoit une exception
	 */
	public ArrayList<Tuile> retourneTuiles() {

		ArrayList<Tuile> tuiles = new ArrayList<Tuile>();

		for (int i = 0; i < nbTuilesNecessaires(); i++) {
			tuiles.add(listeTuiles.get(i));
			listeTuiles.remove(i);
		}

		return tuiles;
	}
	
	/**
	 * Fonction qui met le nombde de joueurs pour la partie Ne peut être appelée
	 * qu'avant le début de la partie
	 * 
	 * @param : int
	 * @return : void
	 * 
	 *  Si le nombre de joueur ne convient pas, on lève une exception
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
	 * Cette méthode permet de tirer un objet random contenu dans une liste
	 * 
	 * @param list
	 * @return un objet random contenu dans la liste
	 */
	public Object randomInList(ArrayList list) {
		int random = (int) (Math.random() * list.size());
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
			for (Joueur j : listeJoueurs()) {
				j.AugmenterSoldeParTour();
			}
		}
	}

	// --------------- GETTER et SETTER ---------------

	
	/**
	 * Fonction qui retoune le client à gauche du joueur donné
	 * 
	 * @param : Joueur
	 * @return : MainInterface (le joueur à gauche)
	 */
	public SantiagoInterface getClientAGauche(SantiagoInterface si){
		
		//On récupère l'index du joueur donné
		int i = listeClients.indexOf(si);
		
		//On récupère le joueur d'après (ou le premier s'il n'y en a pas)
		if (listeJoueurs().size()-1 == i){
		
			return listeClients.get(0);
		
		} else {
			
			return listeClients.get(i+1);
		}
		
	}

	/**
	 * Fonction qui retourne le nombre de joueurs de la partie
	 * 
	 * @param : void
	 * @return : int
	 */
	public int getNombreJoueursRequis() {
		return nombreDeJoueurs;
	}
	
	/**
	 * Fonction qui retourne le nombre de joueurs qui ont déjà rejoins la partie
	 * @param : void
	 * @return : int 
	 */
	public int getNombreJoueurDansLaPartie(){
		return listeJoueurs().size();
	}
	
	/**
	 * Fonction qui retourne une liste qui contient les joueurs des joueurs
	 * 
	 * @param : void
	 * @return : ArrayList<Joueur>
	 */
	public ArrayList<Joueur> getListeJoueurs() {

		// On retourne une "copie" de la liste pour qu'aucun joueur ne puisse
		// être changé après le début de la partie
		ArrayList<Joueur> liste = new ArrayList<Joueur>(listeJoueurs());

		return liste;
	}

	/**
	 * Fonction qui indique que la partie a commencé
	 * Ne peut être appelée qu'une seule fois
	 * 
	 * @param : void
	 * @return : void
	 * 	
	 * Si la partie a déjà commencée, on lève une exception
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
	public SantiagoInterface getConstructeurDeCanal() {
		return this.constructeurDeCanal;
	}
	
	/**
	 * Fonction qui modifie le constructeur de canal
	 * 
	 * @param si un joueur.
	 */
	public void setConstructeurDeCanal(SantiagoInterface si){
		this.constructeurDeCanal = si;
	}

	/**
	 * Fonction qui retourne le nom d'une partie
	 * Le nom est définit par le Leader de la partie lors de la création
	 * @return nomPartie
	 */
	public String getNomPartie() {
		return nomPartie;
	}
}
