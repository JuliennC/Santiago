package network;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import Classes.Joueur;
import Classes.Partie;
import Classes.Plateau.Canal;
import Classes.Plateau.Case;
import Classes.Plateau.Plateau;
import Classes.Tuile.Tuile;
import Exception.JoueurException;
import Exception.PartieException;
import main.MainClient;
import serialisationXML.XMLTools;
import Classes.Static;


public class Santiago extends UnicastRemoteObject implements SantiagoInterface {
	Scanner scString = new Scanner(System.in);
	Scanner scInt = new Scanner(System.in);
	
	// Partie Server
	private ArrayList<Partie> listeParties = new ArrayList<>();
	private HashMap<String, String>listeComptes = new HashMap<>();

	// Partie Client
	protected String TYPE;
	protected Joueur joueur;
	
	
	public Santiago(String type, Joueur j) throws RemoteException {
		super();
		joueur = j;
		this.TYPE = "client";
	}

	
	public Santiago(String type) throws RemoteException {
		super();

		this.TYPE = type;
	}
	
	
	public Joueur getJoueur() throws RemoteException{
		return joueur;
	}
	
	public void setJoueur(Joueur j) throws RemoteException{
		this.joueur = j;
	}
	
	
	public void verifieServer(){
		if (TYPE.equals("client")){
			
			PartieException e = new PartieException("Erreur appelé à un '"+TYPE+"' au lieu d'un server");
			e.printStackTrace();
		}
	}
	
	public void verifieClient(){
		if (TYPE.equals("server")){
			
			PartieException e = new PartieException("Erreur appelé à un '"+TYPE+"' au lieu d'un client");
			e.printStackTrace();
		}
	}

	

	
	@Override
	public String getName() throws RemoteException {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	public void send(String msg) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(msg);
	}
	
	/* ------------------------------------------------------------------------------- */

	/**
	 * Cette méthode permet au joueur de creer une partie
	 * 
	 * @param nom
	 * @param nbJoueur
	 * 
	 * @return p
	 * @throws InterruptedException 
	 */

	public Partie creerPartie(String nom, int nbJoueur) throws RemoteException, InterruptedException {
		this.verifieServer();

		try {

			Partie p = new Partie(nom, nbJoueur);
			
			
			return p;
			
		} catch (PartieException e) {

			e.printStackTrace();
		}
		
		return null;
	}
	
	

	
	
	
	/**
	 * Fonction qui vérifie que tous les joueurs sont bien connectés, et avertis les autres sinon
	 * @throws RemoteException 
	 */
	public void analyseConnexionJoueurs(final String nom) throws RemoteException{
		
		this.verifieServer();
		
		System.out.println("'"+TYPE+"' analyse debute : " + voirParties());
		
		class SayHello extends TimerTask {

			public void run() {
				//System.out.println("a " + TYPE);

				//Partie p = server.getPartieByName(nom);
				
				Partie p = listeParties.get(0);
				
				if(p == null){System.out.println(p+"Personne dans : "+listeParties); return;}
				
				//System.out.println("analyse co : "+p);
				//HashMap<Joueur, Boolean> tab = new HashMap<>();
				ConcurrentHashMap<Joueur, Boolean> tab = new ConcurrentHashMap<>();
				
				for(SantiagoInterface client : p.getListeClients()){
					//System.out.println("e");
					

					try{
						//System.out.println("client : "+client.getJoueur().getPseudo());

						boolean tac = client.tic();
						if (tac) { 
							//System.out.println("vrai");
							tab.put(client.getJoueur(), true); }
						else {
							//System.out.println("faux");
							if(tab.get(client.getJoueur())){
								
								p.addModification(Static.modificationJoueurDeconnection);
								p.supprimeClient(client);
								
							} else {
								tab.put(client.getJoueur(), false);
							}
						}
					
					} catch(Exception e){
					
						//tab.put(client.getJoueur(), false);
						p.addModification(Static.modificationJoueurDeconnection);
						p.supprimeClient(client);
					}
				}
			}
		}
		
		Timer timer = new Timer();
		SayHello hello = new SayHello();
		timer.schedule(hello, 0, 5000);
	}
	
	
	
	

