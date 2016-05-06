package Classes;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import Classes.Tuile.*;
import Exception.JoueurException;
import Exception.PartieException;
import main.MainClient;
import network.Santiago;
import network.SantiagoInterface;
import serialisationXML.XMLTools;
import Classes.Marqueurs.*;
import Classes.Plateau.Canal;
import Classes.Plateau.Case;
import Classes.Plateau.Plateau;

public class Partie implements Serializable{

	static int nombreDeJoueursMin = 3;
	static int nombreDeJoueursMax = 5;	

	
	private String nomPartie = null;

	private ArrayList<SantiagoInterface> listeClients = new ArrayList<SantiagoInterface>();
	
	private int nombreDeJoueurs;
	private boolean partieACommence;
	private SantiagoInterface constructeurDeCanal;
	private Plateau plateau;
	private int tourEnCours = 1;
	
	private ArrayList<Joueur> listeDesJoueur;
	
	private boolean tousConnectes = true;

	private ArrayList<Joueur> listeDesJoueurs = new ArrayList<>();
	
	private ArrayList<Integer> listeModifications = new ArrayList<>();
	
	private ArrayList<String> listeCouleursDispo = new ArrayList<>();

	private Joueur joueurEnCours = null;
	
	public Partie(String aNom, int nbJoueurs) throws PartieException{
		
		this.nomPartie = aNom;
		this.nombreDeJoueurs = nbJoueurs;
		this.partieACommence = false;
	
		plateau = new Plateau();
		
		listeCouleursDispo.add("Rouge");
		listeCouleursDispo.add("Orange");
		listeCouleursDispo.add("Rose");
		listeCouleursDispo.add("Vert");
		listeCouleursDispo.add("Violet");


	}
	
	private ArrayList<String []> listeMessages = new ArrayList<String []>();
	private ArrayList<Joueur> listeDestinataires = new ArrayList<Joueur>();
	
	
	/**
	 * CTOR
	 * 
	 * @throws PartieException
	 */
	public Partie() throws PartieException {

		this.partieACommence = false;
		plateau = new Plateau();

	}
	

	
	public ArrayList<Joueur> getListeDesJoueur() {
		return listeDesJoueur;
	}




	public void setListeDesJoueur(ArrayList<Joueur> listeDesJoueur) {
		this.listeDesJoueur = listeDesJoueur;
	}




	/**
	 * methode de lancement d'une partie
	 * @throws PartieException 
	 * @throws RemoteException 
	 * @throws JoueurException 
	 */
	public void lancePartie() throws PartieException, RemoteException, JoueurException {	
		//attributionDesCouleurs();
		
		Joueur j = listeClients.get(0).getJoueur();

		addModification(Static.modificationPartieCommence);
		System.out.println("On lance la partie");
		
		this.getPlateau().fabriqueTuiles();
		
		Random random = new Random();
		int index = random.nextInt(listeClients.size());
		this.constructeurDeCanal = listeClients.get(index);

		System.out.println("Constructeur de canal : "+constructeurDeCanal.getJoueur().getPseudo());
		addModification(Static.modificationConstructeurDeCanal);
		// ATTENTION : ON LANCE LES PHASES UNIQUEMENT SI LA PARTIE A UN NOM
		// POUR NE PAS QUE CA SE LANCE POUR LES TESTS
		if(nomPartie != null){
			setPartieACommence(true);

			while(! partiEstTerminee() ){			
				lancerSauvegarde();
				
				HashMap<SantiagoInterface, Integer> offres;
				
				// retourner les plantation et les mettre aux enchere
				if(tousConnectes) {		
					offres = phase1();	
					addModification(Static.modificationJoueurEnCours);
				} else {
					break;
				}
				
				//changement du constructeur de canal
				if(tousConnectes) {
					phase2(offres);	
					addModification(Static.modificationJoueurEnCours);
				} else {
					break;
				}
				
				//Choisir une plantation et la placer
				if(tousConnectes) {
					phase3(offres);	
					addModification(Static.modificationJoueurEnCours);
				} else {
					break;
				}
				
				//Phase 4: Soudoyer le constructeur de canal
				if(tousConnectes) {
					phase4();	
					addModification(Static.modificationJoueurEnCours);
				} else {
					break;
				}

				if(tousConnectes) {
					phase6();	
					addModification(Static.modificationJoueurEnCours);
				} else {
					break;
				}
				
				if(tousConnectes) {
					tourEnCours++;					
				} else {
					break;
				}

				
			}

			//C'est donc la fin de la partie
			finDePartie();
			lancerSauvegarde();
		}
	}
	
	 
	
