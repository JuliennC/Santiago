package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Ignore;
import org.junit.Test;
import Classes.Partie;
import Classes.Marqueurs.MarqueurRendement;
import Classes.Marqueurs.*;
import Classes.Plateau.Canal;
import Classes.Plateau.Case;
import Classes.Plateau.Plateau;
import Classes.Plateau.Source;
import Classes.Tuile.Tuile;
import Classes.Tuile.TuileBanane;
import Exception.JoueurException;
import Exception.PartieException;
import network.Santiago;
import network.SantiagoInterface;
import Classes.Joueur;
import Classes.Tuile.*;

public class PartieImplTest {

	/**
	 * Test de la méthode addJoueur
	 * 
	 * @throws PartieException
	 * @throws RemoteException 
	 */
	@Test(expected = PartieException.class)
	public void testAddJoueur() throws PartieException, RemoteException {
		Joueur j1 = new Joueur("Joueur1", 0);
		Joueur j2 = new Joueur("Joueur2", 0);
		Joueur j3 = new Joueur("Joueur3", 0);
		/*SantiagoInterface s1 = new Santiago(j1);
		SantiagoInterface s2 = new Santiago(j2);
		SantiagoInterface s3 = new Santiago(j3);
		Partie p = new Partie();
		p.addClient(s1);
		assertEquals(1, p.getListeJoueurs().size());
		p.addClient(s2);
		assertEquals(2, p.getListeJoueurs().size());
		p.addClient(s3);
		assertEquals(3, p.getListeJoueurs().size());
		p.addClient(s2);
		assertEquals(3, p.getListeJoueurs().size());*/
	}

	/**
	 * Test de la méthode fabriqueTuile
	 * 
	 * @throws PartieException
	 * @throws RemoteException 
	 */
	@Test(expected = PartieException.class)
	public void testFabriqueTuile() throws PartieException, RemoteException {
		Partie p = new Partie();
		assertEquals(45, p.getPlateau().getListeTuiles().size());
		p.setPartieCommence();
		p.getPlateau().fabriqueTuiles();
		
	}

	/**
	 * Test de la méthode nbTuile
	 * 
	 * @throws PartieException
	 */
	@Test
	public void testNbTuile() throws PartieException {
		Partie p = new Partie();
		p.setNombreDeJoueurs(3);
		int nbTuile = p.nbTuilesNecessaires();
		assertEquals(4, nbTuile);
		p.setNombreDeJoueurs(4);
		nbTuile = p.nbTuilesNecessaires();
		assertEquals(4, nbTuile);
		p.setNombreDeJoueurs(5);
		nbTuile = p.nbTuilesNecessaires();
		assertEquals(5, nbTuile);
	}

	/**
	 * Test de la méthode randomInList
	 * 
	 * @throws PartieException
	 * @throws RemoteException 
	 */
	@Test
	public void testRandomInList() throws PartieException, RemoteException {
		Joueur j1 = new Joueur("Joueur1", 0);
		Joueur j2 = new Joueur("Joueur2", 0);
		Joueur j3 = new Joueur("Joueur3", 0);
		/*SantiagoInterface s1 = new Santiago(j1);
		SantiagoInterface s2 = new Santiago(j2);
		SantiagoInterface s3 = new Santiago(j3);
		Partie p = new Partie();
		p.addClient(s1);
		Joueur jRandom = (Joueur) p.randomInList(p.getListeJoueurs());
		assertTrue(j1 == jRandom || j2 == jRandom || j3 == jRandom);
		p.addClient(s2);
		jRandom = (Joueur) p.randomInList(p.getListeJoueurs());
		assertTrue(j1 == jRandom || j2 == jRandom || j3 == jRandom);
		p.addClient(s3);;
		jRandom = (Joueur) p.randomInList(p.getListeJoueurs());
		assertTrue(j1 == jRandom || j2 == jRandom || j3 == jRandom);*/
	}

