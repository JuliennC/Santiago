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
	private Stage stage;
	
	/*-------------Début attribut creation -------------*/
	@FXML
	TextField nomPartie;
	
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
	
	/*-------------Début attribut ChoixPartie -------------*/
	
	@FXML
	Accordion accordion;
	
	@FXML
	Button valid;
	
	@FXML
	Text partieError;
	
	/*------------- Fin attribut ChoixPartie --------------*/
	
	
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
	
	/**
	 * Méthode qui appel la création d'une partie
	 * 
	 * @throws NumberFormatException
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws NotBoundException
	 * @throws PartieException
	 * @throws JoueurException
	 */
	public void createPartie() throws NumberFormatException, RemoteException, MalformedURLException, NotBoundException, PartieException, JoueurException{
		
		RadioButton radio = (RadioButton)this.nbJoueur.getSelectedToggle();
		
		Partie p = this.client.creerPartie(this.nomPartie.getText(),Integer.parseInt(radio.getText()));
		
		this.serveur.ajouterPartieListe(p);
		this.serveur.rejoindrePartie(this.nomPartie.getText(), client);
	}
	
	/**
	 * Cette méthode permet de choisir une partie à rejoindre
	 * *
	 * @param e
	 * @throws RemoteException
	 */
	public void joinPartie(ActionEvent e) throws RemoteException{
		Button b =(Button)e.getSource();
		this.stage = (Stage)b.getScene().getWindow();
		Stage primaryStage = new Stage();
		ObservableList<TitledPane> list = FXCollections.observableArrayList();
		for(Partie p : this.serveur.voirParties()){
			GridPane grid = new GridPane();
			grid.addRow(0,new Text("Nombre de Joueur Requis: "+p.getNombreJoueursRequis()));
			grid.addRow(1,new Text("Nombre de Joueur dans la Partie: "+p.getNombreJoueurDansLaPartie()));
			TitledPane pane = new TitledPane(p.getNomPartie(),grid);
			list.add(pane);
		}
		
		
        this.accordion = new Accordion();
        this.accordion.setPrefSize(570, 200);
        this.accordion.getPanes().addAll(list);
        this.accordion.setExpandedPane(this.accordion.getPanes().get(0));
        Text t = new Text("Choix de la partie:");
        this.valid = new Button("Valider");
        this.partieError = new Text();
         
        final Pane pane1 = new Pane(t);
        pane1.setPrefSize(400,100);
		final ScrollPane pane2 = new ScrollPane(this.accordion);
		pane2.setPrefSize(800,175);
		final Pane pane3 = new Pane(this.partieError);
		pane3.setPrefSize(400,50);
		final Pane pane4 = new Pane(this.valid);
		pane4.setPrefSize(400,50);
		
        final GridPane root = new GridPane(); 
        root.addRow(0,pane1);
        root.addRow(1,pane2); 
        root.addRow(2,pane3);
        root.addRow(3,pane4);
        t.setFont(new Font(24));
        t.setX(210);
        t.setY(50);
        this.valid.setFont(new Font(24));
        this.valid.setLayoutX(240);
        this.valid.setOnAction(this::rejoindrePartie);
        final Scene scene = new Scene(root, 600, 400); 
        primaryStage.setTitle("Choix de la partie"); 
        primaryStage.setScene(scene); 
        primaryStage.show(); 
	}
	
	/**
	 * Cette methode permet de rejoindre la partie sectionnée.
	 * 
	 * @param e
	 */
	public void rejoindrePartie(ActionEvent e){
		TitledPane pane = this.accordion.getExpandedPane();
		String nomPartie = pane.getText();
		try{
			this.serveur.rejoindrePartie(nomPartie, this.client);
		}
        catch(RemoteException | PartieException | JoueurException e1){
        	this.partieError.setFill(Color.RED);
        	this.partieError.setText("Vous ne pouvez pas rejoindre cette partie");
        }
		try {
			Stage stage = (Stage)this.valid.getScene().getWindow();
			stage.close();
			salleDAttente(this.stage,this.serveur.getPartieByName(nomPartie));
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public void salleDAttente(Stage primaryStage,Partie p) throws IOException{
		URL url = getClass().getResource("../view/ChoixPartie.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        BorderPane root = (BorderPane) fxmlLoader.load();
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Santiago");
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(MainClientFxml.class,args);
	}
}
