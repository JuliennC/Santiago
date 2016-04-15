package test;

import static org.junit.Assert.*;

import org.junit.Test;

import Classes.Plateau.Plateau;
import Classes.Tuile.TuileBanane;
import Exception.PartieException;

public class PlateauImplTest {

	@Test
	public void testChercheCaseAdjacente() throws PartieException{
		Plateau p = new Plateau();

		TuileBanane t1 = new TuileBanane(1);
		TuileBanane t2 = new TuileBanane(1);
		TuileBanane t3 = new TuileBanane(1);
		TuileBanane t4 = new TuileBanane(1);
		TuileBanane t5 = new TuileBanane(1);
		TuileBanane t6 = new TuileBanane(1);
		TuileBanane t7 = new TuileBanane(1);
		
		t7.setDesert();
		
		p.getTabPlateau()[0][0].setContientTuile(t1);
		assertEquals(2,p.chercheCaseAdjacente().size());
		
		p.getTabPlateau()[0][7].setContientTuile(t2);
		assertEquals(4,p.chercheCaseAdjacente().size());
		
		p.getTabPlateau()[5][7].setContientTuile(t3);
		assertEquals(6,p.chercheCaseAdjacente().size());
		
		p.getTabPlateau()[5][0].setContientTuile(t4);
		assertEquals(8,p.chercheCaseAdjacente().size());
		
		p.getTabPlateau()[2][3].setContientTuile(t5);
		assertEquals(12,p.chercheCaseAdjacente().size());
		
		p.getTabPlateau()[2][4].setContientTuile(t6);
		assertEquals(14,p.chercheCaseAdjacente().size());
		
		p.getTabPlateau()[4][5].setContientTuile(t7);
		assertEquals(14,p.chercheCaseAdjacente().size());
	}

}