	// --------------- FONCTIONS QUI CORRESPONDENT AUX DIFFERENTES PHASES ---------------


	/**
	 * Fonction qui indique si une partie est terminée
	 * Une partie est terminée lorsque toutes les tuiles sont posées
	 * 
	 * @return boolean
	 */
	
	public boolean partiEstTerminee(){
		return (plateau.getListeTuiles().size() == 0);
	}
	
	
	
	/**
	 * Fonction qui correspond à la phase1 : On retourne les tuiles et les joueurs doivent faire des offres
	 * 
	 * @return HashMap(SantiagoInterface, Integer)
	 * @throws RemoteException
	 */
	
	public HashMap<SantiagoInterface, Integer> phase1() throws RemoteException {
			retourneTuiles();
			addModification(Static.modificationRetourneTuile);
			presentationTuile();
			//On récupère le joueur à gauche
			SantiagoInterface client = getClientAGauche(constructeurDeCanal);
			
			HashMap<SantiagoInterface, Integer> listeOffres = new HashMap<>();

			//On prend les offres des joueurs
			while(! client.equals(constructeurDeCanal)) {
				joueurEnCours = client.getJoueur();
				addModification(Static.modificationJoueurEnCours);

				// Le joueur doit faire une offre
				boolean offreOk = false; 
				int offre = 0;
				while(!offreOk) {
					try {
						offre = client.joueurFaitUneOffre(listeOffres);
						offreOk = true;
					} catch(InputMismatchException e) {
						System.out.println(e.getMessage());
					}
					
				}
				

				//On stocke les offres
				listeOffres.put(client, offre);	
				
				//On change de joueur
				client = getClientAGauche(client);
			}
			
			joueurEnCours = client.getJoueur();
			addModification(Static.modificationJoueurEnCours);

			// Le joueur doit faire une offre
			int offre = client.joueurFaitUneOffre(listeOffres);

			//On stocke les offres
			listeOffres.put(client, offre);	

			System.out.println("Les enchères sont terminées");
			
			//Le joueur en cours est à null (La flèche disparait de l'interface)
			joueurEnCours = null;
			addModification(Static.modificationJoueurEnCours);
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
			addModification(Static.modificationConstructeurDeCanal);
		}

		
		
		
		public void phase3(HashMap<SantiagoInterface, Integer> listeOffres) throws RemoteException, JoueurException{
			
			ArrayList <SantiagoInterface> listeClients = ordreDecroissantOffre(listeOffres);
			HashMap<SantiagoInterface, Tuile> listeTuilesChoisies = phase3point1(listeClients);
			phase3point2(listeTuilesChoisies, listeClients);
			phase3point3(listeOffres);
		}
		
