package main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import Classes.Joueur;
import Classes.Partie;
import Exception.JoueurException;
import Exception.PartieException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
	
	
	@Override

	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			this.serveur = (SantiagoInterface)Naming.lookup("rmi://127.0.0.1:43000/ABC");
		} 
		catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
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
        primaryStage.setScene(scene);
        primaryStage.setTitle("Santiago");
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
        primaryStage.setScene(scene);
        primaryStage.setTitle("Santiago");
        primaryStage.show();
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
		boolean pseudoEstDispo = this.serveur.pseudoEstDisponible(pseudo);
		
		if(!pseudo.isEmpty()&&pseudoEstDispo){
			Joueur joueur = new Joueur(pseudo,10);
			this.client = new Santiago(joueur);
			this.serveur.addPseudo(pseudo);
			//parcoursListePartie();
			startChoixPartie((Stage)this.valider.getScene().getWindow());
		}
		else{
			this.pseudoError.setText("Pseudo invalide");
		}
	}
	
	public void changeAccordion() throws RemoteException{
		ObservableList<TitledPane> list = FXCollections.observableArrayList();
		for(Partie p : this.serveur.voirParties()){
			GridPane grid = new GridPane();
			grid.addRow(0,new Text("Nombre de Joueur Requis: "+p.getNombreJoueursRequis()));
			grid.addRow(1,new Text("Nombre de Joueur dans la Partie: "+p.getNombreJoueurDansLaPartie()));
			TitledPane pane = new TitledPane(p.getNomPartie(),grid);
			list.add(pane);
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
		
		Partie p = this.client.creerPartie(this.nomPartie.getText(),Integer.parseInt(radio.getText()));
		
		this.serveur.ajouterPartieListe(p);
		this.serveur.rejoindrePartie(this.nomPartie.getText(), client);
		salleDAttente((Stage)b.getScene().getWindow(),this.serveur.getPartieByName(this.nomPartie.getText()));
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
		String nomPartie = pane.getText();
		try{
			this.serveur.rejoindrePartie(nomPartie, this.client);
		}
        catch(RemoteException | PartieException | JoueurException e1){
        	this.partieError.setText("Vous ne pouvez pas rejoindre cette partie");
        }
		try {
			Button b = (Button) e.getSource();
			salleDAttente((Stage)b.getScene().getWindow(),this.serveur.getPartieByName(nomPartie));
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
	public void salleDAttente(Stage primaryStage,Partie p) throws IOException, InterruptedException{
		
		URL url = getClass().getResource("../view/SalleDAttente.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        
        BorderPane root = (BorderPane) fxmlLoader.load();
        MainClientFxml controller = (MainClientFxml)fxmlLoader.getController();
        controller.changeText(p);
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Santiago");
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(MainClientFxml.class,args);
	}
}