	/**
	 * Test de la méthode lancePartie
	 * 
	 * @throws PartieException
	 * @throws RemoteException 
	 * @throws JoueurException 
	 */
	@Test
	public void testStart() throws PartieException, RemoteException, JoueurException {
		Joueur j1 = new Joueur("Joueur1", 0);
		Joueur j2 = new Joueur("Joueur2", 0);
		Joueur j3 = new Joueur("Joueur3", 0);
		/*SantiagoInterface s1 = new Santiago(j1);
		SantiagoInterface s2 = new Santiago(j2);
		SantiagoInterface s3 = new Santiago(j3);
		Partie p = new Partie();
		p.addClient(s1);
		p.addClient(s2);
		p.addClient(s3);
		p.lancePartie();
		assertNotEquals(null, p.getConstructeurDeCanal());
		assertEquals(true, p.getPartieACommence());*/
	}

	/**
	 * Test de la méthode AideAuDeveloppement
	 * @throws RemoteException 
	 * @throws JoueurException 
	 * 
	 */
	@Test
	public void testAideAuDeveloppement() throws PartieException, RemoteException, JoueurException {
		Joueur j1 = new Joueur("Joueur1", 0);
		Joueur j2 = new Joueur("Joueur2", 0);
		Joueur j3 = new Joueur("Joueur3", 0);
		/*SantiagoInterface s1 = new Santiago(j1);
		SantiagoInterface s2 = new Santiago(j2);
		SantiagoInterface s3 = new Santiago(j3);
		Partie p = new Partie();
		p.addClient(s1);
		p.addClient(s2);
		p.addClient(s3);
		p.lancePartie();
		p.AideAuDeveloppement();
		assertEquals(3, j1.getSolde());
		assertEquals(3, j2.getSolde());
		assertEquals(3, j3.getSolde());*/
	}



	/**
	 * Test de la méthode rejoindrePartie
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 * 
	 */
	@Test(expected = PartieException.class)
	public void testAjout() throws PartieException, RemoteException, MalformedURLException, NotBoundException {
		
		int nombreJoueursDesires = 4;

		Partie p = new Partie();
		p.setNombreDeJoueurs(nombreJoueursDesires);

		try{
			SantiagoInterface s =	(SantiagoInterface)Naming.lookup("rmi://127.0.0.1:42000/ABC");
		} catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("3");

		//s.ajouterPartieListe(p);
		for(int i = 0; i < 0; i++) {
			
			Joueur joueur = new Joueur("joueur"+i, 0);
			//s.rejoindrePartie(p.getNomPartie(), joueur);
		}

		System.out.println(p.getNombreJoueurDansLaPartie());
	}


	
	@Test
	public void testDemanderPotDeVin() throws PartieException, RemoteException {
		Partie p = new Partie();
		Joueur j1 = new Joueur("Joueur1", 10);
		Joueur j2 = new Joueur("Joueur2", 10);
		Joueur j3 = new Joueur("Joueur3", 10);
		Joueur j4 = new Joueur("Joueur2", 10);
		Joueur j5 = new Joueur("Joueur3", 10);
		
		/*SantiagoInterface s1 = new Santiago(j1);
		SantiagoInterface s2 = new Santiago(j2);
		SantiagoInterface s3 = new Santiago(j3);
		SantiagoInterface s4 = new Santiago(j4);
		SantiagoInterface s5 = new Santiago(j5);
		
		p.addClient(s1);
		p.addClient(s2);
		p.addClient(s3);
		p.addClient(s4);
		p.addClient(s5); p.setConstructeurDeCanal(s5);
		
		HashMap<SantiagoInterface, Integer> listePotsDeVin = new HashMap<>();
		HashMap<SantiagoInterface, SantiagoInterface> listeSoutiens = new HashMap<>();
		HashMap<SantiagoInterface, Integer> listePropositions = new HashMap<>();
		
		//Construction du canal du joueur2:
//		listePotsDeVin.put(s1, 5); listePropositions.put(s1, 5);
//		listePotsDeVin.put(s2, 8); listePropositions.put(s2, 8);
//		listePotsDeVin.put(s3, 5); listeSoutiens.put(s3, s1); s3.cumulerPotDeVin(listePropositions, s1, 5);
//		listePotsDeVin.put(s4, 5); listeSoutiens.put(s4, s2); s4.cumulerPotDeVin(listePropositions, s2, 5);
		//Construction de son propre canal:
		listePotsDeVin.put(s1, 1); listePropositions.put(s1, 1);
		listePotsDeVin.put(s2, 1); listePropositions.put(s2, 1);
		listePotsDeVin.put(s3, 1); listeSoutiens.put(s3, s1); s3.cumulerPotDeVin(listePropositions, s1, 1);
		listePotsDeVin.put(s4, 1); listeSoutiens.put(s4, s2); s4.cumulerPotDeVin(listePropositions, s2, 1);
		
		//Test du cumul des pots de vin :
		int potDeVinJ1 = listePropositions.get(s1);
		int resAttenduJ1 = 2;
		assertEquals(potDeVinJ1, resAttenduJ1);
		
		int potDeVinJ2 = listePropositions.get(s2);
		int resAttenduJ2 = 2;
		assertEquals(potDeVinJ2, resAttenduJ2);
		System.out.println(s5.getJoueur().getSolde());
		//--------------------------------------------------
		//Choix du constructeur de canal:
		//s5.choisirPotDeVin(listePropositions);
		s5.deduirePotDeVin(listePropositions, listeSoutiens, listePotsDeVin, s5);
		System.out.println(s5.getJoueur().getSolde());
		//Construction du canal du joueur2
//		int resAttenduSoldeS5 = 23; int resAttenduSoldeS1 = 10; int resAttenduSoldeS2 = 2; int resAttenduSoldeS4 = 5;
		
		//Construction de son propre canal:
		int resAttenduSoldeS5 = 7; int resAttenduSoldeS1 = 10; int resAttenduSoldeS2 = 10; int resAttenduSoldeS4 = 10;
		
		assertEquals(j1.getSolde(), resAttenduSoldeS1); 
		assertEquals(j2.getSolde(), resAttenduSoldeS2);
		assertEquals(j4.getSolde(), resAttenduSoldeS4); 
		assertEquals(j5.getSolde(), resAttenduSoldeS5);
		*/
	}

	
	
