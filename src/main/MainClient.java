package main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

import Classes.Joueur;
import Classes.Partie;
import Exception.JoueurException;
import Exception.PartieException;
import network.Santiago;
import network.SantiagoInterface;


public class MainClient {
	
	private static Joueur joueur;
	
	public static void main (String args[]) throws RemoteException, MalformedURLException, NotBoundException, PartieException, JoueurException, InterruptedException{
		System.setSecurityManager(new SecurityManager());
		
		Scanner	scString = new Scanner(System.in);
		Scanner scInt = new Scanner(System.in);
		
		int choix = 0;
		String	pseudo;
		
		System.out.println("Entrez votre pseudo de joueur: ");
		pseudo = scString.nextLine().trim();
		
		
		SantiagoInterface serveur =	(SantiagoInterface)Naming.lookup("rmi://127.0.0.1:43000/ABC");
		
		
		//On vérifie que le pseudo est disponible
		boolean pseudoEstDispo = serveur.pseudoEstDisponible(pseudo);
		
		while(! pseudoEstDispo){
			
			System.out.println("Le pseudo que vous avez choisi est déjà utilisé\n");
			System.out.println("Entrez votre pseudo de joueur: ");
			pseudo = scString.nextLine().trim();

			pseudoEstDispo = serveur.pseudoEstDisponible(pseudo);
		}

		joueur = new Joueur(pseudo, 10);

		//
		SantiagoInterface client = new Santiago("client", joueur);
		
		
		String	msg =	"["+client.getName()+"]	est connecté";
		serveur.send(msg);
		
		//Condition = Créer une partie (Test)
		System.out.println("Créer une partie [1] ; Rejoindre une partie [2]");
		choix = scInt.nextInt();
		
		if(choix == 1) {

			//On récupère la partie créée
			Partie partieCreee = client.creerPartie("coucou",3);

			serveur.ajouterPartieListe(partieCreee);

			//On ajoute le joueur à la partie
			serveur.rejoindrePartie(partieCreee.getNomPartie(), client);

			
		} else if(choix == 2){
			
			for(Partie p : serveur.voirParties()) {
				System.out.println("Afficher que les parties non commencées (A la fin des tests)");
				System.out.println("Parties en cours :" +p.getNomPartie());
				System.out.println("--> Nombre de joueurs max: " +p.getNombreJoueursRequis());
				System.out.println("--> Liste des joueurs :");
				for(Joueur j:p.getListeJoueurs()) {
					System.out.println(j.getPseudo());
				}
				
				System.out.println("--> En cours : " +p.getPartieACommence());
			}
			
			
			Partie pRejoint = null;

			while( pRejoint == null){
				System.out.println("Choisissez une partie à rejoindre :");
				String choixPartie = scString.nextLine();
				
								
				pRejoint = serveur.rejoindrePartie(choixPartie, client);	
			}
			
			client.initialiserPartie(pRejoint);
		} else {
			// Chargement d'une partie
			Partie pChargee = null;

			while( pChargee == null){
				System.out.println("Saisissez le nom de la partie à charger :");
				String nomPartie = scString.nextLine();
				
				try {
					pChargee = client.charger(nomPartie);
					
					//On ajoute le joueur à la partie
					boolean existe = serveur.reprendrePartie(pChargee);
					
					if(!existe) {
						Partie partieCreee = client.creerPartie(pChargee);
						
						//On l'ajoute au serveur
						serveur.ajouterPartieListe(partieCreee);

						//On ajoute le joueur à la partie
						serveur.rejoindrePartie(partieCreee.getNomPartie(), client);
					} else {
						//Partie pRejoint = serveur.rejoindrePartie(pChargee, client);
						
						//client.initialiserPartie(pRejoint);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				

			}

		}

		System.out.println("[System] Bataille Remote Object	is ready:");
		//server.setClient(client);
		while(true) {
			
		}

	}

	
	
	
	/**
	 * Fonction qui retourne le Joueur
	 * @return: Joueur
	 */
	public Joueur getJoueur(){
		return joueur;
	}

}