	public ArrayList<Partie> getListeParties(){
		return this.listeParties;
	}
	
	/*
	 * Fonction qui avertie une partie d'une modification
	 *
	public void addNotificationAPArtie(String nomPartie, int modif) throws RemoteException{
		this.verifieServer();
		
		Partie p = getPartieByName(nomPartie);
		p.addModification(modif);
	}*/

	/**
	 * Fonction qui récupère les parties pour un joueur donné 
	 * */
	public ArrayList<Partie> getPartiePourJoueur(String joueur) throws RemoteException {
		
		this.verifieServer();
		
		ArrayList<Partie> res = new ArrayList<>();
		
		for(Partie p : listeParties){
			
			if(! p.partiEstTerminee()) {
			
				for(Joueur j : p.getListeJoueurs()){
					
					String pseudo1 = j.getPseudo();
					
					if(pseudo1.equals(joueur)){
				
						res.add(p);
					}
				}
			}
		}
		
		return res;
	}
	
	/**
	 * Fonction qui analyse si un client est dans la partie
	 */
	public boolean joueurParticipeAUnePartie(Joueur joueur, ArrayList<Partie> listeParties) throws RemoteException {
		
		this.verifieServer();
		
		
		for(Partie p : listeParties){
			
			if(! p.partiEstTerminee()) {
			
				for(SantiagoInterface c : p.getListeClients()){
			
					if(c.getJoueur().getPseudo().equals(joueur.getPseudo()));
						
						return true;
						
				}
			}
		}
		
		return false;
	}
	
	
	/**
	 * Fonction qui joute une tuile au plateau d'une partie
	 */
	public void poseTuileAvecXY(String nomPartie, Tuile tuile, int x, int y) throws RemoteException{
		
		Partie p = getPartieByName(nomPartie);
		
		Case c = p.getPlateau().getTabPlateau()[y][x];
        c.setContientTuile(tuile);

        p.addModification(Static.modificationTuiles);
	}
	
	
	
	
	/**
	 * Créé une partie à partir des données contenues dans le fichier XML
	 * @param aPartie
	 * @return
	 * @throws RemoteException
	 */
	public Partie creerPartie(Partie aPartie) throws RemoteException {
		this.verifieServer();
		
		Partie p = aPartie;
		
		System.out.println("La partie a été chargée correctement !");

		return p;	
	}

	/**
	 * Fonction appelée par le client pour rejoindre une partie
	 * Le client renseigne le nom de la partie à rejoindre et son objet Joueur
	 * @return: booléen de confirmation
	 * @throws PartieException 
	 * @throws JoueurException 
	 */
	public Partie rejoindrePartie(String nom, SantiagoInterface i) throws RemoteException, PartieException, JoueurException {
		System.out.println("coucou");
		this.verifieServer();
		
		/* On vérifie d'abord que la liste contient la partie demandée, 
		*  Sinon, on lève une exception
		*/
		if (! listeContientPartie(listeParties, nom)){
			System.out.println("Aucune partie contenant ce nom n'est enregistrée");
			return null;
		}
		
		
		for(Partie p:listeParties) {

			if(p.getNomPartie().equals(nom)) {
				
				try {
					
					/* On vérifie d'abord que la partie n'a pas déjà commencé, 
					*  Sinon, on lève une exception
					*/
					if(p.getPartieACommence()){
						System.out.println("Vous ne pouvez pas rejoindre la partie, elle a déjà commencé.");
						return null;
					} 
					
					p.addClient(i);
					p.addModification(Static.modificationJoueurs);
					
				} catch (PartieException e) {

					e.printStackTrace();
				}
				return p;
			}
		}
		
		return null;
	}
	