	/**
	 * Test de la méthode Phase2
	 * 
	 * @throws PartieException 
	 * @throws RemoteException 
	 * @throws JoueurException 
	 */
	@Test
	public void testPhase2() throws PartieException, RemoteException{
		Partie p = new Partie();
		Joueur j1 = new Joueur("Joueur1", 0);
		Joueur j2 = new Joueur("Joueur2", 0);
		Joueur j3 = new Joueur("Joueur3", 0);
		/*SantiagoInterface s1 = new Santiago(j1);
		SantiagoInterface s2 = new Santiago(j2);
		SantiagoInterface s3 = new Santiago(j3);
		p.addClient(s1);
		p.addClient(s2);
		p.addClient(s3);
		//Test pour etre sur que c'est bien le premier a avoir passer qui sera constructeur de canal
		HashMap<SantiagoInterface, Integer> listeOffres1 = new HashMap<>();
		listeOffres1.put(s1, 0);	
		listeOffres1.put(s2, 0);	
		listeOffres1.put(s3, 0);	
		p.setConstructeurDeCanal(s1);
		p.phase2(listeOffres1);
		assertEquals(s2,p.getConstructeurDeCanal());
		//Test pour etre sur que c'est bien l'offre la plus basse qui devient le constructeur de canal
		HashMap<SantiagoInterface, Integer> listeOffres2 = new HashMap<>();
		listeOffres2.put(s1, 10);	
		listeOffres2.put(s2, 2);	
		listeOffres2.put(s3, 70);
		p.setConstructeurDeCanal(s2);
		p.phase2(listeOffres2);
		assertEquals(s2,p.getConstructeurDeCanal());*/
	}

	
	@Test
	public void testPlusGrandeOffre() throws RemoteException, PartieException{
		Joueur j1 = new Joueur("Joueur1", 0);
		Joueur j2 = new Joueur("Joueur2", 0);
		Joueur j3 = new Joueur("Joueur3", 0);
		/*SantiagoInterface s1 = new Santiago(j1);
		SantiagoInterface s2 = new Santiago(j2);
		SantiagoInterface s3 = new Santiago(j3);
		Partie p = new Partie();
		HashMap<SantiagoInterface, Integer> listeOffres = new HashMap<>();
		listeOffres.put(s1, 10);
		listeOffres.put(s2, 15);
		listeOffres.put(s3, 20);
		assertEquals(s3,p.plusGrandeOffre(listeOffres));*/
	}
	
