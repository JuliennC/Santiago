package test;

import static org.junit.Assert.*;
import org.junit.Test;
import Classes.Partie;
import Exception.PartieException;
import Classes.Joueur;

public class PartieImplTest {
	
	/**
	 * Test de la méthode addJoueur
	 * @throws PartieException 
	 */
	@Test(expected = PartieException.class)
	public void testAddJoueur () throws PartieException{
		Joueur j1 = new Joueur("Joueur1");
		Joueur j2 = new Joueur("Joueur2");
		Joueur j3 = new Joueur("Joueur3");
		Partie p = new Partie();
		p.addJoueur(j1);
		assertEquals(1,p.getListeJoueurs().size());
		p.addJoueur(j2);
		assertEquals(2,p.getListeJoueurs().size());
		p.addJoueur(j3);
		assertEquals(3,p.getListeJoueurs().size());
		p.addJoueur(j2);
		assertEquals(3,p.getListeJoueurs().size());
	}

	/**
	 * Test de la méthode fabriqueTuile
	 * @throws PartieException 
	 */
	@Test(expected = PartieException.class)
	public void testFabriqueTuile() throws PartieException{
		Partie p = new Partie();
		assertEquals(45,p.getListTuiles().size());
		p.setPartieCommence();
		p.fabriqueTuiles();
	}

	/**
	 * Test de la méthode nbTuile
	 * @throws PartieException 
	 */
	@Test
	public void testNbTuile() throws PartieException{
		Partie p = new Partie();
		p.setNombreDeJoueurs(3);
		int nbTuile = p.nbTuile();
		assertEquals(4,nbTuile);
		p.setNombreDeJoueurs(4);
		nbTuile = p.nbTuile();
		assertEquals(4,nbTuile);
		p.setNombreDeJoueurs(5);
		nbTuile = p.nbTuile();
		assertEquals(5,nbTuile);
	}
	
	/**
	 * Test de la méthode randomInList
	 * @throws PartieException 
	 */
	public void testRandomInList() throws PartieException{
		Joueur j1 = new Joueur("Joueur1");
		Joueur j2 = new Joueur("Joueur2");
		Joueur j3 = new Joueur("Joueur3");
		Partie p = new Partie();
		p.addJoueur(j1);
		Joueur jRandom = (Joueur)p.randomInList(p.getListeJoueurs());
		assertTrue(j1==jRandom||j2==jRandom||j3==jRandom);
		p.addJoueur(j2);
		jRandom = (Joueur)p.randomInList(p.getListeJoueurs());
		assertTrue(j1==jRandom||j2==jRandom||j3==jRandom);
		p.addJoueur(j3);
		jRandom = (Joueur)p.randomInList(p.getListeJoueurs());
		assertTrue(j1==jRandom||j2==jRandom||j3==jRandom);
	}
	
	/**
	 * Test de la méthode start
	 * @throws PartieException 
	 */
	public void testStart() throws PartieException{
		Partie p = new Partie();
		p.start();
		assertNotEquals(null,p.getConstructeurDeCanal());
		assertEquals(true,p.getPartieACommence());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
