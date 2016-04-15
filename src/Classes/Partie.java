package Classes;

import static org.junit.Assert.assertTrue;

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
import Classes.Plateau.Canal;
import Classes.Plateau.Case;
import Classes.Plateau.Plateau;

public class Partie implements Serializable{

	static int nombreDeJoueursMin = 3;
	static int nombreDeJoueursMax = 5;	

	
	private String nomPartie = null;
	//private ArrayList<Joueur> listeJoueurs = new ArrayList<Joueur>();
	private ArrayList<SantiagoInterface> listeClients = new ArrayList<>();
	
	private int nombreDeJoueurs;
	private boolean partieACommence = false;
	private SantiagoInterface constructeurDeCanal;
	private Plateau plateau;
	
	
	public Partie(String aNom, int nbJoueurs) throws PartieException{
		
		this.nomPartie = aNom;
		this.nombreDeJoueurs = nbJoueurs;
		this.partieACommence = false;
	
		plateau = new Plateau();
	}

	
	
	
	/**
	 * CTOR
	 * 
	 * @throws PartieException
	 */
	public Partie() throws PartieException {

		this.partieACommence = false;
		plateau = new Plateau();

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
		
		// retourner les plantation et les mettre aux enchere
		HashMap<SantiagoInterface, Integer> offres = phase1();
		
		//changement du constructeur de canal
		phase2(offres);
		
		//Choisir une plantation et la placer
		phase3(offres);
		if(this.nomPartie == null){
			//Phase 4: Soudoyer le constructeur de canal :
			demanderPotDeVin();
		}
	}
	
	 
	
