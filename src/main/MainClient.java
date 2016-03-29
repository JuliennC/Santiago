package main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

import Classes.Joueur;
import Classes.Partie;
import Exception.PartieException;
import network.Santiago;
import network.SantiagoInterface;


public class MainClient {
	public static void main (String args[]) throws RemoteException, MalformedURLException, NotBoundException, PartieException{
		System.setSecurityManager(new SecurityManager());
		
		Scanner	scString = new Scanner(System.in);
		Scanner scInt = new Scanner(System.in);
		
		int choix = 0;
		String	pseudo;
		Joueur joueur;
		
		System.out.println("Entrez votre pseudo de joueur: ");
		pseudo = scString.nextLine().trim();

		joueur = new Joueur(pseudo, false);
		
		SantiagoInterface client = new Santiago(pseudo);
		SantiagoInterface serveur =	(SantiagoInterface)Naming.lookup("rmi://127.0.0.1:42000/ABC");
		
		String	msg =	"["+client.getName()+"]	est connecté";
		serveur.send(msg);
		
		//Condition = Créer une partie (Test)
		System.out.println("Créer une partie [1] ; Rejoindre une partie [2]");
		choix = scInt.nextInt();
		
		if(choix == 1) {

			//On récupère la partie créée
			Partie partieCreee = client.creerPartie();
			
			//On l'ajoute au serveur
			serveur.ajouterPartieListe(partieCreee);

			//On ajoute le joueur à la partie
			serveur.rejoindrePartie(partieCreee.getNomPartie(), joueur);

			
		} else {
			
			for(Partie p:serveur.voirParties()) {
			
				System.out.println("Parties en cours :" +p.getNomPartie());
				System.out.println("--> Nombre de joueurs max: " +p.getNombreJoueursRequis());
				System.out.println("--> Liste des joueurs :");
				for(Joueur j:p.getListeJoueurs()) {
					System.out.println(j.getPseudo());
				}
				
				System.out.println("--> En cours : " +p.getPartieACommence());
			}
			
			boolean partieRejoint = false;
			
			while(! partieRejoint){

				System.out.println("Choisissez une partie à rejoindre :");
				String choixPartie = scString.nextLine();
				
				try {
					serveur.rejoindrePartie(choixPartie, joueur);
					partieRejoint = true;
					
				} catch(Exception e) {
	
					System.out.println("\n"+e.getMessage()+"\n");
				}
			}
		}
			
			
			
		
		
		System.out.println("[System] Bataille Remote Object	is ready:");
		//server.setClient(client);


	}

}
