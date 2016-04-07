package test;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.HashMap;
import org.junit.Test;
import Classes.Partie;
import Classes.Plateau.Case;
import Classes.Plateau.Plateau;
import Classes.Plateau.Source;
import Exception.PartieException;
import network.Santiago;
import network.SantiagoInterface;
import Classes.Joueur;

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
		SantiagoInterface s1 = new Santiago(j1);
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
		assertEquals(3, p.getListeJoueurs().size());
	}

	/**
	 * Test de la méthode fabriqueTuile
	 * 
	 * @throws PartieException
	 */
	@Test(expected = PartieException.class)
	public void testFabriqueTuile() throws PartieException {
		Partie p = new Partie();
		assertEquals(45, p.getListTuiles().size());
		p.setPartieCommence();
		p.fabriqueTuiles();
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
		SantiagoInterface s1 = new Santiago(j1);
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
		assertTrue(j1 == jRandom || j2 == jRandom || j3 == jRandom);
	}

	/**
	 * Test de la méthode lancePartie
	 * 
	 * @throws PartieException
	 * @throws RemoteException 
	 */
	@Test
	public void testStart() throws PartieException, RemoteException {
		Joueur j1 = new Joueur("Joueur1", 0);
		Joueur j2 = new Joueur("Joueur2", 0);
		Joueur j3 = new Joueur("Joueur3", 0);
		SantiagoInterface s1 = new Santiago(j1);
		SantiagoInterface s2 = new Santiago(j2);
		SantiagoInterface s3 = new Santiago(j3);
		Partie p = new Partie();
		p.addClient(s1);
		p.addClient(s2);
		p.addClient(s3);
		p.lancePartie();
		assertNotEquals(null, p.getConstructeurDeCanal());
		assertEquals(true, p.getPartieACommence());
	}

	/**
	 * Test de la méthode AideAuDeveloppement
	 * @throws RemoteException 
	 * 
	 */
	@Test
	public void testAideAuDeveloppement() throws PartieException, RemoteException {
		Joueur j1 = new Joueur("Joueur1", 0);
		Joueur j2 = new Joueur("Joueur2", 0);
		Joueur j3 = new Joueur("Joueur3", 0);
		SantiagoInterface s1 = new Santiago(j1);
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
		assertEquals(3, j3.getSolde());
	}



	/**
	 * Test de la méthode rejoindrePartie
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 * 
	 */
/*	@Test(expected = PartieException.class)
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
	}*/
	
	
	/**
	 * Test de la méthode Phase2
	 * 
	 * @throws PartieException 
	 * @throws RemoteException 
	 */
	@Test
	public void testPhase2() throws PartieException, RemoteException{
		Partie p = new Partie();
		Joueur j1 = new Joueur("Joueur1", 0);
		Joueur j2 = new Joueur("Joueur2", 0);
		Joueur j3 = new Joueur("Joueur3", 0);
		SantiagoInterface s1 = new Santiago(j1);
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
		assertEquals(s2,p.getConstructeurDeCanal());
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
		
		
		//On affiche le tableau
		System.out.println(plateau.toString());
	}
	
	
}
