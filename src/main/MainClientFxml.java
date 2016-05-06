package main;

import java.io.File;
import java.io.FileNotFoundException;
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
import Classes.Marqueurs.MarqueurGris;
import Classes.Marqueurs.MarqueurRendement;
import Classes.Plateau.Canal;
import Classes.Plateau.Case;
import Classes.Plateau.Plateau;
import Classes.Tuile.Tuile;
import Classes.Tuile.TuilePiment;
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

	private static SantiagoInterface client;
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
	Button createButton, loadButton, joinButton;
	
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
		
		
		ArrayList<Partie> partiesNonTerminees = serveur.getPartiePourJoueur(client.getJoueur().getPseudo());
System.out.println("non term : "+partiesNonTerminees);
		if( (partiesNonTerminees.size() > 0) && (serveur.joueurParticipeAUnePartie(client.getJoueur(), partiesNonTerminees)) ){
			
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
								

								if(modifs.size() == 0){ /*System.out.println("\nRien à modifier");*/ continue; }

								Integer modif = modifs.get(0);
								System.out.println("Modif : "+modif);

								if(modif.equals(Static.modificationJoueurs)){

									//System.out.println("---- joueur");
									//System.out.println("Nouveau joueur : "+p.getJoueursConnectes());
								
							        MainClientFxml.controller.changeText(partie);
									MainClientFxml.controller.lancementPlateau();
									modifs.remove(0);

							
								} else if(modif.equals(Static.modificationPartieCommence)){
							
									MainClientFxml.controller.lancementPlateau();
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
									
									MainClientFxml.controller.modifierConstructeur();
									modifs.remove(0);
									
								} else if(modif.equals(Static.modificationCannaux)){

									System.out.println("Modification des canaux");
									MainClientFxml.controller.metAJourAffichageCanaux();
									modifs.remove(0);
									
								} else {
									System.out.println("Modif non reconnue");
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
		String mdp = this.motDePasse.getText();
		
		//On vérifie que le pseudo est disponible
		boolean pseudoEstDispo = serveur.pseudoEstDisponible(pseudo);
		
		
		if(!pseudo.isEmpty()&&pseudoEstDispo  || (serveur.connexionPseudoEtMDP(pseudo, mdp))){
			Joueur joueur = new Joueur(pseudo,10);
			client = new Santiago("client");
			client.setJoueur(joueur);

			serveur.enregistrePseudoEtMDP(pseudo, mdp);
			//parcoursListePartie();
			startChoixPartie((Stage)this.valider.getScene().getWindow());
		}
		else{
			this.pseudoError.setText("Pseudo invalide \nou mot de passe incorrect.");
		}
	}
	
	public void changeAccordion() throws RemoteException{
		ObservableList<TitledPane> list = FXCollections.observableArrayList();
		
		for(Partie p : serveur.voirParties()){
			boolean b = false;
			if(p.getListeDesJoueur() != null){
				for(Joueur j : p.getListeDesJoueur()){
					if(j.getPseudo().equals(client.getJoueur().getPseudo())){
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
		Partie p = client.charger(this.nomPartieChargement.getText());
		if(p == null){
			this.errorNomChargement.setText("La partie n'éxiste pas");
		}
		else if(p.partiEstTerminee()){
			lancementScore(p);
		}
		else{
			boolean b = false;
			ArrayList<Joueur> listeJoueur = p.getListeDesJoueur();
			for(Joueur j : listeJoueur){
				if(j.getPseudo().equals(client.getJoueur().getPseudo())){
					b = true;
					break;
				}
			}
			if(b){
				p.setPartieACommence(false);
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
	
	
	
	public void skipPartiesEnCours(){
		
		
	}
	
	
	public void joinPartieSalleDAttente(ActionEvent e, TitledPane pane) throws InterruptedException{
		
		String nomPartie = pane.getText();
		Partie pRejoint;
		try{
			pRejoint = serveur.rejoindrePartie(nomPartie, client);
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
	 * @throws RemoteException 
	 */
	public void changeText(Partie p) throws RemoteException{
		int nbJoueurRequis = p.getNombreJoueursRequis();
		int nbJoueurDansPartie = p.getNombreJoueurDansLaPartie();
		


		
		switch(nbJoueurRequis){
			case 3:

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
			case 4:
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
			case 5:
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
        MainClientFxml.controller = (MainClientFxml)fxmlLoader.getController();
        controller.changeText(partie);
        final Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Santiago");
        stage.show();
        
        chercheInfoPartie(partie);

        
	}
	
	public void lancementPlateau() throws IOException{
		System.out.println("Lanchement Plateau");
		
		final URL url = getClass().getResource("../view/Plateau.fxml");

        final FXMLLoader fxmlLoader = new FXMLLoader(url);
        
        final BorderPane root = (BorderPane) fxmlLoader.load();
        MainClientFxml.controller = (MainClientFxml)fxmlLoader.getController();

        stage.getScene().setRoot(root);
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
	
	public void modifierConstructeur() throws IOException{
		
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
	
	
	
	
	
	/**
	 * Fonction appelé lorsque l'utilisateur clique sur une case pour poser une tuile
	 * @throws RemoteException 
	 */
	public void selectionneCase(MouseEvent e) throws RemoteException{

		//On récupère l'imageview
		ImageView view = (ImageView) e.getSource();
		
		//On récupère l'id de l'image
		String id = view.getId();
		
		//On parse l'id pour obtenir les coordonées de la case
		String[] res = id.split("_");
		
		int x = Integer.parseInt(res[1]);
		int y = Integer.parseInt(res[2]);
		
		
		//On dit au server d'ajouter la tuile
        TuilePiment tuile = new TuilePiment(1);
        MarqueurGris m = new MarqueurGris();
        
        tuile.addMarqueur(m);
        
        //On prévient du changmenet
        serveur.poseTuileAvecXY(name, tuile, x, y);
        
	}
	
	
	/**
	 * Fonction qui met à jour le plateau en fonction des tuiles
	 * @throws RemoteException 
	 */
	public void metAJourAffichageTuiles() throws RemoteException{
		
		//On récupère la case
        Partie p = serveur.getPartieByName(name);
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
        
        //On prévient du changmenet
        serveur.metCanalEnEauAvecXY(name, x, y, position);
		

	}
	
	
	
	
	/**
	 * Fonction qui met à jour le plateau en fonction des canaux
	 * @throws RemoteException 
	 */
	public void metAJourAffichageCanaux() throws RemoteException{
		
		//On récupère la case
        Partie p = serveur.getPartieByName(name);
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
                
            	String couleur = client.getJoueur().getCouleur();
            	couleur = "Jaune";
            
            	try{
        		Image image = new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Canal_"+couleur+"_Vertical.jpg"));
        		view.setImage(image);
        		
            	//Deuxieme partie du canal
            	view = (ImageView) scene.lookup(id_2);
            	
            	couleur = client.getJoueur().getCouleur();
            	couleur = "Jaune";

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
	                
	            	String couleur = client.getJoueur().getCouleur();
	            	couleur = "Jaune";

            		Image image = new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Canal_"+couleur+"_Horizontal.jpg"));
	        		view.setImage(image);
	        		
	            	//Deuxieme partie du canal
	            	view = (ImageView) scene.lookup(id_2);
	            	
	            	couleur = client.getJoueur().getCouleur();
	            	couleur = "Jaune";

	            	image = new Image(MainClientFxml.class.getResourceAsStream("../view/Images/Canal_"+couleur+"_Horizontal.jpg"));
	        		view.setImage(image);
            
            	}catch(Exception e){
            		e.printStackTrace();
            	}
            	
        	}
        	
        	
        	
        }
        		
	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Application.launch(MainClientFxml.class,args);
	}
}