	@Test
	public void testOrdreDecroissantOffre() throws RemoteException, PartieException{
		Joueur j1 = new Joueur("Joueur1", 0);
		Joueur j2 = new Joueur("Joueur2", 0);
		Joueur j3 = new Joueur("Joueur3", 0);
		/*SantiagoInterface s1 = new Santiago(j1);
		SantiagoInterface s2 = new Santiago(j2);
		SantiagoInterface s3 = new Santiago(j3);
		Partie p = new Partie();
		HashMap<SantiagoInterface, Integer> listeOffres = new HashMap<>();
		listeOffres.put(s1, 10);
		listeOffres.put(s2, 15);
		listeOffres.put(s3, 20);
		ArrayList<SantiagoInterface> listeComparative = new ArrayList<SantiagoInterface>();
		listeComparative.add(s3);
		listeComparative.add(s2);
		listeComparative.add(s1);
		ArrayList<SantiagoInterface> liste = p.ordreDecroissantOffre(listeOffres);
		assertEquals(listeComparative.get(0),liste.get(0));
		assertEquals(listeComparative.get(1),liste.get(1));
		assertEquals(listeComparative.get(2),liste.get(2));*/
	}
    
	@Test
	public void testPhase3() throws PartieException, RemoteException, JoueurException{
		Joueur j1 = new Joueur("Joueur1", 10, "Blanc");
		assertEquals(22, j1.getListeMarqueurs().size());
		Joueur j2 = new Joueur("Joueur2", 10, "Orange");
		assertEquals(22, j2.getListeMarqueurs().size());
		Joueur j3 = new Joueur("Joueur3", 10, "Rouge");
		/*assertEquals(22, j3.getListeMarqueurs().size());
		SantiagoInterface s1 = new Santiago(j1);
		SantiagoInterface s2 = new Santiago(j2);
		SantiagoInterface s3 = new Santiago(j3);
		Partie p = new Partie("Test",3);
		p.addClient(s1);
		p.addClient(s2);
		p.addClient(s3);
		p.lancePartie();*/
	}

	

	
	/**
	 * Test du plateau
	 * 
	 */
	@Test
	public void testTaillePlateau() throws PartieException {

		Plateau plateau = new Plateau();
		Source source = plateau.getSource();
		
		//On test que chaque case du tableau contienne bien une CASE
		for(int i=0 ; i < plateau.getTabPlateau().length ; i++){
			
			for(int j=0 ; j < plateau.getTabPlateau()[i].length ; j++){
				
				Object o = plateau.getTabPlateau()[i][j];
				
				assertTrue(o instanceof Case);
			}
		}
			
	}
	
	
	
	/**
	 * Test du nombre de canaux
	 * @throws PartieException 
	 */
	@Test
	public void testNombreCanaux() throws PartieException {
		
		Plateau plateau = new Plateau();
		System.out.println("Nombre canaux : "+plateau.getListeCanaux().size());
		assertEquals(24, plateau.getListeCanaux().size());
	}
	
	
	/**
	 * Test de l'affichage plateau
	 * 
	 */

	@Test
	public void testAffichePlateau() throws PartieException {

		
		Plateau plateau = new Plateau();
		Source source = plateau.getSource();
		plateau.afficheCanaux();
		
		System.out.println("On irrigue les canaux au dessus et à droite de la source X : ("+source.getCoordX()[0]+","+source.getCoordX()[1]+") Y : ("+source.getCoordY()[0]+","+source.getCoordY()[1]+")");
		
		for(Canal canal : plateau.getListeCanaux()){

			//On test le canal du haut
			if( (canal.getCoordFin().x == source.getCoordX()[1]) && (canal.getCoordFin().y == source.getCoordY()[0])){
				System.out.println("2 Canal haut : Debut : ("+canal.getCoordFin().x+","+canal.getCoordFin().y+") Fin :("+canal.getCoordFin().x+","+canal.getCoordFin().y+")");
				
				plateau.metCanal(canal);
				//canal.metEnEau();
			}
			
			
			//On test le canal de droite
			if((canal.getCoordDebut().y == canal.getCoordFin().y) && (canal.getCoordDebut().x == source.getCoordX()[0]+1) && (canal.getCoordDebut().y == source.getCoordY()[1])){
				System.out.println("2 Canal Droite : Debut : ("+canal.getCoordDebut().x+","+canal.getCoordDebut().y+") Fin :("+canal.getCoordFin().x+","+canal.getCoordFin().y+")");
				
				plateau.metCanal(canal);
				//canal.metEnEau();
			}
		}
		
		
		//On affiche le tableau
		System.out.println(plateau.toString());
		
	}