		/**
		 * Correspond à la phase 4 d'un tour de jeu.
		 * @throws RemoteException
		 */
		public void phase4() throws RemoteException {
			SantiagoInterface propositionChoisie;
			//On récupère le joueur à gauche
			SantiagoInterface client = getClientAGauche(constructeurDeCanal);
			
			//HashMap des pots de vin de CHAQUE joueur (Même si le joueur soutien un autre joueur)
			HashMap<SantiagoInterface, Integer> listePotsDeVin = new HashMap<>();
			//HashMap des soutiens entre les joueurs
			HashMap<SantiagoInterface, SantiagoInterface> listeSoutiens = new HashMap<>();
			//HashMap des propositions de pot de vin (Avec le cumul des pots de vin lors des soutiens)
			HashMap<SantiagoInterface, Integer> listePropositions = new HashMap<>();
			
			//ArrayList des canaux temporaires
			ArrayList<Canal> listeCanauxTemp = new ArrayList<>();
			
			
			//Pour chaque client
			while(! client.equals(constructeurDeCanal)) {	
				int choix = 0;
				int potDeVin;
				SantiagoInterface joueurSoutenu;
				joueurEnCours = client.getJoueur();
				addModification(Static.modificationJoueurEnCours);

				
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
						
						//poserCanalTemporaire
						listeCanauxTemp.add(client.poserCanalTemporaire(plateau, listeCanauxTemp));
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
			
			joueurEnCours = null; 
			
			addModification(Static.modificationJoueurEnCours);
			
			//Une fois que tous les joueurs ont déposés un pot de vin (ou passer), on passe à la seconde partie de la phase 4:
			propositionChoisie = constructeurDeCanal.choisirPotDeVin(plateau, listePropositions, listeCanauxTemp);
			
			this.addModification(Static.modificationSoldes);
			
			//Informer les joueurs
			propositionChoisie.deduirePotDeVin(listePropositions, listeSoutiens, listePotsDeVin, constructeurDeCanal);

			this.addModification(Static.modificationSoldes);

		}
		
		/**
		 * Fonction qui fait la fin d'une partie et qui compte les scores
		 * 
		 */
		public void finDePartie(){
			
			//On doit mettre toutes les tuiles en désert
			
			//On parcours toutes les cases
			for(int y=0 ; y < plateau.getTabPlateau().length ; y++){
				
				for(int x=0 ; x < plateau.getTabPlateau()[0].length ; x++){
					
					//On récupère la case
					Case c = plateau.getTabPlateau()[y][x];
					
					if(! c.isIrriguee()) {
					
						//On récupère la tuile
						Tuile tuile = c.getContientTuile();
						
						//Si la case contient bien une tuile
						if(c.getContientTuile() != null){
						
							//On met en désert qu'il y ai des marqueurs ou non
							tuile.setDesert();
							//int id = 0;							
						}
						
					}
					
				}
			}
			
			
			//On compte les scores
			compteScores();
			
			
		}
		
		
		
		/**
		 * Fonction qui compte les scores
		 */
		public void compteScores(){
			
			//On parcours toutes les cases
			for(int y=0 ; y < plateau.getTabPlateau().length ; y++){
				
				for(int x=0 ; x < plateau.getTabPlateau()[0].length ; x++){
					
					//On récupère la case
					Case c = plateau.getTabPlateau()[y][x];
											
					if(c.getContientTuile() == null){
						continue;
					}
						
					if(c.getContientTuile().estDesert()){
						continue;
					}



					HashSet<Tuile> champs = plateau.getChampsAvecCase(c);
						
					//On fait un dictionnaire pour stocker les couleurs avec le nombre de marqueurs
					HashMap<String, Integer> dictionaire = new HashMap<>();
					
					//On parcours les champs trouvés
					for(Tuile tuile : champs){
						
						//On récupere la couleur
						String couleur = tuile.getMarqueursActuels().get(0).getCouleur();
						
						//On récupère le nombre de marqueurs de cette couleur déjà compté
						int nb = tuile.getMarqueursActuels().size();
						
						if(dictionaire.keySet().contains(couleur)){
							nb += dictionaire.get(couleur);
						}
						
						dictionaire.put(couleur, nb);
					}

					System.out.println(dictionaire);
					
					//On doit ensuite récupérer le joueur correspondant à chaque couleur de tuile et lui augmenter son score
					for(String couleur : dictionaire.keySet()){
						
						try{

							//On récupère le joueur
							Joueur joueur = getJoueurAvecCouleurMarqueur(couleur);
							
							//On lui augmente son score
							int nbMarqueur = dictionaire.get(couleur);
							int nombreCase = champs.size();
							
							int nouveauScore = nbMarqueur * nombreCase;
							
							joueur.setSolde(joueur.getSolde() + nouveauScore);
							
							this.addModification(Static.modificationSoldes);
							
						} catch(Exception e){
							e.printStackTrace();
						}
						
						
						
					}
				}
			}
			
		}
		
		// --------------- FIN PHASES ---------------
	
		
		
		/**
		 * Fonction qui retourne un joueur en fonction de la couleur de marqueur
		 * 
		 * @param : String couleur
		 * @return : Joueur
		 * @throws RemoteException 
		 * @throws PartieException 
		 */
		public Joueur getJoueurAvecCouleurMarqueur(String couleur) throws RemoteException, PartieException{
			
			Joueur j = null;
			
			for(SantiagoInterface client : listeClients){
				
				if(client.getJoueur().getCouleur().equals(couleur)){
							
					//Si on trouve un deuxieme joueur avec la même couleur de marqueur
					if(j != null){
						throw new PartieException("Attention, deux joueurs trouvés avec la même couleur de marqueur.");
					}
					
					//On sauvegarde le joueur
					j = client.getJoueur();

				}
				
			}
			
			return j;
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
			joueurEnCours = client.getJoueur();
			addModification(Static.modificationJoueurEnCours);
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
			joueurEnCours = client.getJoueur(); addModification(Static.modificationJoueurEnCours);
			Tuile tuile = listeTuilesChoisies.get(client);
			ArrayList<MarqueurRendement>listeMarqueurs = new ArrayList <MarqueurRendement>();
			for(int i = 0; i< tuile.getNombreMarqueursNecessaires();i++){
				if(client.getJoueur().getListeMarqueurs().size()!= 0){
					listeMarqueurs.add(client.getJoueur().getMarqueur());
				}
			}
			// TEST:
			this.plateau.getTabPlateau()[1][1].setContientTuile(tuile);
			this.addModification(5);
			tuile.setMarqueursActuels(listeMarqueurs);
			setPositionTuile(client,tuile);
		}
		joueurEnCours = null; addModification(Static.modificationJoueurEnCours);
		
		if(this.nombreDeJoueurs == 3){
			setPositionTuile(listeClients.get(0),this.plateau.getListeTuilesRetournees().get(0));
		}
	}
	
	
	public void phase3point3(HashMap<SantiagoInterface,Integer> listeOffres) throws RemoteException, JoueurException{
		for(SantiagoInterface client : this.listeClients){
			int solde = client.getJoueur().getSolde() - listeOffres.get(client);
			client.getJoueur().setSolde(solde);
			this.addModification(Static.modificationSoldes);
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
			System.out.println("["+client.getName()+"] : "+tuile.getIntituleDuChamps() + " : " + tuile.getNombreMarqueursNecessaires());
			int coord[]=client.joueurChoisitPlacement();
			if(this.plateau.getListeTuilesRetournees().size()!=0 
					&& tuile == this.plateau.getListeTuilesRetournees().get(0)
					&& this.plateau.chercheCaseAdjacente().size() != 0){
				
				if(this.plateau.estCaseAdjacente(coord[0],coord[1])){
					placementOk = true;
					this.plateau.getTabPlateau()[coord[0]][coord[1]].setContientTuile(tuile);
				}
				else{
					System.out.println("Il faut poser votre tuile à côté d'un champ");
				}
			}
			else{
				if(this.plateau.getTabPlateau()[coord[0]][coord[1]].getContientTuile()== null){
					placementOk = true;
					this.plateau.getTabPlateau()[coord[0]][coord[1]].setContientTuile(tuile);
				}
				else{
					System.out.println("Il y a déjà une tuile sur cette case");
				}
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
			System.out.println(i + " : " + t.getIntituleDuChamps() + " : " + t.getNombreMarqueursNecessaires());
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
		ArrayList<SantiagoInterface> dansLOrdre = new ArrayList<>();
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
		ArrayList<SantiagoInterface> dansLOrdre = new ArrayList<>();
		HashMap<SantiagoInterface, Integer> listeOffres2 = new HashMap<SantiagoInterface, Integer>();
		for(Entry<SantiagoInterface, Integer> e : listeOffres.entrySet()){
			listeOffres2.put(e.getKey(),e.getValue());
		}
		while(!listeOffres2.isEmpty()){
			SantiagoInterface si = plusGrandeOffre(listeOffres2);
			dansLOrdre.add(si);
			listeOffres2.remove(si);
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
	 * @throws RemoteException 
	 */
	public void addClient(SantiagoInterface client) throws PartieException, RemoteException {

		if (!listeClients.contains(client)) {

			listeClients.add(client);
			
			//Il faut qu'on supprime le joueur si on ajoute un client qui était déjà la 
			for (Joueur j : listeDesJoueurs){
				
				//Si le joueur du client est déjà dans la liste des joueurs on la supprime
				if(client.getJoueur().getPseudo().equals(j.getPseudo())){
					
					listeDesJoueurs.remove(j);
					
				}
			}		
		
			listeDesJoueurs.add(client.getJoueur());


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
			
			System.out.println(plateau.getListeTuiles().size());
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
	public Object randomInList(ArrayList<Object> list) {
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
			for (Joueur j : listeDesJoueurs) {
				j.AugmenterSoldeParTour();
			}
		}
	}
	
	public void lancerSauvegarde() {
		for(SantiagoInterface si : listeClients) {
			try {
				System.out.println();
				System.out.println("Sauvegarde pour le joueur : " + si.getJoueur().getPseudo());
				si.sauvegarder(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
	
	/**
	 * Fonction qui ajoute une modification de la partie
	 */
	public void addModification(Integer mo){
		listeModifications.add(mo);
	}

	
	/**
	 * Fonction qui retourne la liste des modifications
	 */
	public ArrayList<Integer> getListeModifications(){
		return listeModifications;
	}
	
	
	/**
	 * Fonction qui supprime la premiere modificatoin
	 */
	public void permiereMidificationTraitee(){
		listeModifications.remove(0);
	}
	
	
	
	/**
	 * Fonction qui retourne la liste des joueurs connectés
	 * @throws RemoteException 
	 */
	public ArrayList<Joueur> getJoueursConnectes() throws RemoteException{
		
		ArrayList<Joueur> res = new ArrayList<>();
		
		for(SantiagoInterface c : listeClients){
			Joueur j = c.getJoueur();
			res.add(c.getJoueur());
		}
		
		return res;
	}
	
	
	
	
	public void attributionDesCouleurs() throws RemoteException{
				
		
		switch(this.getNombreJoueurDansLaPartie()){
		case 3:
			listeClients.get(0).getJoueur().setCouleur("Rouge");
			listeClients.get(1).getJoueur().setCouleur("Orange");
			listeClients.get(2).getJoueur().setCouleur("Rose");
			Joueur j = listeClients.get(0).getJoueur();

			
			
			break;
		case 4:
			listeClients.get(0).getJoueur().setCouleur("Rouge");
			listeClients.get(1).getJoueur().setCouleur("Orange");
			listeClients.get(2).getJoueur().setCouleur("Rose");
			listeClients.get(3).getJoueur().setCouleur("Vert");
			break;
		case 5:
			listeClients.get(0).getJoueur().setCouleur("Rouge");
			listeClients.get(1).getJoueur().setCouleur("Orange");
			listeClients.get(2).getJoueur().setCouleur("Rose");
			listeClients.get(3).getJoueur().setCouleur("Vert");
			listeClients.get(4).getJoueur().setCouleur("Violet");
			break;
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
		if (listeClients.size()-1 == i){
		
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
		return listeClients.size();
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
		ArrayList<Joueur> liste = new ArrayList<Joueur>(listeDesJoueurs);

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
	 * @throws RemoteException 
	 */
	public void setPartieCommence() throws PartieException, RemoteException {

		if (partieACommence == false) {

			partieACommence = true;
			addModification(Static.modificationPartieCommence);
			

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

	public void setNomPartie(String nomPartie) {
		this.nomPartie = nomPartie;
	}



	public int getTourEnCours() {
		return tourEnCours;
	}




	public void setTourEnCours(int tourEnCours) {
		this.tourEnCours = tourEnCours;
	}




	public int getNombreDeJoueurs() {
		return nombreDeJoueurs;
	}




	public void setPartieACommence(boolean partieACommence) {
		this.partieACommence = partieACommence;
	}


	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}
	
	
	
	public List<SantiagoInterface> getListeClients(){
		return new ArrayList<SantiagoInterface>(listeClients);
	}
	
	
	public void supprimeClient(SantiagoInterface client){
		listeClients.remove(client);
	}

	public Joueur getJoueurEnCours() {
		return joueurEnCours;
	}

	public ArrayList<String[]> getListeMessages() {
		return listeMessages;
	}

	public ArrayList<String> listeCouleurs(){
		return listeCouleursDispo;
	}
	
	static public String getCouleurDispoPourPartie(Partie p) throws RemoteException{
	
		ArrayList<String> liste_Copy = new ArrayList<>(p.listeCouleurs());
		ArrayList<String> liste_Copy_bis = new ArrayList<>(p.listeCouleurs());
		
		for(SantiagoInterface client : p.listeClients){
			
			Joueur j = client.getJoueur();
			
			int index = 0;
			
			for (String couleur : liste_Copy_bis){
				
				if(couleur.equals(j.getCouleur())){
					liste_Copy.remove(index);
					index--;
				}
				
				index++;
			}
			
			liste_Copy_bis = new ArrayList<>(liste_Copy);
		}
		
		return liste_Copy_bis.get(0);
	}
	
	
}