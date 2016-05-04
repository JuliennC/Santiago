package main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import Classes.Joueur;
import Classes.Partie;
import Classes.Static;
import Classes.Plateau.Case;
import Classes.Plateau.Plateau;
import Classes.Tuile.Tuile;
import Exception.JoueurException;
import Exception.PartieException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import network.Santiago;
import network.SantiagoInterface;

public class MainClientFxml extends Application implements Initializable{

	private static SantiagoInterface client;
	private SantiagoInterface serveur;

	/*-------------Début attribut creation -------------*/
	@FXML
	TextField nomPartie,partieError;
	
	@FXML
	RadioButton radio3, radio4, radio5;
	
	@FXML
	ToggleGroup nbJoueur;
	
	@FXML
	Accordion listePartie;
	
	@FXML
	TitledPane titleEmpty;
	
	@FXML
	Button createButton, loadButton, joinButton;
	
	/*------------- Fin attribut creation -------------*/
	
	
	
	/*------------- Début attribut Accueil ---------------*/
	@FXML
	TextField pseudo;
	
	@FXML
	Text pseudoError;
	
	@FXML
	Button valider;
	/*--------------- Fin attribut Accueil ---------------*/
	
	
	/*-------------Début attribut Salle D'attente -------------*/
	
	@FXML
	Text joueur1, joueur2, joueur3, joueur4, joueur5;
	
	/*------------- Fin attribut Salle D'attente --------------*/
	
	/*-------------Début attributs Plateau --------------------*/
    @FXML
    ImageView Case_1_1, Case_2_1, Case_4_1, Case_5_1, Case_7_1, Case_8_1, Case_1_2, Case_8_2, Case_7_2, Case_5_2,
    Case_2_2, Case_4_2, Case_1_4, Case_8_4, Case_7_4, Case_5_4, Case_4_4, Case_2_4, Case_1_5, Case_8_5, Case_7_5,
    Case_5_5, Case_4_5, Case_2_5, Case_7_7, Case_5_7, Case_4_7, Case_2_7, Case_1_7, Case_8_7, Case_1_8, Case_8_8,
    Case_7_8, Case_5_8, Case_4_8, Case_2_8, Case_1_10, Case_8_10, Case_7_10, Case_5_10, Case_4_10, Case_2_10,
    Case_1_11, Case_8_11, Case_7_11, Case_5_11, Case_4_11, Case_2_11;
    
    HashMap<ImageView, String> caseMap = new HashMap<>();

    @FXML
    ImageView CanalVide_0_1, CanalVide_0_2, CanalVide_3_9, CanalVide_6_12, CanalVide_3_12, CanalVide_0_12,
    CanalVide_9_9, CanalVide_6_9, CanalVide_0_9, CanalVide_9_6, CanalVide_6_6, CanalVide_3_6, CanalVide_0_6,
    CanalVide_9_3, CanalVide_6_3, CanalVide_3_3, CanalVide_0_3, CanalVide_9_0, CanalVide_6_0, CanalVide_3_0,
    CanalVide_9_12, CanalVide_0_0, Canal_1_9, Canal_7_9, Canal_8_12, Canal_7_12, Canal_5_3, Canal_5_6,
    Canal_5_9, Canal_5_12, Canal_4_3, Canal_4_6, Canal_4_9, Canal_4_12, Canal_2_12, Canal_1_12, Canal_2_3,
    Canal_2_6, Canal_3_9, Canal_1_6, Canal_1_3, Canal_1_0, Canal_2_0, Canal_4_0, Canal_5_0, Canal_7_0,
    Canal_8_0, Canal_8_3, Canal_7_3, Canal_8_6, Canal_7_6, Canal_8_9;
	
	
	/*------------------------------------------------------*/
	
	private static Stage stage;
	private static String name;
	