	/**
	 * Test du compte de point en fin de partie
	 * @throws RemoteException 
	 * 
	 */
	@Test
	public void testScore() throws PartieException, RemoteException {
		try{
		Partie p = new Partie();
		Plateau plateau = p.getPlateau();
		Source source = plateau.getSource();
				
		//on ajoute les joueurs
		Joueur j1 = new Joueur("Joueur1", 0);
		j1.setCouleur("Rouge");
		Joueur j2 = new Joueur("Joueur2", 0);
		j2.setCouleur("Blanc");
		Joueur j3 = new Joueur("Joueur3", 0);
		j3.setCouleur("Orange");
		
		/*SantiagoInterface s1 = new Santiago(j1);
		SantiagoInterface s2 = new Santiago(j2);
		SantiagoInterface s3 = new Santiago(j3);
		p.addClient(s1);
		p.addClient(s2);
		p.addClient(s3);
		
		//On crée des marqueur de rendement
		MarqueurRendement m1 = new MarqueurRouge();
		MarqueurRendement m2 = new MarqueurBlanc();		
		MarqueurRendement m3 = new MarqueurOrange();*/
		
		//On ajoute les tuiles
		
		/*
		 * 1 - Score 
		 * 		j1 = 6;
		 * 		j2 = 3;
		 * 		j3 = 3;
		 */
		/*Tuile t1 = new TuilePiment(2);
		t1.addMarqueur(m1);
		t1.addMarqueur(m1);

		Tuile t2 = new TuileBanane(1);
		t2.addMarqueur(m2);
		
		Tuile t3 = new TuileBanane(2);
		t3.addMarqueur(m3);
		t3.addMarqueur(m3);

		//On pose les tuiles
		
		plateau.getTabPlateau()[0][1].setContientTuile(t1);
		plateau.getTabPlateau()[1][2].setContientTuile(t2);
		plateau.getTabPlateau()[2][3].setContientTuile(t3);*/

		/*plateau.getTabPlateau()[source.getCoordY()[0]][source.getCoordX()[0]].setContientTuile(t1);
		plateau.getTabPlateau()[source.getCoordY()[0]][source.getCoordX()[1]].setContientTuile(t2);
		plateau.getTabPlateau()[source.getCoordY()[1]][source.getCoordX()[0]].setContientTuile(t3);*/

		int i1 = j1.getSolde();
		int i2 = j2.getSolde();
		int i3 = j3.getSolde();
	
		
		/*System.out.println("T1 : "+t1+" : "+t1.getNombreMarqueursActuels());
		System.out.println("T2 : "+t2+" : "+t2.getNombreMarqueursActuels());
		System.out.println("T3 : "+t3+" : "+t3.getNombreMarqueursActuels());*/

		System.out.println(plateau.toString());

		//On calcule
		p.finDePartie();


		System.out.println("J1 : "+j1.getSolde());
		System.out.println("J2 : "+j2.getSolde());
		System.out.println("J3 : "+j3.getSolde());
		
		assertEquals(6+i1 , j1.getSolde());
		assertEquals(3+i2 , j2.getSolde());
		assertEquals(6+i3 , j3.getSolde());
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSauvegarder() throws PartieException, FileNotFoundException, IOException {
		Partie p = new Partie("coucou", 3);
		Joueur j1 = new Joueur("Joueur1", 0);
		Joueur j2 = new Joueur("Joueur2", 20);
		Joueur j3 = new Joueur("Joueur3", 0);
		//SantiagoInterface s1 = new Santiago(j1);
		//SantiagoInterface s2 = new Santiago(j2);
		//SantiagoInterface s3 = new Santiago(j3);
		//p.addClient(s1);
		//p.addClient(s2);
		//p.addClient(s3);
		
		//Test pour etre sur que c'est bien le premier a avoir passer qui sera constructeur de canal
		//HashMap<SantiagoInterface, Integer> listeOffres1 = new HashMap<>();
		//listeOffres1.put(s1, 0);	
		//listeOffres1.put(s2, 0);	
		//listeOffres1.put(s3, 0);	
		//p.setConstructeurDeCanal(s1);
		
		//s1.sauvegarder();
		
		//serveur.rejoindrePartie
	}


}

