package test;

import static org.junit.Assert.*;


import org.junit.Test;
import Classes.Partie;
import Classes.Plateau.Case;
import Classes.Plateau.Plateau;
import Classes.Plateau.Source;
import Exception.PartieException;
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
	 * Test de la méthode start
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
		p.start();
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
		p.start();
		p.AideAuDeveloppement();
		assertEquals(3, j1.getSolde());
		assertEquals(3, j2.getSolde());
		assertEquals(3, j3.getSolde());
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
