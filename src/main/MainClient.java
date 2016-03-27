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
	public static void main (String args[]) throws RemoteException, MalformedURLException, NotBoundException{
		System.setSecurityManager(new SecurityManager());
		
		Scanner	scString = new Scanner(System.in);
		Scanner scInt = new Scanner(System.in);
		
		int choix = 0;
		String	pseudo;
		Joueur joueur;
		
		System.out.println("Entrez votre pseudo de joueur: ");
		pseudo =	"Pierre"; //s.nextLine().trim();
		
		joueur = new Joueur(pseudo, false);

		
		SantiagoInterface client = new Santiago(pseudo);
		SantiagoInterface serveur =	(SantiagoInterface)Naming.lookup("rmi://127.0.0.1:42000/ABC");
		
		String	msg =	"["+client.getName()+"]	est connecté";
		serveur.send(msg);
		
		//Condition = Créer une partie (Test)
		System.out.println("Créer une partie [1] ; Rejoindre une partie [2]");
		choix = scInt.nextInt();
		
		if(choix == 1) {
			serveur.ajouterPartieListe(client.creerPartie());

		} else {
			for(Partie p:serveur.voirParties()) {
				System.out.println("Parties en cours :" +p.getNomPartie());
				System.out.println("--> Nombre de joueurs max: " +p.getNombreJoueurs());
				System.out.println("--> Liste des joueurs :");
				for(Joueur j:p.getListeJoueurs()) {
					System.out.println(j.getPseudo());
				}
				
				System.out.println("--> En cours : " +p.getPartieACommence());
			}
			
			System.out.println("Choisissez une partie à rejoindre :");
			String choixPartie = scString.nextLine();
			
			serveur.rejoindrePartie(choixPartie, joueur);
			//System.out.println(serveur.rejoindrePartie());
		}
		
		System.out.println("[System] Bataille Remote Object	is ready:");
		//server.setClient(client);


	}

}