	/**
	 * Cette méthode permet de trouver la partie correspondant a un nom.
	 * 
	 * @param name
	 * @return
	 */
	 public Partie getPartieByName(String name){
	 this.verifieServer();
	 
		for(Partie p : listeParties){
			if(p.getNomPartie().equals(name)){
				return p;
			}
		}
		return null;
	}
	 /** Rejoindre une partie chargée à partir d'un fichier XML
	 * @param aPartie
	 * @param si
	 * @return
	 * @throws RemoteException
	 * @throws PartieException
	 * @throws JoueurException
	 */
	public Partie rejoindrePartie(Partie aPartie, SantiagoInterface si) throws RemoteException, PartieException, JoueurException {
		System.out.println("coucou");
		this.verifieServer();
		
		for(Joueur j: aPartie.getListeJoueurs()) {
			if(j.getPseudo().equals(si.getJoueur().getPseudo())) {
				si.getJoueur().setCouleur(j.getCouleur());
				si.getJoueur().setSolde(j.getSolde());
				si.getJoueur().setMarqueurRestant(j.getMarqueurRestant());
				
				//On remplace le joueur:
				aPartie.getListeJoueurs().remove(j);
				aPartie.getListeJoueurs().add(si.getJoueur());
				
				aPartie.addClient(si);
				aPartie.addModification(Static.modificationJoueurs);
				
				break;
			}
		}
		System.out.println("La partie a été rejointe !");
		return aPartie;
	}
	
	public Partie initialiserPartie(Partie aPartie) throws RemoteException {
		this.verifieServer();
		return aPartie;
	}
	
	/**
	 * Foncion qui demande à l'utilisateur d'entrer une offre
	 * 
	 * @param : void
	 * @return : int --> l'offre
	 */
	public int joueurFaitUneOffre() throws RemoteException{		
		this.verifieClient();
		
		Scanner scInt = new Scanner(System.in);

		int offre = 0;

		boolean offreOk = false;
		
		while(! offreOk) {

			//On récupère l'offre
			System.out.println("["+joueur.getPseudo()+"] : Vous devez faire une enchère : ");
			offre = scInt.nextInt();
			
			//On regarde la conformité de l'offre
			if (offre < 0) {
				System.out.println("Vous ne pouvez pas faire une offre inférieur à 0");
		
			} else if (offre > joueur.getSolde()) {

				System.out.println("Vous ne pouvez pas faire une offre supérieur à votre solde : "+joueur.getSolde()+".");
			
			} else {
			
				offreOk = true;
			}
			
		}
		
		
		return offre;
	}

	
	/**
	 * Fonction qui demande un choix à l'utilisateur concernant les pots de vin
	 * @return int choix
	 */
	public int propositionPhase4() throws RemoteException {
		this.verifieClient();
		
		boolean choixValide = false;
		int choix = 0;
		
		while(!choixValide) {
			System.out.println("[1] Je souhaite proposer un pot de vin au constructeur de canal pour construire mon canal");
			System.out.println("[2] Je souhaite soutenir la proposition de pot de vin d'un joueur");
			System.out.println("[3] Je souhaite passer mon tour");		
			
			try {
				choix = scInt.nextInt();
				
				if(choix < 4 && choix > 0) {
					choixValide = true;
				}
		    } catch (InputMismatchException ex) {
		    	System.out.println("Erreur dans la valeur. \n");
		    } 		
		}
		return choix;
	}
	

	public int joueurChoisitTuile(int nbTuile){
		this.verifieClient();
		
		return this.joueur.joueurChoisitTuile(nbTuile);
	}
	
	public int[] joueurChoisitPlacement(){
		this.verifieClient();
		
		return this.joueur.joueurChoisitPlacement();
	}
		
	/**
	 * Fonction appelée pour le choix 1
	 * Demande un pot de vin et vérifier si le pot de vin est possible
	 * @return: montant : Pot de vin
	 */
	public int joueurFaitPotDeVin() throws RemoteException {
		this.verifieClient();
		
		boolean montantValide = false;
		int montant = 0;
		
		while(!montantValide) {
			System.out.println("Saisissez le montant de votre pot de vin:");
			
			try {
				montant = scInt.nextInt();
				
				if(montant > 0 && joueur.getSolde()-montant >= 0) {
					montantValide = true;
				} else {
					System.out.println("Votre solde ne peut être inférieur à 0 ! Montant maximum accepté: " + joueur.getSolde() + " Escudos.");
				}
		    } catch (InputMismatchException ex) {
		    	System.out.println("Erreur dans la valeur. \n");
		    } 
		}
		
		return montant;
	}
	
