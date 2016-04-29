package main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import Classes.Joueur;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import network.Santiago;
import network.SantiagoInterface;

public class MainClientFxml extends Application implements Initializable{

	private static Joueur joueur;
	
	
	/*-------------Début attribut ChoixPartie -------------*/
	@FXML
	TextField nomPartie;
	
	@FXML
	RadioButton radio3, radio4, radio5;
	
	@FXML
	Accordion listePartie;
	
	@FXML
	TitledPane titleEmpty;
	
	@FXML
	Button createButton, loadButton, joinButton;
	/*------------- Fin attribut ChoixPartie -------------*/
	
	
	
	/*------------- Début attribut Accueil ---------------*/
	@FXML
	TextField pseudo;
	
	@FXML
	Text pseudoError;
	
	@FXML
	Button valider;
	/*--------------- Fin attribut Accueil ---------------*/
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
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

	public void startChoixPartie(Stage primaryStage) throws IOException{
		final URL url = getClass().getResource("../view/ChoixPartie.fxml");
        final FXMLLoader fxmlLoader = new FXMLLoader(url);
        final BorderPane root = (BorderPane) fxmlLoader.load();
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Santiago");
        primaryStage.show();
	}
	
	public void validerPseudo(ActionEvent e) throws NotBoundException, IOException{
		/*System.out.println("1");
		SantiagoInterface serveur =	(SantiagoInterface)Naming.lookup("rmi://127.0.0.1:43000/ABC");
		*/
		String pseudo = this.pseudo.getText();
		
		//On vérifie que le pseudo est disponible
		//boolean pseudoEstDispo = serveur.pseudoEstDisponible(pseudo);
		
		if(!pseudo.isEmpty()){
			this.joueur = new Joueur(pseudo,10);
			SantiagoInterface client = new Santiago(joueur);
			startChoixPartie((Stage)this.valider.getScene().getWindow());
		}
		else{
			this.pseudoError.setText("Pseudo invalide");
		}
	}
	
	
	public static void main(String[] args) {
		Application.launch(MainClientFxml.class,args);
	}
}