	private static MainClientFxml controller;
	@Override

	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			this.serveur = (SantiagoInterface)Naming.lookup("rmi://127.0.0.1:44000/ABC");
		} 
		catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Cette méthode permet de lancer la scene de choix du pseudo (accueil).
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		caseMap.put(this.Case_1_1, "Case_1_1");
		final URL url = getClass().getResource("../view/Accueil.fxml");
        final FXMLLoader fxmlLoader = new FXMLLoader(url);
        final BorderPane root = (BorderPane) fxmlLoader.load();
        final Scene scene = new Scene(root);
        
        
        stage = primaryStage;
        stage.setScene(scene);
        stage.setTitle("Santiago");
        primaryStage.show();
        
	}

	/**
	 * Cette méthode lance la scène de choix de la partie.
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	public void startChoixPartie(Stage primaryStage) throws IOException{
		URL url = getClass().getResource("../view/ChoixPartie.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        BorderPane root = (BorderPane) fxmlLoader.load();
        MainClientFxml controller = (MainClientFxml)fxmlLoader.getController();
        controller.changeAccordion();
        final Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Santiago");
        stage.show();
	}
	
	
	
	
	/**
	 * Fonction qui va chercher les informations de la partie
	 */
	public void chercheInfoPartie(final Partie p) throws IOException{


		Service<Void> updateSalle = new Service<Void>(){
			protected Task<Void> createTask() {
				return new Task<Void>(){
					
					@Override
					protected Void call() throws Exception {

						ArrayList<Integer> modifs = new ArrayList<>();
						
						int index = 0;
						
						while(true){
							
							Partie partie = serveur.getPartieByName(p.getNomPartie());							

							if(partie == null){ continue; }
						
							if(partie.getListeModifications().size() > 0){
								
								//On ajoute les éléments suivants
								for(int i = index; i < partie.getListeModifications().size(); i++){
									
									modifs.add(partie.getListeModifications().get(i));
									index = i+1;
								}
								

								if(modifs.size() == 0){ /*System.out.println("\nfin");*/ continue; }

								Integer modif = modifs.get(0);
								System.out.println("Modif : "+modif);

								if(modif.equals(Static.modificationJoueurs)){
									//System.out.println("---- joueur");

								
							        MainClientFxml.controller.changeText(partie);
																			    
									modifs.remove(0);

							
								} else if(modif.equals(Static.modificationPartieCommence)){
							
									MainClientFxml.controller.lancementPlateau();
										modifs.remove(0);
							
								} else if(modif.equals(Static.modificationJoueurDeconnection)){
									
									System.out.println("ATTENTION : un joueur s'est déconnecté");
									modifs.remove(0);

								} else if(modif.equals(Static.modificationTuiles)){
									MainClientFxml.controller.modifierTuiles();
									modifs.remove(0);
								}

						

							}
							
						}

					}
			    };
				
			}
			
		};
		

		updateSalle.start();
	}
	
	
	
	
	/**
	 *Cette méthode permet de valider un pseudo, de le verifier 
	 * et de passer à la scène suivante si le pseudo est valide.
	 * 
	 * @param e
	 * @throws NotBoundException
	 * @throws IOException
	 */
	public void validerPseudo(ActionEvent e) throws NotBoundException, IOException{
		
		String pseudo = this.pseudo.getText();
		
		//On vérifie que le pseudo est disponible
		boolean pseudoEstDispo = serveur.pseudoEstDisponible(pseudo);
		
		if(!pseudo.isEmpty()&&pseudoEstDispo){
			Joueur joueur = new Joueur(pseudo,10);
			client = new Santiago("client");
			client.setJoueur(joueur);

			serveur.addPseudo(pseudo);
			//parcoursListePartie();
			startChoixPartie((Stage)this.valider.getScene().getWindow());
		}
		else{
			this.pseudoError.setText("Pseudo invalide");
		}
	}
	
	public void changeAccordion() throws RemoteException{
		ObservableList<TitledPane> list = FXCollections.observableArrayList();
		for(Partie p : serveur.voirParties()){
			if(!p.getPartieACommence()){
				GridPane grid = new GridPane();
				grid.addRow(0,new Text("Nombre de Joueur Requis: "+p.getNombreJoueursRequis()));
				grid.addRow(1,new Text("Nombre de Joueur dans la Partie: "+p.getNombreJoueurDansLaPartie()));
				TitledPane pane = new TitledPane(p.getNomPartie(),grid);
				list.add(pane);
			}
		}
		this.listePartie.getPanes().addAll(list);
		if(!this.listePartie.getPanes().isEmpty()){
			this.listePartie.setExpandedPane(this.listePartie.getPanes().get(0));
		}
	}

	
	/**
	 * Méthode qui appel la création d'une partie
	 * 
	 * @throws NumberFormatException
	 * @throws NotBoundException
	 * @throws PartieException
	 * @throws JoueurException
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void createPartie(ActionEvent e) throws NumberFormatException, NotBoundException, PartieException, JoueurException, IOException, InterruptedException{
		Button b = (Button)e.getSource();
		RadioButton radio = (RadioButton)this.nbJoueur.getSelectedToggle();
		name = this.nomPartie.getText();
		Partie p = serveur.creerPartie(name ,Integer.parseInt(radio.getText()));
		
		serveur.ajouterPartieListe(p);
		
		p = serveur.rejoindrePartie(this.nomPartie.getText(), client);

		salleDAttente((Stage)b.getScene().getWindow(),this.nomPartie.getText());
			
	}
	
	/**
	 * Cette méthode permet de choisir une partie à rejoindre
	 * *
	 * @param e
	 * @throws RemoteException
	 * @throws InterruptedException 
	 */
	public void joinPartie(ActionEvent e) throws RemoteException, InterruptedException{
		TitledPane pane = this.listePartie.getExpandedPane();
		name = pane.getText();
		Partie pRejoint;
		try{
			pRejoint = serveur.rejoindrePartie(name, this.client);
			serveur.initialiserPartie(pRejoint);
		}
        catch(RemoteException | PartieException | JoueurException e1){
        	this.partieError.setText("Vous ne pouvez pas rejoindre cette partie");
        }
		try {
			Button b = (Button) e.getSource();
			salleDAttente((Stage)b.getScene().getWindow(),name);
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Cette méthope permet de modifier les joueurs en attente lors du lancement de la scene d'attente
	 * 
	 * @param p
	 */
	public void changeText(Partie p){
		int nbJoueurRequis = p.getNombreJoueursRequis();
		int nbJoueurDansPartie = p.getNombreJoueurDansLaPartie();
		
		System.out.println("p : "+p);
		System.out.println("p : "+p.getListeJoueurs());


		
		switch(nbJoueurRequis){
			case 3:

				if(nbJoueurDansPartie == 1){
					this.joueur1.setText("Joueur 1 : "+p.getListeJoueurs().get(0).getPseudo());
					this.joueur2.setText("Joueur 2 : En Attente");
					this.joueur3.setText("Joueur 3 : En Attente");
				}
				else if(nbJoueurDansPartie == 2){
					this.joueur1.setText("Joueur 1 : "+p.getListeJoueurs().get(0).getPseudo());
					this.joueur2.setText("Joueur 2 : "+p.getListeJoueurs().get(1).getPseudo());
					this.joueur3.setText("Joueur 3 : En Attente");
					
				}
				else{
					this.joueur1.setText("Joueur 1 : "+p.getListeJoueurs().get(0).getPseudo());
					this.joueur2.setText("Joueur 2 : "+p.getListeJoueurs().get(1).getPseudo());
					this.joueur3.setText("Joueur 3 : "+p.getListeJoueurs().get(2).getPseudo());
				}				
				break;
			case 4:
				if(nbJoueurDansPartie == 1){
					this.joueur1.setText("Joueur 1 : "+p.getListeJoueurs().get(0).getPseudo());
					this.joueur2.setText("Joueur 2 : En Attente");
					this.joueur3.setText("Joueur 3 : En Attente");
					this.joueur4.setText("Joueur 4 : En Attente");
				}
				else if(nbJoueurDansPartie == 2){
					this.joueur1.setText("Joueur 1 : "+p.getListeJoueurs().get(0).getPseudo());
					this.joueur2.setText("Joueur 2 : "+p.getListeJoueurs().get(1).getPseudo());
					this.joueur3.setText("Joueur 3 : En Attente");
					this.joueur4.setText("Joueur 4 : En Attente");
				}
				else if(nbJoueurDansPartie == 3){
					this.joueur1.setText("Joueur 1 : "+p.getListeJoueurs().get(0).getPseudo());
					this.joueur2.setText("Joueur 2 : "+p.getListeJoueurs().get(1).getPseudo());
					this.joueur3.setText("Joueur 3 : "+p.getListeJoueurs().get(2).getPseudo());
					this.joueur4.setText("Joueur 4 : En Attente");
				}
				else{
					this.joueur1.setText("Joueur 1 : "+p.getListeJoueurs().get(0).getPseudo());
					this.joueur2.setText("Joueur 2 : "+p.getListeJoueurs().get(1).getPseudo());
					this.joueur3.setText("Joueur 3 : "+p.getListeJoueurs().get(2).getPseudo());
					this.joueur4.setText("Joueur 4 : "+p.getListeJoueurs().get(3).getPseudo());
				}
				break;
			case 5:
				if(nbJoueurDansPartie == 1){
					this.joueur1.setText("Joueur 1 : "+p.getListeJoueurs().get(0).getPseudo());
					this.joueur2.setText("Joueur 2 : En Attente");
					this.joueur3.setText("Joueur 3 : En Attente");
					this.joueur4.setText("Joueur 4 : En Attente");
					this.joueur5.setText("Joueur 5 : En Attente");
				}
				else if(nbJoueurDansPartie == 2){
					this.joueur1.setText("Joueur 1 : "+p.getListeJoueurs().get(0).getPseudo());
					this.joueur2.setText("Joueur 2 : "+p.getListeJoueurs().get(1).getPseudo());
					this.joueur3.setText("Joueur 3 : En Attente");
					this.joueur4.setText("Joueur 4 : En Attente");
					this.joueur5.setText("Joueur 5 : En Attente");
				}
				else if(nbJoueurDansPartie == 3){
					this.joueur1.setText("Joueur 1 : "+p.getListeJoueurs().get(0).getPseudo());
					this.joueur2.setText("Joueur 2 : "+p.getListeJoueurs().get(1).getPseudo());
					this.joueur3.setText("Joueur 3 : "+p.getListeJoueurs().get(2).getPseudo());
					this.joueur4.setText("Joueur 4 : En Attente");
					this.joueur5.setText("Joueur 5 : En Attente");
				}
				else if(nbJoueurDansPartie == 4){
					this.joueur1.setText("Joueur 1 : "+p.getListeJoueurs().get(0).getPseudo());
					this.joueur2.setText("Joueur 2 : "+p.getListeJoueurs().get(1).getPseudo());
					this.joueur3.setText("Joueur 3 : "+p.getListeJoueurs().get(2).getPseudo());
					this.joueur4.setText("Joueur 4 : "+p.getListeJoueurs().get(3).getPseudo());
					this.joueur5.setText("Joueur 5 : En Attente");
				}
				else {
					this.joueur1.setText("Joueur 1 : "+p.getListeJoueurs().get(0).getPseudo());
					this.joueur2.setText("Joueur 2 : "+p.getListeJoueurs().get(1).getPseudo());
					this.joueur3.setText("Joueur 3 : "+p.getListeJoueurs().get(2).getPseudo());
					this.joueur4.setText("Joueur 4 : "+p.getListeJoueurs().get(3).getPseudo());
					this.joueur5.setText("Joueur 5 : "+p.getListeJoueurs().get(4).getPseudo());
				}
				break;
		}
	}
	
	/**
	 * Cette méthode lance la scene d'attente
	 * 
	 * @param primaryStage
	 * @param p
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public void salleDAttente(Stage primaryStage,final String nomPartie) throws IOException, InterruptedException{
		Partie partie = serveur.getPartieByName(nomPartie);
		URL url = getClass().getResource("../view/SalleDAttente.fxml");
        final FXMLLoader fxmlLoader = new FXMLLoader(url);
        
        BorderPane root = (BorderPane) fxmlLoader.load();
        this.controller = (MainClientFxml)fxmlLoader.getController();
        controller.changeText(partie);
        final Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Santiago");
        stage.show();
        
        chercheInfoPartie(partie);

        
	}
	
	
	public void lancementPlateau() throws IOException{

		final URL url = getClass().getResource("../view/Plateau.fxml");

        final FXMLLoader fxmlLoader = new FXMLLoader(url);
        
        final BorderPane root = (BorderPane) fxmlLoader.load();
        this.controller = (MainClientFxml)fxmlLoader.getController();
        stage.getScene().setRoot(root);
        /*  final Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Santiago");
        stage.show();*/
	}
	
	public void modifierTuiles() throws IOException {  
		System.out.println(name);
        Partie p = serveur.getPartieByName(name);
        
        System.out.println("Partie : " +p);
        
        Plateau plateau = p.getPlateau();
        
        Case c = plateau.getTabPlateau()[1][1];
    	if(c.getContientTuile() != null){
    		Tuile t = c.getContientTuile();
    		afficherTuile(t, 1, 1);

    	}
    	
        //On parcourt le plateau
//        for(int i = 0; i <= 6; i++) {
//        	for(int j = 0; j <= 8; i++) {
//        		Case c = plateau.getTabPlateau()[1][1];
//            	if(c.getContientTuile() != null){
//            		Tuile t = c.getContientTuile();
//            		afficherTuile(t);
//
//            	}
//            }
//        }
	}
	
	public void afficherTuile(Tuile t, int posX, int posY) {
//		ArrayList listeMarqueurs = t.getMarqueursActuels();
//		
//	    String caseAModifier = "Case_"+posX+"_"+posY;
	    
		
//		if(t.getIntituleDuChamps().equals("Champs de banane")) {
//			if(listeMarqueurs.isEmpty()) {
//				
//			}
//		} else if(t.getIntituleDuChamps().equals("Champs de canne")) {
//			
//		} else if(t.getIntituleDuChamps().equals("Champs de haricot")) {
//			
//		} else if(t.getIntituleDuChamps().equals("Champs de piment")) {
//			
//		} else if(t.getIntituleDuChamps().equals("Champs de pomme de terre")) {
//			
//		}
		
		System.out.println("Afficher Tuile : " +t.getIntituleDuChamps());
		Image c = new Image(MainClientFxml.class.getResourceAsStream("../view/Bananes0.jpg"));
		System.out.println("Case 1:" +this.Case_1_1);
		System.out.println("Ici");
		System.out.println("Image " +c);
		//System.out.println("Case 2:" +this.Case_1_1.getId());
		this.Case_1_1.setImage(c);
		System.out.println("Ici 2");
		//System.out.println("Case 3:" +this.Case_1_1);
		
	}
	
	public static void main(String[] args) {
		Application.launch(MainClientFxml.class,args);
	}
}