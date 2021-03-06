package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ResourceBundle;

import Classes.Joueur;
import Classes.Partie;
import Classes.Static;
import Classes.Marqueurs.MarqueurRouge;
import Classes.Marqueurs.MarqueurRendement;
import Classes.Marqueurs.MarqueurRouge;
import Classes.Marqueurs.MarqueurRendement;
import Classes.Plateau.Canal;
import Classes.Plateau.Case;
import Classes.Plateau.Plateau;
import Classes.Tuile.Tuile;
import Classes.Tuile.TuileBanane;
import Classes.Tuile.TuileCanne;
import Classes.Tuile.TuileHaricot;
import Classes.Tuile.TuilePiment;
import Classes.Tuile.TuileRetournee;
import Exception.JoueurException;
import Exception.PartieException;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.input.MouseEvent;
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

	private SantiagoInterface serveur;
	

	/*-------------Début attribut creation -------------*/
	@FXML
	TextField nomPartie,partieError,nomPartieChargement;
	
	@FXML
	RadioButton radio3, radio4, radio5;
	
	@FXML
	ToggleGroup nbJoueur;
	
	@FXML
	Accordion listePartie;
	
	@FXML
	TitledPane titleEmpty;
	
	@FXML
	Button createButton, loadButton, joinButton, actualiserLesParties;
	
	@FXML
	Text errorNomChargement;
	
	/*------------- Fin attribut creation -------------*/
	
	
	
	/*------------- Début attribut Accueil ---------------*/
	@FXML
	TextField pseudo, motDePasse;
	
	@FXML
	Text pseudoError;
	
	@FXML
	Button valider;
	/*--------------- Fin attribut Accueil ---------------*/
	
	
	/*-------------Début attribut Salle D'attente -------------*/
	
	@FXML
	Text joueur1, joueur2, joueur3, joueur4, joueur5;
	
	/*------------- Fin attribut Salle D'attente --------------*/
	

	/*-------------Début attribut Score -------------*/
	
	@FXML
	Text gagnant,deuxieme,troisieme,quatrieme,cinquieme;
	
	/*------------- Fin attribut Score --------------*/

	
	
	/*-------------Début attribut Rejoindre Parties Commencées -------------*/

	
	@FXML
	Accordion accordeonPartiesCommencees;
	
	@FXML
	Button boutonSkip, boutonRejoindre;
	
	/*------------- Fin attribut Rejoindre Parties Commencées --------------*/
	
	/*-------------Début attributs Plateau --------------------*/
	@FXML
    ImageView Case_0_0, Case_0_1, Case_0_2, Case_0_3, Case_0_4, Case_0_5, Case_1_0, Case_1_1, Case_1_2, Case_1_3,
    Case_1_4, Case_1_5, Case_2_0, Case_2_1, Case_2_2, Case_2_3, Case_2_4, Case_2_5, Case_3_0, Case_3_1, Case_3_2, Case_3_3,
    Case_3_4, Case_3_5, Case_4_0, Case_4_1, Case_4_2, Case_4_3, Case_4_4, Case_4_5, Case_5_0, Case_5_1, Case_5_2,
    Case_5_3, Case_5_4, Case_5_5, Case_6_0, Case_6_1, Case_6_2, Case_6_3, Case_6_4, Case_6_5, Case_7_0,
    Case_7_1, Case_7_2, Case_7_3, Case_7_4, Case_7_5;

    
    @FXML
    private ImageView Canal_0_3_v, Canal_4_2_h, Canal_6_6_v, Canal_3_4_h, Canal_4_2_v, Canal_5_0_h, Canal_8_1_v, Canal_1_0_h, Canal_2_6_v,
    Canal_6_5_v, Canal_2_6_h, Canal_0_4_v, Canal_8_2_h, Canal_4_1_v, Canal_8_2_v, Canal_7_4_h, Canal_2_5_v, Canal_6_6_h, Canal_4_4_h,
    Canal_0_1_v, Canal_3_6_h, Canal_5_2_h, Canal_4_4_v, Canal_8_3_v, Canal_8_4_v, Canal_1_2_h, Canal_2_0_h, Canal_0_2_v, Canal_8_4_h,
    Canal_4_3_v, Canal_6_0_h, Canal_8_5_v, Canal_7_6_h, Canal_3_0_h, Canal_4_6_v, Canal_5_4_h, Canal_4_6_h, Canal_8_6_v, Canal_2_2_v,
    Canal_1_4_h, Canal_6_1_v, Canal_2_2_h, Canal_8_6_h, Canal_4_5_v, Canal_6_2_h, Canal_2_1_v, Canal_7_0_h, Canal_6_2_v, Canal_4_0_h,
    Canal_3_2_h;


    
    
    
    
    
    @FXML
    ImageView constructeur1,constructeur2,constructeur3,constructeur4,constructeur5;
    
    @FXML
    ImageView tourJoueur1,tourJoueur2,tourJoueur3,tourJoueur4,tourJoueur5;
    
    @FXML
    ImageView tuile1,tuile2,tuile3,tuile4,tuile5;
    
    @FXML
    ImageView pioche1,pioche2,pioche3,pioche4,pioche5;
    
    @FXML
    Text nomJoueur1,nomJoueur2,nomJoueur3,nomJoueur4,nomJoueur5;

    
    ImageView MarqueurC0_0_1,  MarqueurC0_0_2, MarqueurC1_0_1, MarqueurC1_0_2, MarqueurC2_0_1, MarqueurC2_0_2, MarqueurC3_0_1,
    MarqueurC4_2, MarqueurC3_0_2, MarqueurC4_0_2, MarqueurC1_1_1, MarqueurC0_1_2, MarqueurC0_1_1,  MarqueurC7_0_2,
    MarqueurC7_0_1, MarqueurC6_0_2, MarqueurC6_0_1, MarqueurC5_0_2, MarqueurC5_0_1, MarqueurC4_0_1, MarqueurC1_1_2,
    MarqueurC7_2_2, MarqueurC7_2_1, MarqueurC6_2_2, MarqueurC6_2_1, MarqueurC5_2_2, MarqueurC5_2_1, MarqueurC4_2_2,
    MarqueurC4_2_1, MarqueurC3_2_2, MarqueurC3_2_1, MarqueurC2_2_2, MarqueurC2_2_1, MarqueurC1_2_2, MarqueurC1_2_1,
    MarqueurC0_2_2, MarqueurC0_2_1, MarqueurC7_1_2, MarqueurC7_1_1, MarqueurC6_1_2, MarqueurC6_1_1, MarqueurC5_1_2,
    MarqueurC5_1_1, MarqueurC4_1_2, MarqueurC4_1_1, MarqueurC3_1_2, MarqueurC3_1_1, MarqueurC2_1_2, MarqueurC2_1_1,
    MarqueurC6_5_2, MarqueurC7_5_2, MarqueurC7_5_1, MarqueurC6_5_1, MarqueurC5_5_2, MarqueurC5_5_1, MarqueurC4_5_2,
    MarqueurC4_5_1, MarqueurC3_5_2, MarqueurC3_5_1, MarqueurC2_5_2, MarqueurC2_5_1, MarqueurC1_5_2, MarqueurC1_5_1,
    MarqueurC0_5_2, MarqueurC0_5_1, MarqueurC7_4_2, MarqueurC7_4_1, MarqueurC6_4_2, MarqueurC6_4_1, MarqueurC5_4_2,
    MarqueurC5_4_1, MarqueurC4_4_2, MarqueurC4_4_1, MarqueurC3_4_2, MarqueurC3_4_1, MarqueurC2_4_2, MarqueurC2_4_1,
    MarqueurC1_4_2, MarqueurC1_4_1, MarqueurC0_4_2, MarqueurC0_4_1, MarqueurC7_3_2, MarqueurC7_3_1, MarqueurC6_3_2,
    MarqueurC6_3_1, MarqueurC5_3_2, MarqueurC5_3_1, MarqueurC4_3_2, MarqueurC4_3_1, MarqueurC3_3_2, MarqueurC3_3_1,
    MarqueurC2_3_2, MarqueurC2_3_1, MarqueurC1_3_2, MarqueurC1_3_1, MarqueurC0_3_2, MarqueurC0_3_1;
	
    @FXML
    Text soldeJoueur1,soldeJoueur2,soldeJoueur3,soldeJoueur4,soldeJoueur5;
    
    @FXML
    TextArea zoneTexte;
    
    @FXML
    TextField zoneSaisie;
    
    @FXML
    Button btnEnvoyer;	
	/*------------------------------------------------------*/
	
	private static Stage stage;
	private static String namePartie;
	private static String nomJoueur;

	private static MainClientFxml controller;
	private static int indexMessage = 0;

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
	 * Fonction qui renvoie un client en fonction du nom du joueur et de la partie
	 * @throws RemoteException 
	 */
	public SantiagoInterface getClient(String pseudo, String nomPartie) throws RemoteException{
		
		Partie partie = serveur.getPartieByName(nomPartie);
	
		if(partie==null){System.out.println("ERREUR : aucune partie trouvée");};
		
		for(SantiagoInterface client : partie.getListeClients()){
			
			Joueur jiuh = client.getJoueur();
			
			if (pseudo.equals(client.getJoueur().getPseudo())){
				return client;
			}
		}
		

		return null;
	}
	
	
	
	

	/**
	 * Cette méthode lance la scène de choix de la partie.
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	public void startChoixPartie(Stage primaryStage) throws IOException{
		
		
		ArrayList<Partie> partiesNonTerminees = serveur.getPartiePourJoueur(MainClientFxml.nomJoueur);
System.out.println("non term : "+partiesNonTerminees);
		if( (partiesNonTerminees.size() > 0) && (serveur.joueurParticipeAUnePartie(MainClientFxml.nomJoueur, partiesNonTerminees)) ){
			
			URL url = getClass().getResource("../view/chargePartiesCommencees.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(url);
			BorderPane root = (BorderPane) fxmlLoader.load();
			MainClientFxml controller = (MainClientFxml)fxmlLoader.getController();
			controller.changeAccordionPartiesCommencees(partiesNonTerminees);
			final Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Santiago");
			stage.show();
			
		} else {
		
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
	}
	

	
	
	
	public void changeAccordionPartiesCommencees(ArrayList<Partie> partiesNonTerminees) throws RemoteException{
		Platform.runLater(() -> {
			ObservableList<TitledPane> list = FXCollections.observableArrayList();
			for(Partie p : partiesNonTerminees){
	
					GridPane grid = new GridPane();
					grid.addRow(0,new Text("Nombre de Joueur Requis: "+p.getNombreJoueursRequis()));
					grid.addRow(1,new Text("Nombre de Joueur dans la Partie: "+p.getNombreJoueurDansLaPartie()));
					TitledPane pane = new TitledPane(p.getNomPartie(),grid);
					list.add(pane);
			}
	
			this.accordeonPartiesCommencees.getPanes().addAll(list);
			if(!this.accordeonPartiesCommencees.getPanes().isEmpty()){
				this.accordeonPartiesCommencees.setExpandedPane(this.accordeonPartiesCommencees.getPanes().get(0));
			}			
		});
		

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
							try{
							Partie partie = serveur.getPartieByName(p.getNomPartie());							
							
							if(partie == null){ continue; }
						
							if(partie.getListeModifications().size() > 0){
								//On ajoute les éléments suivants
								for(int i = index; i < partie.getListeModifications().size(); i++){
									
									modifs.add(partie.getListeModifications().get(i));
									index = i+1;
								}
								

								if(modifs.size() == 0){ /*System.out.println("\nRien à modifier");*/ continue; }

								Integer modif = modifs.get(0);
								System.out.println("Modif : "+modif);

								if(modif.equals(Static.modificationJoueurs)){

									System.out.println("Nouveau joueur ");

									
							        MainClientFxml.controller.changeText(partie);
							  		modifs.remove(0);

							
								} else if(modif.equals(Static.modificationPartieCommence)){
									MainClientFxml.controller.lancementPlateau(partie);

									modifs.remove(0);

							
								} else if(modif.equals(Static.modificationJoueurDeconnection)){
									
									System.out.println("ATTENTION : un joueur s'est déconnecté");
									
							        controller.changeText(partie);
									modifs.remove(0);
									
								} else if(modif.equals(Static.modificationTuiles)){

									System.out.println("Modification des tuiles");
									MainClientFxml.controller.metAJourAffichageTuiles();
									modifs.remove(0);
									
								}else if(modif.equals(Static.modificationConstructeurDeCanal)){

									MainClientFxml.controller.modifierConstructeur(partie);
									modifs.remove(0);

								} else if(modif.equals(Static.modificationJoueurEnCours)){
								
									MainClientFxml.controller.afficherJoueurEnCours(partie);
									modifs.remove(0);
								} else if(modif.equals(Static.modificationRetourneTuile)){
									
									MainClientFxml.controller.afficheTuilesRetournees(partie);
									modifs.remove(0);
								
								} else if(modif.equals(Static.modificationConstructeurDeCanal)){
									
									MainClientFxml.controller.modifierConstructeur(partie);
									modifs.remove(0);
									
								} else if(modif.equals(Static.modificationCannaux)){

									System.out.println("Modification des canaux");
									MainClientFxml.controller.metAJourAffichageCanaux();
									modifs.remove(0);
																		
								} else if(modif.equals(Static.modificationSoldes)){

									System.out.println("Modification des soldes");
									MainClientFxml.controller.afficheJoueur(partie);
									modifs.remove(0);

				
								}else if(modif.equals(Static.modificationTuilesRetournees)){
									
									System.out.println("Modification des tuiles retournées");
									MainClientFxml.controller.afficheTuilesRetournees(partie);
									modifs.remove(0);
									
								} else {
									System.out.println("Modif non reconnue");
								}
							}

							
							}catch(Exception e){
								e.printStackTrace();
							}
							
						}
				
					}
						
					
			    };
				
			}
			
		};
		
		

		updateSalle.start();
	}
	
	
	/**
	 * Fonction qui va chercher les informations de la partie
	 */
	public void miseAjourTexte(Partie p) throws IOException{
		Service<Void> miseAjourTexte = new Service<Void>(){
			protected Task<Void> createTask() {
				return new Task<Void>(){
					@Override
					protected Void call() throws Exception {
						Partie partie = serveur.getPartieByName(p.getNomPartie());
						while(true){
							MainClientFxml.controller.afficherTexte(partie);
							Thread.sleep(500);
						}

						}
						
					
			    };
				
			}
			
		};
		miseAjourTexte.start();
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
		String mdp = this.motDePasse.getText();
		
		//On vérifie que le pseudo est disponible
		boolean pseudoEstDispo = serveur.pseudoEstDisponible(pseudo);
		
		
		if(!pseudo.isEmpty()&&pseudoEstDispo  || (serveur.connexionPseudoEtMDP(pseudo, mdp))){
			
			MainClientFxml.nomJoueur = pseudo;
			
			serveur.enregistrePseudoEtMDP(pseudo, mdp);
			//parcoursListePartie();
			startChoixPartie((Stage)this.valider.getScene().getWindow());
		
		} else {

			this.pseudoError.setText("Pseudo invalide ou mot de passe incorrecte.");
		}
	}
	
	public void changeAccordion() throws RemoteException{
		Platform.runLater(() -> {
			ObservableList<TitledPane> list = FXCollections.observableArrayList();
			
			try {
				for(Partie p : serveur.voirParties()){
					boolean b = false;
					if(p.getListeDesJoueur() != null){
						for(Joueur j : p.getListeDesJoueur()){
							if(j.getPseudo().equals(nomJoueur)){
								b = true;
								break;
							}
						}
					}
					if(b){
						GridPane grid = new GridPane();
						grid.addRow(0,new Text("Nombre de Joueur Requis: "+p.getNombreJoueursRequis()));
						grid.addRow(1,new Text("Nombre de Joueur dans la Partie: "+p.getNombreJoueurDansLaPartie()));
						TitledPane pane = new TitledPane(p.getNomPartie(),grid);
						pane.textFillProperty().set(Color.RED);;
						list.add(pane);
					}
					else if(p.getNombreJoueurDansLaPartie()!=p.getNombreJoueursRequis()){
						GridPane grid = new GridPane();
						grid.addRow(0,new Text("Nombre de Joueur Requis: "+p.getNombreJoueursRequis()));
						grid.addRow(1,new Text("Nombre de Joueur dans la Partie: "+p.getNombreJoueurDansLaPartie()));
						TitledPane pane = new TitledPane(p.getNomPartie(),grid);
						list.add(pane);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

			this.listePartie.getPanes().addAll(list);
			if(!this.listePartie.getPanes().isEmpty()){
				this.listePartie.setExpandedPane(this.listePartie.getPanes().get(0));
			}			
		});

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
		MainClientFxml.namePartie = this.nomPartie.getText();
		Partie p = serveur.creerPartie(MainClientFxml.namePartie ,Integer.parseInt(radio.getText()));
		
		serveur.ajouterPartieListe(p);
		
		String couleur = Partie.getCouleurDispoPourPartie(p);
		
		Joueur joueur = new Joueur(MainClientFxml.nomJoueur,10, couleur);
		SantiagoInterface client = new Santiago("client");
		client.setJoueur(joueur);

		p = serveur.rejoindrePartie(this.nomPartie.getText(), client);
		
		salleDAttente((Stage)b.getScene().getWindow(),this.nomPartie.getText());
			
	}
	
	/**
	 * Cette méthode permet de charger une partie et de lancer la salle d'attente
	 * si la partie est finie il charge l'interface des scores
	 * 
	 * @param e
	 * @throws RemoteException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws PartieException
	 * @throws JoueurException
	 * @throws InterruptedException
	 */
	public void chargerPartie(ActionEvent e) throws RemoteException, FileNotFoundException, IOException, PartieException, JoueurException, InterruptedException{
		Button button = (Button) e.getSource();
		Partie p = serveur.charger(this.nomPartieChargement.getText());
		if(p == null){
			this.errorNomChargement.setText("La partie n'éxiste pas");
		}
		else if(p.partiEstTerminee()){
			lancementScore(p);
		}
		else{
			
			Joueur joueurRes = null;
			
			ArrayList<Joueur> listeJoueur = p.getListeDesJoueur();
			for(Joueur j : listeJoueur){
				if(j.getPseudo().equals(MainClientFxml.nomJoueur)){
					joueurRes = j;
					break;
				}
			}
			if(joueurRes != null){
				p.setPartieACommence(false);
				
				SantiagoInterface client = new Santiago("client");
				client.setJoueur(joueurRes);
				
				p.addClient(client);
				
				this.serveur.ajouterPartieListe(p);
				salleDAttente((Stage)button.getScene().getWindow(),p.getNomPartie());
			}
			else{
				this.errorNomChargement.setText("La partie n'éxiste pas");
			}
			
			/*this.serveur.rejoindrePartie(this.nomPartie.getText(), client);
			salleDAttente((Stage)b.getScene().getWindow(),this.nomPartie.getText());*/
		}
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
		
		joinPartieSalleDAttente(e, pane);
	}
	
	
	

	public void rejoindrePartieEnCours(ActionEvent e) throws InterruptedException{

		TitledPane pane = this.accordeonPartiesCommencees.getExpandedPane();

		joinPartieSalleDAttente(e, pane);

	}
	
	
	
	public void skipPartiesEnCours() throws IOException{
		
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
	
	
	public void joinPartieSalleDAttente(ActionEvent e, TitledPane pane) throws InterruptedException{
		
		MainClientFxml.namePartie = pane.getText();
		Partie pRejoint = null;
		try{
			
			pRejoint = serveur.getPartieByName(MainClientFxml.namePartie);
			String couleur = Partie.getCouleurDispoPourPartie(pRejoint);
			
			Joueur joueur = new Joueur(MainClientFxml.nomJoueur, 10, couleur);
			SantiagoInterface client = new Santiago("client");
			client.setJoueur(joueur);
			
			pRejoint = serveur.rejoindrePartie(MainClientFxml.namePartie, client);
			serveur.initialiserPartie(pRejoint);
		}
        catch(RemoteException | PartieException | JoueurException e1){
        	this.partieError.setText("Vous ne pouvez pas rejoindre cette partie");
        }
		try {
			Button b = (Button) e.getSource();
			salleDAttente((Stage)b.getScene().getWindow(),pRejoint.getNomPartie());
		}
	
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * Cette méthope permet de modifier les joueurs en attente lors du lancement de la scene d'attente
	 * 
	 * @param p
	 * @throws RemoteException 
	 */
	public void changeText(Partie p) throws RemoteException{
		
		Platform.runLater(() -> {
			int nbJoueurRequis = p.getNombreJoueursRequis();
			int nbJoueurDansPartie = p.getNombreJoueurDansLaPartie();
			


			
			switch(nbJoueurRequis){
				case 3:
					try {
						if(nbJoueurDansPartie == 1){
							this.joueur1.setText("Joueur 1 : "+p.getJoueursConnectes().get(0).getPseudo());
							this.joueur2.setText("Joueur 2 : En Attente");
							this.joueur3.setText("Joueur 3 : En Attente");
						}
						else if(nbJoueurDansPartie == 2){
							this.joueur1.setText("Joueur 1 : "+p.getJoueursConnectes().get(0).getPseudo());
							this.joueur2.setText("Joueur 2 : "+p.getJoueursConnectes().get(1).getPseudo());
							this.joueur3.setText("Joueur 3 : En Attente");
							
						}
						else{
							this.joueur1.setText("Joueur 1 : "+p.getJoueursConnectes().get(0).getPseudo());
							this.joueur2.setText("Joueur 2 : "+p.getJoueursConnectes().get(1).getPseudo());
							this.joueur3.setText("Joueur 3 : "+p.getJoueursConnectes().get(2).getPseudo());
						}				
						break;						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				case 4:
					try {
						if(nbJoueurDansPartie == 1){
							this.joueur1.setText("Joueur 1 : "+p.getJoueursConnectes().get(0).getPseudo());
							this.joueur2.setText("Joueur 2 : En Attente");
							this.joueur3.setText("Joueur 3 : En Attente");
							this.joueur4.setText("Joueur 4 : En Attente");
						}
						else if(nbJoueurDansPartie == 2){
							this.joueur1.setText("Joueur 1 : "+p.getJoueursConnectes().get(0).getPseudo());
							this.joueur2.setText("Joueur 2 : "+p.getJoueursConnectes().get(1).getPseudo());
							this.joueur3.setText("Joueur 3 : En Attente");
							this.joueur4.setText("Joueur 4 : En Attente");
						}
						else if(nbJoueurDansPartie == 3){
							this.joueur1.setText("Joueur 1 : "+p.getJoueursConnectes().get(0).getPseudo());
							this.joueur2.setText("Joueur 2 : "+p.getJoueursConnectes().get(1).getPseudo());
							this.joueur3.setText("Joueur 3 : "+p.getJoueursConnectes().get(2).getPseudo());
							this.joueur4.setText("Joueur 4 : En Attente");
						}
						else{
							this.joueur1.setText("Joueur 1 : "+p.getJoueursConnectes().get(0).getPseudo());
							this.joueur2.setText("Joueur 2 : "+p.getJoueursConnectes().get(1).getPseudo());
							this.joueur3.setText("Joueur 3 : "+p.getJoueursConnectes().get(2).getPseudo());
							this.joueur4.setText("Joueur 4 : "+p.getJoueursConnectes().get(3).getPseudo());
						}
						break;						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				case 5:
					try {
							if(nbJoueurDansPartie == 1){
							this.joueur1.setText("Joueur 1 : "+p.getJoueursConnectes().get(0).getPseudo());
							this.joueur2.setText("Joueur 2 : En Attente");
							this.joueur3.setText("Joueur 3 : En Attente");
							this.joueur4.setText("Joueur 4 : En Attente");
							this.joueur5.setText("Joueur 5 : En Attente");
						}
						else if(nbJoueurDansPartie == 2){
							this.joueur1.setText("Joueur 1 : "+p.getJoueursConnectes().get(0).getPseudo());
							this.joueur2.setText("Joueur 2 : "+p.getJoueursConnectes().get(1).getPseudo());
							this.joueur3.setText("Joueur 3 : En Attente");
							this.joueur4.setText("Joueur 4 : En Attente");
							this.joueur5.setText("Joueur 5 : En Attente");
						}
						else if(nbJoueurDansPartie == 3){
							this.joueur1.setText("Joueur 1 : "+p.getJoueursConnectes().get(0).getPseudo());
							this.joueur2.setText("Joueur 2 : "+p.getJoueursConnectes().get(1).getPseudo());
							this.joueur3.setText("Joueur 3 : "+p.getJoueursConnectes().get(2).getPseudo());
							this.joueur4.setText("Joueur 4 : En Attente");
							this.joueur5.setText("Joueur 5 : En Attente");
						}
						else if(nbJoueurDansPartie == 4){
							this.joueur1.setText("Joueur 1 : "+p.getJoueursConnectes().get(0).getPseudo());
							this.joueur2.setText("Joueur 2 : "+p.getJoueursConnectes().get(1).getPseudo());
							this.joueur3.setText("Joueur 3 : "+p.getJoueursConnectes().get(2).getPseudo());
							this.joueur4.setText("Joueur 4 : "+p.getJoueursConnectes().get(3).getPseudo());
							this.joueur5.setText("Joueur 5 : En Attente");
						}
						else {
							this.joueur1.setText("Joueur 1 : "+p.getJoueursConnectes().get(0).getPseudo());
							this.joueur2.setText("Joueur 2 : "+p.getJoueursConnectes().get(1).getPseudo());
							this.joueur3.setText("Joueur 3 : "+p.getJoueursConnectes().get(2).getPseudo());
							this.joueur4.setText("Joueur 4 : "+p.getJoueursConnectes().get(3).getPseudo());
							this.joueur5.setText("Joueur 5 : "+p.getJoueursConnectes().get(4).getPseudo());
						}
						break;					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			}
			
		});
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
		Platform.runLater(() -> {
			Partie partie = null;
			try {
				partie = serveur.getPartieByName(nomPartie);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			URL url = getClass().getResource("../view/SalleDAttente.fxml");
	        final FXMLLoader fxmlLoader = new FXMLLoader(url);
	        
	        BorderPane root = null;
			try {
				root = (BorderPane) fxmlLoader.load();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MainClientFxml.controller = (MainClientFxml)fxmlLoader.getController();
	        try {
				controller.changeText(partie);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        final Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.setTitle("Santiago");
	        stage.show();
	        
	        try {
				chercheInfoPartie(partie);
				miseAjourTexte(partie);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		});       
	}
	
	public void lancementPlateau(Partie p) throws IOException{
		Platform.runLater(() -> {
			System.out.println("Lanchement Plateau");
			
			final URL url = getClass().getResource("../view/Plateau.fxml");
	
	        final FXMLLoader fxmlLoader = new FXMLLoader(url);
	        
	        BorderPane root = null;
			try {
				root = (BorderPane) fxmlLoader.load();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MainClientFxml.controller = (MainClientFxml)fxmlLoader.getController();
	        try {
	        	MainClientFxml.controller.afficheJoueur(p);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        stage.getScene().setRoot(root);			
		});
	}
	
	public void lancementScore(Partie partie) throws IOException{
		final URL url = getClass().getResource("../view/Score.fxml");

        final FXMLLoader fxmlLoader = new FXMLLoader(url);
        
        final BorderPane root = (BorderPane) fxmlLoader.load();
        MainClientFxml controller = (MainClientFxml)fxmlLoader.getController();
        controller.affichageDesScores(partie);
        stage.getScene().setRoot(root);
	}

	public void affichageDesScores(Partie partie){
		Collections.sort(partie.getListeDesJoueur(), new Comparator<Joueur>() {
	        public int compare(Joueur j1, Joueur j2)
	        {
	        	Integer solde1 = (Integer)j1.getSolde();
	        	Integer solde2 = (Integer)j2.getSolde();
	            return  solde1.compareTo(solde2);
	        }
	    });
		Collections.reverse(partie.getListeDesJoueur());
		int i = 0;
		for(Joueur j : partie.getListeDesJoueur()){
			if(i == 0){
				this.gagnant.setText("Le gagnant est : " + j.getPseudo() + "\navec un score de : " + j.getSolde());
			}
			else if(i == 1){
				this.deuxieme.setText("Le deuxième est : " + j.getPseudo() + "\navec un score de : " + j.getSolde());
			}
			else if(i == 2){
				this.troisieme.setText("Le troisième est : " + j.getPseudo() + " \navec un score de : " + j.getSolde());	
			}
			else if(i == 3){
				this.quatrieme.setText("Le quatrième est : " + j.getPseudo() + " avec un score de : " + j.getSolde());
			}
			else if(i == 4){
				this.cinquieme.setText("Le cinquième est : " + j.getPseudo() + " avec un score de : " + j.getSolde());
			}
			i++;
		}
	}
	
	public void modifierConstructeur(Partie p) throws IOException{
		Joueur constructeur = p.getConstructeurDeCanal().getJoueur();
		if(constructeur != null){
			System.out.println("Coucou");
			if(constructeur.getPseudo().equals(this.nomJoueur1.getText())){
				this.constructeur1.setImage(new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Constructeur.jpg")));
				this.constructeur2.setImage(null);
				this.constructeur3.setImage(null);
				this.constructeur4.setImage(null);
				this.constructeur5.setImage(null);
			}
			else if(constructeur.getPseudo().equals(this.nomJoueur2.getText())){
				this.constructeur1.setImage(null);
				this.constructeur2.setImage(new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Constructeur.jpg")));
				this.constructeur3.setImage(null);
				this.constructeur4.setImage(null);
				this.constructeur5.setImage(null);
			}
			else if(constructeur.getPseudo().equals(this.nomJoueur3.getText())){
				this.constructeur1.setImage(null);
				this.constructeur2.setImage(null);
				this.constructeur3.setImage(new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Constructeur.jpg")));
				this.constructeur4.setImage(null);
				this.constructeur5.setImage(null);
			}
			else if(constructeur.getPseudo().equals(this.nomJoueur4.getText())){
				this.constructeur1.setImage(null);
				this.constructeur2.setImage(null);
				this.constructeur3.setImage(null);
				this.constructeur4.setImage(new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Constructeur.jpg")));
				this.constructeur5.setImage(null);
			}
			else if(constructeur.getPseudo().equals(this.nomJoueur5.getText())){
				this.constructeur1.setImage(null);
				this.constructeur2.setImage(null);
				this.constructeur3.setImage(null);
				this.constructeur4.setImage(null);
				this.constructeur5.setImage(new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Constructeur.jpg")));
			}
		}
	}

	/*public void afficherTuile(Tuile t, int posX, int posY) {
		ArrayList listeMarqueurs = t.getMarqueursActuels();
//		
	    String caseAModifier = "Case_"+posX+"_"+posY;
	    
		if(t.estDesert()) {
			Image c = new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Dos_Tuiles.jpg"));
			//this.Case_1_1.setImage(c);
		} else {
			if(t.getIntituleDuChamps().equals("Champs de banane")) {
				if(listeMarqueurs.isEmpty()) {
					Image c = new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Bananes0.jpg"));
				} else if(listeMarqueurs.size() == 1) {
					Image c = new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Bananes1.jpg"));
				} else if(listeMarqueurs.size() == 1) {
					Image c = new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Bananes2.jpg"));
				}
			} else if(t.getIntituleDuChamps().equals("Champs de canne")) {
				
			} else if(t.getIntituleDuChamps().equals("Champs de haricot")) {
				
			} else if(t.getIntituleDuChamps().equals("Champs de piment")) {
				
			} else if(t.getIntituleDuChamps().equals("Champs de pomme de terre")) {
				
			}			
		}
		
		System.out.println("Afficher Tuile : " +t.getIntituleDuChamps());
		Image c = new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Bananes0.jpg"));
		System.out.println("Case 1:" +this.Case_1_1);
		System.out.println("Ici");
		System.out.println("Image " +c);
		//System.out.println("Case 2:" +this.Case_1_1.getId());
		this.Case_1_1.setImage(c);
		System.out.println("Ici 2");
		//System.out.println("Case 3:" +this.Case_1_1);
		
	}*/


	
	public void afficheTuilesRetournees(Partie p){
		
		
		
		ArrayList<Tuile> liste = p.getPlateau().getListeTuilesRetournees();
		if(p.getNombreJoueursRequis()==5){
			Image image1 = new Image(MainClientFxml.class.getResourceAsStream(liste.get(0).getPath()));
			if(liste.get(0).estDesert()){ this.pioche1.setDisable(true); }

			Image image2 = new Image(MainClientFxml.class.getResourceAsStream(liste.get(1).getPath()));
			if(liste.get(1).estDesert()){ this.pioche2.setDisable(true); }

			Image image3 = new Image(MainClientFxml.class.getResourceAsStream(liste.get(2).getPath()));
			if(liste.get(3).estDesert()){ this.pioche3.setDisable(true); }

			Image image4 = new Image(MainClientFxml.class.getResourceAsStream(liste.get(3).getPath()));
			if(liste.get(4).estDesert()){ this.pioche4.setDisable(true); }

			Image image5 = new Image(MainClientFxml.class.getResourceAsStream(liste.get(4).getPath()));
			if(liste.get(5).estDesert()){ this.pioche5.setDisable(true); }

			this.pioche1.setImage(image1);
			this.pioche2.setImage(image2);
			this.pioche3.setImage(image3);
			this.pioche4.setImage(image4);
			this.pioche5.setImage(image5);
			
		}
		else{
			try{
				System.out.println(liste.get(0).getPath());
				Image image1 = new Image(MainClientFxml.class.getResourceAsStream(liste.get(0).getPath()));
				if(liste.get(0).estDesert()){ this.pioche1.setDisable(true); }

				Image image2 = new Image(MainClientFxml.class.getResourceAsStream(liste.get(1).getPath()));
				if(liste.get(1).estDesert()){ this.pioche2.setDisable(true); }

				Image image3 = new Image(MainClientFxml.class.getResourceAsStream(liste.get(2).getPath()));
				if(liste.get(2).estDesert()){ this.pioche3.setDisable(true); }

				Image image4 = new Image(MainClientFxml.class.getResourceAsStream(liste.get(3).getPath()));
				if(liste.get(3).estDesert()){ this.pioche4.setDisable(true); }
				
				this.pioche1.setImage(image1);
				this.pioche2.setImage(image2);
				this.pioche3.setImage(image3);
				this.pioche4.setImage(image4);
			}
			catch(Exception e){
				e.printStackTrace();
			}

		}
	}
	
	@FXML
	public void clickTuile(MouseEvent e) throws RemoteException{
		Partie p = serveur.getPartieByName(namePartie);
		ImageView image =(ImageView)e.getSource();
		
				
		Joueur j = getClient(nomJoueur, p.getNomPartie()).getJoueur();
		if(j.getPseudo().equals(this.nomJoueur1.getText())){
			this.tuile1.setImage(image.getImage());
		}
		else if(j.getPseudo().equals(this.nomJoueur2.getText())){
			this.tuile2.setImage(image.getImage());
		}
		else if(j.getPseudo().equals(this.nomJoueur3.getText())){
			this.tuile3.setImage(image.getImage());
		}
		else if(j.getPseudo().equals(this.nomJoueur4.getText())){
			this.tuile4.setImage(image.getImage());	
		}
		else if(j.getPseudo().equals(this.nomJoueur5.getText())){
			this.tuile5.setImage(image.getImage());
		}


		this.pioche1.setDisable(true);
		this.pioche2.setDisable(true);
		this.pioche3.setDisable(true);
		this.pioche4.setDisable(true);
		this.pioche5.setDisable(true);
		
		selectionTuile(p,image);

	}
	
	public void selectionTuile(Partie p, ImageView image) throws RemoteException{
		if(p.getNombreJoueursRequis()==5){
			if(image == this.pioche1){
				serveur.ajouteTuileAJoueur(p.getNomPartie(),nomJoueur,p.getPlateau().getListeTuilesRetournees().get(0));
				serveur.ajouteTuileRetourneAIndex(p, new TuileRetournee(), 0);
			}
			else if(image == this.pioche2){
				serveur.ajouteTuileAJoueur(p.getNomPartie(),nomJoueur,p.getPlateau().getListeTuilesRetournees().get(1));
				serveur.ajouteTuileRetourneAIndex(p, new TuileRetournee(), 1);
			}
			else if(image == this.pioche3){
				serveur.ajouteTuileAJoueur(p.getNomPartie(),nomJoueur,p.getPlateau().getListeTuilesRetournees().get(2));
				serveur.ajouteTuileRetourneAIndex(p, new TuileRetournee(), 2);
			}
			else if(image == this.pioche4){
				serveur.ajouteTuileAJoueur(p.getNomPartie(),nomJoueur,p.getPlateau().getListeTuilesRetournees().get(3));
				serveur.ajouteTuileRetourneAIndex(p, new TuileRetournee(), 3);
			}
			else if(image == this.pioche5){
				serveur.ajouteTuileAJoueur(p.getNomPartie(),nomJoueur,p.getPlateau().getListeTuilesRetournees().get(4));
				serveur.ajouteTuileRetourneAIndex(p, new TuileRetournee(), 4);
			}
		}
		else{
			if(image == this.pioche1){
				System.out.println("TestTuile");
				serveur.ajouteTuileAJoueur(p.getNomPartie(),nomJoueur,p.getPlateau().getListeTuilesRetournees().get(0));
				serveur.ajouteTuileRetourneAIndex(p, new TuileRetournee(), 0);
			}
			else if(image == this.pioche2){
				serveur.ajouteTuileAJoueur(p.getNomPartie(),nomJoueur,p.getPlateau().getListeTuilesRetournees().get(1));
				serveur.ajouteTuileRetourneAIndex(p, new TuileRetournee(), 1);
			}
			else if(image == this.pioche3){
				serveur.ajouteTuileAJoueur(p.getNomPartie(),nomJoueur,p.getPlateau().getListeTuilesRetournees().get(2));
				serveur.ajouteTuileRetourneAIndex(p, new TuileRetournee(), 2);
			}
			else if(image == this.pioche4){
				serveur.ajouteTuileAJoueur(p.getNomPartie(),nomJoueur,p.getPlateau().getListeTuilesRetournees().get(3));
				serveur.ajouteTuileRetourneAIndex(p, new TuileRetournee(), 3);
			}

		}
		
		serveur.addNotificationAPArtie(namePartie, Static.modificationTuilesRetournees);
	}
	
	
	/**
	 * Fonction appelé lorsque l'utilisateur clique sur une case pour poser une tuile
	 * @throws RemoteException 
	 */
	public void selectionneCase(MouseEvent e) throws RemoteException{

		//On vérifie que le joueur à une tuile selectionnée
		Joueur j = getClient(nomJoueur, namePartie).getJoueur();
		
		if(j.getTuileSelectionee() == null){ return; }
		
		//On récupère l'imageview
		ImageView view = (ImageView) e.getSource();

		//On récupère l'id de l'image
		String id = view.getId();
		
		//On parse l'id pour obtenir les coordonées de la case
		String[] res = id.split("_");
		
		int x = Integer.parseInt(res[1]);
		int y = Integer.parseInt(res[2]);
		
		
		//On dit au server d'ajouter la tuile
        Tuile tuile = j.getTuileSelectionee();
        serveur.ajouteTuileAJoueur(namePartie, nomJoueur, null);
        
        //On prévient du changmenet
        serveur.poseTuileAvecXY(MainClientFxml.namePartie, tuile, x, y);
        
	}
	
	
	/**
	 * Fonction qui met à jour le plateau en fonction des tuiles
	 * @throws RemoteException 
	 */
	public void metAJourAffichageTuiles() throws RemoteException{
		Platform.runLater(() -> {
			//On récupère la case
	        Partie p = null;
			try {
				p = serveur.getPartieByName(MainClientFxml.namePartie);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(p == null){ return;}
			
	        Case[][] tabCases = p.getPlateau().getTabPlateau();
	        
	        //On parcours les cases
	        for(int y=0 ; y < tabCases.length ; y++){
	        	
	        	Case[] ligne = tabCases[y];
	        	
	        	for(int x=0 ; x < tabCases[y].length ; x++){
	        		
	        		Case c = ligne[x];
	        		
	        		Tuile tuile = c.getContientTuile();
	        	
	        		if(tuile != null){
	        			
	        			//On construit l'id de l'imageview que l'on veut
	        			String idCase = "#Case_"+x+"_"+y;
	        			
	        			//On met l'image view
	        			Scene scene = stage.getScene();
	        			ImageView view = (ImageView) scene.lookup(idCase);
	        			
	        			try{
	        				Image image = new Image(MainClientFxml.class.getResourceAsStream(tuile.getPath()));
	        				view.setImage(image);

	        			}catch(Exception e){
	        				e.printStackTrace();
	        			}
	        			
	        			
	        			//L'index du marqueurs
	        			int index = 0;
	        			
	        			//On s'occupe des marqueurs
	        			for(MarqueurRendement mr : tuile.getMarqueursActuels()){
	        				
	        				index++;
	        				
	        				//On construit l'id de l'imageview que l'on veut
	            			String idMarqueur = "#MarqueurC"+x+"_"+y+"_"+index;
	            			
	            			ImageView viewMarqueurs = (ImageView) scene.lookup(idMarqueur);
	            			
	            			try{
	            				Image image = new Image(MainClientFxml.class.getResourceAsStream( "../view/Images/single-cubeOrange.png" /*mr.getPath()*/));
	            				viewMarqueurs.setImage(image);

	            			}catch(Exception e){
	            				e.printStackTrace();
	            			}
	        			
	        			}
	        			
	        			
	        		}
	        		
	        	}        	
	        }
		});
	}
	

	public void afficherJoueurEnCours(Partie partie) {
		Platform.runLater(() -> {
			zoneTexte.setDisable(true);
			
			Joueur j = partie.getJoueurEnCours();
			Image image = new Image(MainClientFxml.class.getResourceAsStream("../view/Images/fleche.png"));
	
			tourJoueur1.setImage(null); tourJoueur2.setImage(null); tourJoueur3.setImage(null);
			tourJoueur4.setImage(null); tourJoueur5.setImage(null);
			
			if(j.getPseudo().equals(this.nomJoueur1.getText())) {
				tourJoueur1.setImage(image);
			} else if (j.getPseudo().equals(this.nomJoueur2.getText())) {
				tourJoueur2.setImage(image);
			} else if (j.getPseudo().equals(this.nomJoueur3.getText())) {
				tourJoueur3.setImage(image);
			} else if (j.getPseudo().equals(this.nomJoueur4.getText())) {
				tourJoueur4.setImage(image);
			} else if (j.getPseudo().equals(this.nomJoueur5.getText())) {
				tourJoueur5.setImage(image);
			}	
			
			if(!j.getPseudo().equals(nomJoueur)) {
				btnEnvoyer.setDisable(true);
				zoneSaisie.setDisable(true);
				
//				Case_0_0.setDisable(true); Case_0_1.setDisable(true); Case_0_2.setDisable(true); Case_0_3.setDisable(true); Case_0_4.setDisable(true); Case_0_5.setDisable(true); Case_1_0.setDisable(true); Case_1_1.setDisable(true); Case_1_2.setDisable(true); Case_1_3.setDisable(true);
//			    Case_1_4.setDisable(true); Case_1_5.setDisable(true); Case_2_0.setDisable(true); Case_2_1.setDisable(true); Case_2_2.setDisable(true); Case_2_3.setDisable(true); Case_2_4.setDisable(true); Case_2_5.setDisable(true); Case_3_0.setDisable(true); Case_3_1.setDisable(true); Case_3_2.setDisable(true); Case_3_3.setDisable(true);
//			    Case_3_4.setDisable(true); Case_3_5.setDisable(true); Case_4_0.setDisable(true); Case_4_1.setDisable(true); Case_4_2.setDisable(true); Case_4_3.setDisable(true); Case_4_4.setDisable(true); Case_4_5.setDisable(true); Case_5_0.setDisable(true); Case_5_1.setDisable(true); Case_5_2.setDisable(true);
//			    Case_5_3.setDisable(true); Case_5_4.setDisable(true); Case_5_5.setDisable(true); Case_6_0.setDisable(true); Case_6_1.setDisable(true); Case_6_2.setDisable(true); Case_6_3.setDisable(true); Case_6_4.setDisable(true); Case_6_5.setDisable(true); Case_7_0.setDisable(true);
//			    Case_7_1.setDisable(true); Case_7_2.setDisable(true); Case_7_3.setDisable(true); Case_7_4.setDisable(true); Case_7_5.setDisable(true);
//				
//			    Canal_0_3_v.setDisable(true); Canal_4_2_h.setDisable(true); Canal_6_6_v.setDisable(true); Canal_3_4_h.setDisable(true); Canal_4_2_v.setDisable(true); Canal_5_0_h.setDisable(true); Canal_8_1_v.setDisable(true); Canal_1_0_h.setDisable(true); Canal_2_6_v.setDisable(true);
//			    Canal_6_5_v.setDisable(true); Canal_2_6_h.setDisable(true); Canal_0_4_v.setDisable(true); Canal_8_2_h.setDisable(true); Canal_4_1_v.setDisable(true); Canal_8_2_v.setDisable(true); Canal_7_4_h.setDisable(true); Canal_2_5_v.setDisable(true); Canal_6_6_h.setDisable(true); Canal_4_4_h.setDisable(true);
//			    Canal_0_1_v.setDisable(true); Canal_3_6_h.setDisable(true); Canal_5_2_h.setDisable(true); Canal_4_4_v.setDisable(true); Canal_8_3_v.setDisable(true); Canal_8_4_v.setDisable(true); Canal_1_2_h.setDisable(true); Canal_2_0_h.setDisable(true); Canal_0_2_v.setDisable(true); Canal_8_4_h.setDisable(true);
//			    Canal_4_3_v.setDisable(true); Canal_6_0_h.setDisable(true); Canal_8_5_v.setDisable(true); Canal_7_6_h.setDisable(true); Canal_3_0_h.setDisable(true); Canal_4_6_v.setDisable(true); Canal_5_4_h.setDisable(true); Canal_4_6_h.setDisable(true); Canal_8_6_v.setDisable(true); Canal_2_2_v.setDisable(true);
//			    Canal_1_4_h.setDisable(true); Canal_6_1_v.setDisable(true); Canal_2_2_h.setDisable(true); Canal_8_6_h.setDisable(true); Canal_4_5_v.setDisable(true); Canal_6_2_h.setDisable(true); Canal_2_1_v.setDisable(true); Canal_7_0_h.setDisable(true); Canal_6_2_v.setDisable(true); Canal_4_0_h.setDisable(true);
//			    Canal_3_2_h.setDisable(true);
			} else {
				btnEnvoyer.setDisable(false);
				zoneSaisie.setDisable(false);
				
//				Case_0_0.setDisable(false); Case_0_1.setDisable(false); Case_0_2.setDisable(false); Case_0_3.setDisable(false); Case_0_4.setDisable(false); Case_0_5.setDisable(false); Case_1_0.setDisable(false); Case_1_1.setDisable(false); Case_1_2.setDisable(false); Case_1_3.setDisable(false);
//			    Case_1_4.setDisable(false); Case_1_5.setDisable(false); Case_2_0.setDisable(false); Case_2_1.setDisable(false); Case_2_2.setDisable(false); Case_2_3.setDisable(false); Case_2_4.setDisable(false); Case_2_5.setDisable(false); Case_3_0.setDisable(false); Case_3_1.setDisable(false); Case_3_2.setDisable(false); Case_3_3.setDisable(false);
//			    Case_3_4.setDisable(false); Case_3_5.setDisable(false); Case_4_0.setDisable(false); Case_4_1.setDisable(false); Case_4_2.setDisable(false); Case_4_3.setDisable(false); Case_4_4.setDisable(false); Case_4_5.setDisable(false); Case_5_0.setDisable(false); Case_5_1.setDisable(false); Case_5_2.setDisable(false);
//			    Case_5_3.setDisable(false); Case_5_4.setDisable(false); Case_5_5.setDisable(false); Case_6_0.setDisable(false); Case_6_1.setDisable(false); Case_6_2.setDisable(false); Case_6_3.setDisable(false); Case_6_4.setDisable(false); Case_6_5.setDisable(false); Case_7_0.setDisable(false);
//			    Case_7_1.setDisable(false); Case_7_2.setDisable(false); Case_7_3.setDisable(false); Case_7_4.setDisable(false); Case_7_5.setDisable(false);
//			
//			    Canal_0_3_v.setDisable(false); Canal_4_2_h.setDisable(false); Canal_6_6_v.setDisable(false); Canal_3_4_h.setDisable(false); Canal_4_2_v.setDisable(false); Canal_5_0_h.setDisable(false); Canal_8_1_v.setDisable(false); Canal_1_0_h.setDisable(false); Canal_2_6_v.setDisable(false);
//			    Canal_6_5_v.setDisable(false); Canal_2_6_h.setDisable(false); Canal_0_4_v.setDisable(false); Canal_8_2_h.setDisable(false); Canal_4_1_v.setDisable(false); Canal_8_2_v.setDisable(false); Canal_7_4_h.setDisable(false); Canal_2_5_v.setDisable(false); Canal_6_6_h.setDisable(false); Canal_4_4_h.setDisable(false);
//			    Canal_0_1_v.setDisable(false); Canal_3_6_h.setDisable(false); Canal_5_2_h.setDisable(false); Canal_4_4_v.setDisable(false); Canal_8_3_v.setDisable(false); Canal_8_4_v.setDisable(false); Canal_1_2_h.setDisable(false); Canal_2_0_h.setDisable(false); Canal_0_2_v.setDisable(false); Canal_8_4_h.setDisable(false);
//			    Canal_4_3_v.setDisable(false); Canal_6_0_h.setDisable(false); Canal_8_5_v.setDisable(false); Canal_7_6_h.setDisable(false); Canal_3_0_h.setDisable(false); Canal_4_6_v.setDisable(false); Canal_5_4_h.setDisable(false); Canal_4_6_h.setDisable(false); Canal_8_6_v.setDisable(false); Canal_2_2_v.setDisable(false);
//			    Canal_1_4_h.setDisable(false); Canal_6_1_v.setDisable(false); Canal_2_2_h.setDisable(false); Canal_8_6_h.setDisable(false); Canal_4_5_v.setDisable(false); Canal_6_2_h.setDisable(false); Canal_2_1_v.setDisable(false); Canal_7_0_h.setDisable(false); Canal_6_2_v.setDisable(false); Canal_4_0_h.setDisable(false);
//			    Canal_3_2_h.setDisable(false);
			}
		});

		
	}
	
	public void afficheJoueur(Partie p) throws RemoteException{
		Platform.runLater(() -> {
			System.out.println("coucou");
			switch(p.getNombreJoueurDansLaPartie()){
				case 3:
					try {
						this.nomJoueur1.setText(p.getJoueursConnectes().get(0).getPseudo());
						this.nomJoueur2.setText(p.getJoueursConnectes().get(1).getPseudo());
						this.nomJoueur3.setText(p.getJoueursConnectes().get(2).getPseudo());
						
						this.nomJoueur1.setFill(Color.web(p.getJoueursConnectes().get(0).getCodeCouleur()));
						this.nomJoueur2.setFill(Color.web(p.getJoueursConnectes().get(1).getCodeCouleur()));
						this.nomJoueur3.setFill(Color.web(p.getJoueursConnectes().get(2).getCodeCouleur()));
						
						this.soldeJoueur1.setText(""+p.getJoueursConnectes().get(0).getSolde());
						this.soldeJoueur2.setText(""+p.getJoueursConnectes().get(1).getSolde());
						this.soldeJoueur3.setText(""+p.getJoueursConnectes().get(2).getSolde());
						
						this.soldeJoueur1.setFill(Color.web(p.getJoueursConnectes().get(0).getCodeCouleur()));
						this.soldeJoueur2.setFill(Color.web(p.getJoueursConnectes().get(1).getCodeCouleur()));
						this.soldeJoueur3.setFill(Color.web(p.getJoueursConnectes().get(2).getCodeCouleur()));
						
						break;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				case 4:
					try {
						this.nomJoueur1.setText(p.getJoueursConnectes().get(0).getPseudo());
						this.nomJoueur2.setText(p.getJoueursConnectes().get(1).getPseudo());
						this.nomJoueur3.setText(p.getJoueursConnectes().get(2).getPseudo());
						this.nomJoueur4.setText(p.getJoueursConnectes().get(3).getPseudo());
						
						this.nomJoueur1.setFill(Color.web(p.getJoueursConnectes().get(0).getCodeCouleur()));
						this.nomJoueur2.setFill(Color.web(p.getJoueursConnectes().get(1).getCodeCouleur()));
						this.nomJoueur3.setFill(Color.web(p.getJoueursConnectes().get(2).getCodeCouleur()));
						this.nomJoueur4.setFill(Color.web(p.getJoueursConnectes().get(3).getCodeCouleur()));
						
						this.soldeJoueur1.setText(""+p.getJoueursConnectes().get(0).getSolde());
						this.soldeJoueur2.setText(""+p.getJoueursConnectes().get(1).getSolde());
						this.soldeJoueur3.setText(""+p.getJoueursConnectes().get(2).getSolde());
						this.soldeJoueur4.setText(""+p.getJoueursConnectes().get(3).getSolde());
						
						this.soldeJoueur1.setFill(Color.web(p.getJoueursConnectes().get(0).getCodeCouleur()));
						this.soldeJoueur2.setFill(Color.web(p.getJoueursConnectes().get(1).getCodeCouleur()));
						this.soldeJoueur3.setFill(Color.web(p.getJoueursConnectes().get(2).getCodeCouleur()));
						this.soldeJoueur4.setFill(Color.web(p.getJoueursConnectes().get(3).getCodeCouleur()));
						
						break;					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				case 5:
					try {
						this.nomJoueur1.setText(p.getJoueursConnectes().get(0).getPseudo());
						this.nomJoueur2.setText(p.getJoueursConnectes().get(1).getPseudo());
						this.nomJoueur3.setText(p.getJoueursConnectes().get(2).getPseudo());
						this.nomJoueur4.setText(p.getJoueursConnectes().get(3).getPseudo());
						this.nomJoueur5.setText(p.getJoueursConnectes().get(4).getPseudo());
						
						this.nomJoueur1.setFill(Color.web(p.getJoueursConnectes().get(0).getCodeCouleur()));
						this.nomJoueur2.setFill(Color.web(p.getJoueursConnectes().get(1).getCodeCouleur()));
						this.nomJoueur3.setFill(Color.web(p.getJoueursConnectes().get(2).getCodeCouleur()));
						this.nomJoueur4.setFill(Color.web(p.getJoueursConnectes().get(3).getCodeCouleur()));
						this.nomJoueur5.setFill(Color.web(p.getJoueursConnectes().get(4).getCodeCouleur()));
						
						this.soldeJoueur1.setText(""+p.getJoueursConnectes().get(0).getSolde());
						this.soldeJoueur2.setText(""+p.getJoueursConnectes().get(1).getSolde());
						this.soldeJoueur3.setText(""+p.getJoueursConnectes().get(2).getSolde());
						this.soldeJoueur4.setText(""+p.getJoueursConnectes().get(3).getSolde());
						this.soldeJoueur5.setText(""+p.getJoueursConnectes().get(4).getSolde());
						
						this.soldeJoueur1.setFill(Color.web(p.getJoueursConnectes().get(0).getCodeCouleur()));
						this.soldeJoueur2.setFill(Color.web(p.getJoueursConnectes().get(1).getCodeCouleur()));
						this.soldeJoueur3.setFill(Color.web(p.getJoueursConnectes().get(2).getCodeCouleur()));
						this.soldeJoueur4.setFill(Color.web(p.getJoueursConnectes().get(3).getCodeCouleur()));
						this.soldeJoueur5.setFill(Color.web(p.getJoueursConnectes().get(4).getCodeCouleur()));
						
						this.pioche5.setImage(new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Dos_Tuiles.jpg")));
						
						break;					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}			
		});
	}


	
	
	/**
	 * Fonction appelé lorsque l'utilisateur clique sur une case pour poser une tuile
	 * @throws RemoteException 
	 */
	public void selectionneCanal(MouseEvent e) throws RemoteException{
		
		System.out.println("Canal pressed ");
		//On récupère l'imageview
		ImageView view = (ImageView) e.getSource();
		
		//On récupère l'id de l'image
		String id = view.getId();
		
		//On parse l'id pour obtenir les coordonées de la case
		String[] res = id.split("_");
		
		int x = Integer.parseInt(res[1]);
		int y = Integer.parseInt(res[2]);
		String position = res[3];
		
		//On prend le début du canal 
		if(position.equals("h")){
			
			if(x%2 == 0){
				x--;
			}
			
		} else {
			
			if(y%2 == 0){
				y--;
			}
			
		}
		
		
		SantiagoInterface client = getClient(MainClientFxml.nomJoueur , MainClientFxml.namePartie);
        
        //On prévient du changmenet
        serveur.metCanalEnEauAvecXY(MainClientFxml.namePartie, x, y, position, client.getJoueur().getCouleur());
		

	}
	
	
	
	
	/**
	 * Fonction qui met à jour le plateau en fonction des canaux
	 * @throws RemoteException 
	 */
	public void metAJourAffichageCanaux() throws RemoteException{
		
		//On récupère la case
        Partie p = serveur.getPartieByName(MainClientFxml.namePartie);
        ArrayList<Canal> listeCanaux = p.getPlateau().getListeCanaux();
        
     
    	
    	
        //On parcours les cases
        for(Canal canal : listeCanaux){
        	
        	if(! canal.estEnEau()) { continue; }
        	
        	if(canal.canalEstVertical()){
            	
        		//Premiere partie du canal
                String id_1 = "#Canal_"+canal.getCoordFin().x+"_"+canal.getCoordFin().y+"_v";
                String id_2 = "#Canal_"+canal.getCoordFin().x+"_"+(canal.getCoordFin().y+1)+"_v";
                
                //On met l'image view
    			Scene scene = stage.getScene();
    			ImageView view = (ImageView) scene.lookup(id_1);
                
            	String couleur = canal.getCouleur();
            
            	try{
        		Image image = new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Canal_"+couleur+"_Vertical.jpg"));
        		view.setImage(image);
        		
            	//Deuxieme partie du canal
            	view = (ImageView) scene.lookup(id_2);
            	
            	couleur = canal.getCouleur();

            	image = new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Canal_"+couleur+"_Vertical.jpg"));
        		view.setImage(image);
            	}catch(Exception e){
            		e.printStackTrace();
            	}
        	
        	} else {
        		
            	try{
            		
	        		//Premiere partie du canal
	                String id_1="";
	                String id_2="";


                	id_1 = "#Canal_"+canal.getCoordFin().x+"_"+canal.getCoordFin().y+"_h";
		            id_2 = "#Canal_"+(canal.getCoordFin().x+1)+"_"+canal.getCoordFin().y+"_h";
			            
	                                
	                //On met l'image view
	    			Scene scene = stage.getScene();
	    			ImageView view = (ImageView) scene.lookup(id_1);
	                
	            	String couleur = canal.getCouleur();

            		Image image = new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Canal_"+couleur+"_Horizontal.jpg"));
	        		view.setImage(image);
	        		
	            	//Deuxieme partie du canal
	            	view = (ImageView) scene.lookup(id_2);
	            	
	            	couleur = canal.getCouleur();

	            	image = new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Canal_"+couleur+"_Horizontal.jpg"));
	        		view.setImage(image);
            
            	}catch(Exception e){
            		e.printStackTrace();
            	}
            	
        	}
        	
        	
        	
        }
        		
	}

	public void envoyerMessage(MouseEvent event) throws RemoteException {
		String cmd = zoneSaisie.getText();
		//client.recevoirCommande(cmd);
		
		try {
			File file = new File("messageEnvoye.txt"+nomJoueur);
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fwritter = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bwritter = new BufferedWriter(fwritter);
			bwritter.write(cmd);
			bwritter.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		zoneSaisie.clear();
	}	
	
	public void afficherTexte(Partie partie) throws RemoteException {
		Platform.runLater(() -> {
			String thisLine;
		     try {
		    	 String nomFichier = nomJoueur;
		    	 File file = new File(nomFichier);
		    	 
		    	 if (file.exists()) {
			         BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));         
			         
			         while ((thisLine = br.readLine()) != null) { 
				         System.out.println("Message du serveur :" +thisLine);
				         zoneTexte.appendText(thisLine);
				         String Newligne=System.getProperty("line.separator");
				         zoneTexte.appendText(Newligne);
				         //Puis on efface le contenu (A faire lors des tests non local)
				         
			         } // end while 
			         BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), false));
			         bw.write("");
			         bw.flush(); bw.close();
			         br.close(); 
		    	 }
		     } catch (IOException e) {
		    	   	System.out.println("Error: " + e);
		     }			
			
		});

	}
	
	/**
	 * Fonction qui actualise la liste des parties que l'utilisateur peut rejoindre
	 * @param args
	 * @throws RemoteException 
	 */
	public void actualiserLesParties(ActionEvent e) throws RemoteException {
		

		changeAccordion();
	}
	
	
	
	public static void main(String[] args) {
		Application.launch(MainClientFxml.class,args);
	}
}