	/**
	 * Afficher la liste des propositions de pot de vin (Avec cumul des pots de vin)
	 * 
	 */
	@Override
	public void afficherPropositionsPotDeVin(HashMap<SantiagoInterface, Integer> listePropositions) throws RemoteException{
		this.verifieClient();
		
		for(SantiagoInterface si : listePropositions.keySet()) {
			System.out.println("Joueur : " +si.getJoueur().getPseudo() + " | Couleur : " + si.getJoueur().getCouleur() + 
					" | Montant proposé: " +listePropositions.get(si));
		}
	}
	
	/**
	 * Fonction appelée pour le choix 2
	 * Affiche la liste des propositions (Fonction afficherPropositionsPotDeVin)
	 * Demande de choisir un joueur dans la liste
	 * @return SantiagoInterface: joueur soutenu
	 */
	public SantiagoInterface soutenirJoueur(HashMap<SantiagoInterface, Integer> listePropositions) throws RemoteException {
		this.verifieClient();
		
		boolean choixJoueur = false;
		String choix = "";
		SantiagoInterface joueur = null;
		
		System.out.println("La liste des pots de vin: ");
		afficherPropositionsPotDeVin(listePropositions);
		

		while(!choixJoueur) {
			if(choix.contains("")) {
				System.out.println("Quel joueur voulez vous soutenir ?");
			} else {
				System.out.println("Ce joueur n'est pas dans la liste des pots de vin.. Réessayez :");
				System.out.println("Quel joueur voulez vous soutenir ?");
			}
			
			choix = scString.nextLine();			
			
			for(SantiagoInterface si : listePropositions.keySet()){
				if(si.getJoueur().getPseudo().equals(choix) ) {
					choixJoueur = true;
					joueur = si;
					break;
				}
			}
			choix = "";
		}

		return joueur;
	}
	
	/**
	 * Fonction appelée à la suite du soutien d'un joueur
	 * Cette fonction à pour rôle de cumuler le pot de vin du joueur au pot de vin du joueur soutenu
	 * Actualiste la liste des propositions
	 */
	@Override
	public void cumulerPotDeVin(HashMap<SantiagoInterface, Integer> listePropositions, SantiagoInterface joueurSoutenu, int potDeVin) throws RemoteException {
		this.verifieClient();
		
		for(SantiagoInterface si : listePropositions.keySet()) {
			if(si.equals(joueurSoutenu)) {
				listePropositions.replace(si, listePropositions.get(si) + potDeVin);
				break;								
			}
		}
		
		System.out.println("Votre pot de vin a été cumulé au pot de vin de " + joueurSoutenu.getJoueur().getPseudo());
	}
	
