package test;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.junit.Test;
import Classes.Partie;
import Exception.PartieException;
import network.Santiago;
import network.SantiagoInterface;
import Classes.Joueur;

public class PartieImplTest {

	/**
	 * Test de la méthode addJoueur
	 * 
	 * @throws PartieException
	 */
	@Test(expected = PartieException.class)
	public void testAddJoueur() throws PartieException {
		Joueur j1 = new Joueur("Joueur1", 0);
		Joueur j2 = new Joueur("Joueur2", 0);
		Joueur j3 = new Joueur("Joueur3", 0);
		Partie p = new Partie();
		p.addJoueur(j1);
		assertEquals(1, p.getListeJoueurs().size());
		p.addJoueur(j2);
		assertEquals(2, p.getListeJoueurs().size());
		p.addJoueur(j3);
		assertEquals(3, p.getListeJoueurs().size());
		p.addJoueur(j2);
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
		int nbTuile = p.nbTuile();
		assertEquals(4, nbTuile);
		p.setNombreDeJoueurs(4);
		nbTuile = p.nbTuile();
		assertEquals(4, nbTuile);
		p.setNombreDeJoueurs(5);
		nbTuile = p.nbTuile();
		assertEquals(5, nbTuile);
	}

	/**
	 * Test de la méthode randomInList
	 * 
	 * @throws PartieException
	 */
	@Test
	public void testRandomInList() throws PartieException {
		Joueur j1 = new Joueur("Joueur1", 0);
		Joueur j2 = new Joueur("Joueur2", 0);
		Joueur j3 = new Joueur("Joueur3", 0);
		Partie p = new Partie();
		p.addJoueur(j1);
		Joueur jRandom = (Joueur) p.randomInList(p.getListeJoueurs());
		assertTrue(j1 == jRandom || j2 == jRandom || j3 == jRandom);
		p.addJoueur(j2);
		jRandom = (Joueur) p.randomInList(p.getListeJoueurs());
		assertTrue(j1 == jRandom || j2 == jRandom || j3 == jRandom);
		p.addJoueur(j3);
		jRandom = (Joueur) p.randomInList(p.getListeJoueurs());
		assertTrue(j1 == jRandom || j2 == jRandom || j3 == jRandom);
	}

	/**
	 * Test de la méthode lancePartie
	 * 
	 * @throws PartieException
	 */
	@Test
	public void testStart() throws PartieException {
		Partie p = new Partie();
		Joueur j1 = new Joueur("Joueur1", 0);
		Joueur j2 = new Joueur("Joueur2", 0);
		Joueur j3 = new Joueur("Joueur3", 0);
		p.addJoueur(j1);
		p.addJoueur(j2);
		p.addJoueur(j3);
		p.lancePartie();
		assertNotEquals(null, p.getConstructeurDeCanal());
		assertEquals(true, p.getPartieACommence());
	}

	/**
	 * Test de la méthode AideAuDeveloppement
	 * 
	 */
	@Test
	public void testAideAuDeveloppement() throws PartieException {
		Joueur j1 = new Joueur("Joueur1", 0);
		Joueur j2 = new Joueur("Joueur2", 0);
		Joueur j3 = new Joueur("Joueur3", 0);
		Partie p = new Partie();
		p.addJoueur(j1);
		p.addJoueur(j2);
		p.addJoueur(j3);
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



}