	// --------------- FONCTIONS QUI CORRESPONDENT AUX DIFFERENTES PHASES ---------------

	
		public HashMap<SantiagoInterface, Integer> phase1() throws RemoteException {
			
			retourneTuiles();
			presentationTuile();
			//On récupère le joueur à gauche
			SantiagoInterface client = getClientAGauche(constructeurDeCanal);
			
			HashMap<SantiagoInterface, Integer> listeOffres = new HashMap<>();

			//On prend les offres des joueurs
			while(! client.equals(constructeurDeCanal)) {

				// Le joueur doit faire une offre
				int offre = client.joueurFaitUneOffre();
				
				//On vérifie que l'offre est valide 
				boolean offreValide = ! (listeOffres.containsValue(offre)); 
				
				while(! offreValide){
					
					//On affiche une erreur
					client.afficheErreur("Erreur : vous devez entrer une offre qui différentes des autres joueurs");
					
					//On redemande une offre
					offre = client.joueurFaitUneOffre();
					
					//On véruifie la nouvelle offre
					offreValide = ! (listeOffres.containsValue(offre));
					
				}
				
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
				
		/**
		 * Fonction qui règle la sécheresse
		 * 
		 * @param : void
		 * @return : void
		 */
		public void phase6() {

			//On parcours toutes les cases
			for(int i=0 ; i < plateau.getTabPlateau().length ; i++){
				
				for(int j=0 ; j < plateau.getTabPlateau()[i].length ; j++){
					
					//On récupère la case
					Case c = plateau.getTabPlateau()[i][j];
					
					//On récupère la tuile
					Tuile tuile = c.getContientTuile();
						
					//Si la case contient bien une tuile
					if(c.getContientTuile() != null){
						
						//Si la tuile ne contient plus de marqueur, alors elle devient desert
						if(tuile.getNombreMarqueursActuels() == 0){
							tuile.setDesert();
						
							
						//Sinon, on poursuit le traitement
						} else {
							
							
							//Si la case n'est pas irriguée, il faut enlever un marqueur
							if(! c.isIrriguee()){
								
								//On enleve un marqueur
								try {
									tuile.supprimeUnMarqueur();
									
								}catch(Exception e){
								
									System.out.println(e.getMessage());
								
								}
							}
							
						}
						
					}
					
				}
				
			}
			
			
			
			
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
			HashMap<SantiagoInterface, Tuile> listeTuilesChoisies = phase3point1(listeClients);
			phase3point2(listeTuilesChoisies, listeClients);
		}
		
		/**
		 * Correspond à la phase 4 d'un tour de jeu.
		 * @throws RemoteException
		 */
		public void demanderPotDeVin() throws RemoteException {
			SantiagoInterface propositionChoisie;
			//On récupère le joueur à gauche
			SantiagoInterface client = getClientAGauche(constructeurDeCanal);
			
			//HashMap des pots de vin de CHAQUE joueur (Même si le joueur soutien un autre joueur)
			HashMap<SantiagoInterface, Integer> listePotsDeVin = new HashMap<>();
			//HashMap des soutiens entre les joueurs
			HashMap<SantiagoInterface, SantiagoInterface> listeSoutiens = new HashMap<>();
			//HashMap des propositions de pot de vin (Avec le cumul des pots de vin lors des soutiens)
			HashMap<SantiagoInterface, Integer> listePropositions = new HashMap<>();
			

			//Pour chaque client
			while(! client.equals(constructeurDeCanal)) {	
				int choix = 0;
				int potDeVin;
				SantiagoInterface joueurSoutenu;

				
				//Etape 1: On demande un choix [Pot de vin ; Soutenir joueur ; Passer]
				choix = client.propositionPhase4();
				
				//Etape 2: Selon le choix du joueur:
				switch(choix) {
					case 1:	
						//On demande le montant du pot de vin
						potDeVin = client.joueurFaitPotDeVin();
						
						//On retient la somme proposée par le client
						listePotsDeVin.put(client, potDeVin);
						//On la met dans la liste des propositions (Ici la somme pourra être augmentée par un soutien d'un joueur)
						listePropositions.put(client, potDeVin);
						
						//poserCanalTemporaire();
						break;
					case 2:
						//On demande le joueur soutenu.
						joueurSoutenu = client.soutenirJoueur(listePotsDeVin);
						
						//On demande le montant du pot de vin
						potDeVin = client.joueurFaitPotDeVin();
						
						//On retient la somme proposée par le client
						listePotsDeVin.put(client, potDeVin);	
						//On retient que cette somme est liée au pot de vin d'un autre joueur
						listeSoutiens.put(client, joueurSoutenu);
						
						//On cumule ce pot de vin au pot de vin de l'autre joueur
						client.cumulerPotDeVin(listePropositions, joueurSoutenu, potDeVin);
						break;

					case 3:
						break;
				}
				
				
				//On change de joueur
				client = getClientAGauche(client);
			}
			
			//Une fois que tous les joueurs ont déposés un pot de vin (ou passer), on passe à la seconde partie de la phase 4:
			propositionChoisie = constructeurDeCanal.choisirPotDeVin(listePropositions);
			
			//Informer les joueurs
			propositionChoisie.deduirePotDeVin(listePropositions, listeSoutiens, listePotsDeVin, constructeurDeCanal);
		}

		// --------------- FIN PHASES ---------------
	
	/**
	 * Methode qui permet aux joueurs de choisir leurs tuiles
	 * 	
	 * @param listeClients
	 * @throws RemoteException 
	 */
	public HashMap<SantiagoInterface, Tuile> phase3point1(ArrayList <SantiagoInterface> listeClients) throws RemoteException{
		
		// on cree le HashMap qui accueil les couple client tuile
		HashMap<SantiagoInterface, Tuile> listeTuilesChoisies = new HashMap<>();
		for (SantiagoInterface client : listeClients){
			presentationTuile();
			// le client choisit sa tuile
			int indexTuileChoisie = client.joueurChoisitTuile(this.plateau.getListeTuilesRetournees().size());
			
			// on insert le couple dans le hashmap
			listeTuilesChoisies.put(client, this.plateau.getListeTuilesRetournees().get(indexTuileChoisie));
			this.plateau.getListeTuilesRetournees().remove(indexTuileChoisie);
		}
		// si le nombre de joueur est de 3 on ajjoute la derniere tuile au joueur ayant fait la plus grande offre
		return listeTuilesChoisies;
	}
	
	/**
	 * Methode qui permet aux joueurs de poser leurs tuiles
	 * 
	 * @param listeClients
	 * @throws RemoteException 
	 */
	public void phase3point2(HashMap<SantiagoInterface, Tuile> listeTuilesChoisies, ArrayList <SantiagoInterface> listeClients) throws RemoteException{
		for(SantiagoInterface client : listeClients){
			Tuile tuile = listeTuilesChoisies.get(client);
			ArrayList<MarqueurRendement>listeMarqueurs = new ArrayList <MarqueurRendement>();
			for(int i = 0; i< tuile.getNombreMarqueursNecessaires();i++){
				if(client.getJoueur().getListeMarqueurs().size()!= 0){
					listeMarqueurs.add(client.getJoueur().getMarqueur());
				}
			}
			tuile.setMarqueursActuels(listeMarqueurs);
			setPositionTuile(client,tuile);
		}
		if(this.nombreDeJoueurs == 3){
			setPositionTuile(listeClients.get(0),this.plateau.getListeTuilesRetournees().get(0));
		}
	}
	
	/**
	 * Cette fonction permet de placer la tuile d'un client
	 * 
	 * @param client : le client qui doit placer sa tuile
	 * @param tuile : la tuile a placer
	 * @throws RemoteException
	 */
	public void setPositionTuile(SantiagoInterface client, Tuile tuile) throws RemoteException{
		boolean placementOk = false;
		while(!placementOk){
			System.out.println("["+client.getName()+"] : "+tuile.getIntitule() + " : " + tuile.getNombreMarqueursNecessaires());
			int coord[]=client.joueurChoisitPlacement();
			if(this.plateau.getTabPlateau()[coord[0]][coord[1]].getContientTuile()== null){
				placementOk = true;
				this.plateau.getTabPlateau()[coord[0]][coord[1]].setContientTuile(tuile);
			}
			else{
				System.out.println("Il y a déjà une tuile sur cette case");
			}
		}
	}
	
	
	/**
	 * Methode qui permet de présenter les tuiles
	 */
	public void presentationTuile(){
		int i = 0;
		System.out.println("Les tuiles retournées sont:");
		for(Tuile t: this.plateau.getListeTuilesRetournees()){
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
	public void retourneTuiles() {

		ArrayList<Tuile> tuiles = new ArrayList<Tuile>();
		for (int i = 0; i < nbTuilesNecessaires(); i++) {
			Tuile t = plateau.getListeTuiles().get(i);
			tuiles.add(t);
			plateau.getListeTuiles().remove(i);
		}

		plateau.setListeTuilesRetournees(tuiles);
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
	
	/**
	 * Fonction qui retourne le plateau d'une partie

	 * @return Plateau
	 */
	public Plateau getPlateau() {
		return plateau;
	}
}