	/**
	 * Fonction appelée par le constructeur de canal
	 * Cette fonction appelle la fonction(constructeurPoserCanalPerso) qui retourne le coût de poser son propre canal
	 * Si le constructeur à un solde suffisant alors le choix lui est proposé
	 * 
	 * Cas 1: Appelle la fonction soutenirJoueur qui affiche la liste des propositions et retourne un joueur choisi
	 * 		  Ajoute le pot de vin au solde du constructeur de canal
	 * 
	 * Cas 2: Pose le canal et déduit le coût au solde
	 * 
	 * @return SantiagoInterface joueurChoisi: Le joueur choisi
	 */
	@Override
	public SantiagoInterface choisirPotDeVin(Plateau plateau, HashMap<SantiagoInterface, Integer> listePropositions, ArrayList<Canal> listeCanauxTemp) throws RemoteException {
		this.verifieClient();
		
		int coutCanalPerso;
		boolean choixOk = false;
		int choixProposition = 0;
		SantiagoInterface joueurChoisi = null;
		
		//Fonction pour vérifier si le constructeur a la possibilité de poser son propre canal
		coutCanalPerso = constructeurPoserCanalPerso(listePropositions);	
		
		while(!choixOk) {
			if(coutCanalPerso + 1 < joueur.getSolde()) {
				System.out.println("Faites un choix:");
				System.out.println("[1] Choisir la proposition d'un joueur");
				System.out.println("[2] Choisir de constuire son propre canal (Coût: Proposition la plus élevée + 1 Escudo");	
				
				try {
					choixProposition = scInt.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Erreur dans la valeur. \n");
				}
				
			} else {
				System.out.println("Vous n'avez pas un solde suffisant pour poser votre propre canal.");
				System.out.println("Vous devez donc poser le canal d'un joueur :");
				choixProposition = 1;
			}
			
			
			switch(choixProposition) {
				case 1:
					choixOk = true;
					joueurChoisi = this.soutenirJoueur(listePropositions);
					
					for(SantiagoInterface si : listePropositions.keySet()) {
						if(si.equals(joueurChoisi)) {
							try {
								joueur.setSolde(joueur.getSolde() + listePropositions.get(si));
								
								//on met le canal en eau:
								for(Canal c:plateau.getListeCanaux()) {
									if(c.getCouleur().equals(joueurChoisi.getJoueur().getCouleur())) {
										plateau.metCanal(c);
									}
								}
							} catch (JoueurException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}							
						}
					}
					
					break;
				case 2:
					choixOk = true;
					System.out.println("Vous avez décidé de poser votre propre canal. Son coût est de " + (coutCanalPerso + 1) + " Escudos");
					joueurChoisi = this;
					System.out.println("Choisir la position du canal:");
					
					try {
						joueur.setSolde(joueur.getSolde() - coutCanalPerso - 1);
						
						Canal c = poserCanalTemporaire(plateau, listeCanauxTemp);
						plateau.metCanal(c);
					} catch (JoueurException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				default:
					System.out.println("Le choix indiqué n'existe pas ...");
			}	
		}
		return joueurChoisi;
	}
	
	/**
	 * Cette fonction est appelé à la fin de la phase 4
	 * Si le constructeur de canal a choisi de construire son propre canal, ne fait rien
	 * Sinon parcourt la liste des soutiens pour leur déduire le pot de vin de soutien
	 * Puis déduit le pot de vin du joueur qui a créé la proposition de base
	 */
	@Override
	public void deduirePotDeVin(HashMap<SantiagoInterface, Integer> listePropositions, HashMap<SantiagoInterface, SantiagoInterface> listeSoutiens, HashMap<SantiagoInterface, Integer> listePotsDeVin, SantiagoInterface constructeur) throws RemoteException{
		this.verifieClient();
		
		if(!this.equals(constructeur)) {
			// On déduit le pot de vin des joueurs ayant soutenu la proposition
			for(SantiagoInterface si : listeSoutiens.keySet()) {
				if(listeSoutiens.get(si).equals(this)) {
					for(SantiagoInterface siSoutien : listePotsDeVin.keySet()) {
						if(si.equals(siSoutien)) {
							try {
								si.getJoueur().setSolde(si.getJoueur().getSolde() - listePotsDeVin.get(siSoutien));
							} catch (RemoteException | JoueurException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}		
				}
			}
			
			// On déduit le pot de vin du joueur ayant fait la proposition
			for(SantiagoInterface si : listePotsDeVin.keySet()) {
				if(si.equals(this)) {
					try {
						si.getJoueur().setSolde(si.getJoueur().getSolde() - listePotsDeVin.get(si));
					} catch (RemoteException | JoueurException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}	
		}
		

	}

	/**
	 * Calcule le cout pour le constructeur de poser son canal perso (Basé sur la proposition max + 1)
	 * @param listePropositions
	 * @return int: propositionMax
	 */
	public int constructeurPoserCanalPerso(HashMap<SantiagoInterface, Integer> listePropositions) {
		this.verifieClient();
		
		int propositionMax = 0;
		
		for(SantiagoInterface si : listePropositions.keySet()) {
			if(listePropositions.get(si) > propositionMax) {
				propositionMax = listePropositions.get(si);							
			}
		}
		
		return propositionMax;
	}
	
	/**
	 * Permet aux joueurs de poser un canal temporaire
	 * Canal : Couleur du joueur
	 * 		   Mise en eau = false
	 */
	@Override
	public Canal poserCanalTemporaire(Plateau plateau, ArrayList<Canal> listeCanauxTemp) throws RemoteException {
		this.verifieClient();
		
		// TODO Auto-generated method stub
		System.out.println("Positionnez votre canal temporaire sur le plateau...");
		Canal canal = new Canal(joueur.getCouleur());
		Point coordDebut = new Point();
		Point coordFin = new Point();
		boolean canalValide = false;
		
		while(!canalValide) {
			//Wait listener 
			//On récupère les coordonnéés au clic sur le plateau
			int xDeb = 2;
			int yDeb = 1;
			int xFin = 2;
			int yFin = 2;

			coordDebut.setLocation(xDeb, yDeb);
			coordFin.setLocation(xFin, yFin);
			

			for(Canal c:plateau.getListeCanaux()) {
				if(c.getCoordDebut().equals(canal.getCoordDebut())) {
					if(c.getCoordFin().equals(canal.getCoordFin())) {
						//La position est valide, on vérifie que le canal n'existe pas déjà (!!! Pas sur que ça serve, à avoir avec l'interface)
						canalValide = true;
						
						for(Canal c2:listeCanauxTemp) {
							if(c2.getCoordDebut().equals(canal.getCoordDebut())) {
								if(c2.getCoordFin().equals(canal.getCoordFin())) {
									canalValide = false;
									break;
								}
							}
						}
						break;
					}
				}
			}
			
			if(canalValide == true) {
				System.out.println("Canal validé. Au tour des autres joueurs...");
			} else {
				System.out.println("Position invalide, réessayez...");
			}
		}
		return canal;
	}
	
	
	/**
	 * Fonction qui affiche une erreur
	 * @param : String message
	 * @return : void
	 */
	public void afficheErreur(String message) throws RemoteException{
		
		//On enregistre également le pseudo pour que personne ne prenne le même pseudo
		System.out.println(message);
	}
	
	/**
	 * Fonction qui enregitre le pseudo d'un utilisateur
	 * @param : String pseudo
	 * @return : void
	 */
	public void enregistrePseudoEtMDP(String pseudo, String mdp) throws RemoteException{
		this.verifieServer();
		
		//On enregistre également le pseudo pour que personne ne prenne le même pseudo
		listeComptes.put(pseudo, mdp);
	}
	
	/**
	 * Fonction qui dit si un pseudo est dispo ou non
	 * @params : String pseudo
	 * @return : boolean
	 */
	public boolean pseudoEstDisponible(String pseudo) throws RemoteException{
		this.verifieServer();
		
		//On enregistre également le pseudo pour que personne ne prenne le même pseudo
		return !(listePseudos().contains(pseudo));
	}
	
	
	
	
	/**
	 * Fonction qui vérifie si une connexion est possible avec un pseudo et un mdp
	 * @params : String pseudo
	 * @params : String mot de passe
	 * @return : boolean
	 */
	public boolean connexionPseudoEtMDP(String pseudo, String motDePasse) throws RemoteException{
		this.verifieServer();
		
		return listeComptes.get(pseudo).equals(motDePasse);
	}
	
	/**
	 * Fonction qui test si une partie est prête à être lancée.
	 * Si une partie est prête à être lancée, on lance la partie, 
	 * Sinon on ne fait rien
	 * @param : Partie partie que l'on veut tester
	 * @throws PartieException 
	 * @throws RemoteException 
	 * @throws JoueurException 
	 */
	public void testPartieEstPrete() throws PartieException, RemoteException, JoueurException{
		this.verifieServer();
		
		/*Pour qu'une partie soit prête, il faut : 
		*
		*	- Que le nombre de rejoueur requis soit atteint
		*	- Qu'elle n'ait pas déjà commencé (bien évidemment)
		*/
		for(Partie aP : listeParties) {
			if(aP.getNombreJoueursRequis() == aP.getNombreJoueurDansLaPartie()){
				
				if(! aP.getPartieACommence()){
					
					attributionDesCouleurs(aP);
					aP.lancePartie();
					
				}
			}			
		}
	}
	
	public void attributionDesCouleurs(Partie partie){
	
		this.verifieServer();
		
		ArrayList<Joueur> listJoueur = partie.getListeJoueurs();
		switch(partie.getNombreJoueurDansLaPartie()){
		case 3:
			listJoueur.get(0).setCouleur("Rouge");
			listJoueur.get(1).setCouleur("Orange");
			listJoueur.get(2).setCouleur("Rose");
			break;
		case 4:
			listJoueur.get(0).setCouleur("Rouge");
			listJoueur.get(1).setCouleur("Orange");
			listJoueur.get(2).setCouleur("Rose");
			listJoueur.get(3).setCouleur("Vert");
			break;
		case 5:
			listJoueur.get(0).setCouleur("Rouge");
			listJoueur.get(1).setCouleur("Orange");
			listJoueur.get(2).setCouleur("Rose");
			listJoueur.get(3).setCouleur("Vert");
			listJoueur.get(3).setCouleur("Violet");
			break;
		}
	}
	
	
	
	
	/**
	 * Fonction qui retourne la liste des pseudos utilisés
	 */
	public Set<String> listePseudos(){
		return listeComptes.keySet();
	}
	
	
	
	/**
	 * Fonction qui vérifie si une partie est présente dans une liste de partie
	 * @parameters : arraylist<Partie> , String nomPartie
	 */
	public boolean listeContientPartie(ArrayList<Partie> liste, String nomPartie){
		this.verifieServer();
		
		for(Partie p : liste){
			
			if(p.getNomPartie().equals(nomPartie)){
				
				return true;
			}
		}
		
		return false;
	}
	
	

	
	/**
	 * Fonction appelée par le serveur uniquement pour mettre à jour la liste des parties
	 * Appelé lorsque le client appelle la fonction creerPartie().
	 */
	public void ajouterPartieListe(Partie p) throws RemoteException {
		this.verifieServer();
		
		listeParties.add(p);
		
		analyseConnexionJoueurs(p.getNomPartie());

	}

	
	
	
	/**
	 * Fonction appelée par le client pour voir la liste des parties en cours sur le serveur
	 * @return: ArrayList listeParties
	 */
	public ArrayList<Partie> voirParties() throws RemoteException {
		this.verifieServer();
			
		// TODO Auto-generated method stub
		return listeParties;
	}

	/**
	 * Fonction appelée par le client qui a créé la partie pour sauvegarder la partie en cours 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */

	public void sauvegarder(Partie p) throws RemoteException, FileNotFoundException, IOException {
		this.verifieClient();
		System.out.println("Sauvegarde");
		System.out.println("Sauvegarde de la partie " +p.getNomPartie());
		XMLTools.encodeToFile(p, p.getNomPartie());
		
		System.out.println("Partie sauvegardée ! :D");
	}
	
	/**
	 * Charge un fichier XML contenant une partie
	 */
	public Partie charger(String fileName) throws RemoteException, IOException {
		try{
			Partie pChargee = (Partie) XMLTools.decodeFromFile(fileName);
			System.out.println(pChargee.getPlateau().getListeTuiles().size());
			if(pChargee != null) {
				System.out.println("Chargement de la partie " +pChargee.getNomPartie());
				return pChargee;
			}
			else{
				return null;
			}
		}
		catch(FileNotFoundException e){
			return null;
		}
	}

	
	/**
	 * 
	 * @param partieRejointe
	 * @return true si la partie existe déjà ; false sinon
	 */
	public boolean reprendrePartie(Partie partieRejointe) throws RemoteException {
		this.verifieServer();
		
		for(Partie p:listeParties) {
			if(p.getNomPartie().equals(partieRejointe.getNomPartie())) {
				return true;
			}
		}
		
		return false;

	}
	
	
	
	/**
	 * Fonction qui infique que le joueur est bien en ligne
	 */
	public boolean tic(){
		
		this.verifieClient();
		return true;
	}
	
}
